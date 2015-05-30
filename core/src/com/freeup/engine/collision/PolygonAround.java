package com.freeup.engine.collision;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.freeup.dino.runner.screen.HT2;
import com.freeup.dino.runner.screen.PlayScreen;
/**
 * @author phuongnh
 */
public class PolygonAround {
	/*
	 * polygonDimension là tập hợp điểm bao quanh Image.
	 * Tọa độ các điểm tính theo width height của image.
	 */
	public Polygon polygonDimension;
	
	public void setPolygonDimension(Pixmap pixmap) {
		if (pixmap == null){
			System.out.println("fffffffffff");
			return;
		}
		
		if (pixmap != null){
			System.out.println("gggggggggg");
		
		}
		float[] f = HT2.boundaryHelper.createNewAround(pixmap);
		System.out.println("1111111111111111111111");
		if(f == null){
			System.out.println("4444444444444");
		}
		polygonDimension = new Polygon(f);
	}
	
	public Array<Vector2> getListVector2() {
		System.out.println("22222222222222222");
		Array<Vector2> listPoint = new Array<Vector2>();
		float[] f = polygonDimension.getTransformedVertices();
		
		for (int i = 0; i< f.length; i+=2)
			listPoint.add(new Vector2(f[i], f[i+1]));
		return listPoint;
	}
	public Rectangle getBoundingRectangle() {
		System.out.println("1111111111111111111111");
		return polygonDimension.getBoundingRectangle();
	}
	
	public boolean checkHit(PolygonAround pA) {
		System.out.println("33333333333333333");
		if (getBoundingRectangle().overlaps(pA.getBoundingRectangle())) {
			for (Vector2 v2 : getListVector2())
				if (Intersector.isPointInPolygon(pA.getListVector2(), v2)) {
					return true;
				}
		}
		return false;
	}
}