package com.freeup.engine.collision;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.freeup.dino.runner.screen.PlayScreen;
/**
 * @author phuongnh
 */
public class PolygonAround {
	
	public Polygon polygonDimension;
	
	public void setPolygonDimension(Pixmap pixmap) {
		if (pixmap == null)
			return;
		float[] f = PlayScreen.boundaryHelper.createNewAround(pixmap);
		polygonDimension = new Polygon(f);
	}
	
	public Array<Vector2> getListVector2() {
		Array<Vector2> listPoint = new Array<Vector2>();
		float[] f = polygonDimension.getTransformedVertices();
		
		for (int i = 0; i< f.length; i+=2)
			listPoint.add(new Vector2(f[i], f[i+1]));
		return listPoint;
	}
	public Rectangle getBoundingRectangle() {
		return polygonDimension.getBoundingRectangle();
	}
	
	public boolean checkHit(PolygonAround pA) {		
		if (getBoundingRectangle().overlaps(pA.getBoundingRectangle())) {
			for (Vector2 v2 : getListVector2())
				if (Intersector.isPointInPolygon(pA.getListVector2(), v2)) {
					return true;
				}
		}
		return false;
	}
}