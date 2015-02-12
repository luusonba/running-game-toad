package com.tai.flappy;

import java.util.HashMap;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.tai.flappy.screen.PlayScreen;
import com.tai.flappy.utils.config;

public class FlappyBird  extends Game {

	//design viewport
	//public static final Vector2 VIEWPORT = new Vector2(320, 480);
		
	//Quan ly textureAtals va sound
    public AssetManager manager = new AssetManager();
	public static HashMap<String, Sound> sounds = new HashMap<String, Sound>();
	public static int screenW = 0;
	public static int screenH = 0;
	public PlayScreen getPlayScreen() {
		return new PlayScreen(this);
	}
	
	public FlappyBird(int width, int height){		
		screenW = width;
		screenH = height;		
	}
	
	@Override
	public void create() {
		//nap danh sach cac sound, de bat ky dau cuxng co the goi va "play"
		sounds.put(config.SoundJump, Gdx.audio.newSound(Gdx.files.internal("data/sounds/sfx_wing.mp3")));
		sounds.put(config.SoundScore, Gdx.audio.newSound(Gdx.files.internal("data/sounds/sfx_point.mp3")));
		sounds.put(config.SoundHit, Gdx.audio.newSound(Gdx.files.internal("data/sounds/sfx_hit.mp3")));			
	}

	@Override
    public void resize(
        int width,
        int height )
    {
        super.resize( width, height );
        
        if( getScreen() == null ) {
        	setScreen( getPlayScreen() );
        }
    };

    @Override
    public void render()
    {
        super.render();
    }

    @Override
    public void pause()
    {
        super.pause();

    }

    @Override
    public void resume() {
        super.resume();
    }
    
    @Override
    public void setScreen(Screen screen )
    {
        super.setScreen( screen );
    }

    @Override
    public void dispose()
    {
    	//giai phong sounds
		for (String key: sounds.keySet()) {
		    sounds.get(key).dispose();
		}
		
		//giai phong texture
		manager.dispose();
		
        super.dispose();
    }	
}
