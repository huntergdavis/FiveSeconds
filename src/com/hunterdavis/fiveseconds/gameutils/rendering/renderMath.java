package com.hunterdavis.fiveseconds.gameutils.rendering;

public class renderMath {
	
	public static float fdistance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
}
