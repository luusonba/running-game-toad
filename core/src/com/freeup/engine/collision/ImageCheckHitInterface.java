package com.freeup.engine.collision;

import com.badlogic.gdx.graphics.Pixmap;

/**
 * @author phuongnh
 */
public interface ImageCheckHitInterface {
	
	public void setPolygonAround(Pixmap pixmap);
	public void setEnableHitTest(boolean enable);
	public boolean getEnableHitTest();
	
	public void setX(float x);
	public void setY(float y);
	public void setPosition(float x, float y);
	
	public void setScale(float scaleX, float scaleY);
	public void setScaleX(float scaleX);
	public void setScaleY(float scaleY);
	
	public void setRotation(float degrees);
	
	public void setOrigin(float originX, float originY);
	public void setOriginX(float originX);
	public void setOriginY(float originY);
}
