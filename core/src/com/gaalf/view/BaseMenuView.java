package com.gaalf.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gaalf.presenter.BaseMenuPresenter;

public abstract class BaseMenuView extends BaseView {

    Table table;
    private Skin skin = new Skin(Gdx.files.internal("skin/flat-earth-ui.json"));

    public BaseMenuView(SpriteBatch batch, BaseMenuPresenter presenter){
        super(batch, presenter);
        table = new Table();
        table.top();
        table.setFillParent(true);
    }

    public Table getTable(){
        return table;
    }

    public Skin getSkin(){
        return skin;
    }


}
