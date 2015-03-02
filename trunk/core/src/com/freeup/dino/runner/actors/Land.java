package com.freeup.dino.runner.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.freeup.dino.runner.utils.config;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class Land extends  Image {

	public Land(TextureRegion region)
	{
		super(region);
		
		actionMoveLeft();
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (getX() <= -config.kLandWidth) setX(0);
	}
	
	private void actionMoveLeft(){
		addAction(forever(Actions.moveBy(-config.kLandWidth, 0, config.kmoveLeftDura)));
	}
	
}
