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

        addLevelSelectButtons();

        TextButton backButton = addBackButton();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.openMainMenuView();
            }
        });

        addActor(table);
    }

    public void addLevelSelectButtons() {
        FileHandle[] fileHandles = Gdx.files.internal("levels").list(".tmx");
        for(FileHandle fileHandle : fileHandles) {
            TextButton selectLevelButton = addMenuButton(fileHandle.name().split("\\.")[0]);

            // ADD SELECT LEVEL LOGIC
        }
    }


}
