package com.freeup.engine.collision;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.math.Vector2;

public class BoundaryHelper {

	private Pixmap pixMap;
	private Vector2 vStart, v1, v2;
	private int huongDiChuyenPx = 2;//0:trai, 1:len, 2:phai, 3:xuong.
	private int huongNhinPx = 1;
	public List<Vector2> lV;
	private float[] listF;
	private Color tempColor = new Color();
	public BoundaryHelper() {
		super();
		v1 = new Vector2();
		v2 = new Vector2();
		lV = new ArrayList<Vector2>();
	}
	
	public float[] createNewAround(Pixmap pixMap) {
		if (pixMap == null)
			return null;
		this.pixMap = pixMap;
		lV.clear();
		timPointDau();
		veBao();
		reducePixelContinue();
//		reducePixelInnerConvexPolygon();
		drawBoundOnPixmap();
		createListFloat();
		
		return listF;
	}
		
	private void createListFloat() {
		listF = new float[lV.size() * 2];
		for (int i = 0; i < lV.size(); i++) {
			listF[i*2] = lV.get(i).x;
			listF[i*2+1] = lV.get(i).y;
		}
	}

	private void timPointDau() {
		int h2 = pixMap.getHeight() / 2;
		for (int i = 0; i < pixMap.getWidth(); i++)
			if (!checkPixelTrans(pixMap.getPixel(i, h2))) {
				v1 = new Vector2(i, h2);
				return;
			}
		for (int i = 0; i < pixMap.getWidth(); i++)
			for (int j = 0; j < pixMap.getHeight(); j++)
				if (!checkPixelTrans(pixMap.getPixel(i, j))) {
					v1 = new Vector2(i, j);
					return;
				}
	}
	
	private void veBao() {
		vStart = new Vector2(v1);
		lV.add(new Vector2(v1));
		do {
			timPointTiep();
			if ((v2.x != vStart.x) | (v2.y != vStart.y)) {
				lV.add(new Vector2(v2.x, pixMap.getHeight() - v2.y));
				v1.set(v2);
			}
		} while ((vStart.x != v2.x)  | (vStart.y != v2.y));
//		System.out.println("lV.leng : " + lV.size());
	}
	
	/**
	 * Tìm điểm tiếp theo nối thành bao.
	 * @param v1
	 * @return
	 */
	private void timPointTiep() {
		huongNhinPx = timHuongNhinDau(huongDiChuyenPx);
		if (getPixelTheoHuong())
			return;
		do {
			timHuongNhinTiep();
		} while (!getPixelTheoHuong());
	}
	
	/**
	 * Khi nhảy tới 1 pixel tiếp theo, ta có hướng di chuyển.
	 * Bắt đầu từ hướng di chuyển nhìn sang trái để lấy ra hướng nhìn.
	 * @param huongDiChuyen
	 * @return
	 */
	private int timHuongNhinDau(int huongDiChuyen) {
		if (huongDiChuyen == 0)
			return 3;
		else
			return huongDiChuyen - 1;
	}
	
	private void timHuongNhinTiep() {
		if (huongNhinPx == 3)
			huongNhinPx = 0;
		else
			huongNhinPx += 1;
	}
	
	private boolean getPixelTheoHuong() {
		switch (huongNhinPx) {
		case 0:
			if (v1.x -1 < 0)
				return false;
			if (!checkPixelTrans(pixMap.getPixel((int)v1.x - 1, (int)v1.y))) {
				v2.set((int)v1.x - 1, (int)v1.y);
				huongDiChuyenPx = huongNhinPx;
				return true;
			}
			break;
		case 1:
			if (v1.y -1 < 0)
				return false;
			if (!checkPixelTrans(pixMap.getPixel((int)v1.x, (int)v1.y - 1))) {
				v2.set((int)v1.x, (int)v1.y - 1);
				huongDiChuyenPx = huongNhinPx;
				return true;
			}
			break;
		case 2:
			if (v1.x + 1 > pixMap.getWidth()-1)
				return false;
			if (!checkPixelTrans(pixMap.getPixel((int)v1.x + 1, (int)v1.y))) {
				v2.set((int)v1.x + 1, (int)v1.y);
				huongDiChuyenPx = huongNhinPx;
				return true;
			}
			break;
		case 3:
			if (v1.y + 1 > pixMap.getHeight()-1)
				return false;
			if (!checkPixelTrans(pixMap.getPixel((int)v1.x, (int)v1.y + 1))) {
				v2.set((int)v1.x, (int)v1.y + 1);
				huongDiChuyenPx = huongNhinPx;
				return true;
			}
			break;
		}
		return false;
	}
	
	/**
	 * Giảm bớt số lượng pixel hittest.
	 * Cứ x pixel liên tiếp thì giữ lại 1.
	 */
	private void reducePixelContinue() {
		for (int i = 0; i < 3; i++)
			findAndRemovePixelContinue();
	}
	private void findAndRemovePixelContinue() {
		for (int i = lV.size() - 1; i > 0; i--)
			if (i % 2 == 1)
				lV.remove(i);
	}
	
	/**private void reducePixelInnerConvexPolygon() {
		for (int i = 0; i< 11; i++)
			findAndRemovePixelInner();
	}
	private void findAndRemovePixelInner(){
		ArrayList<Integer> aI = new ArrayList<Integer>();
		for (int i = 0; i < lV.size()-2; i++)
			if (Intersector.pointLineSide(lV.get(i), lV.get(i+1), lV.get(i+2)) == -1)
				continue;
			else
				aI.add(i+1);
			
		for (int i = aI.size() - 1; i >= 0; i--) {
			int iR = aI.get(i);
			lV.remove(iR);
		}
	}*/
	
	private void drawBoundOnPixmap() {
		for (int i = 0; i< lV.size(); i++)
			pixMap.drawPixel((int)lV.get(i).x, pixMap.getHeight() - (int)lV.get(i).y, -889192193);
	}
	
	private boolean checkPixelTrans(int iColor) {
		Color.rgba8888ToColor(tempColor, iColor);
		if (tempColor.a == 0)
			return true;
		return false;
	}
}