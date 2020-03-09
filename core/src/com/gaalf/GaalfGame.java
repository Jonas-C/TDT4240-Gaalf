package com.gaalf;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gaalf.presenter.MainMenuPresenter;

public class GaalfGame extends Game {
	SpriteBatch batch;
	Texture img;

	public static int V_WIDTH = 1920;
	public static int V_HEIGHT = 1080;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		setScreen(new MainMenuPresenter(this));
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		super.render();
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public SpriteBatch getBatch(){
		return batch;
	}
}
