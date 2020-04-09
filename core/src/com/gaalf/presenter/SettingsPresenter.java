package com.gaalf.presenter;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.gaalf.GaalfGame;
import com.gaalf.view.BaseView;
import com.gaalf.view.SettingsView;

public class SettingsPresenter extends BaseMenuPresenter {

    private SettingsView view;
    private Array<String> ballNames;
    private int currentBallIndex;

    SettingsPresenter(final GaalfGame game){
        super(game);
        ballNames = new Array<>();
        TextureAtlas textureAtlas = game.assetManager.manager.get(game.assetManager.ballSpriteAtlas);
        Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();
        for(TextureAtlas.AtlasRegion region : regions){
            ballNames.add(region.name);
        }
        view = new SettingsView(game.getBatch(), this);
        currentBallIndex = ballNames.indexOf(game.settingsManager.getBallChoice(), false);
        view.setBallChoiceLabel(game.settingsManager.getBallChoice());
    }

    public void openMainMenuView() {
        game.settingsManager.setBallChoice(ballNames.get(currentBallIndex));
        game.setScreen(new MainMenuPresenter(game));
    }

    @Override
    public BaseView getView() {
        return view;
    }

    public boolean isMusicEnabled() {
        return game.settingsManager.musicIsEnabled;
    }

    public void setMusicEnabled(boolean musicEnabled) {
        if (musicEnabled){
            menuMusic.play();
        }
        else{
            menuMusic.pause();
        }
        game.settingsManager.setMusicEnabled(musicEnabled);
    }
    public float getMusicVolume(){
        return game.settingsManager.musicVolume;
    }

    public void setMusicVolume(float volume){
        menuMusic.setVolume(volume);
        game.settingsManager.setMusicVolume(volume);
    }


    public float getSoundVolume(){
        return game.settingsManager.soundVolume;
    }

    public void setSoundVolume(float volume){
        game.settingsManager.setSoundVolume(volume);
    }

    public boolean isSoundEffectsEnabled(){

        return game.settingsManager.soundIsEnabled;
    }
    public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
        game.settingsManager.setSoundEffectsEnabled(soundEffectsEnabled);
    }

    public String getUsername(){
        return game.settingsManager.displayName;
    }

    public void setUsername(String usernameSet){
        game.settingsManager.setUsername(usernameSet);
    }


    public void handleBallChange(String button){
        if(button.equals("left_arrow")){
            if(currentBallIndex <= 0){
                currentBallIndex = ballNames.size - 1;
            } else {
                currentBallIndex--;
            }
        } else{
            if(currentBallIndex >= ballNames.size -1) {
                currentBallIndex = 0;
            } else {
                currentBallIndex++;
            }
        }
        view.setBallChoiceLabel(ballNames.get(currentBallIndex));
    }
}
