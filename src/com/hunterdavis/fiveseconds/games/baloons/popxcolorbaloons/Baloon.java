package com.hunterdavis.fiveseconds.games.baloons.popxcolorbaloons;

import java.util.Random;

import android.graphics.RectF;

// each credits line is a tiny inner class for storing credits lines
class Baloon {
	int xLocation;
	int yLocation;
	int size;
	int age;
	int color;
	RectF drawableRect;
	int tailLength;
	Boolean leftTail;

	Baloon(int xLoc, int yLoc, int initColor, int initSize) {
		xLocation = xLoc;
		yLocation = yLoc;
		age = 0;
		color = initColor;
		size = initSize;
		drawableRect = new RectF(xLoc - size, yLoc + size, xLoc + size,
				yLoc - size);
		tailLength = initSize * 3;
		leftTail = new Random().nextBoolean();
	}
}
