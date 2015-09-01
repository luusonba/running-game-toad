package com.freeup.dino.runner.screen;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
		
	private Label labelScore;
	private Label labelHiScore;
	private Label labelCountDJ;
	private Label labelDJ;
	private Table tableTop;
	private Preferences prefs;
	private int minR = -3;
	private int maxR = 5;
	
	private int score;
	private float wScore;
    	
	public class GameState {
		public static final int GAME_START = 0;
	    public static final int GAME_RUNNING = 1;
	    public static final int GAME_PAUSED = 2;
	    public static final int GAME_OVER = 3;
	}
		
	private float screenW = 0;
	private float screenH = 0;
	private int iCloud = 0;
	private int iPlant = 0;
	private final float CONST_COLOR = 247/255f;
	
	private String CONST_STR_START = "TAP TO START";
	private String CONST_STR_DINO = "DINO RUNNER";
	private String CONST_STR_COUNTDJ = "2JUMP: ";
	private String CONST_STR_DJ = "DOUBLE JUMP!";
	private String CONST_STR_HI = "HI ";
	private String CONST_STR_SPACE = " ";
	
	public PlayScreen(DinoRunner game) {		
		screenW = game.VIEWPORT.x;
		screenH = game.VIEWPORT.y;
		stage = new MyStage(0, 0, true);
		stage.setPlayScreen(this);
		game.manager.load("images/sprites.atlas", TextureAtlas.class);
		game.manager.finishLoading();
		atlas = game.manager.get("images/sprites.atlas", TextureAtlas.class);
		prefs = Gdx.app.getPreferences("firstrunner");
		if (!prefs.contains("highScore")) {
			prefs.putInteger("highScore", 0);
		}
	}
	
	public void setHighScore(long val) {
		prefs.putLong("highScore", val);
		prefs.flush();
	}

	public long getHighScore() {
		return prefs.getLong("highScore");
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
        pipe = null;
        land = null;
        dino = null;
        addLand();        
        addSubLand();
        config.landY = land.getY() + land.getHeight() - 15;	
        addBird();               
        addButton();
        addLabel();
                
        if(config.state == GameState.GAME_START){
        	addScreenPlay();
        }
	}
	
	public void showPlay(boolean isShow) {
		tableTop.setVisible(isShow);
	}
		
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
		
	private void addLabel() {
		LabelStyle textStyle = new LabelStyle();
		
		textStyle.font = new BitmapFont(Gdx.files.internal("font/score.fnt"),
				Gdx.files.internal("font/score.png"), false);
		labelScore = new Label("0",textStyle);
		wScore = labelScore.getWidth() * 4;
		labelScore.setPosition(screenW - wScore / 2 - 20,
				screenH - labelScore.getHeight() - 10);
		
		textStyle.font = new BitmapFont(Gdx.files.internal("font/hiscore.fnt"),
				Gdx.files.internal("font/hiscore.png"), false);
		labelHiScore = new Label(CONST_STR_HI + getHighScore(), textStyle);		
		labelHiScore.setPosition(screenW - 2 * wScore - 50,
				screenH - labelHiScore.getHeight() - 10);
		
		textStyle.font = new BitmapFont(Gdx.files.internal("font/dj.fnt"),
				Gdx.files.internal("font/dj.png"), false);
		labelCountDJ = new Label(CONST_STR_COUNTDJ + config.doubleJump, textStyle);		
		labelCountDJ.setPosition(10, screenH - labelHiScore.getHeight() - 10);
		
		labelDJ = new Label(CONST_STR_DJ, textStyle);		
		labelDJ.setPosition(screenW / 2 - labelDJ.getWidth() / 2, screenH - screenH / 4);
		
		stage.addActor(labelHiScore);
		stage.addActor(labelScore);
		stage.addActor(labelCountDJ);
		stage.addActor(labelDJ);
		showDJ(false);
	}
	
	public void showDJ(boolean isJump) {
		labelDJ.setVisible(isJump); bo cho n ay remove bot label ra khi start
	}
	
	public void updateScore() {
		score ++;
		labelScore.setText("" + score);
	}
	
	public void updateCountDJ() {
		labelCountDJ.setText(CONST_STR_COUNTDJ + config.doubleJump);
	}
	
	private void addScreenPlay() {
		LabelStyle textStyle = new LabelStyle();
		textStyle.font = new BitmapFont(Gdx.files.internal("font/dino.fnt"), Gdx.files.internal("font/dino.png"), false);
		
		Label labelTitle;
		labelTitle = new Label(CONST_STR_DINO, textStyle);
		labelTitle.setFontScale((float)screenW/480);
        		
		tableTop = new Table();
		tableTop.add(labelTitle).row();
		
		textStyle.font = new BitmapFont(Gdx.files.internal("font/tap.fnt"), Gdx.files.internal("font/tap.png"), false);
		labelTitle = new Label(CONST_STR_SPACE, textStyle);
		labelTitle.setFontScale((float)screenW/480);
		tableTop.add(labelTitle).row();
		
		tableTop.setPosition(screenW/2, screenH - screenH/4);
	    stage.addActor(tableTop);
	    	    
		labelTitle = new Label(CONST_STR_START, textStyle);
		labelTitle.setFontScale((float)screenW/480);
		tableTop.add(labelTitle).row();
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
		pipe.setPlayScreen(this);
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
    	float scale = config.scale;        
        float w = (float)config.VIRTUAL_WIDTH * scale;
        float h = (float)config.VIRTUAL_HEIGHT * scale;
        Gdx.gl.glViewport((width - (int)w)/2, (height - (int)h)/2, (int)w, (int)h);
    }
    	
    @Override
    public void render (float delta ){
    	if (delta > 0.1f){
    	    delta = 0.1f;
    	}
    	Gdx.gl.glClearColor(CONST_COLOR, CONST_COLOR, CONST_COLOR, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	switch (config.state) {
    	case GameState.GAME_START:
	        stage.act(delta);
	        
	        if(stage.getCamera().position.x - config.VIRTUAL_WIDTH / 2> subLand.getX()){	        	
	        	land.setPosition(subLand.getX(),200);
	            subLand.setPosition(land.getX()+ config.VIRTUAL_WIDTH, 200);
	        }	        
    		break;
		case GameState.GAME_RUNNING:
	        if(stage.getCamera().position.x - config.VIRTUAL_WIDTH / 2 > subLand.getX()){
	            land.moveleft.setDuration(config.kmoveLeftDura);
				land.setPosition(subLand.getX(),200);
				subLand.moveleft.setDuration(config.kmoveLeftDura);
	            subLand.setPosition(land.getX() + config.VIRTUAL_WIDTH, 200);
	        }
			
			if (dino.isDie){
	    		if (score > getHighScore()) {
					setHighScore(score);
				}	    
	    		oldScore = 0;
	    		score = 0;
	    		config.kmoveLeftDura = 0.50f;
	    		config.kfallDura = 0.20f;
	    		config.doubleJump = 2;
	    		config.canJump = true;
	    		config.state = GameState.GAME_OVER;    		
	    	}
	    	else {
	    		if(score > oldScore && score % 10 == 0){
	    			oldScore = score;
	    			if(config.kmoveLeftDura > config.maxSpeed){
	    				config.kmoveLeftDura = config.kmoveLeftDura - 0.0075f;
	    			}
	    			
	    			if(score % 20 == 0 && config.kfallDura < config.maxFallDura){
	    				config.kfallDura = config.kfallDura - 0.005f;	    				    			
		    		}
	    			
	    			if(score % 50 == 0){
	    				config.doubleJump = config.doubleJump + 1;
	    				DinoRunner.sounds.get(config.SoundScore).play(config.volume);
	    				if(score == 50 || score == 100 || score == 200){
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
	    	}
	        // update the action of actors
	        stage.act(delta);
			break;
		case GameState.GAME_PAUSED:			
			stage.act(0f);
	        // clear the screen with the given RGB color (black)
			break;
		case GameState.GAME_OVER:
			stage.act(0f);
	        // clear the screen with the given RGB color (black)
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