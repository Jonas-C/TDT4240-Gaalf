package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.presenter.LevelSelectMenuPresenter;

public class LevelSelectMenuView extends BaseMenuView {

    private final String TAG = MainMenuView.class.getSimpleName();

    public LevelSelectMenuView(SpriteBatch batch, final LevelSelectMenuPresenter presenter) {
        super(batch, presenter);

        addTitle("Select level");

        TextButton backButton = addBackButton();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.openMainMenuView();
            }
        });

        addActor(table);
    }
}
