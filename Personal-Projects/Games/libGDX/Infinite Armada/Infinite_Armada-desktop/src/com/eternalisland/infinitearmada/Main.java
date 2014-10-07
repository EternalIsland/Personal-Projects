package com.eternalisland.infinitearmada;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = Infinite_Armada.TITLE + " v " + Infinite_Armada.VERSION;
		cfg.vSyncEnabled = true;
		cfg.useGL20 = true;
		cfg.width = 480;
		cfg.height = 800;

		new LwjglApplication(new Infinite_Armada(), cfg);
	}
}