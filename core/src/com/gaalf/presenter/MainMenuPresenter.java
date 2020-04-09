package com.gaalf.presenter;

import com.gaalf.GaalfGame;
import com.gaalf.view.MainMenuView;
import com.gaalf.view.BaseView;

public class MainMenuPresenter extends BaseMenuPresenter {

    private BaseView view;

    public MainMenuPresenter(final GaalfGame game){
        super(game);

        view = new MainMenuView(game.getBatch(), this);
    }

    public void startTestLevel(){
        menuMusic.dispose();

//      game.setScreen(new GamePresenter(game, game.levelManager.getLevels().get(0) ));
        game.playersManager.addPlayer(game.settingsManager.displayName, true);
        game.playersManager.addPlayer("Jonas", true, game.settingsManager.getBallChoice());
        game.devicePlayer = game.playersManager.getPlayers().get(0);
        game.setScreen(new GamePresenter(game, game.levelManager.getRandomLevel()));
        System.out.println("name:" + game.settingsManager.displayName);
    }

    public void openLevelSelectMenu() {
        game.playersManager.addPlayer("Jonas", true, game.settingsManager.getBallChoice());
        game.playersManager.addPlayer("E", false, "Square");
        game.devicePlayer = game.playersManager.getPlayers().get(0);
        game.setScreen(new MapPackSelectPresenter(game));
    }

    public void openSettingsView() {
        game.setScreen(new SettingsPresenter(game));
    }

    @Override
    public BaseView getView() {
        return view;
    }
}
