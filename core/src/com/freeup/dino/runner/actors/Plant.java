package com.freeup.dino.runner.actors;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.freeup.dino.runner.screen.PlayScreen;
import com.freeup.dino.runner.screen.PlayScreen.GameState;
import com.freeup.dino.runner.utils.config;

public class Plant extends Image {

	private boolean getScore;
	private Dino dino;
	private PlayScreen screen;
	private float space = 20;
	
	public Plant(TextureRegion region, Dino dino, boolean getScore) {
		super(region);
		this.dino = dino;
		this.getScore = getScore;
		if(config.state == GameState.GAME_RUNNING){
			actionMoveLeft();
		}		
	}
	
	public void setPlayScreen(PlayScreen screen){
		this.screen = screen;		
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
        checkCollision();
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
        		screen.updateScore();
        	}
        }
	}

	private void checkCollision() {
		if (isCollision()) {
			dino.hitMe();
			screen.resetConfig();
		}
	}
	
	private boolean isCollision() {
		float maxx1 = getX() + getWidth();
		float minx1 = getX();
		float maxy1 = getY() + getHeight();
		float miny1 = getY();
    
		float maxx2 = dino.getX() + dino.getWidth() - space;
		float minx2 = dino.getX() + space;
		float miny2 = dino.getY() + space;
		float maxy2 = dino.getY() + dino.getHeight() - space;
	    
		return !(maxx1 < minx2 ||
				maxx2 < minx1 ||
				maxy1 < miny2 ||
				maxy2 < miny1);  
	}
}