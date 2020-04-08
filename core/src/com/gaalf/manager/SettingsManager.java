package com.gaalf.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SettingsManager {

    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREF_BALL = "ball";
    private static final String PREFS_NAME = "Settings.preferences";

    public float musicVolume;
    public boolean musicIsEnabled;
    public float soundVolume;
    public boolean soundIsEnabled;
    public String ballChoice;

    public SettingsManager(){
        musicVolume = getMusicVolume();
        musicIsEnabled = isMusicEnabled();
        soundVolume = getSoundVolume();
        soundIsEnabled = isSoundEffectsEnabled();
        ballChoice = getBallChoice();
    }

    private Preferences getPreferences(){
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    public String getBallChoice(){
        return getPreferences().getString(PREF_BALL, "Golfball");
    }

    public void setBallChoice(String ballChoice){
        if(!ballChoice.equals(getBallChoice())) {
            getPreferences().putString(PREF_BALL, ballChoice);
            getPreferences().flush();
        }
    }

    public float getMusicVolume(){
        return getPreferences().getFloat(PREF_MUSIC_VOLUME, 0.5f);
    }

    public void setMusicVolume(float volume){
        getPreferences().putFloat(PREF_MUSIC_VOLUME, volume);
        getPreferences().flush(); //written to disk and saved (forhåpentligvis)
        musicVolume = volume;
    }

    public boolean isMusicEnabled() {
        return getPreferences().getBoolean(PREF_MUSIC_ENABLED, true);
    }

    public void setMusicEnabled(boolean musicEnabled) {
        getPreferences().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
        getPreferences().flush();
        musicIsEnabled = musicEnabled;
    }

    public float getSoundVolume(){
        return getPreferences().getFloat(PREF_SOUND_VOL, 0.5f);
    }

    public void setSoundVolume(float volume){
        getPreferences().getFloat(PREF_SOUND_VOL, volume);
        getPreferences().flush();
        soundVolume = volume;
    }

    public boolean isSoundEffectsEnabled(){
        return getPreferences().getBoolean(PREF_SOUND_ENABLED, true);
    }

    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        getPreferences().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
        getPreferences().flush();
        soundIsEnabled = soundEffectsEnabled;
    }
}
