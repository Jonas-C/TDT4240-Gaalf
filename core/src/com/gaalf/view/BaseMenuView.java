package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.gaalf.GaalfGame;
import com.gaalf.presenter.BaseMenuPresenter;


public abstract class BaseMenuView extends BaseView {

    Table table;

    final float BUTTON_WIDTH = getViewport().getScreenWidth() / 5;
    final float TITLE_BTM_PADDING = 20; // BTM is BOTTOM if you didn't catch that
    final float BUTTON_BTM_PADDING = 15;
    final float BACK_BUTTON_TOP_PADDING = 30;

    BaseMenuView(SpriteBatch batch, BaseMenuPresenter presenter){
        super(batch, presenter);
        table = new Table();
        table.top();
        table.padTop(GaalfGame.V_HEIGHT / 12);
        table.setFillParent(true);
    }

    private Table getTable(){
        return table;
    }

    void addTitle(String title) {
        Label titleLabel = new Label(title, getSkin(), "title");
        getTable().add(titleLabel).padBottom(TITLE_BTM_PADDING);
    }

    TextButton addMenuButton(String text) {
        table.row();
        TextButton menuButton = new TextButton(text, getSkin());
        table.add(menuButton)
                .width(BUTTON_WIDTH)
                .padBottom(BUTTON_BTM_PADDING);

        return menuButton;
    }

    public void drawBackground(Sprite background, Array<Sprite> clouds){
        getBatch().begin();
        getBatch().draw(background, 0, 0, GaalfGame.V_WIDTH, GaalfGame.V_HEIGHT);
        for(Sprite cloud : clouds){
            getBatch().draw(cloud, cloud.getX(), cloud.getY());
        }
        getBatch().end();
    }

    TextButton addBackButton() {
        getTable().row();
        TextButton backButton = new TextButton("Back", getSkin());

        getTable().add(backButton)
                .width(BUTTON_WIDTH)
                .padTop(BACK_BUTTON_TOP_PADDING)
                .padBottom(BUTTON_BTM_PADDING);

        return backButton;
    }
}
