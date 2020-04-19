package com.gaalf.presenter;


import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.gaalf.GaalfGame;
import com.gaalf.game.events.BallStrokeEventArgs;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.PlayerComponent;
import com.gaalf.game.enums.GameEvent;
import com.gaalf.model.PlayerInfo;
import com.gaalf.network.IMultiplayerGameListener;
import com.gaalf.network.MultiplayerGameClient;
import com.gaalf.view.BaseGameView;
import com.gaalf.view.MPGameView;

public class MPGamePresenter extends BaseGamePresenter implements IMultiplayerGameListener {

    private MultiplayerGameClient mpgc;
    private boolean shouldGoNext;
    private boolean levelWon;
    private MPGameView view;
    MPGamePresenter(final GaalfGame game, FileHandle level, MultiplayerGameClient mpgc){
        super(game, level);
        view = new MPGameView(game.getBatch(), this);
        setupMultiplexer();
        this.mpgc = mpgc;
        shouldGoNext = false;
        levelWon = false;
        mpgc.setMpGameListener(this);
        addListener(shootableSystem);
        view.addScoreLabel(playerInfo.getPlayerID(), playerInfo.getPlayerName());
        for(PlayerInfo playerInfo : game.playersManager.getPlayers()){
            view.addPlayer(playerInfo, game.levelManager.getLevelInt(), game.levelManager.getLevels().size());
        }
    }

    @Override
    public void ballHit(int playerId, Vector2 startPosition, Vector2 velocity) {
        notifyObservers(GameEvent.BALL_STROKE, new BallStrokeEventArgs(playerId, startPosition, velocity));
    }

    @Override
    public void goNextLevel() {
        getView().clearWindow();
        shouldGoNext = true;
    }

    @Override
    public void onReceiveEvent(GameEvent event, Object object) {
        switch(event){
            case SCORE_CHANGED:
                PlayerComponent playerComponent = (PlayerComponent) object;
                if(playerComponent.onThisDevice){
                    view.setPlayerLabelText(playerInfo.getPlayerID(), playerInfo.getPlayerName() + ": " + (playerComponent.playerScore));
                }
                view.updateScoreboard(playerComponent.playerNumber, game.levelManager.getLevelInt(), playerComponent.playerScore, playerComponent.playerTotalScore);
                break;
            case LEVEL_COMPLETE:
                levelCleared();
                break;
            case BALL_STROKE:
                BallStrokeEventArgs ballStroke = (BallStrokeEventArgs) object;
                mpgc.sendBallHit(ballStroke.startPosition, ballStroke.velocity);
                break;
            default:
                break;
        }
    }

    @Override
    public void playerQuit(int playerId) {
        game.playersManager.removePlayer(playerId);
        view.removePlayer(playerId);
        notifyObservers(GameEvent.PLAYER_LEFT, playerId);
        ImmutableArray<Entity> balls = engine.getEntitiesFor(Family.all(PlayerComponent.class).get());
        for(Entity ball : balls){
            PlayerComponent playerComponent = ball.getComponent(PlayerComponent.class);
            if(playerComponent.playerNumber == playerId){
                BodyComponent bodyComponent = ball.getComponent(BodyComponent.class);
                world.destroyBody(bodyComponent.body);
                engine.removeEntity(ball);
            }
        }
    }

    @Override
    public void gameQuit() {

    }

    @Override
    public void update(float delta){
        super.update(delta);
        if(shouldGoNext){
            newLevel(game.levelManager.nextLevel());
            shouldGoNext = false;
            levelWon = false;
        }
    }

    @Override
    public BaseGameView getView() {
        return view;
    }

    @Override
    public void levelCleared() {
        getView().levelCleared(game.levelManager.hasNext());
    }

    @Override
    public void exitMainMenu(){
        mpgc.leaveGame();
        mpgc.close();
        gameMusic.dispose();
        game.playersManager.getPlayers().clear();
        game.setScreen(new MainMenuPresenter(game));
    }

    @Override
    public void nextLevel() {
        getView().clearWindow();
        mpgc.nextLevel();
        shouldGoNext = true;
        levelWon = false;
    }

    @Override
    public void levelWon() {
        if(!levelWon){
            levelWon = true;
            levelCleared();
        }
    }

    @Override
    public void ballReset(int playerId) {

    }
}
