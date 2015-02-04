package com.tai.flappy;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "FlappyBird";
		cfg.useGL20 = false;
		cfg.width = 320;
		cfg.height = 480;
		
		new LwjglApplication(new FlappyBird(), cfg);
	}
}
