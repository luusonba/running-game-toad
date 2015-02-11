package com.tai.flappy.utils;

import java.util.Random;

public class config {

	//key de nap cac sound vao mang va lay sound ra de play
	public static String SoundJump = "jump";
    public static String SoundScore = "score";
    public static String SoundHit = "hit";
    
    //Volume
    public static float volume = 1.0f;
    
    //chieu cao va chieu dai thiet ke cua hinh land.png
    public static int kLandHeight = 112;
    public static float kLandWidth = 336;
    
    //thoi gian move sang trai khoang kLandWidth
    public static float kmoveLeftDura = 1.5f;

    //chieu cao va thoi gian khi bay len do "tap"
    public static int kjumpHeight = 150;
    public static float kjumpDura = 0.3f;
    
    //khoang thoi gian them ong nuoc vao man hinh
    public static float kTimeAddPipe = 2;
    
    //khoang ho giua hai ong nuoc
    public static float kHoleBetweenPipes = 100;
    
    //position land Y
    public static float landY = 0;
    
    //check first tap is init
    public static boolean firstTap = false;
    
    //ham tinh random mot so trong pham vi tu min den max
	public static int random(int min, int max)
	{
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}	
}
