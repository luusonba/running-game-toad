package com.freeup.dino.runner.actors;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.freeup.dino.runner.utils.config;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class Land extends Image {

	public Land(TextureRegion region) {
		super(region);		
		actionMoveLeft();
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);		
		if (getX() <= -config.kLandWidth + 240) {
			setX(0);
		}
	}
	
	private void actionMoveLeft(){
		MoveByAction moveleft = new MoveByAction();
	    moveleft.setDuration(config.kmoveLeftDura);
	    moveleft.setAmountX(-config.kLandWidth);
	    
		addAction(forever(moveleft));
	}	
}
