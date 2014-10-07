package com.eternalisland.infinitearmada.screens;

import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.eternalisland.infinitearmada.Infinite_Armada;
import com.eternalisland.infinitearmada.tween.ActorAccessor;

public class MainMenu implements Screen {

	private Stage stage;
	private Table table;
	private TextButton exitButton, playButton;
	private TextureAtlas atlas;
	private Label header;
	private Skin skin;
	private TweenManager tweenManager;
	private static Infinite_Armada game;

	public MainMenu(Infinite_Armada game) {
		// TODO Auto-generated constructor stub
		MainMenu.game = game;
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tweenManager.update(delta);

		stage.act(delta);
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		stage.setViewport(width, height, true);

		table.invalidateHierarchy();
		table.setSize(width, height);
	}

	@Override
	public void show() {
		stage = new Stage();

		Gdx.input.setInputProcessor(stage);

		atlas = new TextureAtlas("ui/button.pack");
		skin = new Skin(Gdx.files.internal("ui/menuSkin.json"), atlas);

		table = new Table();
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		exitButton = new TextButton("EXIT", skin);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		exitButton.pad(15);

		playButton = new TextButton("PLAY", skin);
		playButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener())
						.setScreen(new GameScreen(game));
			}
		});
		playButton.pad(12);

		header = new Label(Infinite_Armada.TITLE, skin);
		header.setFontScale(1.8f);

		table.add(header);
		table.getCell(header).spaceBottom(30);
		table.row();
		table.add(playButton);
		table.getCell(playButton).spaceBottom(15);
		table.row();
		table.add(exitButton);
		stage.addActor(table);

		tweenManager = new TweenManager();
		Tween.registerAccessor(Actor.class, new ActorAccessor());

		Timeline.createSequence().beginSequence()
				.push(Tween.to(header, ActorAccessor.RGB, 1).target(0, 0, 1))
				.push(Tween.to(header, ActorAccessor.RGB, 1).target(0, 1, 0))
				.push(Tween.to(header, ActorAccessor.RGB, 1).target(1, 0, 0))
				.push(Tween.to(header, ActorAccessor.RGB, 1).target(1, 0, 1))
				.push(Tween.to(header, ActorAccessor.RGB, 1).target(1, 1, 1))
				.push(Tween.to(header, ActorAccessor.RGB, 1).target(0, 1, 1))
				.end().repeat(Tween.INFINITY, 0).start(tweenManager);

		Timeline.createSequence()
				.beginSequence()
				.push(Tween.set(header, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(playButton, ActorAccessor.ALPHA).target(0))
				.push(Tween.set(exitButton, ActorAccessor.ALPHA).target(0))
				.push(Tween.to(header, ActorAccessor.ALPHA, 0.7f).target(1))
				.push(Tween.to(playButton, ActorAccessor.ALPHA, 0.7f).target(1))
				.push(Tween.to(exitButton, ActorAccessor.ALPHA, 0.7f).target(1))
				.end().start(tweenManager);

		Tween.from(table, ActorAccessor.ALPHA, 1).target(0);
		Tween.from(table, ActorAccessor.ALPHA, 1)
				.target(Gdx.graphics.getHeight() / 8).start(tweenManager);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		this.dispose();

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
		atlas.dispose();
		skin.dispose();
	}

}
