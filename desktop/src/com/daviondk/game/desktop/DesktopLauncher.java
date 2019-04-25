package com.daviondk.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.daviondk.game.StartGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Dreamers stories: Drake";
		config.width = 800;
		config.height = 600;
		config.fullscreen = false;
		new LwjglApplication(new StartGame(), config);
	}
}
