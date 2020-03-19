package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gaalf.presenter.BaseMenuPresenter;

abstract class BaseMenuView extends BaseView {

    Table table;

    public final float BUTTON_WIDTH = getViewport().getScreenWidth() / 5;
    public final float TABLE_PADDING = 20;
    public final float TITLE_BTM_PADDING = 50; // BTM is BOTTOM if you didn't catch that
    public final float BUTTON_BTM_PADDING = 15;
    public final float BACK_BUTTON_TOP_PADDING = 30;

    BaseMenuView(SpriteBatch batch, BaseMenuPresenter presenter){
        super(batch, presenter);
        table = new Table();
        table.top();
        table.pad(TABLE_PADDING);
        table.setFillParent(true);
    }

    Table getTable(){
        return table;
    }


}
