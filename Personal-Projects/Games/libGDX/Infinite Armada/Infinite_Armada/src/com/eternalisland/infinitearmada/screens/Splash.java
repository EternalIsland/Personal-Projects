package com.eternalisland.infinitearmada.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.eternalisland.infinitearmada.Infinite_Armada;
import com.eternalisland.infinitearmada.tween.SpriteAccessor;

public class Splash implements Screen {

	private Sprite splashSprite;
	private SpriteBatch batch;
	private TweenManager tweenManager;

	static Infinite_Armada game;

	public Splash(Infinite_Armada game) {
		// TODO Auto-generated constructor stub
		Splash.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tweenManager.update(delta);

		batch.begin();
		splashSprite.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		Texture splashTexture = new Texture("images/Infinite Armada.png");
		splashSprite = new Sprite(splashTexture);
		splashSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		Tween.set(splashSprite, SpriteAccessor.ALPHA).target(0)
				.start(tweenManager);
		Tween.to(splashSprite, SpriteAccessor.ALPHA, 0.5f).target(1)
				.repeatYoyo(1, 0.5f).setCallback(new TweenCallback() {

					@Override
					public void onEvent(int type, BaseTween<?> source) {
						// TODO Auto-generated method stub
						((Game) Gdx.app.getApplicationListener())
								.setScreen(new MainMenu(game));
					}

				}).start(tweenManager);
	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		batch.dispose();
		splashSprite.getTexture().dispose();
	}

}
