package com.hunterdavis.fiveseconds.credits;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.hunterdavis.fiveseconds.gameutils.rendering.GameCanvasThread;
import com.hunterdavis.fiveseconds.gameutils.rendering.GameSurfaceView;

class CreditsPanel extends GameSurfaceView implements SurfaceHolder.Callback {
	// values
	private static float TextTickValue = (float) 1.5;
	private static final float TextTickFastForwardSpeed = 18.0f;
	private static final float TextTickSlowSpeed = 3.0f;

	// member variables
	public Boolean surfaceCreated;
	public Context mContext;
	private int mWidth = 0;
	private int mHeight = 0;
	private boolean gameOver = false;
	private int currentCreditTopLineItem = 0;
	private int numberOfLinesOnScreen = 1;
	List<creditsLineItem> credits = new ArrayList<creditsLineItem>();
	Paint paint = null;
	private String finalScore = "";

	// each credits line is a tiny inner class for storing credits lines
	class creditsLineItem {
		String line;
		int age;
		float accumulatedHeightTicks;

		creditsLineItem(String lineToStore) {
			line = lineToStore;
			age = 0;
			accumulatedHeightTicks = 0;
		}

		creditsLineItem(String lineToStore, int ageToInit, int yPosInitial) {
			line = lineToStore;
			age = ageToInit;
			accumulatedHeightTicks = yPosInitial;
		}
	}

	public void readInCreditsTxt(int creditsReference, String scoreText) {
		InputStream inputStream = getResources().openRawResource(
				creditsReference);
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String eachLine = "";
		while (eachLine != null) {
			credits.add(new creditsLineItem(eachLine));
			try {
				eachLine = bufferedReader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if ((scoreText != null) && !scoreText.equals("")) {
			finalScore = scoreText;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		synchronized (getHolder()) {

			int action = event.getAction();
			if (action == MotionEvent.ACTION_DOWN) {
				TextTickValue = TextTickFastForwardSpeed;
				if (gameOver == true) {
					doLose();
				}
				return true;
			} else if (action == MotionEvent.ACTION_MOVE) {

				return true;
			} else if (action == MotionEvent.ACTION_UP) {
				TextTickValue = TextTickSlowSpeed;
				return true;
			}

			return true;
		}
	}

	public CreditsPanel(Context context, AttributeSet attrs) {
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

	// we update the position of the text lines on screen in updateGameState
	public void updateGameState() {

		if (gameOver == true) {
			return;
		}

		// update current line a tick
		updateCurrentLineTick();
	}

	public void updateCurrentLineTick() {

		if (credits.size() <= currentCreditTopLineItem) {
			gameOver = true;
			return;
		}

		if (credits.get(currentCreditTopLineItem).accumulatedHeightTicks > ((mHeight / 15) * numberOfLinesOnScreen)) {
			numberOfLinesOnScreen++;
		}

		for (int i = 0; i < numberOfLinesOnScreen; i++) {
			if ((currentCreditTopLineItem + i) < credits.size()) {
				credits.get(currentCreditTopLineItem + i).age++;
				credits.get(currentCreditTopLineItem + i).accumulatedHeightTicks += TextTickValue;
			}
		}

		if (credits.get(currentCreditTopLineItem).accumulatedHeightTicks > mHeight) {
			currentCreditTopLineItem++;
			// numberOfLinesOnScreen--;

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
		paint.setColor(Color.BLACK);
		// clear the screen with the black painter.
		canvas.drawRect(0, 0, mWidth, mHeight, paint);

		// draw game over if game over
		if (gameOver == true) {
			paint.setColor(Color.WHITE);
			paint.setTextSize(30);
			canvas.drawText("Game Over", (mWidth / 2), mHeight / 4, paint);
			canvas.drawText(finalScore, mWidth / 2, (mHeight / 4) * 3, paint);
		} else {
			paint.setColor(Color.WHITE);
			paint.setTextSize(30);

			for (int i = 0; i < numberOfLinesOnScreen; i++) {
				if (currentCreditTopLineItem + i < credits.size()) {
					creditsLineItem currentLineItem = credits
							.get(currentCreditTopLineItem + i);
					canvas.drawText(currentLineItem.line, (mWidth / 2),
							(mHeight - currentLineItem.accumulatedHeightTicks),
							paint);
				}
			}
		}
	}

} // end class