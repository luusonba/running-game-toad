package com.freeup.dino.runner.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.freeup.dino.runner.screen.PlayScreen;
import com.freeup.dino.runner.utils.config;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;


public class Bird extends Image {
	
	public int score;
	private Action curAction;

	Animation animation;
	TextureRegion curFrame;
	float dura;
	public boolean isDie;
	
	public Bird(TextureRegion[] regions)
	{
		super(regions[0]);
		setOrigin(getWidth()/2, getHeight()/2);
		animation = new Animation (0.03f, regions);
		dura = 0;
		isDie = false;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (isDie) return;
		//animation flying...		
		dura += delta;
		curFrame = animation.getKeyFrame(dura, true);
		setDrawable(new TextureRegionDrawable(curFrame));		
	}

	public void tapMe() {
		//if(config.firstTap == false){
		//config.firstTap = true;
		//}
		this.removeAction(curAction);

		//float y = getY() + config.kjumpHeight;
		float y = config.landY + config.kjumpHeight;
		//fly up
        RotateToAction faceup = new RotateToAction();
        faceup.setDuration(config.kjumpDura);
        //faceup.setRotation(30.0f);
        faceup.setRotation(0.0f);
        
        MoveToAction moveup = new MoveToAction();
        moveup.setDuration(config.kjumpDura);
        moveup.setPosition(getX(), y);
        moveup.setInterpolation(Interpolation.sineOut);
        
        Action fly  = parallel( faceup, moveup);
        
        //fall down
        float durafall = getDuraDown(y, config.kLandHeight);
        
        RotateToAction facedown = new RotateToAction();
        facedown.setDuration(durafall);
        //facedown.setRotation(-90.0f);
        facedown.setRotation(0.0f);
        
        MoveToAction movedown = new MoveToAction();
        movedown.setDuration(durafall);
        movedown.setPosition(this.getX(), config.kLandHeight);
        movedown.setInterpolation(Interpolation.sineIn);
        
        Action fall  = parallel( facedown, movedown);
        
        curAction = sequence(fly, fall);
        this.addAction(curAction);		
	}

	public void hitMe() {		
		isDie = true;
		this.removeAction(curAction);
		
        //fall down
        RotateToAction facedown = new RotateToAction();
        facedown.setDuration(config.kjumpDura);
        //facedown.setRotation(-90.0f);
        facedown.setRotation(0.0f);
        
        MoveToAction movedown = new MoveToAction();
        movedown.setDuration(getDuraDown(getY(), config.kLandHeight) * 1/2);
        movedown.setPosition(this.getX(), config.kLandHeight);
        movedown.setInterpolation(Interpolation.sineIn);
        
        curAction  = parallel( facedown, movedown);
        this.addAction(curAction);
	}

	
	public void updateScore() {
		score++;
		PlayScreen.labelScore.setText("" + score);
	}
	
	float getDuraDown(float up, float down) {
	    
	    float dy = up - down;
	    float duraDown;
	    
	    //neu dy <= kjumheight => thoi gian khong doi
	    if (dy <= config.kjumpHeight) duraDown = config.kjumpDura;
	    else //neu cao hon thi tinh theo ti le thuan
	    {
	        duraDown = dy * (config.kjumpDura) / config.kjumpHeight;
	    }
	    
	    return duraDown;
	}

	
	
}