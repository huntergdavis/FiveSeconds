package com.hunterdavis.fiveseconds.games.baloons.popxcolorbaloons;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.hunterdavis.easyaudiomanager.EasyAudioManager;
import com.hunterdavis.fiveseconds.R;
import com.hunterdavis.fiveseconds.credits.CreditsScreen;
import com.hunterdavis.fiveseconds.gameutils.rendering.GameCanvasThread;
import com.hunterdavis.fiveseconds.gameutils.rendering.GameSurfaceView;
import com.hunterdavis.fiveseconds.gameutils.rendering.namedColor;

// TODO: Auto-generated Javadoc
/**
 * The Class baloonPanel.
 */
class baloonPanel extends GameSurfaceView implements SurfaceHolder.Callback {
	// member variables
	/** The num baloons. */
	public int numBaloons = 18;
	
	/** The num baloons to win. */
	public int numBaloonsToWin = 3;
	
	/** The color to win. */
	public int colorToWin = 0;
	
	/** The color to win name. */
	public String colorToWinName = "";

	/** The canvasthread. */
	@SuppressWarnings("unused")
	private GameCanvasThread canvasthread;

	/** The surface created. */
	public Boolean surfaceCreated;
	
	/** The m context. */
	public Context mContext;
	
	/** The m width. */
	private int mWidth = 0;
	
	/** The m height. */
	private int mHeight = 0;
	
	/** The game over. */
	private boolean gameOver = false;
	
	/** The first run. */
	private boolean firstRun = true;
	
	/** The game started. */
	private boolean gameStarted = false;
	
	/** The baloons. */
	private Baloon baloons[];
	
	/** The colors. */
	private namedColor colors[];
	
	/** The color hit count. */
	private int colorHitCount[];
	
	/** The paint. */
	private Paint paint = null;
	
	/** The audio manager. */
	private EasyAudioManager audioManager;

	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (getHolder()) {

			int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				if (gameStarted == false) {
					gameStarted = true;
				} else if (!gameOver) {
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

	/**
	 * Instantiates a new baloon panel.
	 *
	 * @param context the context
	 * @param attrs the attrs
	 */
	public baloonPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		surfaceCreated = false;

		getHolder().addCallback(this);
		setFocusable(true);
	}

	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder, int, int, int)
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder)
	 */
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

	/* (non-Javadoc)
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.SurfaceHolder)
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		surfaceCreated = false;

	}

	/**
	 * Inits the game state.
	 */
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
		int numBaloonsCanWin = 0;
		for (int i = 0; i < numBaloons; i++) {
			baloons[i] = new Baloon(rand.nextInt(15 + (mWidth - 15)),
					rand.nextInt(15 + (mHeight - 15)),
					colors[rand.nextInt(colors.length)].color,
					6 + rand.nextInt(20));
			if(baloons[i].color == colorToWin) {
				numBaloonsCanWin++;
			}
		}
		for(int i = numBaloonsCanWin;i<numBaloonsToWin;i++) {
			makeOneBaloonInArrayColorToWin();
		}

		int colorRand = rand.nextInt(colors.length);
		colorToWin = colors[colorRand].color;
		colorToWinName = colors[colorRand].colorName;

		firstRun = false;
	}
	
	/**
	 * Make one baloon in array color to win.
	 */
	public void makeOneBaloonInArrayColorToWin() {
		for(int i =0;i<baloons.length;i++) {
			if(baloons[i].color != colorToWin) {
				baloons[i].color = colorToWin;
				return;
			}
		}
	}

	// we update the position of the baloons on screen in updateGameState
	/* (non-Javadoc)
	 * @see com.hunterdavis.fiveseconds.gameutils.rendering.GameSurfaceView#updateGameState()
	 */
	public void updateGameState() {

		if (gameOver == true) {
			return;
		}

		// if first game state, init
		if ((mWidth > 0) && firstRun) {
			initGameState();
		}

		if (gameStarted) {
			// update current baloons a tick
			updateCurrentBaloonTick();
		}
	}

	/**
	 * Update current baloon tick.
	 */
	public void updateCurrentBaloonTick() {
		if (firstRun == true) {
			return;
		}
		for (int i = 0; i < baloons.length; i++) {
			baloons[i].age++;

			baloons[i].updateXandYLoc(baloons[i].xLocation,
					(baloons[i].yLocation - 1));

			if (baloons[i].yLocation - baloons[i].size - 2 < 0) {
				baloons[i].updateXandYLoc(baloons[i].xLocation, mHeight);
			}
		}
	}

	/**
	 * Test baloons for pops.
	 *
	 * @param event the event
	 */
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

	/**
	 * Update baloon pop count.
	 *
	 * @param color the color
	 */
	public void updateBaloonPopCount(int color) {
		for (int i = 0; i < colors.length; i++) {
			if (colors[i].color == color) {
				colorHitCount[i]++;
				if ((colorHitCount[i] >= numBaloonsToWin)
						&& (colors[i].color == colorToWin)) {
					gameOver = true;
					CreditsScreen.startCreditScreen(getContext(),
							R.raw.compressedtitletheme,
							R.raw.popxcolorbaloonscredits, "You popped "
									+ colorHitCount[i] + " "
									+ colors[i].colorName + " baloons");
					doLose();
				}
			}
		}
	}

	/**
	 * Do lose.
	 */
	public void doLose() {
		// quit to mainmenu
		((Activity) mContext).finish();
	}

	/* (non-Javadoc)
	 * @see com.hunterdavis.fiveseconds.gameutils.rendering.GameSurfaceView#onDraw(android.graphics.Canvas)
	 */
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
		if ((!gameOver) && (firstRun == false) && gameStarted) {
			drawBaloons(canvas, paint);
		}

		if (gameStarted == false) {
			paint.setColor(Color.MAGENTA);
			paint.setTextSize(30);
			canvas.drawText("Pop " + numBaloonsToWin + " " + colorToWinName
					+ " Baloons!", (mWidth / 2), mHeight / 4, paint);
		}

	}

	/**
	 * Draw baloons.
	 *
	 * @param canvas the canvas
	 * @param paint the paint
	 */
	public void drawBaloons(Canvas canvas, Paint paint) {
		for (int i = 0; i < baloons.length; i++) {
			baloons[i].drawBaloon(canvas, paint);
			if (baloons[i].shouldThisBaloonDie()) {
				Random rand = new Random();
				baloons[i] = new Baloon(rand.nextInt(15 + (mWidth - 15)),
						rand.nextInt(15 + (mHeight - 15)),
						colors[rand.nextInt(colors.length)].color,
						6 + rand.nextInt(20));
			}
		}
	}

	/**
	 * Sets the num baloons to win.
	 *
	 * @param numBaloonsToSet the new num baloons to win
	 */
	public void setNumBaloonsToWin(int numBaloonsToSet) {
		numBaloonsToWin = numBaloonsToSet;
		numBaloons = numBaloonsToWin * 10;
		
	}

	// setAudioManager is a setter method for the audio manager
	/**
	 * Sets the audio manager.
	 *
	 * @param audioM the new audio manager
	 */
	public void setAudioManager(EasyAudioManager audioM) {
		audioManager = audioM;
	}
} // end class