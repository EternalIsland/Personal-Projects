package com.eternalisland.infinitearmada.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.eternalisland.infinitearmada.Infinite_Armada;
import com.eternalisland.infinitearmada.actors.Player;
import com.eternalisland.infinitearmada.world.World;

public class GameScreen implements Screen {

	private static final int SHIP_WIDTH = 44;
	private static final int SHIP_HEIGHT = 70;
	private static final int GAME_WIDTH = Gdx.graphics.getWidth();
	private static final int GAME_HEIGHT = Gdx.graphics.getHeight();

	static Infinite_Armada game;
	Stage stage;
	SpriteBatch batch;
	Player player;
	World world;
	private BitmapFont font;

	public GameScreen(Infinite_Armada game) {
		GameScreen.game = game;

		font = new BitmapFont(Gdx.files.internal("font/white.fnt"), false);
		font.setColor(Color.WHITE);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.setViewport(width, height, true);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		batch = new SpriteBatch();
		stage = new Stage(GAME_WIDTH, GAME_HEIGHT, true, batch);
		Gdx.input.setInputProcessor(stage);

		player = new Player(GAME_WIDTH / 2 + SHIP_WIDTH, SHIP_HEIGHT);
		stage.addActor(player);

		world = new World(stage, player);
		stage.addActor(world);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		stage.dispose();
		batch.dispose();
		player.dispose();
	}

	public static Infinite_Armada getGame() {
		return game;
	}

}
