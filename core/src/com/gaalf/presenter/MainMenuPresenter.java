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

        game.playersManager.addPlayer("Jonas", true, game.settingsManager.getBallChoice(), game.settingsManager.getShotIndicatorChoice());
        game.devicePlayer = game.playersManager.getPlayers().get(0);
        game.setScreen(new GamePresenter(game, game.levelManager.getRandomLevel()));
    }

    public void openLevelSelectMenu() {
        game.playersManager.addPlayer("Jonas", true, game.settingsManager.getBallChoice(), game.settingsManager.getShotIndicatorChoice());
        game.playersManager.addPlayer("E", false, "Square", "Filled white shot indicator");
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
