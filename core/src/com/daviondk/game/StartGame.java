package com.daviondk.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StartGame extends Game {
	SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		Assets.load();
		setScreen(new Menu(this));
	}

	@Override
	public void render() {
		super.render();
	}

}
