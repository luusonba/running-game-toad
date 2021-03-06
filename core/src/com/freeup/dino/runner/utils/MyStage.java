package com.freeup.dino.runner.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.freeup.dino.runner.DinoRunner;
import com.freeup.dino.runner.screen.PlayScreen;
import com.freeup.dino.runner.screen.PlayScreen.GameState;

public class MyStage extends Stage {

	private PlayScreen screen;
	private int CONST_SPACE_LAND = 0;

	public MyStage(float width, float height, boolean keepAspecyRatio) {
		super(new StretchViewport(config.VIRTUAL_WIDTH, config.VIRTUAL_HEIGHT));
	}

	public void setPlayScreen(PlayScreen screen) {
		this.screen = screen;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (screen != null) {
			if (screen.isTouchRate(x, y)) {
				Gdx.net.openURI("https://play.google.com/store/apps/details?id=com.freeup.dino.runner");
			} else if (screen.isTouchController(x, y)) {
				if (screen.game.actionResolver.getSignedInGPGS()) {
					screen.game.actionResolver.getLeaderboardGPGS();
				} else {
					screen.game.actionResolver.loginGPGS();
				}
			} else if (screen.isTouchSound(x, y)) {
				screen.changeFX();
			} else {
				screen.showDJ(false);
				screen.showPlusDJ(false);
				if (screen.dino.isDie && screen.btnRestart.isVisible()) {
					screen.dino.isDie = false;
					config.state = GameState.GAME_RUNNING;
					screen.showGame();
					screen.showRunning(true);
				} else {
					if (config.state == GameState.GAME_RUNNING) {
						if (screen.dino.getY() <= config.landY + CONST_SPACE_LAND) {
							screen.dino.tapMe();
							DinoRunner.sounds.get(config.SoundJump).play(
									config.volume);
							config.canJump = true;
						} else if ((screen.dino.getY() > config.landY
								+ CONST_SPACE_LAND)
								&& config.canJump == true && config.doubleJump > 0) {
							screen.dino.tapMe();
							DinoRunner.sounds.get(config.SoundJump).play(
									config.volume);
							config.canJump = false;
							config.doubleJump = config.doubleJump - 1;
							screen.showDJ(true);
							screen.updateCountDJ();
						}
					} else if (config.state == GameState.GAME_START) {
						config.state = GameState.GAME_RUNNING;
						screen.showPlay(false);
						screen.showRunning(true);
					}
				}
			}
		}
		return super.touchDown(x, y, pointer, button);
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return super.touchDragged(x, y, pointer);
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return super.touchUp(x, y, pointer, button);
	}
}