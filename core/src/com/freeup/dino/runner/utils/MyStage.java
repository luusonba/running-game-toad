package com.freeup.dino.runner.utils;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.freeup.dino.runner.DinoRunner;
import com.freeup.dino.runner.screen.PlayScreen;
import com.freeup.dino.runner.screen.PlayScreen.GameState;

public class MyStage extends Stage {
	
	PlayScreen screen;
	
	public MyStage(float width, float height, boolean keepAspecyRatio) {
		super(new StretchViewport(480, 800));
	}
		
	public void setPlayScreen(PlayScreen screen){
		this.screen = screen;		
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		if (screen != null) {
			if (screen.dino.isDie) {				
				screen.dino.isDie = false;
				config.state = GameState.GAME_RUNNING;
				screen.showGame();
			} else {				
				if(config.state == GameState.GAME_RUNNING){
					if (screen.dino.getY() <= config.landY + 20) {
						screen.dino.tapMe();
						DinoRunner.sounds.get(config.SoundJump).play(config.volume);
						config.canJump = true;
					} else if ((screen.dino.getY() > config.landY + 20) && config.canJump == true 
							&& config.doubleJump > 0) {
						screen.dino.tapMe();
						DinoRunner.sounds.get(config.SoundJump).play(config.volume);
						config.canJump = false;
						config.doubleJump = config.doubleJump - 1;
					}
				} else if(config.state == GameState.GAME_START) {
					config.state = GameState.GAME_RUNNING;
					screen.showPlay(false);
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