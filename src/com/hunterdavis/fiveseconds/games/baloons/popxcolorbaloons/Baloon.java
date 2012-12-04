package com.hunterdavis.fiveseconds.games.baloons.popxcolorbaloons;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;

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
		drawableRect = new RectF(xLoc - size, yLoc + size, xLoc + size, yLoc
				- size);
		tailLength = initSize * 3;
		leftTail = new Random().nextBoolean();
	}

	public void updateXandYLoc(int xLoc, int yLoc) {
		xLocation = xLoc;
		yLocation = yLoc;
		drawableRect = new RectF(xLoc - size, yLoc + size, xLoc + size, yLoc
				- size);
	}

	// draw the baloon on the canvas with the given paint
	public void drawBaloon(Canvas canvas, Paint paint) {

		paint.setColor(Color.BLACK);

		// first draw a 'string'
		canvas.drawLine(xLocation, yLocation + size, xLocation, yLocation
				+ tailLength, paint);

		// draw a little tail on the string
		if (leftTail) {
			canvas.drawLine(xLocation, yLocation + tailLength, xLocation
					- (tailLength / 8), yLocation + tailLength
					+ (tailLength / 8), paint);
		} else {
			canvas.drawLine(xLocation, yLocation + tailLength, xLocation
					+ (tailLength / 8), yLocation + tailLength
					+ (tailLength / 8), paint);
		}
		paint.setColor(color);
		paint.setStyle(Style.FILL);
		canvas.drawOval(drawableRect, paint);
	}
}
