package com.freeup.dino.runner.screen;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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
	private Land subLand;
	private Cloud cloud;
	public Dino dino;
	private Plant pipe;
	
	private float duraAddPipe;
	private float duraAddCloud;
	private int oldScore = 0;
		
	public static Label labelScore;
	public Table tableTop;
	private Preferences prefs;
	private int minR = -4;
	private int maxR = 4;
	
	private static final int VIRTUAL_WIDTH = 480;
    private static final int VIRTUAL_HEIGHT = 800;
    private static final float ASPECT_RATIO =
        (float)VIRTUAL_WIDTH/(float)VIRTUAL_HEIGHT;
    	
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
	private ShapeRenderer render;
	
	public PlayScreen(DinoRunner game) {		
		
		this.game = game;		
		screenW = DinoRunner.VIEWPORT.x;
		screenH = DinoRunner.VIEWPORT.y;
		stage = new MyStage(0, 0, true);
		stage.setPlayScreen(this);		
		game.manager.load("images/sprites.atlas", TextureAtlas.class);
		game.manager.finishLoading();
		atlas = game.manager.get("images/sprites.atlas", TextureAtlas.class);
		prefs = Gdx.app.getPreferences("firstrunner");
		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}		
		render = new ShapeRenderer();
	}
	
	public void setHighScore(long val) {
		prefs.putLong("highScore", val);
		prefs.flush();
	}

	public long getHighScore() {
		return prefs.getLong("highScore");
	}
		
	private void checkHit() {
		if(pipe != null){
//			if (overlaps(dino.getBoundingTop(),pipe.getBounding())||
//					overlaps(dino.getBoundingMiddle(),pipe.getBounding())||
//					overlaps(dino.getBoundingBottom(),pipe.getBounding())){				
//				dino.hitMe();
//			}
			if (dino.getBoundingTop().overlaps(pipe.getBounding())||
					dino.getBoundingMiddle().overlaps(pipe.getBounding())||
					dino.getBoundingBottom().overlaps(pipe.getBounding())){				
				dino.hitMe();
			}
		}
	}
	
