package com.hunterdavis.fiveseconds.games.baloons.popxcolorbaloons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.hunterdavis.fiveseconds.gameutils.rendering.GameCanvasThread;
import com.hunterdavis.fiveseconds.gameutils.rendering.GameSurfaceView;

class baloonPanel extends GameSurfaceView implements SurfaceHolder.Callback {
	// member variables
	@SuppressWarnings("unused")
	private GameCanvasThread canvasthread;

	public Boolean surfaceCreated;
	public Context mContext;
	private int mWidth = 0;
	private int mHeight = 0;
	private boolean gameOver = false;
	List<Baloon> baloons = new ArrayList<Baloon>();
	Paint paint = null;

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

	public void drawBaloon(Baloon baloon, Canvas canvas, Paint paint) {

		paint.setColor(Color.BLACK);

		// first draw a 'string'
		canvas.drawLine(baloon.xLocation, baloon.yLocation, baloon.xLocation,
				baloon.yLocation + baloon.tailLength, paint);

		// draw a little tail on the string
		if (baloon.leftTail) {
			canvas.drawLine(baloon.xLocation, baloon.yLocation
					+ baloon.tailLength, baloon.xLocation
					- (baloon.tailLength / 8), baloon.yLocation
					+ baloon.tailLength + (baloon.tailLength / 8), paint);
		} else {
			canvas.drawLine(baloon.xLocation, baloon.yLocation
					+ baloon.tailLength, baloon.xLocation
					+ (baloon.tailLength / 8), baloon.yLocation
					+ baloon.tailLength + (baloon.tailLength / 8), paint);
		}
		paint.setColor(baloon.color);
		paint.setStyle(Style.FILL);
		canvas.drawOval(baloon.drawableRect, paint);
	}

	public void drawBaloons(Canvas canvas, Paint paint) {
		for (int i = 0; i < baloons.size(); i++) {
			drawBaloon(baloons.get(i), canvas, paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (getHolder()) {

			int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				return true;
			} else if (action == MotionEvent.ACTION_MOVE) {
				return true;
			} else if (action == MotionEvent.ACTION_UP) {
				return true;
			}
			return true;
		}
	}

	public baloonPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		surfaceCreated = false;

		baloons.add(new Baloon(32, 32, Color.GREEN, 15));

		getHolder().addCallback(this);
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		//
		if (surfaceCreated == false) {
			createThread(holder);
			// Bitmap kangoo = BitmapFactory.decodeResource(getResources(),
			// R.drawable.kangoo);
			surfaceCreated = true;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		surfaceCreated = false;

	}

	// we update the position of the text lines on screen in updateGameState
	public void updateGameState() {

		if (gameOver == true) {
			return;
		}

		// update current line a tick
		updateCurrentLineTick();
	}

	public void updateCurrentLineTick() {

	}

	public void doLose() {
		// quit to mainmenu
		((Activity) mContext).finish();
	}

	@Override
	public void onDraw(Canvas canvas) {

		mWidth = canvas.getWidth();
		mHeight = canvas.getHeight();

		if (paint == null) {
			paint = new Paint();
			paint.setTextAlign(Paint.Align.CENTER);
		}

		paint.setColor(Color.WHITE);
		// clear the screen with the black painter.
		canvas.drawRect(0, 0, mWidth, mHeight, paint);

		// draw game over if game over
		if (gameOver == true) {
			paint.setColor(Color.WHITE);
			paint.setTextSize(30);
			canvas.drawText("Game Over", (mWidth / 2), mHeight / 4, paint);
		} else {
			drawBaloons(canvas, paint);
		}
	}

} // end class