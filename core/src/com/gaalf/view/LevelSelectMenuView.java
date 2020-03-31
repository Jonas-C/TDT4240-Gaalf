package com.gaalf.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.presenter.LevelSelectMenuPresenter;

public class LevelSelectMenuView extends BaseMenuView {

    private final String TAG = LevelSelectMenuView.class.getSimpleName();

    public LevelSelectMenuView(SpriteBatch batch, final LevelSelectMenuPresenter presenter) {
        super(batch, presenter);

        addTitle("Select level");

        addLevelSelectButtons(presenter);

        TextButton backButton = addBackButton();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.openMainMenuView();
            }
        });

        addActor(table);
    }

    public void addLevelSelectButtons(final LevelSelectMenuPresenter presenter) {
        FileHandle[] fileHandles = Gdx.files.internal("levels").list(".tmx");
        for(final FileHandle fileHandle : fileHandles) {
            TextButton selectLevelButton = addMenuButton(fileHandle.name().split("\\.")[0]);
            selectLevelButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    presenter.startLevel(fileHandle.path());
                }
            });

            // ADD SELECT LEVEL LOGIC
        }
    }


}
