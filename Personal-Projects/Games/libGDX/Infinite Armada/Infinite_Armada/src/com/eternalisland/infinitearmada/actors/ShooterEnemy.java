package com.eternalisland.infinitearmada.actors;

public class ShooterEnemy extends Enemy {

	static final int ENEMY_WIDTH = 125;
	static final int ENEMY_HEIGHT = 128;
	boolean reverse = false;

	public ShooterEnemy(float x, float y, Player player) {
		super(x, y, player, ENEMY_WIDTH, ENEMY_HEIGHT);

		this.sprite = atlas.createSprite("shooter");
		this.velocity = 2;

		this.setHealth(100);
		this.setDamageTaken(50);
		this.setPlayerDamage(15);
	}

	public int getEnemyWidth() {
		return ENEMY_WIDTH;
	}

	public int getEnemyHeight() {
		return ENEMY_HEIGHT;
	}

	@Override
	public void act(float delta) {
		super.act(delta);

		if (position.x > Enemy.SCREEN_WIDTH - ENEMY_WIDTH) {
			reverse = true;
		} else if (position.x <= 0) {
			reverse = false;
		}

		if (reverse == false) {
			position.x += this.velocity;
		} else {
			position.x -= this.velocity;
		}
	}
}
