package com.gaalf.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gaalf.presenter.SettingsPresenter;

public class SettingsView extends BaseMenuView {
    private final Label ballChoiceLabel;
    private final Label sIChoiceLabel;
    SettingsPresenter presenter;

    public SettingsView(SpriteBatch batch, final SettingsPresenter presenter){
        super(batch, presenter);
        this.presenter = presenter;
        Label.LabelStyle labelStyle = new Label.LabelStyle(getSkin().get(TextButton.TextButtonStyle.class).font, Color.FOREST);

        addTitle("Settings");
        Table settingsTable = new Table();

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

        final TextField displayNameField = new TextField(presenter.getDisplayName(), getSkin());
        displayNameField.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input(String text) {
                        presenter.setDisplayName(text);
                        displayNameField.setText(text);
                    }

                    @Override
                    public void canceled() {

                    }
                }, "Set display name", presenter.getDisplayName(), "");
            }
        });
        final TextButton displayNameButton = new TextButton("Ok", getSkin());
        displayNameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.setDisplayName(displayNameField.getText());
            }
        });



        Label volumeMusicLabel = new Label("Music volume", labelStyle);
        Label musicOnOffLabel = new Label("Music", labelStyle);
        Label volumeSoundLabel = new Label("SFX volume", labelStyle);
        Label soundOnOffLabel = new Label("Sound effects", labelStyle);

        Label ballLabel = new Label("Choose ball", labelStyle);
        ballChoiceLabel = new Label("", labelStyle);
        TextButton ballLeftArrowButton = new TextButton("<", getSkin());
        ballLeftArrowButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.handleBallChange("left_arrow");
            }
        });
        TextButton ballRightArrowButton = new TextButton(">", getSkin());
        ballRightArrowButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.handleBallChange("right_arrow");
            }
        });

        Label sILabel = new Label("Choose shot indicator", labelStyle);
        sIChoiceLabel = new Label("", labelStyle);
        TextButton sILeftArrowButton = new TextButton("<", getSkin());
        sILeftArrowButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.handleShotIndicatorChange("left_arrow");
            }
        });
        TextButton sIrightArrowButton = new TextButton(">", getSkin());
        sIrightArrowButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.handleShotIndicatorChange("right_arrow");
            }
        });

        Label displayNameLabel = new Label("Choose Display Name", labelStyle);

        settingsTable.row().padBottom(20);
        settingsTable.add(volumeMusicLabel).expandX().fill().left().padRight(10);
        settingsTable.add(volumeMusicSlider).left();
        settingsTable.row().padBottom(20);
        settingsTable.add(musicOnOffLabel).expandX().fill().left();
        settingsTable.add(musicCheckbox).left();
        settingsTable.row().padBottom(20);
        settingsTable.add(volumeSoundLabel).expandX().fill().left();
        settingsTable.add(volumeSoundSlider).left();
        settingsTable.row().padBottom(20);
        settingsTable.add(soundOnOffLabel).expandX().fill().left();
        settingsTable.add(soundCheckbox).left();
        settingsTable.row().padBottom(10);

        settingsTable.add(ballLabel).colspan(3).center();
        settingsTable.row().padBottom(20);
        Table ballSettingsTable = new Table();
        ballSettingsTable.add(ballLeftArrowButton).left();
        ballSettingsTable.add(ballChoiceLabel).center().expand();
        ballSettingsTable.add(ballRightArrowButton).right();
        settingsTable.add(ballSettingsTable).colspan(3).expand().fill();
        settingsTable.row().padTop(10);

        settingsTable.add(sILabel).colspan(3).center();
        settingsTable.row().padBottom(20);
        Table shotIndicatorSettingsTable = new Table();
        shotIndicatorSettingsTable.add(sILeftArrowButton).left();
        shotIndicatorSettingsTable.add(sIChoiceLabel).center().expand();
        shotIndicatorSettingsTable.add(sIrightArrowButton).right();
        settingsTable.add(shotIndicatorSettingsTable).colspan(3).expand().fill();
        settingsTable.row().padTop(10);

        settingsTable.add(displayNameLabel).colspan(2).center();
        settingsTable.row().padTop(10);
        settingsTable.add(displayNameField).right();
        settingsTable.add(displayNameButton).center();

        table.row();

        table.add(settingsTable);
        table.row();
        TextButton backButton = addBackButton();
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                presenter.openMainMenuView();
            }
        });
        table.row();
        addActor(table);
    }


    @Override
    public void update(float delta) {

    }

    public void setBallChoiceLabel(String ballChoice){
        ballChoiceLabel.setText(ballChoice);
    }

    public void setSIChoiceLabel(String shotIndicatorChoice) {
        sIChoiceLabel.setText(shotIndicatorChoice);
    }
}
