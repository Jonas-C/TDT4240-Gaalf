package com.gaalf.presenter;

import com.badlogic.gdx.files.FileHandle;
import com.gaalf.GaalfGame;
import com.gaalf.view.BaseView;
import com.gaalf.view.MapPackSelectView;

public class MapPackSelectPresenter extends BaseMenuPresenter{
    private BaseView view;

    MapPackSelectPresenter(final GaalfGame game){
        super(game);
        view = new MapPackSelectView(game.getBatch(), this, game.levelManager.getMapPacks());
    }

    public void openMainMenuView() {
        game.playersManager.removePlayer(game.devicePlayer);
        game.setScreen(new MainMenuPresenter(game));
    }

    @Override
    public BaseView getView() {
        return view;
    }

    public void selectMapPack(FileHandle mapPack){
        menuMusic.dispose();
//        game.levelManager
        game.levelManager.setLevels(mapPack.name());
        game.setScreen(new LevelSelectMenuPresenter(game));
    }
}
