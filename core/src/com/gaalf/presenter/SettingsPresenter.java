package com.gaalf.presenter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.gaalf.GaalfGame;
import com.gaalf.view.BaseView;
import com.gaalf.view.SettingsView;

public class SettingsPresenter extends BaseMenuPresenter {

    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREFS_NAME = "b2dtut";


    protected Preferences getPreferences() {
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public boolean isSoundEffectsEnabled(){
        return getPreferences().getBoolean(PREF_SOUND_ENABLED, true);
    }
    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPreferences().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPreferences().flush();
    }

    public boolean isMusicEnabled() {
        return getPreferences().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        // if musicEnabled menuMusi.play(); else pause();
        getPreferences().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPreferences().flush();
    }
    public float getMusicVolume(){
        return getPreferences().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume){
        menuMusic.setVolume(volume);
        getPreferences().putFloat(PREF_MUSIC_VOLUME, volume);
        getPreferences().flush(); //written to disk and saved (forhåpentligvis)
    }

    public float getSoundVolume(){
        return getPreferences().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume){
        getPreferences().getFloat(PREF_SOUND_VOL, volume);
        getPreferences().flush();
    }

    private BaseView view;

    public SettingsPresenter(final GaalfGame game){
        super(game);
        view = new SettingsView(game.getBatch(), this);
    }

    public void openMainMenuView() {
        game.setScreen(new MainMenuPresenter(game));
    }


    @Override
    public BaseView getView() {
        return view;
    }
}
