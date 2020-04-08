package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.presenter.MainMenuPresenter;

public class MainMenuView extends BaseMenuView {

    private final String TAG = MainMenuView.class.getSimpleName();

    public MainMenuView(SpriteBatch batch, final MainMenuPresenter presenter){
        super(batch, presenter);

        addTitle("GALF");

        TextButton startTestLevelButton = addMenuButton("Start test level");
        startTestLevelButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.startTestLevel();
            }
        });

        TextButton startSpGameButton = addMenuButton("Start single-player game");
        startSpGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.openLevelSelectMenu();
            }
        });

        TextButton startMpGameButton = addMenuButton("Start multiplayer game");
        startMpGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.openServerSelectMenu();
            }
        });

        TextButton settingsButton = addMenuButton("Settings");
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                log("Opening settings", TAG);
                presenter.openSettingsView();
            }
        });

        addActor(table);
    }

    @Override
    public void update(float delta) {

    }
}
