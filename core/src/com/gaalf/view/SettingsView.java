package com.gaalf.view;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.presenter.SettingsPresenter;


public class SettingsView extends BaseMenuView {

    private static final String TAG = SettingsView.class.getSimpleName();

    public SettingsView(SpriteBatch batch, final SettingsPresenter presenter){
        super(batch, presenter);

        addTitle("Settings");

        TextButton backButton = addBackButton();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.openMainMenuView();
            }
        });

        addActor(table);
    }



    @Override
    public void update(float delta) {

    }
}
