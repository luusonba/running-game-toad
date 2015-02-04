package com.tai.flappy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
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
	
	public PlayScreen(FlappyBird game)
	{
		stage = new MyStage(0, 0, true);
		stage.setPlayScreen(this);
		
		game.manager.load("data/flappy.txt", TextureAtlas.class);
		game.manager.finishLoading();

		atlas = game.manager.get("data/flappy.txt", TextureAtlas.class);
		
	}
	
	@Override
    public void show()
    {
        // set the stage as the input processor
        Gdx.input.setInputProcessor( stage );
        Gdx.input.setCatchBackKey(true);

        resetGame();
        
    }

	public void resetGame()
	{
		stage.clear();
        duraAddPipe = 0;
        addBackground();
        addLand();
        addBird();
        addLabelScore();
	}
	
	private void addBackground()
	{
        Image bg = new Image(atlas.findRegion("background"));
        stage.addActor(bg);
	}
	
	private void addLand()
	{
		land = new Land(atlas.findRegion("land"));		
		stage.addActor(land);
	}
	
	private void addBird()
	{
		TextureRegion[] regions = new TextureRegion[] { atlas.findRegion("bird1"),
				atlas.findRegion("bird2"), atlas.findRegion("bird3") };
		
		bird = new Bird(regions);
		//bird.setPosition(FlappyBird.VIEWPORT.x/2 - bird.getWidth()/2, FlappyBird.VIEWPORT.y/2);
		bird.setPosition(FlappyBird.VIEWPORT.x/2 - bird.getWidth()/2, land.getY() + land.getHeight());
		
		stage.addActor(bird);
	}
	
	private void addLabelScore()
	{
		LabelStyle textStyle = new LabelStyle();
		textStyle.font = new BitmapFont(Gdx.files.internal("data/flappyfont.fnt"), Gdx.files.internal("data/flappyfont.png"), false);

		labelScore = new Label("0",textStyle);
		labelScore.setPosition(FlappyBird.VIEWPORT.x/2 - labelScore.getWidth()/2, FlappyBird.VIEWPORT.y - labelScore.getHeight());
		
		stage.addActor(labelScore);
	}
	
	private void addPipe()
	{
	    //random cho Hole lech len va xuong
	    //random lech
	    //int r = config.random(0, 4);
	    //float dy = r * 10; //random 0, 10, 20, 30, 40
	    
	    //random lech len hay xuong
	    //r = config.random(0, 1);
	    //if (r==0) dy = -dy;
	    
	    
	    //add pipe1 ben tren vaf pipe2 ben duoi...khoang cach giua co dinh la kHoleBetweenPipe px
	    //tam hole giua canh tren man hinh va canh tren Land...
	    //sau nay random lech len , lech xuong sau...
	    /*Pipe pipe1 = new Pipe(atlas.findRegion("pipe1"), bird, true);
	    pipe1.setZIndex(1);
	    float x = FlappyBird.VIEWPORT.x;
	    float y = (FlappyBird.VIEWPORT.y - config.kLandHeight)/2 + config.kLandHeight + config.kHoleBetweenPipes/2;
	    pipe1.setPosition(x, y + dy);
	    
	    Pipe pipe2 = new Pipe(atlas.findRegion("pipe2"), bird, false);
	    pipe2.setZIndex(1);
	    y = (FlappyBird.VIEWPORT.y - config.kLandHeight)/2 + config.kLandHeight - pipe2.getHeight() - config.kHoleBetweenPipes/2;
	    pipe2.setPosition(x, y + dy);*/
	    
	    /*Pipe pipe1 = new Pipe(atlas.findRegion("pipe1"), bird, true);
	    pipe1.setZIndex(1);
	    float x = FlappyBird.VIEWPORT.x;
	    float y = (FlappyBird.VIEWPORT.y - config.kLandHeight)/2 + config.kLandHeight + config.kHoleBetweenPipes/2;
	    pipe1.setPosition(x, y + dy);*/
	    
	    Pipe pipe2 = new Pipe(atlas.findRegion("pipe2"), bird, true);
	    pipe2.setZIndex(1);
	    float x = FlappyBird.VIEWPORT.x;
	    float y = (FlappyBird.VIEWPORT.y - config.kLandHeight)/2 + config.kLandHeight - pipe2.getHeight() - config.kHoleBetweenPipes/2;
	    pipe2.setPosition(x, y);
	    
	    //stage.addActor(pipe1);
	    stage.addActor(pipe2);
	    
		land.setZIndex(pipe2.getZIndex());
		bird.setZIndex(land.getZIndex());
		labelScore.setZIndex(bird.getZIndex());

	}
	
    @Override
    public void resize(
        int width,
        int height )
    {
    	// resize the stage
//    	stage.setViewport(width, height, false );
    	stage.setViewport( FlappyBird.VIEWPORT.x, FlappyBird.VIEWPORT.y, false );
    	
    }

    @Override
    public void render(
        float delta )
    {
    	
    	if (bird.isDie)
    	{
    		land.clearActions();
    	}
    	else
    	{
	    	duraAddPipe += delta;
	    	if (duraAddPipe > config.kTimeAddPipe)
	    	{
	    		duraAddPipe = 0;
	    		addPipe();
	    	}
    	}
    	
        // update the action of actors
        stage.act( delta );

        // clear the screen with the given RGB color (black)
        Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );

        // draw the actors
        stage.draw();
    }

    @Override
    public void hide()
    {
        // dispose the resources by default
        dispose();
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void dispose()
    {
    }
}