package com.eternalisland.infinitearmada.actors;

public class Boss1 extends Enemy {

	static final int ENEMY_WIDTH = 200;
	static final int ENEMY_HEIGHT = 245;

	public Boss1(float x, float y, Player player) {
		super(x, y, player, ENEMY_WIDTH, ENEMY_HEIGHT);

		this.sprite = atlas.createSprite("boss");
		this.velocity = 0.5;

		this.setHealth(500);
		this.setDamageTaken(10);
		this.setPlayerDamage(100);
	}

	public int getEnemyWidth() {
		return ENEMY_WIDTH;
	}

	public int getEnemyHeight() {
		return ENEMY_HEIGHT;
	}
}
