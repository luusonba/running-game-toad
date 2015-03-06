package com.freeup.dino.runner.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.freeup.dino.runner.DinoRunner;
import com.freeup.dino.runner.actors.Bird;
import com.freeup.dino.runner.actors.Cloud;
import com.freeup.dino.runner.actors.Land;
import com.freeup.dino.runner.actors.Pipe;
import com.freeup.dino.runner.utils.MyStage;
import com.freeup.dino.runner.utils.config;

public class PlayScreen implements Screen {
	
	private MyStage stage;
	private TextureAtlas atlas;
	
	private Land land;
	public Bird bird;
	
	float duraAddPipe;
		
	public static Label labelScore;
	public Button btnPlay, btnPause;
	public Table tableTop;
	private static Preferences prefs;
	Skin skin;
		
	public class GameState {
		public static final int GAME_START = 0;
	    public static final int GAME_RUNNING = 1;
	    public static final int GAME_PAUSED = 2;
	    public static final int GAME_OVER = 3;
	}
		
	float screenW = 0;
	float screenH = 0;
	DinoRunner game;
			
	public PlayScreen(DinoRunner game) {		
		
		this.game = game;		
		screenW = DinoRunner.VIEWPORT.x;
		screenH = DinoRunner.VIEWPORT.y;
		
		stage = new MyStage(0, 0, true);
		stage.setPlayScreen(this);		
		game.manager.load("images/sprites.pack", TextureAtlas.class);
		game.manager.finishLoading();
		atlas = game.manager.get("images/sprites.pack", TextureAtlas.class);			    
		skin = new Skin();
        skin.addRegions(atlas);
		
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
        config.state = GameState.GAME_START;
        showGame();     
    }
		
	public void showGame()	{		
		stage.clear();
        duraAddPipe = 0;
        /*addBackground();*/
        addLand();
        addBird();
        addButton();
        addLabelScore();
                
        if(config.state == GameState.GAME_START){
        	addScreenPlay();
        }
	}
	
	private void showPlay(boolean isShow) {
		tableTop.setVisible(isShow);
	}
		
