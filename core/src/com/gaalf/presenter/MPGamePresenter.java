package com.gaalf.presenter;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.gaalf.GaalfGame;
import com.gaalf.game.ecs.component.BodyComponent;
import com.gaalf.game.ecs.component.PlayerComponent;
import com.gaalf.game.enums.GameEvent;
import com.gaalf.network.IMultiplayerGameListener;
import com.gaalf.network.MultiplayerGameClient;

import java.util.AbstractMap;
import java.util.Map;

public class MPGamePresenter extends BaseGamePresenter implements IMultiplayerGameListener {

    private MultiplayerGameClient mpgc;
    public MPGamePresenter(final GaalfGame game, FileHandle level, MultiplayerGameClient mpgc){
        super(game, level);
        this.mpgc = mpgc;
        mpgc.setMpGameListener(this);
        addListener(shootableSystem);
        for(int i = 0; i < game.playersManager.getPlayers().size(); i++){
            System.out.println("Player " + i);
            System.out.println(game.playersManager.getPlayers().get(i).getPlayerName());
            System.out.println(game.playersManager.getPlayers().get(i).getPlayerID());
            System.out.println(game.playersManager.getPlayers().get(i).isThisDevice());
            System.out.println(game.playersManager.getPlayers().get(i).getBallChoice());

        }

    }

    @Override
    public void ballHit(int playerId, Vector2 velocity) {
        System.out.println(velocity);
        AbstractMap.SimpleEntry<Integer, Vector2> shot = new AbstractMap.SimpleEntry<>(playerId, velocity);
        notifyObservers(GameEvent.BALL_STROKE, shot);
    }

    @Override
    public void onReceiveEvent(GameEvent event, Object object) {
        switch(event){
            case SCORE_CHANGED:
                setScoreLabel(playerInfo.getPlayerID(), playerInfo.getPlayerName() + ": " + object);
                break;
            case LEVEL_COMPLETE:
                levelCleared();
                break;
            case BALL_STROKE:
                mpgc.sendBallHit((Vector2)object);
            default:
                break;
        }
    }

    @Override
    public void playerQuit(int playerId) {
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
    public void levelCleared() {

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

    }
}
