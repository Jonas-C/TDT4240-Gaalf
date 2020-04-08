package com.gaalf.presenter;

import com.badlogic.gdx.files.FileHandle;
import com.gaalf.GaalfGame;
import com.gaalf.view.BaseView;
import com.gaalf.view.LevelSelectMenuView;

public class LevelSelectMenuPresenter extends BaseMenuPresenter {

    private BaseView view;

    LevelSelectMenuPresenter(final GaalfGame game) {
        super(game);
        view = new LevelSelectMenuView(game.getBatch(), this, game.levelManager.getLevels());
    }

    public void openMapSelectView() {
        game.setScreen(new MapPackSelectPresenter(game));
    }

    @Override
    public BaseView getView() {
        return view;
    }

    public void selectLevel(FileHandle level){
        menuMusic.dispose();
        game.setScreen(new GamePresenter(game, level));
    }
}
