package com.freeup.dino.runner.actors;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.freeup.dino.runner.screen.PlayScreen.GameState;
import com.freeup.dino.runner.utils.config;

public class Cloud extends Image {

	boolean getScore;
			
	public Cloud(TextureRegion region) {
		super(region);
		
		if(config.state == GameState.GAME_RUNNING){
			actionMoveLeft();
		}
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
	    moveleft.setDuration(config.cmoveLeftDura);
	    moveleft.setAmountX(-config.kLandWidth);
	    
	    addAction(forever(moveleft));
	}	
}
