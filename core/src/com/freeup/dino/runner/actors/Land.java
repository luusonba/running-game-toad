package com.freeup.dino.runner.actors;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.freeup.dino.runner.screen.PlayScreen.GameState;
import com.freeup.dino.runner.utils.config;

public class Land extends Image {
	public MoveByAction moveleft;
	
	public Land(TextureRegion region) {
		super(region);
		
		if(config.state == GameState.GAME_RUNNING || config.state == GameState.GAME_START){
			actionMoveLeft();
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
	}
	
	public void actionMoveLeft() {		
		moveleft = new MoveByAction();
	    moveleft.setDuration(config.kmoveLeftDura);
	    moveleft.setAmountX(-config.kLandWidth);
	    addAction(forever(moveleft));
	}
}