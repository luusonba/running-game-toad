package com.tai.flappy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.tai.flappy.FlappyBird;
import com.tai.flappy.actors.Bird;
import com.tai.flappy.actors.Land;
import com.tai.flappy.actors.Pipe;
import com.tai.flappy.utils.MyStage;
import com.tai.flappy.utils.config;

public class PlayScreen implements Screen {
	
	private MyStage stage;
	private TextureAtlas atlas;
	
	private Land land;
	public Bird bird;
	
	float duraAddPipe;
		
	public static Label labelScore;
	
	public static Button btnPlay;
	
	private static Preferences prefs;
	
	static final int GAME_RUNNING = 0;
	static final int GAME_PAUSED = 1;
	static final int GAME_OVER = 2;
	int state;
	int screenW = 0;
	int screenH = 0;
	FlappyBird game;
		
	public PlayScreen(FlappyBird game) {		
		
		this.game = game;
		state = GAME_RUNNING;
		screenW = FlappyBird.screenW;
		screenH = FlappyBird.screenH;
		
		stage = new MyStage(screenW, screenH, true);
		stage.setPlayScreen(this);
				
		game.manager.load("data/flappy.txt", TextureAtlas.class);
		game.manager.finishLoading();

		atlas = game.manager.get("data/flappy.txt", TextureAtlas.class);
		
		// Create (or retrieve existing) preferences file
		prefs = Gdx.app.getPreferences("FlappyBird");

		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}		
	}
	
	public static void setHighScore(int val) {
		prefs.putInteger("highScore", val);
		prefs.flush();
	}

	public static int getHighScore() {
		return prefs.getInteger("highScore");
	}
	
	@Override
    public void show() {
        // set the stage as the input processor
        Gdx.input.setInputProcessor( stage );
        Gdx.input.setCatchBackKey(true);

        resetGame();     
    }
	
	public void resetGame()	{
		state = GAME_RUNNING;
		stage.clear();
        duraAddPipe = 0;
        addBackground();
        addLand();
        addBird();
        addLabelScore();        
        addButton();
	}
	
	private void addButton() {		
		
		Button btnSound;
		Button btnPause;
	    Skin skin;
	    TextureAtlas buttonAtlas;
	    ButtonStyle btnStyleSound = new ButtonStyle();
	    ButtonStyle btnStylePause = new ButtonStyle();
	    Texture.setEnforcePotImages(false);
	    skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("data/buttons/button.pack"));
        skin.addRegions(buttonAtlas);
        btnStyleSound.up = skin.getDrawable("buttonon");
        btnStyleSound.checked = skin.getDrawable("buttonoff");
        
        btnStylePause.up = skin.getDrawable("buttonon");
        btnStylePause.checked = skin.getDrawable("buttonoff");
                                        
        btnSound = new Button(btnStyleSound);
        btnPause = new Button(btnStylePause);
        
        btnSound.setPosition(screenW - btnSound.getWidth(), screenH - btnSound.getHeight());
        btnPause.setPosition(screenW - btnSound.getWidth() - btnPause.getWidth(), screenH - btnPause.getHeight());
        
        if(config.volume == 0.0f){
        	btnSound.setChecked(true);
        }else{
        	btnSound.setChecked(false);     	
        }
                                
        btnSound.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {                    
                return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(config.volume == 0.0f){
                	normalizeFX();
                }else{
                	muteFX();                    	
                }
            }
        });
        
        btnPause.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {                    
                return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(state != GAME_PAUSED){
                	state = GAME_PAUSED;
                }else{
                	state = GAME_RUNNING;
                }
            }
        });
        
        stage.addActor(btnSound);
        stage.addActor(btnPause);
	}
	
	public static void muteFX(){
	    config.volume = 0.0f;
	}
	public static void normalizeFX(){
		config.volume = 1.0f;
	}
	
	private void addBackground() {
        Image bg = new Image(atlas.findRegion("background"));
        stage.addActor(bg);
	}
	
	private void addLand() {
		land = new Land(atlas.findRegion("land"));
		config.landY = land.getY() + land.getHeight();
		stage.addActor(land);
	}
	
	private void addBird() {
		TextureRegion[] regions = new TextureRegion[] { atlas.findRegion("bird1"),
				atlas.findRegion("bird2"), atlas.findRegion("bird3") };
		
		bird = new Bird(regions);
		bird.setPosition(screenW/2 - bird.getWidth()/2, config.landY);		
		stage.addActor(bird);
	}
	
	private void addLabelScore() {
		LabelStyle textStyle = new LabelStyle();
		textStyle.font = new BitmapFont(Gdx.files.internal("data/flappyfont.fnt"), Gdx.files.internal("data/flappyfont.png"), false);

		labelScore = new Label("0",textStyle);
		labelScore.setPosition(screenW/2 - labelScore.getWidth()/2, screenH - labelScore.getHeight());
		
		stage.addActor(labelScore);
	}
	
	private void addPipe() {	    
	    Pipe pipe2 = new Pipe(atlas.findRegion("pipe2"), bird, true);
	    pipe2.setZIndex(1);
	    float x = screenW + (50 * config.random(0, 4));

	    /*****************Positon Y cua Pipe****************/
	    //float y = config.landY;		
	    float y = config.landY - 300;
	    	    
	    pipe2.setPosition(x, y);
	    
	    stage.addActor(pipe2);
	    
		land.setZIndex(pipe2.getZIndex());
		bird.setZIndex(land.getZIndex());
		labelScore.setZIndex(bird.getZIndex());
	}
	
    @Override
    public void resize(int width, int height){    	
    	// resize the stage    	
    	stage.setViewport(screenW, screenH, false );
    }

    @Override
    public void render (float delta ){
    	
    	if (delta > 0.1f)
    	    delta = 0.1f;
    	
    	switch (state) {
		case GAME_RUNNING:
			if (bird.isDie){
	    		if (bird.score > getHighScore()) {
					setHighScore(bird.score);
				}
	    		state = GAME_OVER;    		
	    	}
	    	else {			
		    	duraAddPipe += delta;
		    	if (duraAddPipe > config.kTimeAddPipe){
		    		duraAddPipe = 0;
		    		addPipe();
		    	}
	    	}
	    	
	        // update the action of actors
	        stage.act( delta );

	        // clear the screen with the given RGB color (black)
	        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
	        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
			break;
		case GAME_OVER:
			land.clearActions();
			break;
		case GAME_PAUSED:
			/////////
			//https://github.com/libgdx/libgdx-demo-superjumper/blob/master/core/src/com/badlogicgames/superjumper/GameScreen.java
			break;
		default:
			break;
		}
    	    	        
        // draw the actors
        stage.draw();
    }

    @Override
    public void hide(){
        // dispose the resources by default
        dispose();
    }

    @Override
    public void pause() {    
    	state = GAME_PAUSED;
    }

    @Override
    public void resume() {
    	state = GAME_RUNNING;
    }

    @Override
    public void dispose() {
    }
}