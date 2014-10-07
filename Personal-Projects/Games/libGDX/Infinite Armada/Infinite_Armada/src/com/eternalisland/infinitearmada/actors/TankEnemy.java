package com.eternalisland.infinitearmada.actors;

public class TankEnemy extends Enemy {

	static final int ENEMY_WIDTH = 128;
	static final int ENEMY_HEIGHT = 116;

	public TankEnemy(float x, float y, Player player) {
		super(x, y, player, ENEMY_WIDTH, ENEMY_HEIGHT);

		this.sprite = atlas.createSprite("tank");
		this.velocity = 1;

		this.setHealth(200);
		this.setDamageTaken(20);
		this.setPlayerDamage(20);
	}

	public int getEnemyWidth() {
		return ENEMY_WIDTH;
	}

	public int getEnemyHeight() {
		return ENEMY_HEIGHT;
	}
}
