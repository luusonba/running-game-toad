package com.tai.flappy.actors;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.forever;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.tai.flappy.FlappyBird;
import com.tai.flappy.utils.config;

public class Pipe extends Image {

	boolean getScore;
	
	Bird bird;
	
	public Pipe(TextureRegion region, Bird bird, boolean getScore) {
		super(region);
		this.bird = bird;
		this.getScore = getScore;
		
		if(config.firstTap == true){
			actionMoveLeft();
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (bird.isDie) {
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
        if (getX() <= bird.getX()) {
        	if (getScore) {
        		getScore = false; //chi tinh diem mot lan....
        		bird.updateScore();
        		FlappyBird.sounds.get(config.SoundScore).play(config.volume);
        	}
        }
	}
	
	private void checkCollision() {
		if (isCollision()) {
			bird.hitMe();
			FlappyBird.sounds.get(config.SoundHit).play(config.volume);
		}
	}
	
	private boolean isCollision() {
		float d = 4; //gia giam de chim dung vao pipe nhieu hon....
    
		float maxx1 = getX() + getWidth() - d;
		float minx1 = getX() + d;
		float maxy1 = getY() + getHeight() - d;
		float miny1 = getY() + d;
    
		float maxx2 = bird.getX() + bird.getWidth() - d;
		float minx2 = bird.getX() + d;
		float maxy2 = bird.getY() + bird.getHeight() - d;
		float miny2 = bird.getY() + d;
    
		return !(maxx1 < minx2 ||
				maxx2 < minx1 ||
				maxy1 < miny2 ||
				maxy2 < miny1);    
	}
	
}