//	private boolean overlaps(Rectangle rectDino, Rectangle rectPlant){
//		return (((rectDino.getX()+rectDino.getWidth()>=rectPlant.getX())
//				&&rectDino.getX()+rectDino.getWidth()<=rectPlant.getX()+rectPlant.getWidth())
//				||(rectDino.getX()>=rectPlant.getX()&&rectDino.getX()<=(rectPlant.getX()+rectPlant.getWidth()))
//				)&&(rectDino.getY()>=rectPlant.getY()&&rectDino.getY()<=rectPlant.getY()+rectPlant.getHeight());
//	}
	
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
        pipe = null;
        land = null;
        dino = null;
        addLand();        
        addSubLand();
        config.landY = land.getY() + land.getHeight() - 15;	
        addBird();               
        addButton();
        addLabelScore();
                
        if(config.state == GameState.GAME_START){
        	addScreenPlay();
        	addScreenTap();
        }
	}
	
	/*private void showPlay(boolean isShow) {
		tableTop.setVisible(isShow);
	}*/
		
	private void addButton() {
		        
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
	}
	
	public static void muteFX(){
	    config.volume = 0.0f;
	}
	public static void normalizeFX(){
		config.volume = 1.0f;
	}
		
	private void addLabelScore() {
		LabelStyle textStyle = new LabelStyle();
		textStyle.font = new BitmapFont(Gdx.files.internal("font/score.fnt"), Gdx.files.internal("font/score.png"), false);

		labelScore = new Label("0",textStyle);
		labelScore.setPosition(screenW - labelScore.getWidth()/2 - 30, screenH - labelScore.getHeight() - 5);
		
		stage.addActor(labelScore);
	}
	
	private void addScreenTap() {
		LabelStyle textStyle = new LabelStyle();
		textStyle.font = new BitmapFont(Gdx.files.internal("font/tap.fnt"), Gdx.files.internal("font/tap.png"), false);
		
		Label labelTitle;
		labelTitle = new Label("TAP TO START",textStyle);
		labelTitle.setFontScale((float)screenW/480);		       	
            	/*showPlay(false);
            	config.state = GameState.GAME_RUNNING;   
            	oldScore = 0;
            	startTime = System.currentTimeMillis();*/
        		
		/*tableTop = new Table();*/		
		tableTop.add(labelTitle);
		tableTop.row();

		labelTitle.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {                    
                return true;
            }
            
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {            	
				// TODO Auto-generated method stub
				config.state = GameState.GAME_RUNNING;
            }			
		});
		
		/*tableTop.setPosition(screenW/2, screenH - screenH/4);
	    stage.addActor(tableTop);*/
	}
	
	private void addScreenPlay() {
		LabelStyle textStyle = new LabelStyle();
		textStyle.font = new BitmapFont(Gdx.files.internal("font/dino.fnt"), Gdx.files.internal("font/dino.png"), false);
		
		Label labelTitle;
		labelTitle = new Label("DINO RUNNER",textStyle);
		labelTitle.setFontScale((float)screenW/480);		       	
            	/*showPlay(false);
            	config.state = GameState.GAME_RUNNING;   
            	oldScore = 0;
            	startTime = System.currentTimeMillis();*/
        		
		tableTop = new Table();
		tableTop.add(labelTitle);
		tableTop.row();

		
		tableTop.setPosition(screenW/2, screenH - screenH/4);
	    stage.addActor(tableTop);
	}
	
	private void addLand() {
		land = new Land(atlas.findRegion("lands/land"));
		land.setY(200);
		land.setX(0);		
		stage.addActor(land);
	}
	
	private void addSubLand() {
		subLand = new Land(atlas.findRegion("lands/land"));
		subLand.setY(200);
		subLand.setX(subLand.getWidth()/2);		
		stage.addActor(subLand);
	}
	
	private void addBird() {
		TextureRegion[] regions = new TextureRegion[] { atlas.findRegion("dinos/left"),
				atlas.findRegion("dinos/right"), atlas.findRegion("dinos/fly"), atlas.findRegion("dinos/die") };
		dino = new Dino(regions);
		dino.setPosition(dino.getWidth()/2, config.landY);
		stage.addActor(dino);
	}
	
	private void addPipe() {		
		int i = random(1, 5);
		switch (i) {
		case 1:
			pipe = new Plant(atlas.findRegion("plants/plant1"), dino, true);
			break;
		case 2:
			pipe = new Plant(atlas.findRegion("plants/plant2"), dino, true);
			break;
		case 3:
			pipe = new Plant(atlas.findRegion("plants/plant3"), dino, true);
			break;
		case 4:
			pipe = new Plant(atlas.findRegion("plants/plant4"), dino, true);
			break;
		case 5:
			pipe = new Plant(atlas.findRegion("plants/plant5"), dino, true);
			break;				
		default:
			pipe = new Plant(atlas.findRegion("plants/plant5"), dino, true);
			break;
		}
		pipe.setZIndex(1);	    
	    float x = screenW + 10;
	    float y = config.landY;	    	
	    pipe.setPosition(x, y);	    
	    stage.addActor(pipe);
	    
	    land.setZIndex(pipe.getZIndex());
		dino.setZIndex(land.getZIndex());
		labelScore.setZIndex(dino.getZIndex());
		
		pipe.toBack();
	}
	
	private void addCloud() {	    
		cloud = new Cloud(atlas.findRegion("cloud/cloud"));
	    float x = screenW + 10;
	    float y = config.landY + (40 * random(8, 10));
	    cloud.setPosition(x, y);
	    
	    stage.addActor(cloud);	    
	}
	
    @Override
    public void resize(int width, int height){
        float aspectRatio = (float)width/(float)height;
        float scale = 1f;
        if(aspectRatio > ASPECT_RATIO) {
            scale = (float)height/(float)VIRTUAL_HEIGHT;
        } else if(aspectRatio < ASPECT_RATIO) {
            scale = (float)width/(float)VIRTUAL_WIDTH;
        } else {
            scale = (float)width/(float)VIRTUAL_WIDTH;
        }
        config.scaleX = (float)width/(float)VIRTUAL_WIDTH;
        config.scaleY = (float)height/(float)VIRTUAL_HEIGHT;
        float w = (float)VIRTUAL_WIDTH*scale;
        float h = (float)VIRTUAL_HEIGHT*scale;
        
        Gdx.gl.glViewport((width - (int)w)/2, (height - (int)h)/2, (int)w, (int)h);
    }
    	
    @Override
    public void render (float delta ){
    	if (delta > 0.1f){
    	    delta = 0.1f;
    	}
    	switch (config.state) {
    	case GameState.GAME_START:
	        stage.act(delta);
	        
	        if(stage.getCamera().position.x -VIRTUAL_WIDTH/2> subLand.getX()){	        	
	        	land.setPosition(subLand.getX(),200);
	            subLand.setPosition(land.getX()+VIRTUAL_WIDTH, 200);
	        }
	        
	        Gdx.gl.glClearColor( 247/255f, 247/255f, 247/255f, 1f );
	        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );	        
    		break;
		case GameState.GAME_RUNNING:
	        Gdx.gl.glClearColor( 247/255f, 247/255f, 247/255f, 1f );
	        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
	        
	        render.begin(ShapeType.Filled);
	        render.setColor(Color.RED);
	        render.rect(dino.getBoundingTop().x, dino.getBoundingTop().y, dino.getBoundingTop().width, dino.getBoundingTop().height);
	        render.rect(dino.getBoundingMiddle().x, dino.getBoundingMiddle().y, dino.getBoundingMiddle().width, dino.getBoundingMiddle().height);
	        render.rect(dino.getBoundingBottom().x, dino.getBoundingBottom().y, dino.getBoundingBottom().width, dino.getBoundingBottom().height);
	        dino.setBounding();
	        if(pipe!=null){	        	
	        	pipe.setBounding();
	        	render.rect(pipe.getBounding().x, pipe.getBounding().y, pipe.getBounding().width, pipe.getBounding().height);
	        }	        
	        render.end();
	        if(stage.getCamera().position.x -VIRTUAL_WIDTH/2> subLand.getX()){
	            land.moveleft.setDuration(config.kmoveLeftDura);
				land.setPosition(subLand.getX(),200);
				subLand.moveleft.setDuration(config.kmoveLeftDura);
	            subLand.setPosition(land.getX()+VIRTUAL_WIDTH, 200);
	        }
			
			if (dino.isDie){
	    		if (dino.score > getHighScore()) {
					setHighScore(dino.score);
				}	    
	    		oldScore = 0;
	    		config.kmoveLeftDura = 0.50f;
	    		config.kfallDura = 0.20f;
	    		config.state = GameState.GAME_OVER;    		
	    	}
	    	else {
	    		if(dino.score > oldScore && dino.score % 10 == 0){
	    			oldScore = dino.score;
	    			if(config.kmoveLeftDura > config.maxSpeed){
	    				config.kmoveLeftDura = config.kmoveLeftDura - 0.008f;
	    			}
	    			
	    			if(dino.score % 20 == 0 && config.kfallDura < config.maxFallDura){
	    				config.kfallDura = config.kfallDura - 0.005f;	    				    			
		    		}
	    			
	    			if(dino.score % 50 == 0){
	    				DinoRunner.sounds.get(config.SoundScore).play(config.volume);
	    				if(dino.score == 50 || dino.score == 100 ||dino.score == 200){
		    				minR = minR + 1;
		    			}
	    			}	
	    		}
	    			    		
		    	duraAddPipe += delta;		    	
		    	if(iPlant == 0){
		    		iPlant = random(minR, maxR);
		    	}
		    	
		    	if (duraAddPipe > config.kmoveLeftDura/0.60f + 0.1f * iPlant){
		    		iPlant = 0;
		    		duraAddPipe = 0;
		    		addPipe();
		    	}
		    	
		    	duraAddCloud += delta;
		    	if(iCloud == 0){
		    		iCloud = random(2, 5);
		    	}
		    	if (duraAddCloud > config.kmoveLeftDura/0.60f * iCloud){
		    		iCloud = 0;
		    		duraAddCloud = 0;
		    		addCloud();
		    		cloud.toBack();
		    	}
		    	checkHit();
	    	}
	        // update the action of actors
	        stage.act(delta);
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