package com.tai.flappy.utils;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tai.flappy.FlappyBird;
import com.tai.flappy.screen.PlayScreen;

public class MyStage extends Stage {
	
	PlayScreen screen;
	
	public MyStage(float width, float height, boolean keepAspecyRatio)
	{
		super(width, height, keepAspecyRatio);
	}
		
	public void setPlayScreen(PlayScreen screen)
	{
		this.screen = screen;
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {

		if (screen != null)
		{
			if (screen.bird.isDie)
			{
				screen.resetGame();
			}
			else
			{
				if(screen.bird.getY() == config.landY){
					screen.bird.tapMe();
					FlappyBird.sounds.get(config.SoundJump).play();
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