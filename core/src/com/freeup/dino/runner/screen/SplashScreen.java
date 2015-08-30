package com.freeup.dino.runner.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.freeup.dino.runner.DinoRunner;
import com.freeup.dino.runner.utils.SpriteAccessor;
import com.freeup.dino.runner.utils.config;

public class SplashScreen implements Screen {

	private TweenManager manager;
	private SpriteBatch batcher;
	private Sprite sprite;
	private DinoRunner game;
	public static TextureRegion logo;
	public static Texture logoTexture;
	
	private static final int VIRTUAL_WIDTH = 480;
    private static final int VIRTUAL_HEIGHT = 800;
    private static final float ASPECT_RATIO =
        (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
    
	public SplashScreen(DinoRunner game) {
		this.game = game;
	}

	@Override
	public void show() {
		logoTexture = new Texture(Gdx.files.internal("images/splash.png"));
		logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		
		logo = new TextureRegion(logoTexture, 0, 0, logoTexture.getWidth(), logoTexture.getHeight());
		sprite = new Sprite(logo);
		sprite.setColor(1, 1, 1, 0);

		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();
		float aspectRatio = (float)width/(float)height;
        float scale = 1f;
        if(aspectRatio > ASPECT_RATIO) {
            scale = (float)height/(float)VIRTUAL_HEIGHT;
        } else if(aspectRatio < ASPECT_RATIO) {
            scale = (float)width/(float)VIRTUAL_WIDTH;
        } else {
            scale = (float)width/(float)VIRTUAL_WIDTH;
        }
        config.scale = scale;
		sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
		sprite.setPosition(((width / 2) - (sprite.getWidth() / 2)), ((height / 2)
				- (sprite.getHeight() / 2)));
		setupTween();
		batcher = new SpriteBatch();
	}

	private void setupTween() {
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		manager = new TweenManager();

		TweenCallback cb = new TweenCallback() {
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				game.setScreen(new PlayScreen(game));
			}
		};

		Tween.to(sprite, SpriteAccessor.ALPHA, .8f).target(1)
				.ease(TweenEquations.easeInOutQuad).repeatYoyo(1, .4f)
				.setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
				.start(manager);
	}

	@Override
	public void render(float delta) {
		manager.update(delta);
		Gdx.gl.glClearColor(247/255f, 247/255f, 247/255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batcher.begin();
		sprite.draw(batcher);
		batcher.end();
	}

	@Override
    public void resize(int width, int height){
		
    }

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}