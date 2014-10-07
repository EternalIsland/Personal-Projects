package com.eternalisland.infinitearmada.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Bullet {

	Texture texture;
	Sprite sprite;
	static Vector2 position;
	int velocity;
	SpriteBatch batch;

	public Bullet(float x, float y) {
		this.texture = new Texture("images/bullet.png");
		this.sprite = new Sprite(texture);
		Bullet.position = new Vector2(x, y);
		sprite.setBounds(0, 0, sprite.getWidth(), sprite.getHeight());
		this.velocity = 20;
	}

	public void update() {
		position.y += velocity;
		sprite.setPosition(position.x, position.y);
	}

	public void draw(SpriteBatch batch, float parentAlpha) {
		sprite.setColor(1, 1, 1, parentAlpha);
		sprite.draw(batch);
	}

	public void dispose() {
		texture.dispose();
	}

	public Vector2 getPosition() {
		return position;
	}

	public Sprite getSprite() {
		return sprite;
	}

}
