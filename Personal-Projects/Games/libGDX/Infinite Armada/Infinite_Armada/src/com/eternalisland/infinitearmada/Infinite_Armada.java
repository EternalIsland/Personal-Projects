package com.eternalisland.infinitearmada;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eternalisland.infinitearmada.screens.MainMenu;
import com.eternalisland.infinitearmada.screens.Splash;

public class Infinite_Armada extends Game {

	public static final String TITLE = "Infinite Armada", VERSION = "0.0.5";
	public static SpriteBatch batch;
	public static BitmapFont font;

	MainMenu mainMenuScreen;
	Splash slpashScreen;

	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		mainMenuScreen = new MainMenu(this);
		slpashScreen = new Splash(this);
		setScreen(slpashScreen);
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
