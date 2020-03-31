package com.gaalf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gaalf.manager.GameAssetManager;
import com.gaalf.manager.SettingsManager;
import com.gaalf.presenter.MainMenuPresenter;

public class GaalfGame extends Game {
	SpriteBatch batch;
	public GameAssetManager assetManager = new GameAssetManager();
	public SettingsManager settingsManager;


	public static int V_WIDTH = 1280;
	public static int V_HEIGHT = 720;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager.loadMusic();
		assetManager.loadSound();
		assetManager.manager.finishLoading();
		settingsManager = new SettingsManager();
		setScreen(new MainMenuPresenter(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0,0, 0);
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	public SpriteBatch getBatch(){
		return batch;
	}
}
