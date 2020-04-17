package com.gaalf.presenter;

import com.gaalf.GaalfGame;
import com.gaalf.view.BaseMenuView;
import com.gaalf.view.MainMenuView;

public class MainMenuPresenter extends BaseMenuPresenter {

    private BaseMenuView view;

    public MainMenuPresenter(final GaalfGame game){
        super(game);

        view = new MainMenuView(game.getBatch(), this);
    }

    public void startTestLevel(){
        menuMusic.dispose();

//      game.setScreen(new GamePresenter(game, game.levelManager.getLevels().get(0) ));
        game.playersManager.addPlayer(game.settingsManager.displayName, true,game.settingsManager.getBallChoice());
        game.playersManager.addPlayer("Jonas", true, game.settingsManager.getBallChoice());
        game.devicePlayer = game.playersManager.getPlayers().get(0);
        game.setScreen(new GamePresenter(game, game.levelManager.getRandomLevel()));
        System.out.println("name:" + game.settingsManager.displayName);
    }

    public void openLevelSelectMenu() {
        game.playersManager.addPlayer(game.settingsManager.getDisplayName(), true, game.settingsManager.getBallChoice());
//        game.playersManager.addPlayer("E", false, "Square");
        game.devicePlayer = game.playersManager.getPlayers().get(0);
        game.setScreen(new MapPackSelectPresenter(game));
    }

    public void openServersView() {
        game.setScreen(new ServersPresenter(game));
    }

    public void openSettingsView() {
        game.setScreen(new SettingsPresenter(game));
    }

    @Override
    public BaseMenuView getView() {
        return view;
    }
}
