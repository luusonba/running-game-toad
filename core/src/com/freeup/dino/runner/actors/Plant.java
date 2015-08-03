package com.freeup.dino.runner.actors;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.freeup.dino.runner.screen.PlayScreen.GameState;
import com.freeup.dino.runner.utils.config;

public class Plant extends Image {

	boolean getScore;
	Rectangle boundingRect;
	Dino dino;
	TextureRegion txtRegion;
	
	public Plant(TextureRegion region, Dino dino, boolean getScore) {
		super(region);
		this.txtRegion = region;
		this.dino = dino;
		this.getScore = getScore;
		boundingRect = new Rectangle();
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
	
	public void setBounding(){
		boundingRect.set((getX()*config.scaleX)-((6/config.kmoveLeftDura)*config.scaleX), getY()*config.scaleY, getWidth()*config.scaleX, getHeight()*config.scaleY);
	}
	
	public Rectangle getBounding(){
		return boundingRect;
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