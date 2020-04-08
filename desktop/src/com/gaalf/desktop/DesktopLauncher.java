package com.gaalf.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gaalf.GaalfGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		// config.width = GaalfGame.V_WIDTH;
		// config.height = GaalfGame.V_HEIGHT;
		config.width = 1280;
		config.height = 720;
		new LwjglApplication(new GaalfGame(), config);
	}
}
