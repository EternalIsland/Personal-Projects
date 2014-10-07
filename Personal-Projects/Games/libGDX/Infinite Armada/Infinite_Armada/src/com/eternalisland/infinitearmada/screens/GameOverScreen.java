package com.eternalisland.infinitearmada.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.eternalisland.infinitearmada.Infinite_Armada;

public class GameOverScreen implements Screen {

	private TextButton replayButton;
	private static Infinite_Armada game;
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private Label header;

	public GameOverScreen(Infinite_Armada game) {
		GameOverScreen.game = game;

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	@Override
	public void show() {
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		atlas = new TextureAtlas("ui/button.pack");
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);

		table = new Table();
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		replayButton = new TextButton("REPLAY", skin);
		replayButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new GameScreen(game));
			}
		});
		replayButton.pad(12);

		header = new Label("GAME OVER", skin);
		header.setFontScale(1.8f);

		table.row();
		table.add(header);
		table.getCell(header).spaceBottom(30);
		table.row();
		table.add(replayButton);
		stage.addActor(table);
	}

	@Override
	public void hide() {
		this.dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		stage.dispose();
		atlas.dispose();
		skin.dispose();
	}

}
