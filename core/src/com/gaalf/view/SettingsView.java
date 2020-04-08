package com.gaalf.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gaalf.presenter.SettingsPresenter;

public class SettingsView extends BaseMenuView {
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;
    private Label usernameLabel;

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

        final TextField username = new TextField(null, getSkin());
        username.setMessageText("Username");
        //username.setPosition(30,30);
        final TextButton changeUsernameBtn = new TextButton("Ok", getSkin());
        changeUsernameBtn.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                presenter.setUsername(username.getText());
                return false;
            }
        });


        //TODO: Gjøre teksten på knappene større
        volumeMusicLabel = new Label( "Music volume", getSkin());
        musicOnOffLabel = new Label( "Music", getSkin() );
        volumeSoundLabel = new Label( "Sound effects volume", getSkin() );
        soundOnOffLabel = new Label( "Sound effects", getSkin() );
        usernameLabel = new Label ("Display name", getSkin());

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
        table.add(username);
        table.row();
        table.add(changeUsernameBtn);

        String test = username.getText();
        System.out.println(test);

        table.add();

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
