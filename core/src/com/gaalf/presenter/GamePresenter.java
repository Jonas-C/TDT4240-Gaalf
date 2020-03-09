package com.gaalf.presenter;

import com.gaalf.GaalfGame;
import com.gaalf.view.BaseGameView;
import com.gaalf.view.BaseView;
import com.gaalf.view.GameView;

public class GamePresenter extends BaseGamePresenter {

    private final GaalfGame game;
    private BaseView view;

    public GamePresenter(final GaalfGame game){
        super();
        this.game = game;
        view = new GameView(game.getBatch(), this);

    }


    @Override
    public BaseGameView getView() {
        return null;
    }
}
