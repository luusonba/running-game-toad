package com.freeup.dino.runner.screen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.freeup.engine.collision.BoundaryHelper;
import com.freeup.engine.collision.ImageCheckHit;
import com.freeup.engine.collision.InputHelper;

public class HT2 implements ApplicationListener{
	Stage stage;
	Vector2 vTouch = new Vector2();
	BitmapFont font, fontHit;
	SpriteBatch sb;
	ImageCheckHit i1, i2;
	private boolean isHit = false;
	public static BoundaryHelper boundaryHelper;
	@Override
	public void create() {
		stage = new Stage();
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		fontHit = new BitmapFont();
		fontHit.setScale(3f);
		fontHit.setColor(Color.RED);
		sb  = new SpriteBatch();
		boundaryHelper = new BoundaryHelper();
		Pixmap pixmap = new Pixmap(Gdx.files.internal("data/gnu.png"));
		i1 = new ImageCheckHit(pixmap);
		i1.setPosition(100, 100);
		
		stage.addActor(i1);
		
		i2 = new ImageCheckHit(new Pixmap(Gdx.files.internal("data/rock.png")));
		i2.setOrigin(i2.getWidth() /2, i2.getHeight() / 2);
		
		i2.setPosition(200, 0);
		stage.addActor(i2);
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1f, 1f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.draw();
		stage.act();
//		vTouch.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
		i2.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
		sb.begin();
		font.draw(sb, "frames/s : " + String.valueOf(Gdx.graphics.getFramesPerSecond()), 50, 20);
		if (isHit)
			fontHit.draw(sb, "HIT", 400, 400);
		sb.end();
		checkHit();
		
		if (InputHelper.pressKey(Keys.NUM_1))
			i2.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.rotateBy(360, 5)));
		if (InputHelper.pressKey(Keys.NUM_2))
			i1.addAction(Actions.repeat(RepeatAction.FOREVER, Actions.sequence(
					Actions.scaleTo(1.2f, 1f,2), Actions.scaleTo(0.8f, 1f,2))));
	}
	private void checkHit() {
		if (i1.checkHit(i2))
			isHit = true;
		else
			isHit = false;
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}
}