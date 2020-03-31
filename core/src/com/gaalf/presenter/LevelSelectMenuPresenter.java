package com.gaalf.presenter;

import com.gaalf.GaalfGame;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.view.BaseView;
import com.gaalf.view.LevelSelectMenuView;

public class LevelSelectMenuPresenter extends BaseMenuPresenter {

    private BaseView view;

    public LevelSelectMenuPresenter(final GaalfGame game, GameAssetManager assetManager) {
        super(game, assetManager);
        view = new LevelSelectMenuView(game.getBatch(), this);
    }

    public void openMainMenuView() {
        game.setScreen(new MainMenuPresenter(game, assetManager));
    }

    @Override
    public BaseView getView() {
        return view;
    }
}
