package com.gaalf.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SettingsManager {

    private Preferences preferences;

    private static final String PREF_MUSIC_VOLUME = "volume";
    private static final String PREF_MUSIC_ENABLED = "music.enabled";
    private static final String PREF_SOUND_ENABLED = "sound.enabled";
    private static final String PREF_SOUND_VOL = "sound";
    private static final String PREF_BALL = "ball";
    private static final String PREF_SHOT_INDICATOR = "shot indicator";
    private static final String PREFS_NAME = "Settings.preferences";
    private static final String PREF_DISPLAY_NAME = "displayName";

    public float musicVolume;
    public boolean musicIsEnabled;
    public float soundVolume;
    public boolean soundIsEnabled;
    public String displayName;
    public String ballChoice;
    public String shotIndicatorChoice;

    public SettingsManager(){
        musicVolume = getMusicVolume();
        musicIsEnabled = isMusicEnabled();
        soundVolume = getSoundVolume();
        soundIsEnabled = isSoundEffectsEnabled();
        displayName = getDisplayName();
        ballChoice = getBallChoice();
        shotIndicatorChoice = getShotIndicatorChoice();
    }

    private Preferences getPreferences(){
        if(preferences == null){
            preferences = Gdx.app.getPreferences(PREFS_NAME);
            preferences.flush();
        }
        return preferences;
    }

    public String getShotIndicatorChoice() {
        return getPreferences().getString(PREF_SHOT_INDICATOR, "White SI");
    }

    public void setShotIndicatorChoice(String newChoice) {
        if(!newChoice.equals(getBallChoice())) {
            getPreferences().putString(PREF_SHOT_INDICATOR, newChoice);
            getPreferences().flush();
        }
    }

    public String getBallChoice(){
        return getPreferences().getString(PREF_BALL, "Golf ball");
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
        getPreferences().flush(); //written to disk and saved
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

    public String getDisplayName(){
        return getPreferences().getString(PREF_DISPLAY_NAME);
    }

    public void setDisplayName(String displayName){
        getPreferences().putString(PREF_DISPLAY_NAME, displayName);
        getPreferences().flush();
        this.displayName = displayName;
    }
}
