package com.freeup.dino.runner.screen;

import java.util.Random;

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
import com.freeup.dino.runner.actors.Dino;
import com.freeup.dino.runner.actors.Cloud;
import com.freeup.dino.runner.actors.Land;
import com.freeup.dino.runner.actors.Plant;
import com.freeup.dino.runner.utils.MyStage;
import com.freeup.dino.runner.utils.config;

public class PlayScreen implements Screen {
	
	private MyStage stage;
	private TextureAtlas atlas;
	
	private Land land;
	public Dino dino;
	
	float duraAddPipe;
	float duraAddCloud;
		
	public static Label labelScore;
	public Button btnPlay, btnScreenPause;
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
	int iCloud = 0;
	int iPlant = 0;
			
	public PlayScreen(DinoRunner game) {		
		
		this.game = game;		
		screenW = DinoRunner.VIEWPORT.x;
		screenH = DinoRunner.VIEWPORT.y;
		
		stage = new MyStage(0, 0, true);
		stage.setPlayScreen(this);		
		game.manager.load("images/sprites.atlas", TextureAtlas.class);
		game.manager.finishLoading();
		atlas = game.manager.get("images/sprites.atlas", TextureAtlas.class);			    
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
        duraAddCloud = 0;
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
	    int screenSize = Gdx.graphics.getWidth();
	    if(screenSize >= 320){
	    	btnStylePause.up = skin.getDrawable("buttons/screenpause");	    	
	    } else{	    	
	    	btnStylePause.up = skin.getDrawable("buttons/small_screenpause");
	    }
                                        
        btnScreenPause = new Button(btnStylePause); 
        if(screenSize < 320){
        	btnScreenPause.setSize(35, 35);	    	
	    }
        
        btnRestart = new Button(skin.getDrawable("buttons/restart"));
                                    
        btnScreenPause.setPosition(screenW - btnScreenPause.getWidth() - btnScreenPause.getWidth(), screenH - btnScreenPause.getHeight());        
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
         
        btnScreenPause.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {                    
                return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if(config.state != GameState.GAME_PAUSED){
                	config.state = GameState.GAME_PAUSED;
                	btnScreenPause.setVisible(false);
                	showPlay(true);
                }else{
                	config.state = GameState.GAME_RUNNING;
                }
            }
        });
        
        stage.addActor(btnScreenPause);        
        stage.addActor(btnRestart);
	}
	
	public static void muteFX(){
	    config.volume = 0.0f;
	}
	public static void normalizeFX(){
		config.volume = 1.0f;
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
	
	private void addLand() {
		land = new Land(atlas.findRegion("lands/land"));
		land.setY(200);
		config.landY = land.getY() + land.getHeight() - 15;		
		stage.addActor(land);
	}
	
	private void addBird() {
		TextureRegion[] regions = new TextureRegion[] { atlas.findRegion("dinos/left"),
				atlas.findRegion("dinos/right") };
		
		dino = new Dino(regions);
		dino.setPosition(screenW/2 - dino.getWidth()/2, config.landY);
		stage.addActor(dino);
	}
	
	private void addPipe() {	    
	    Plant pipe2 = new Plant(atlas.findRegion("plants/big5"), dino, true);
	    pipe2.setZIndex(1);
	    float x = screenW + 10;
	    float y = config.landY;
	    	    
	    pipe2.setPosition(x, y);
	    
	    stage.addActor(pipe2);
	    
		land.setZIndex(pipe2.getZIndex());
		dino.setZIndex(land.getZIndex());
		labelScore.setZIndex(dino.getZIndex());
	}
	
	private void addCloud() {	    
	    Cloud cloud = new Cloud(atlas.findRegion("cloud/cloud"));
	    float x = screenW + 10;
	    float y = config.landY + (40 * random(8, 12));
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
			if (dino.isDie){
	    		if (dino.score > getHighScore()) {
					setHighScore(dino.score);
				}
	    		config.state = GameState.GAME_OVER;    		
	    	}
	    	else {			
		    	duraAddPipe += delta;		    	
		    	if(iPlant == 0){
		    		iPlant = random(-3, 5);
		    	}
		    	if (duraAddPipe > config.kTimeAddPipe + 0.1f * iPlant){
		    		
		    		iPlant = 0;
		    		duraAddPipe = 0;
		    		addPipe();
		    	}
		    	
		    	duraAddCloud += delta;
		    	if(iCloud == 0){
		    		iCloud = random(2, 5);
		    	}
		    	if (duraAddCloud > config.kTimeAddPipe * iCloud){
		    		iCloud = 0;
		    		duraAddCloud = 0;
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
    
    public int random(int min, int max)	{
    	Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
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