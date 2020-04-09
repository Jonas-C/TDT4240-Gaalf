package com.gaalf.presenter;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.gaalf.GaalfGame;
import com.gaalf.network.IMultiplayerGameListener;

public class MPGamePresenter extends BaseGamePresenter implements IMultiplayerGameListener {

    public MPGamePresenter(final GaalfGame game, FileHandle level){
        super(game, level);
    }
    @Override
    public void ballHit(int playerId, Vector2 velocity) {

    }

    @Override
    public void gamePlayerQuit(int playerId) {

    }

    @Override
    public void gameQuit() {

    }

    @Override
    public void levelCleared() {

    }

    @Override
    public void nextLevel() {

    }
}
