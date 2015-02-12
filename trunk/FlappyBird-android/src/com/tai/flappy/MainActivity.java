package com.tai.flappy;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); 
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = false;
        
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        
        int width = displaymetrics.widthPixels;
        int height = displaymetrics.heightPixels;
        View view = initializeForView(new FlappyBird(width, height), cfg);
        view.getLayoutParams().height = 240;
        view.getLayoutParams().width = 320;
    }
}