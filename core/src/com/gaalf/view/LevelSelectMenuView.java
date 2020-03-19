package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.presenter.LevelSelectMenuPresenter;

public class LevelSelectMenuView extends BaseMenuView {

    private final String TAG = MainMenuView.class.getSimpleName();

    public LevelSelectMenuView(SpriteBatch batch, final LevelSelectMenuPresenter presenter) {
        super(batch, presenter);

        Label titleLabel = new Label("Select level", getSkin(), "title");
        getTable().add(titleLabel).padBottom(TITLE_BTM_PADDING);

        getTable().row();
        TextButton backButton = new TextButton("Back", getSkin());
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.openMainMenuView();
            }
        });
        getTable().add(backButton)
                .width(BUTTON_WIDTH)
                .padTop(BACK_BUTTON_TOP_PADDING)
                .padBottom(BUTTON_BTM_PADDING);


        addActor(table);
    }
}
