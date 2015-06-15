package com.freeup.engine.collision;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.freeup.dino.runner.utils.config;
/**
 * @author phuongnh
 */
public class ImageCheckHit extends Image implements ImageCheckHitInterface{
	public PolygonAround pA;
	private boolean enableHitTest = true;
	
	public ImageCheckHit() {
		super();
	}
	
	public ImageCheckHit(Pixmap pixmap) {
		super();
		pA = new PolygonAround();
		initImageAndBound(pixmap);
	}

	public void initImageAndBound(Pixmap pixmap) {
		pA.setPolygonDimension(pixmap);
		
		/*TextureRegion tR = new TextureRegion(new Texture(pixmap));
		TextureRegionDrawable tRD = new TextureRegionDrawable(tR);
		setDrawable(tRD);
		setWidth(tRD.getMinWidth());
		setHeight(tRD.getMinHeight());*/
	}

	public ImageCheckHit(TextureRegion region) {
		super(region);
		pA = new PolygonAround();
		Texture texture = region.getTexture();
		if (!texture.getTextureData().isPrepared()) {
		    texture.getTextureData().prepare();
		}
		initImageAndBound(texture.getTextureData().consumePixmap());
	}

	public ImageCheckHit(Texture texture) {
		super(texture);
		pA = new PolygonAround();
	}
	
	@Override
	public void setX(float x) {
		super.setX(x);
		if (enableHitTest)
			pA.polygonDimension.setPosition(x, pA.polygonDimension.getY());
	}

	@Override
	public void setY(float y) {
		super.setY(y);
		if (enableHitTest)
			pA.polygonDimension.setPosition(pA.polygonDimension.getX(), y);
	}
	
	@Override
	public void setPosition(float x, float y) {
		super.setPosition(x, y);
		if (enableHitTest){
			pA.polygonDimension.setPosition(x * config.scale, y * config.scale);
		}
	}
	
	@Override
	public void setScale(float scaleX, float scaleY) {
		super.setScale(scaleX, scaleY);
		if (enableHitTest)
			pA.polygonDimension.setScale(scaleX, scaleY);
	}
	
	@Override
	public void setScaleX(float scaleX) {
		super.setScaleX(scaleX);
		if (enableHitTest)
			pA.polygonDimension.setScale(scaleX, pA.polygonDimension.getScaleY());
	}

	@Override
	public void setScaleY(float scaleY) {
		super.setScaleY(scaleY);
		if (enableHitTest)
			pA.polygonDimension.setScale(pA.polygonDimension.getScaleX(), scaleY);
	}

	@Override
	public void setRotation(float degrees) {
		super.setRotation(degrees);
		pA.polygonDimension.setRotation(degrees);
	}

	@Override
	public boolean remove() {
		return super.remove();
	}

	@Override
	public void setOriginX(float originX) {
		super.setOriginX(originX);
		if (enableHitTest)
			pA.polygonDimension.setOrigin(originX, getOriginY());
	}

	@Override
	public void setOriginY(float originY) {
		super.setOriginY(originY);
		if (enableHitTest)
			pA.polygonDimension.setOrigin(getOriginX(), originY);
	}

	@Override
	public void setOrigin(float originX, float originY) {
		super.setOrigin(originX, originY);
		if (enableHitTest)
			pA.polygonDimension.setOrigin(originX, originY);
	}

	@Override
	public void setEnableHitTest(boolean enable) {
		this.enableHitTest = enable;
	}
	
	@Override
	public boolean getEnableHitTest() {
		return enableHitTest;
	}

	@Override
	public void setPolygonAround(Pixmap pixmap) {
		pA.setPolygonDimension(pixmap);
	}
	
	public boolean checkHit(ImageCheckHit iCH) {
		return pA.checkHit(iCH.pA);
	}
}