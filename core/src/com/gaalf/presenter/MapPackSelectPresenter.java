package com.gaalf.presenter;

import com.badlogic.gdx.files.FileHandle;
import com.gaalf.GaalfGame;
import com.gaalf.view.BaseMenuView;
import com.gaalf.view.MapPackSelectView;

public class MapPackSelectPresenter extends BaseMenuPresenter{
    private BaseMenuView view;

    MapPackSelectPresenter(final GaalfGame game){
        super(game);
        view = new MapPackSelectView(game.getBatch(), this, game.levelManager.getMapPacks());
    }

    public void openMainMenuView() {
        game.playersManager.removePlayer(game.devicePlayer);
        game.setScreen(new MainMenuPresenter(game));
    }

    @Override
    public BaseMenuView getView() {
        return view;
    }

    public void selectMapPack(FileHandle mapPack){
        game.levelManager.setLevels(mapPack.name());
        game.setScreen(new LevelSelectMenuPresenter(game));
    }
}
