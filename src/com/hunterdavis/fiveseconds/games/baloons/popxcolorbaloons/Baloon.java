package com.hunterdavis.fiveseconds.games.baloons.popxcolorbaloons;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;

import com.hunterdavis.fiveseconds.gameutils.rendering.Effects;
import com.hunterdavis.fiveseconds.gameutils.rendering.renderMath;

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
	int poppingFramesRemaining;
	Boolean popped;
	Random random;

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
		poppingFramesRemaining = 3;
		popped = false;
		random = new Random();
	}

	public void pop() {
		popped = true;
	}

	// if the given point is within the baloon's bounding box
	public Boolean isPointWithinBaloon(float xLoc, float yLoc) {

		float overallDistance = renderMath.fdistance(xLoc, yLoc, xLocation,
				yLocation);
		if (overallDistance > size) {
			return false;
		} else {
			return true;
		}
	}

	public void updateXandYLoc(int xLoc, int yLoc) {
		xLocation = xLoc;
		yLocation = yLoc;
		drawableRect = new RectF(xLoc - size, yLoc + size, xLoc + size, yLoc
				- size);
	}

	public void updateSize(int initSize) {
		size = initSize;
		drawableRect = new RectF(xLocation - size, yLocation + size, xLocation
				+ size, yLocation - size);
	}

	public boolean shouldThisBaloonDie() {
		if (popped == true) {
			poppingFramesRemaining -= 1;
			updateSize(size + 5);
		}
		if (poppingFramesRemaining <= 0) {
			return true;
		}
		return false;
	}

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

		// draw the baloon oval
		if ((popped == false) || (poppingFramesRemaining >= 1)) {
			paint.setColor(color);
			paint.setStyle(Style.FILL);
			canvas.drawOval(drawableRect, paint);
		}

		// draw second from last frame
		if (poppingFramesRemaining == 1) {
			drawBaloonCracks(canvas, 10);
		}

	}

	private void drawBaloonCracks(Canvas canvas, int numCracks) {
		Effects.drawCracks(canvas, xLocation - size, xLocation + size,
				yLocation + size, yLocation - size, numCracks, random);
	}
}
