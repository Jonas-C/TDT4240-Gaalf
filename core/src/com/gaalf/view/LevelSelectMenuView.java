package com.gaalf.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.presenter.LevelSelectMenuPresenter;

import java.util.ArrayList;

public class LevelSelectMenuView extends BaseMenuView {

    private final String TAG = LevelSelectMenuView.class.getSimpleName();

    public LevelSelectMenuView(SpriteBatch batch, final LevelSelectMenuPresenter presenter, ArrayList<FileHandle> levels) {
        super(batch, presenter);

        addTitle("Select level");

        addLevelSelectButtons(presenter, levels);

        TextButton backButton = addBackButton();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.openMapSelectView();
            }
        });

        addActor(table);
    }

    public void addLevelSelectButtons(final LevelSelectMenuPresenter presenter, ArrayList<FileHandle> levels) {
        for(final FileHandle level : levels) {
            TextButton selectLevelButton = addMenuButton(level.name().split("\\.")[0]);
            selectLevelButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    presenter.selectLevel(level);
                }
            });

            // ADD SELECT LEVEL LOGIC
        }
    }


}
