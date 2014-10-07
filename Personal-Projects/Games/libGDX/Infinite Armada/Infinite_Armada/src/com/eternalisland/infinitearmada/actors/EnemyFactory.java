package com.eternalisland.infinitearmada.actors;

public class EnemyFactory {

	// TODO finish factory, add new sprites for different ships

	Player player;
	float x, y;

	public EnemyFactory(Player player) {
		this.player = player;
	}

	public Enemy makeEnemy(String newShipType, float x, float y) {
		this.x = x;
		this.y = y;

		if (newShipType.equals("F")) {
			return new FlyerEnemy(this.x, this.y, this.player);
		} else if (newShipType.equals("T")) {
			return new TankEnemy(this.x, this.y, this.player);
		} else if (newShipType.equals("S")) {
			return new ShooterEnemy(this.x, this.y, this.player);
		} else if (newShipType.equals("B1")) {
			return new Boss1(this.x, this.y, this.player);
		}

		return null;

	}
}
