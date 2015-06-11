package com.freeup.dino.runner.actors;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.freeup.dino.runner.DinoRunner;
import com.freeup.dino.runner.screen.PlayScreen.GameState;
import com.freeup.dino.runner.utils.config;

public class Plant extends Image {

	boolean getScore;
	
	Dino dino;
	TextureRegion txtRegion;
	
	private Rectangle boundingPlant;	
	
	public Plant(TextureRegion region, Dino dino, boolean getScore) {
		super(region);
		this.txtRegion = region;
		this.dino = dino;
		this.getScore = getScore;
		boundingPlant = new Rectangle(getX()*config.scale, getY()*config.scale, getWidth()*config.scale, getHeight()*config.scale);		
		if(config.state == GameState.GAME_RUNNING){
			actionMoveLeft();
		}
	}
		
	public Rectangle getBoundingPlant() {
		return boundingPlant;
	}
	
	@Override
	public void act(float delta) {		
		super.act(delta);
		boundingPlant.setPosition(getX()*config.scale, getY()*config.scale);
		if (dino.isDie) {
			clearActions();
			return;
		}
		
		//ra khoi man hinh remove...
		if (getX() < -getWidth()) {
			remove();
		}
		checkCollision();
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
	
	private void checkCollision(){
		if (isCollision()){
			dino.hitMe();
			DinoRunner.sounds.get(config.SoundHit).play(config.volume);
		}
	}
	
	private boolean isCollision(){		
		return (Intersector.overlaps(dino.getBoundingBottom(), boundingPlant) ||
				Intersector.overlaps(dino.getBoundingTop(), boundingPlant));
	}
}