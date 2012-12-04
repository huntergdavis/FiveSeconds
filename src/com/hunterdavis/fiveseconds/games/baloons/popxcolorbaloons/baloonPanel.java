package com.hunterdavis.fiveseconds.games.baloons.popxcolorbaloons;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.hunterdavis.fiveseconds.R;
import com.hunterdavis.fiveseconds.credits.CreditsScreen;
import com.hunterdavis.fiveseconds.gameutils.rendering.GameCanvasThread;
import com.hunterdavis.fiveseconds.gameutils.rendering.GameSurfaceView;
import com.hunterdavis.fiveseconds.gameutils.rendering.namedColor;

class baloonPanel extends GameSurfaceView implements SurfaceHolder.Callback {
	// member variables
	public int numBaloons = 18;
	public int numBaloonsToWin = 3;
	public int colorToWin = 0;

	@SuppressWarnings("unused")
	private GameCanvasThread canvasthread;

	public Boolean surfaceCreated;
	public Context mContext;
	private int mWidth = 0;
	private int mHeight = 0;
	private boolean gameOver = false;
	private boolean firstRun = true;
	private Baloon baloons[];
	// List<namedColor> colors = new ArrayList<namedColor>();
	private namedColor colors[];
	private int colorHitCount[];
	private Paint paint = null;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (getHolder()) {

			int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				if (!gameOver) {
					testBaloonsForPops(event);
				}
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
		Random rand = new Random();

		// init colors array with some nice colors

		colorHitCount = new int[10];
		for (int i = 0; i < 10; i++) {
			colorHitCount[i] = 0;
		}
		colors = new namedColor[10];
		colors[0] = new namedColor(Color.BLACK, "Black");
		colors[1] = new namedColor(Color.BLUE, "Blue");
		colors[2] = new namedColor(Color.DKGRAY, "Dark Gray");
		colors[3] = new namedColor(Color.GRAY, "Gray");
		colors[4] = new namedColor(Color.GREEN, "Green");
		colors[5] = new namedColor(Color.LTGRAY, "Light Gray");
		colors[6] = new namedColor(Color.MAGENTA, "Magenta");
		colors[7] = new namedColor(Color.RED, "Red");
		colors[8] = new namedColor(Color.YELLOW, "Yellow");
		colors[9] = new namedColor(Color.CYAN, "Cyan");

		baloons = new Baloon[numBaloons];
		for (int i = 0; i < numBaloons; i++) {
			baloons[i] = new Baloon(rand.nextInt(15 + (mWidth - 15)),
					rand.nextInt(15 + (mHeight - 15)),
					colors[rand.nextInt(colors.length)].color,
					6 + rand.nextInt(20));
		}

		colorToWin = colors[rand.nextInt(colors.length)].color;

		firstRun = false;
	}

	// we update the position of the baloons on screen in updateGameState
	public void updateGameState() {

		if (gameOver == true) {
			return;
		}

		// if first game state, init
		if ((mWidth > 0) && firstRun) {
			initGameState();
		}

		// update current baloons a tick
		updateCurrentBaloonTick();
	}

	public void updateCurrentBaloonTick() {
		if (firstRun == true) {
			return;
		}
		for (int i = 0; i < baloons.length; i++) {
			baloons[i].age++;
			if (baloons[i].poppingFramesRemaining <= 0) {
				Log.e("SHIIIIIIIIT", "GOT HEEEEEREEEEE");
				/*
				 * Random rand = new Random(); baloon = new
				 * Baloon(rand.nextInt(15 + (mWidth - 15)), rand.nextInt(15 +
				 * (mHeight - 15)), colors.get(rand
				 * .nextInt(colors.size())).color, 6 + rand.nextInt(20));
				 */
			}

			baloons[i].updateXandYLoc(baloons[i].xLocation,
					(baloons[i].yLocation - 1));

			if (baloons[i].yLocation - baloons[i].size - 2 < 0) {
				baloons[i].updateXandYLoc(baloons[i].xLocation, mHeight);
			}
		}
	}

	public void testBaloonsForPops(MotionEvent event) {
		float xVal = event.getX();
		float yVal = event.getY();
		for (int i = 0; i < baloons.length; i++) {
			if (baloons[i].isPointWithinBaloon(xVal, yVal)) {
				baloons[i].pop();
				updateBaloonPopCount(baloons[i].color);
			}
		}
	}

	public void updateBaloonPopCount(int color) {
		for (int i = 0; i < colors.length; i++) {
			if (colors[i].color == color) {
				colorHitCount[i]++;
				if ((colorHitCount[i] >= numBaloonsToWin)
						&& (colors[i].color == colorToWin)) {
					gameOver = true;
					CreditsScreen.startCreditScreen(getContext(),
							R.raw.compressedtitletheme,
							R.raw.fivesecondscredits);
					doLose();
				}
			}
		}
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
		if ((!gameOver) && (firstRun == false)) {
			drawBaloons(canvas, paint);
		}

	}

	public void drawBaloons(Canvas canvas, Paint paint) {
		for (int i = 0; i < baloons.length; i++) {
			if (baloons[i].drawBaloonAndTestForPop(canvas, paint)) {
				Random rand = new Random();
				baloons[i] = new Baloon(rand.nextInt(15 + (mWidth - 15)),
						rand.nextInt(15 + (mHeight - 15)),
						colors[rand.nextInt(colors.length)].color,
						6 + rand.nextInt(20));
			}
		}
	}

} // end class