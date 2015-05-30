package com.freeup.dino.runner.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.freeup.dino.runner.DinoRunner;
import com.freeup.dino.runner.screen.HT2;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Dino Runner";
		cfg.width = 320;
		cfg.height = 480;
		new LwjglApplication(new HT2(), cfg);
	}
}