package com.gaalf.view;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gaalf.GaalfGame;
import com.gaalf.presenter.BaseGamePresenter;

public abstract class BaseGameView extends BaseView implements Screen {

    Table table;

    BaseGameView(SpriteBatch batch, BaseGamePresenter presenter){
        super(batch, presenter);
        table = new Table();
        table.top();
        table.setFillParent(true);
    }

    Table getTable(){
        return table;
    }
}
