package com.gaalf.presenter;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.gaalf.GaalfGame;
import com.gaalf.view.BaseMenuView;
import com.gaalf.view.SettingsView;

public class SettingsPresenter extends BaseMenuPresenter {

    private SettingsView view;
    private Array<String> ballNames;
    private Array<String> shotIndicatorNames;
    private int currentBallIndex;
    private int currentShotIndicatorIndex;

    SettingsPresenter(final GaalfGame game){
        super(game);
        ballNames = new Array<>();
        shotIndicatorNames = new Array<>();
        TextureAtlas ballTextureAtlas = game.assetManager.manager.get(game.assetManager.ballSpriteAtlas);
        Array<TextureAtlas.AtlasRegion> ballRegions = ballTextureAtlas.getRegions();
        for(TextureAtlas.AtlasRegion region : ballRegions){
            ballNames.add(region.name);
        }
        TextureAtlas sITextureAtlas = game.assetManager.manager.get(game.assetManager.shotIndicatorSpriteAtlas);
        Array<TextureAtlas.AtlasRegion> sIRegions = sITextureAtlas.getRegions();
        for (TextureAtlas.AtlasRegion region : sIRegions) {
            shotIndicatorNames.add(region.name);
        }

        view = new SettingsView(game.getBatch(), this);
        currentBallIndex = ballNames.indexOf(game.settingsManager.getBallChoice(), false);
        currentShotIndicatorIndex = shotIndicatorNames.indexOf(
                game.settingsManager.getShotIndicatorChoice(),
                false);
        view.setBallChoiceLabel(game.settingsManager.getBallChoice());
        view.setSIChoiceLabel(game.settingsManager.getShotIndicatorChoice());
    }

    public void openMainMenuView() {
        game.settingsManager.setBallChoice(ballNames.get(currentBallIndex));
        game.settingsManager.setShotIndicatorChoice(shotIndicatorNames.get(currentShotIndicatorIndex));
        game.setScreen(new MainMenuPresenter(game));
    }

    @Override
    public BaseMenuView getView() {
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

    public String getDisplayName(){
        return game.settingsManager.displayName;
    }

    public void setDisplayName(String displayName){
        System.out.println(displayName);
        game.settingsManager.setDisplayName(displayName);
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

    public void handleShotIndicatorChange(String button) {
        if(button.equals("left_arrow")){
            if(currentShotIndicatorIndex <= 0){
                currentShotIndicatorIndex = shotIndicatorNames.size - 1;
            } else {
                currentShotIndicatorIndex--;
            }
        } else{
            if(currentShotIndicatorIndex >= shotIndicatorNames.size -1) {
                currentShotIndicatorIndex = 0;
            } else {
                currentShotIndicatorIndex++;
            }
        }
        view.setSIChoiceLabel(shotIndicatorNames.get(currentShotIndicatorIndex));
    }
}
