package com.freeup.dino.runner.utils;

public class config {

	// key de nap cac sound vao mang va lay sound ra de play
	public static String SoundJump = "jump";
	public static String SoundScore = "score";
	public static String SoundHit = "hit";

	// Volume
	public static float volume = 1.0f;

	public static final int VIRTUAL_WIDTH = 480;
	public static final int VIRTUAL_HEIGHT = 800;

	// chieu cao va chieu dai thiet ke cua hinh land.png
	public static int kLandHeight = 112;
	public static float kLandWidth = 336;

	// thoi gian move sang trai khoang kLandWidth
	public static float kmoveLeftDura = 0.46f;
	public static float kfallDura = 0.20f;

	public static float maxSpeed = 0.31f;
	public static float maxFallDura = 0.15f;

	// chieu cao va thoi gian khi bay len do "tap"
	public static int kjumpHeight = 250;

	// Count double jump
	public static int doubleJump = 2;

	// Can double jump
	public static boolean canJump = true;

	// position land Y
	public static float landY = 0;
	public static int state = -1;

	public static long dieTime = 0;
	public static float scale = 1f;
}
