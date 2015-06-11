package com.freeup.dino.runner.utils;

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
    public static float kmoveLeftDura = 0.55f;
    public static float kfallDura = 0.20f;
    
    public static float scale = 1f;
    
    public static float maxFallDura = 0.15f;
    public static float maxspeed = 0.30f;
    
    //chieu cao va thoi gian khi bay len do "tap"
    public static int kjumpHeight = 250;
            
    //position land Y
    public static float landY = 0;
    
    public static int state = -1;
}
