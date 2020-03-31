package com.gaalf.presenter;

import com.gaalf.GaalfGame;
import com.gaalf.manager.GameAssetManager;

public abstract class BaseMenuPresenter extends BasePresenter {
    BaseMenuPresenter(GaalfGame game, GameAssetManager assetManager) {
        super(game, assetManager);
    }
}
