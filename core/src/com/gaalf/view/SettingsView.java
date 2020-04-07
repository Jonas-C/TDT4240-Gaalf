package com.gaalf.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.gaalf.presenter.SettingsPresenter;

public class SettingsView extends BaseMenuView {
    private Label ballChoiceLabel;

    public SettingsView(SpriteBatch batch, final SettingsPresenter presenter){
        super(batch, presenter);

        addTitle("Settings");

        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, getSkin());
        volumeMusicSlider.setValue(presenter.getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                presenter.setMusicVolume(volumeMusicSlider.getValue());

                return false;
            }
        });

        final Slider volumeSoundSlider = new Slider(0f, 1f, 0.1f, false, getSkin());
        volumeSoundSlider.setValue(presenter.getSoundVolume());
        volumeSoundSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                presenter.setSoundVolume(volumeSoundSlider.getValue());
                return false;
            }
        });

        final CheckBox musicCheckbox = new CheckBox(null, getSkin());
        musicCheckbox.setChecked(presenter.isMusicEnabled());
        musicCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                presenter.setMusicEnabled(enabled);
                return false;
            }
        });

        final CheckBox soundCheckbox = new CheckBox(null, getSkin());
        soundCheckbox.setChecked(presenter.isSoundEffectsEnabled());
        soundCheckbox.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckbox.isChecked();
                presenter.setSoundEffectsEnabled(enabled);
                return false;
            }
        });

        Label volumeMusicLabel = new Label("Music volume", getSkin());
        Label musicOnOffLabel = new Label("Music", getSkin());
        Label volumeSoundLabel = new Label("Sound effects volume", getSkin());
        Label soundOnOffLabel = new Label("Sound effects", getSkin());
        Label ballLabel = new Label("Chosen ball", getSkin());
        ballChoiceLabel = new Label("", getSkin());
        TextButton leftArrowButton = new TextButton("<", getSkin());
        leftArrowButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.handleBallChange("left_arrow");
            }
        });
        TextButton rightArrowButton = new TextButton(">", getSkin());
        rightArrowButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.handleBallChange("right_arrow");
            }
        });

        table.row();
        table.add(volumeMusicLabel);
        table.add(volumeMusicSlider);
        table.row();
        table.add(musicOnOffLabel);
        table.add(musicCheckbox);
        table.row();
        table.add(volumeSoundLabel);
        table.add(volumeSoundSlider);
        table.row();
        table.add(soundOnOffLabel);
        table.add(soundCheckbox);
        table.row();
        table.add(ballLabel);
        table.row();
        table.add(leftArrowButton);
        table.add(ballChoiceLabel);
        table.add(rightArrowButton);


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

    public void setBallChoiceLabel(String ballChoice){
        ballChoiceLabel.setText(ballChoice);
    }
}
