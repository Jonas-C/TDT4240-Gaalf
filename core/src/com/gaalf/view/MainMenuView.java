package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.presenter.MainMenuPresenter;

public class MainMenuView extends BaseMenuView {

    private final String TAG = MainMenuView.class.getSimpleName();

    public MainMenuView(SpriteBatch batch, final MainMenuPresenter presenter){
        super(batch, presenter);

        // Create and add title
        Label titleLabel = new Label("GALF", getSkin(), "title");
        getTable().add(titleLabel).padBottom(TITLE_BTM_PADDING);

        // Create and add start single-player game button
        getTable().row();   // Add new row
        TextButton startSpGameButton = new TextButton("Start single-player game", getSkin());
        startSpGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.openLevelSelectMenu();
            }
        });
        getTable().add(startSpGameButton).width(BUTTON_WIDTH).padBottom(BUTTON_BTM_PADDING);

        // Create and add start multiplayer game button
        getTable().row();   // Add new row
        TextButton startMpGameButton = new TextButton("Start multiplayer game", getSkin());
        getTable().add(startMpGameButton).width(BUTTON_WIDTH).padBottom(BUTTON_BTM_PADDING);

        // Create and add settings button
        getTable().row();   // Add new row
        TextButton settingsButton = new TextButton("Settings", getSkin());
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                log("Opening settings", TAG);
                presenter.openSettingsView();
            }
        });
        getTable().add(settingsButton).width(BUTTON_WIDTH).padBottom(BUTTON_BTM_PADDING);

        addActor(table);
    }

    @Override
    public void update(float delta) {

    }
}
