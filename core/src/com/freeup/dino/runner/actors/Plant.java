package com.freeup.dino.runner.actors;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.freeup.dino.runner.screen.PlayScreen.GameState;
import com.freeup.dino.runner.utils.config;
import com.freeup.engine.collision.ImageCheckHit;

public class Plant extends ImageCheckHit {

	boolean getScore;
	
	Dino dino;
	TextureRegion txtRegion;
	
	public Plant(TextureRegion region, Dino dino, boolean getScore) {
		super(region);
		this.txtRegion = region;
		this.dino = dino;
		this.getScore = getScore;
		if(config.state == GameState.GAME_RUNNING){
			actionMoveLeft();
		}
	}
		
	@Override
	public void act(float delta) {		
		super.act(delta);
		if (dino.isDie) {
			clearActions();
			return;
		}
		
		//ra khoi man hinh remove...
		if (getX() < -getWidth()) {
			remove();
		}		
        bypass();
	}
	
	private void actionMoveLeft() {		
	    MoveByAction moveleft = new MoveByAction();	    
	    moveleft.setDuration(config.kmoveLeftDura);
	    moveleft.setAmountX(-config.kLandWidth);	    
	    addAction(forever(moveleft));
	}
	
	private void bypass() {
        if (getX() <= dino.getX()) {
        	if (getScore) {
        		getScore = false;
        		dino.updateScore();
        	}
        }
	}
}