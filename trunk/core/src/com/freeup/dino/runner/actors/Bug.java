package com.freeup.dino.runner.actors;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.freeup.dino.runner.utils.config;

public class Bug extends Image {

	public Bug(TextureRegion region) {
		super(region);
		actionMoveLeft();
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
				
		//ra khoi man hinh remove...
		if (getX() < -getWidth()) {
			remove();
		}		
	}
	
	private void actionMoveLeft() {		
	    MoveByAction moveleft = new MoveByAction();
	    moveleft.setDuration(config.kmoveLeftDura);
	    moveleft.setAmountX(-config.kLandWidth);
	    
	    addAction(forever(moveleft));
	}	
}
