package com.freeup.dino.runner.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.freeup.dino.runner.DinoRunner;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Dino Runner";
		cfg.width = 240;
		cfg.height = 320;
		new LwjglApplication(new DinoRunner(), cfg);
	}
}