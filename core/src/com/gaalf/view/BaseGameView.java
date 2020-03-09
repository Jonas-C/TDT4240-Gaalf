package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gaalf.presenter.BaseGamePresenter;

public abstract class BaseGameView extends BaseView {

    public BaseGameView(SpriteBatch batch, BaseGamePresenter presenter){
        super(batch, presenter);
    }
}
