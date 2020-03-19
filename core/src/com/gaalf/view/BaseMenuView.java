package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gaalf.presenter.BaseMenuPresenter;

abstract class BaseMenuView extends BaseView {

    Table table;


    BaseMenuView(SpriteBatch batch, BaseMenuPresenter presenter){
        super(batch, presenter);
        table = new Table();
        table.top();
        table.setFillParent(true);
    }

    Table getTable(){
        return table;
    }


}
