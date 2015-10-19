package com.freeup.dino.runner.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.freeup.dino.runner.DinoRunner;
import com.freeup.dino.runner.service.ActionResolver;
import com.freeup.dino.runner.service.AdsController;

public class DesktopLauncher implements
	AdsController, ActionResolver {
	public static void main(String[] arg) {

		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Dino Runner";
		cfg.width = 1000;
		cfg.height = 2000;
		new LwjglApplication(new DinoRunner(null, null), cfg);
	}

	@Override
	public boolean getSignedInGPGS() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loginGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitScoreGPGS(int score) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getLeaderboardGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getAchievementsGPGS() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showBannerAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hideBannerAd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWifiConnected() {
		// TODO Auto-generated method stub
		return false;
	}
}