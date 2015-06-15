package com.freeup.engine.collision;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class InputHelper {
	private static boolean daNhan = false;
	private static int keyDangXuLy = Keys.UNKNOWN;

	/*
	 * Kiểm tra nếu 1 nút nhấn thì trả v�?.
	 * Nhấn đè thì chỉ trả v�? 1 lần.
	 */
	public static boolean pressKey(int newKey) {
		if (Gdx.input.isKeyPressed(newKey) && (daNhan != true)) {
			daNhan = true;
			keyDangXuLy = newKey;
			return true;
		}
		if (Gdx.input.isKeyPressed(keyDangXuLy) == false) {
			daNhan = false;
		}
		return false;
		
	}
	
	/*
	 * Kiểm tra nếu nhấn 1 nút thì trả v�?.
	 * Nhấn đè thì trả v�? liên tiếp.
	 */
	public static boolean pressKeyLong(int key) {
		return Gdx.input.isKeyPressed(key);
	}
}
