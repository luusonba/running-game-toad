package com.freeup.dino.runner.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.freeup.dino.runner.DinoRunner;
import com.freeup.dino.runner.screen.PlayScreen;
import com.freeup.dino.runner.utils.config;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;


public class Dino extends Image {
		
	private Action curAction;

	Animation animation;
	TextureRegion curFrame;
	TextureRegion[] regions;
	float dura;
	public boolean isDie;
	public int score;
	Rectangle boundRectTop;
	Rectangle boundRectMiddle;
	Rectangle boundRectBottom;
		
	public Dino(TextureRegion[] regions)
	{
		super(regions[0]);
		setOrigin(getWidth()/2, getHeight()/2);
		this.regions = regions;
		TextureRegion[] runs = new TextureRegion[] { regions[0],
				regions[1] };
		boundRectTop = new Rectangle();
		boundRectMiddle = new Rectangle();
		boundRectBottom = new Rectangle();
		animation = new Animation (0.1f, runs);
		dura = 0;
		isDie = false;		
	}
	
	public void setBounding(){
		boundRectTop.set((getX()+getWidth()/2)*config.scaleX, (getY()+(getHeight()/3)*2)*config.scaleY, getWidth()/2*config.scaleX, (getHeight()/3)*config.scaleY);
		boundRectMiddle.set(getX()*config.scaleX, (getY()+(getHeight()/3))*config.scaleY, (getWidth()*2/3)*config.scaleX, (getHeight()*2/3)*config.scaleY);
		boundRectBottom.set((getX()+getWidth()/3-15)*config.scaleX, getY()*config.scaleY, (getWidth()/3+15)*config.scaleX, getHeight()*config.scaleY);
	}
	
	public Rectangle getBoundingTop(){
		return boundRectTop;
	}
	
	public Rectangle getBoundingMiddle(){
		return boundRectMiddle;
	}
	
	public Rectangle getBoundingBottom(){
		return boundRectBottom;
	}
	
	public void updateScore() {
		score++;
		PlayScreen.labelScore.setText("" + score);		
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (isDie || getY()> config.landY){ 
			return;		
		}
		dura += delta;
		curFrame = animation.getKeyFrame(dura, true);
		setDrawable(new TextureRegionDrawable(curFrame));				
	}
	
	public void tapMe() {
		this.removeAction(curAction);
		setDrawable(new TextureRegionDrawable(regions[2]));
		float y = config.landY + config.kjumpHeight;
		//fly up
                
        MoveToAction moveup = new MoveToAction();
        //moveup.setDuration(0.33f*config.kmoveLeftDura);
        moveup.setDuration(config.kfallDura);
        moveup.setPosition(getX(), y);
        moveup.setInterpolation(Interpolation.sineOut);
        Action fly  = parallel(moveup);        
        //fall down
        float durafall = getDuraDown(y, config.kLandHeight);
                
        MoveToAction movedown = new MoveToAction();
        movedown.setDuration(durafall);
        movedown.setPosition(this.getX(), config.landY);
        movedown.setInterpolation(Interpolation.sineIn);        
        Action fall  = parallel(movedown);      
        curAction = sequence(fly, fall);
        this.addAction(curAction);        
	}

	public void hitMe() {
		DinoRunner.sounds.get(config.SoundHit).play(config.volume);
		isDie = true;		
		this.removeAction(curAction);
		setDrawable(new TextureRegionDrawable(regions[3]));
	}
	
	float getDuraDown(float up, float down) {
	    
	    float dy = up - down;
	    float duraDown;
	    
	    //neu dy <= kjumheight => thoi gian khong doi
	    if (dy <= config.kjumpHeight) duraDown = 0.33f*config.kmoveLeftDura;
	    else //neu cao hon thi tinh theo ti le thuan
	    {
	        duraDown = dy * (0.33f*config.kmoveLeftDura) / config.kjumpHeight;
	    }
	    
	    return duraDown;
	}

	
	
}