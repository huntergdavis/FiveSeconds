package com.hunterdavis.fiveseconds.games.baloons.popxcolorbaloons;

import java.util.ArrayList;
import java.util.HashMap;
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
	public static final int numBaloons = 18;

	@SuppressWarnings("unused")
	private GameCanvasThread canvasthread;

	public Boolean surfaceCreated;
	public Context mContext;
	private int mWidth = 0;
	private int mHeight = 0;
	private boolean gameOver = false;
	private boolean firstRun = true;
	List<Baloon> baloons = new ArrayList<Baloon>();
	List<namedColor> colors = new ArrayList<namedColor>();
	Paint paint = null;

	private class namedColor {
		int color;
		String colorName;

		namedColor(int initColor, String name) {
			color = initColor;
			colorName = name;
		}
	};

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

	public void initGameState() {
		firstRun = false;

		Random rand = new Random();

		// init colors array with some nice colors
		colors.add(new namedColor(Color.BLACK, "Black"));
		colors.add(new namedColor(Color.BLUE, "Blue"));
		colors.add(new namedColor(Color.DKGRAY, "Dark Gray"));
		colors.add(new namedColor(Color.GRAY, "Gray"));
		colors.add(new namedColor(Color.GREEN, "Green"));
		colors.add(new namedColor(Color.LTGRAY, "Light Gray"));
		colors.add(new namedColor(Color.MAGENTA, "Magenta"));
		colors.add(new namedColor(Color.RED, "Red"));
		colors.add(new namedColor(Color.YELLOW, "Yellow"));
		colors.add(new namedColor(Color.CYAN, "Cyan"));

		for (int i = 0; i < numBaloons; i++) {
			baloons.add(new Baloon(rand.nextInt(15 + (mWidth - 15)), rand
					.nextInt(15 + (mHeight - 15)), colors.get(rand
					.nextInt(colors.size())).color, 6 + rand.nextInt(20)));
		}
	}

	// we update the position of the text lines on screen in updateGameState
	public void updateGameState() {

		if (gameOver == true) {
			return;
		}

		// if first game state, init
		if ((mWidth > 0) && firstRun) {
			initGameState();
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

	public void drawBaloon(Baloon baloon, Canvas canvas, Paint paint) {

		paint.setColor(Color.BLACK);

		// first draw a 'string'
		canvas.drawLine(baloon.xLocation, baloon.yLocation + baloon.size,
				baloon.xLocation, baloon.yLocation + baloon.tailLength, paint);

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

} // end class