	private void addButton() {
		Button btnRestart;		
	    
	    
	    ButtonStyle btnStylePause;
	    	    
	    btnStylePause = new ButtonStyle();
	            
        btnStylePause.up = skin.getDrawable("buttons/screenplay");
        btnStylePause.checked = skin.getDrawable("buttons/screenpause");
                                        
        btnPause = new Button(btnStylePause);        
        btnRestart = new Button(skin.getDrawable("buttons/restart"));
                                    
        btnPause.setPosition(screenW - btnPause.getWidth() - btnPause.getWidth(), screenH - btnPause.getHeight());        
        btnRestart.setPosition(screenW/2 - btnRestart.getWidth()/2, screenH/2 - btnRestart.getHeight()/2);
                        
        btnRestart.setVisible(false);
        
        /*if(config.volume == 0.0f){
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
        });*/
         
        btnPause.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {                    
                return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(config.state != GameState.GAME_PAUSED){
                	config.state = GameState.GAME_PAUSED;
                	showPlay(true);
                }else{
                	config.state = GameState.GAME_RUNNING;
                }
            }
        });
        
        stage.addActor(btnPause);        
        stage.addActor(btnRestart);
	}
	
	public static void muteFX(){
	    config.volume = 0.0f;
	}
	public static void normalizeFX(){
		config.volume = 1.0f;
	}
	
	/*private void addBackground() {
		Image bg = new Image(atlas.findRegion("background"));
		stage.addActor(bg);
	}*/
	
	private void addLand() {
		land = new Land(atlas.findRegion("lands/land1"));
		config.landY = land.getY() + land.getHeight();		
		stage.addActor(land);
	}
	
	private void addBird() {
		TextureRegion[] regions = new TextureRegion[] { atlas.findRegion("dinos/left"),
				atlas.findRegion("dinos/right") };
		
		bird = new Bird(regions);		
		bird.setPosition(screenW/2 - bird.getWidth()/2, config.landY);
		stage.addActor(bird);
	}
	
	private void addLabelScore() {
		LabelStyle textStyle = new LabelStyle();
		textStyle.font = new BitmapFont(Gdx.files.internal("font/font.fnt"), Gdx.files.internal("font/font.png"), false);

		labelScore = new Label("0",textStyle);
		labelScore.setPosition(screenW/2 - labelScore.getWidth()/2, screenH - labelScore.getHeight());
		
		stage.addActor(labelScore);
	}
	
	private void addScreenPlay() {
		LabelStyle textStyle = new LabelStyle();
		textStyle.font = new BitmapFont(Gdx.files.internal("font/font.fnt"), Gdx.files.internal("font/font.png"), false);
		
		Label labelTitle;
		labelTitle = new Label("DINO RUNNER",textStyle);
		labelTitle.setFontScale((float)screenW/480);
		
		btnPlay = new Button(skin.getDrawable("buttons/start"));        
        btnPlay.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {                    
                return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {            	
            	showPlay(false);
            	config.state = GameState.GAME_RUNNING;            	
            }
        });
				
		tableTop = new Table();
		tableTop.add(labelTitle);
		tableTop.row();
		tableTop.add(btnPlay);

		
		tableTop.setPosition(screenW/2, screenH - screenH/4);
	    stage.addActor(tableTop);
	}
	
	private void addPipe() {	    
	    Pipe pipe2 = new Pipe(atlas.findRegion("plants/big5"), bird, true);
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
	
	private void addCloud() {	    
	    Cloud cloud = new Cloud(atlas.findRegion("cloud/cloud"));
	    cloud.setRotation(-90.0f);
	    float x = screenW + (50 * config.random(0, 4));

	    /*****************Positon Y cua Pipe****************/
	    //float y = config.landY;		
	    float y = config.landY + 400;
	    	    
	    cloud.setPosition(x, y);
	    
	    stage.addActor(cloud);
	    
	}
	
    @Override
    public void resize(int width, int height){    	
    	// resize the stage
    	stage.getViewport().update(width, height);
    }

    @Override
    public void render (float delta ){
    	if (delta > 0.1f)
    	    delta = 0.1f;
    	
    	switch (config.state) {
    	case GameState.GAME_START:
	        // update the action of actors
	        stage.act(delta);

	        // clear the screen with the given RGB color (black)
	        Gdx.gl.glClearColor( 247/255f, 247/255f, 247/255f, 1f );
	        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
    		break;
		case GameState.GAME_RUNNING:
			if (bird.isDie){
	    		if (bird.score > getHighScore()) {
					setHighScore(bird.score);
				}
	    		config.state = GameState.GAME_OVER;    		
	    	}
	    	else {			
		    	duraAddPipe += delta;
		    	if (duraAddPipe > config.kTimeAddPipe){
		    		duraAddPipe = 0;
		    		addPipe();
		    		addCloud();
		    	}
	    	}
	    	
	        // update the action of actors
	        stage.act(delta);

	        // clear the screen with the given RGB color (black)
	        Gdx.gl.glClearColor( 247/255f, 247/255f, 247/255f, 1f );
	        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
			break;
		case GameState.GAME_PAUSED:			
			stage.act(0f);

	        // clear the screen with the given RGB color (black)
	        Gdx.gl.glClearColor( 247/255f, 247/255f, 247/255f, 1f );
	        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
			break;
		case GameState.GAME_OVER:
			stage.act(0f);

	        // clear the screen with the given RGB color (black)
	        Gdx.gl.glClearColor( 247/255f, 247/255f, 247/255f, 1f );
	        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
			land.clearActions();
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
    	//state = GameState.GAME_PAUSED;
    }

    @Override
    public void resume() {
    	//state = GameState.GAME_RUNNING;
    }

    @Override
    public void dispose() {
    }
}