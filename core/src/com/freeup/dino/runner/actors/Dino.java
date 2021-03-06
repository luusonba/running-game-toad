package com.freeup.dino.runner.actors;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.freeup.dino.runner.DinoRunner;
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

	public Dino(TextureRegion[] regions) {
		super(regions[0]);
		setOrigin(getWidth() / 2, getHeight() / 2);
		this.regions = regions;
		TextureRegion[] runs = new TextureRegion[] { regions[0], regions[1] };
		animation = new Animation(0.1f, runs);
		dura = 0;
		isDie = false;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (isDie || getY() > config.landY) {
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

		MoveToAction moveup = new MoveToAction();
		moveup.setDuration(config.kfallDura);
		moveup.setPosition(getX(), y);
		moveup.setInterpolation(Interpolation.sineOut);
		Action fly = parallel(moveup);
		float durafall = getDuraDown(y, config.kLandHeight);

		MoveToAction movedown = new MoveToAction();
		movedown.setDuration(durafall);
		movedown.setPosition(this.getX(), config.landY);
		movedown.setInterpolation(Interpolation.sineIn);
		Action fall = parallel(movedown);
		curAction = sequence(fly, fall);
		this.addAction(curAction);
	}

	public void hitMe() {
		DinoRunner.sounds.get(config.SoundHit).play(config.volume);
		isDie = true;
		config.dieTime = System.currentTimeMillis();
		this.removeAction(curAction);
		setDrawable(new TextureRegionDrawable(regions[3]));
	}

	float getDuraDown(float up, float down) {
		float dy = up - down;
		float duraDown;

		if (dy <= config.kjumpHeight) {
			duraDown = 0.33f * config.kmoveLeftDura;
		} else {
			duraDown = dy * (0.33f * config.kmoveLeftDura) / config.kjumpHeight;
		}
		return duraDown;
	}
}