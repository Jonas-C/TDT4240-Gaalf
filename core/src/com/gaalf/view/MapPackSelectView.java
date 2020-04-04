package com.gaalf.view;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.presenter.MapPackSelectPresenter;

import java.util.ArrayList;

public class MapPackSelectView extends BaseMenuView {
    public MapPackSelectView(SpriteBatch batch, final MapPackSelectPresenter presenter, ArrayList<FileHandle> mapPacks) {
        super(batch, presenter);

        addTitle("Select level");

        addMapPackSelectButtons(presenter, mapPacks);

        TextButton backButton = addBackButton();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.openMainMenuView();
            }
        });

        addActor(table);
    }

    private void addMapPackSelectButtons(final MapPackSelectPresenter presenter, ArrayList<FileHandle> mapPacks) {
        for(final FileHandle mapPack : mapPacks) {
            TextButton selectLevelButton = addMenuButton(mapPack.name().split("\\.")[0]);
            selectLevelButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    presenter.selectMapPack(mapPack);
                }
            });
        }
    }
}
