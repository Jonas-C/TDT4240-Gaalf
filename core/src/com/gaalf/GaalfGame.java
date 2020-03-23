package com.gaalf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gaalf.presenter.MainMenuPresenter;

public class GaalfGame extends Game {
	SpriteBatch batch;

	public static int V_WIDTH = 1280;
	public static int V_HEIGHT = 720;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MainMenuPresenter(this));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
