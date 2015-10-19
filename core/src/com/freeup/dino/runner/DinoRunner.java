package com.freeup.dino.runner;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.freeup.dino.runner.screen.SplashScreen;
import com.freeup.dino.runner.service.ActionResolver;
import com.freeup.dino.runner.service.AdsController;
import com.freeup.dino.runner.utils.config;

public class DinoRunner extends Game {

	public AdsController adsController;
	public ActionResolver actionResolver;

	// design viewport
	public final Vector2 VIEWPORT = new Vector2(480, 800);

	// Quan ly textureAtals va sound
	public AssetManager manager = new AssetManager();
	public static HashMap<String, Sound> sounds = new HashMap<String, Sound>();

	public SplashScreen getSplashScreen() {
		return new SplashScreen(this);
	}

	public DinoRunner(AdsController adsController, ActionResolver actionResolver) {
		this.adsController = adsController;
		this.actionResolver = actionResolver;
	}

	@Override
	public void create() {
		// nap danh sach cac sound, de bat ky dau cuxng co the goi va "play"
		sounds.put(config.SoundJump,
				Gdx.audio.newSound(Gdx.files.internal("sounds/jump.mp3")));
		sounds.put(config.SoundScore,
				Gdx.audio.newSound(Gdx.files.internal("sounds/score.mp3")));
		sounds.put(config.SoundHit,
				Gdx.audio.newSound(Gdx.files.internal("sounds/hit.mp3")));
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		if (getScreen() == null) {
			setScreen(getSplashScreen());
		}
	};

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void setScreen(Screen screen) {
		super.setScreen(screen);
	}

	@Override
	public void dispose() {
		// giai phong sounds
		for (String key : sounds.keySet()) {
			sounds.get(key).dispose();
		}

		// giai phong texture
		manager.dispose();

		super.dispose();
	}
}