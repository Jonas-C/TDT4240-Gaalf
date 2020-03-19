package com.gaalf.view;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.presenter.SettingsPresenter;


public class SettingsView extends BaseMenuView {

    private static final String TAG = SettingsView.class.getSimpleName();

    public SettingsView(SpriteBatch batch, final SettingsPresenter presenter){
        super(batch, presenter);

        Label titleLabel = new Label("Settings", getSkin(), "title");
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
                .padBottom(BUTTON_BTM_PADDING)
                .padTop(BACK_BUTTON_TOP_PADDING);

        addActor(table);
    }



    @Override
    public void update(float delta) {

    }
}
