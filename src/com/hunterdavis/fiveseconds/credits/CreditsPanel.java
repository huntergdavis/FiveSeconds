package com.hunterdavis.fiveseconds.credits;

import java.io.InputStream;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class CreditsPanel extends SurfaceView implements SurfaceHolder.Callback {
	// member variables
	private CanvasThread canvasthread;
	public Boolean surfaceCreated;
	public Context mContext;
	private int mWidth = 0;
	private int mHeight = 0;
	private boolean gameOver = false;

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

	float fdistance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	public CreditsPanel(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		surfaceCreated = false;

		getHolder().addCallback(this);
		setFocusable(true);
	}

	public void createThread(SurfaceHolder holder) {
		canvasthread = new CanvasThread(getHolder(), this, mContext,
				new Handler());
		canvasthread.setRunning(true);
		canvasthread.start();
	}

	public void terminateThread() {
		canvasthread.setRunning(false);
		try {
			canvasthread.join();
		} catch (InterruptedException e) {

		}
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

	public void updateGameState() {

		// if(gameOver == true) {
		// return;
		// }

		// move player 1 a tick (based on testing)
		// movePlayer1Tick();

		// move player 2 a tick (based on difficulty)
		// movePlayer2Tick();

		// move the ball a tick (based on difficulty and bounces)
		// int numberOfTicksToMoveBall = (numberOfBounces / 3) + (difficulty + 5
		// / 3);
		// for(int i =0;i<numberOfTicksToMoveBall;i++) {
		// moveBallTick();
		// }
	}

	@Override
	public void onDraw(Canvas canvas) {

		mWidth = canvas.getWidth();
		mHeight = canvas.getHeight();

		Paint paint = new Paint();

		// our player sizes should be a function both of difficulty and of
		// screen size
		// player2Size = mWidth/10 + (4 * difficulty);
		// player1Size = mWidth/5 - (4*difficulty);

		// draw player 1
		// paint.setColor(playerColor);
		// test for out of bounds
		// if ((player1Pos + player1Size) > mWidth) {
		// player1Pos = mWidth - player1Size - 1;
		// }
		// canvas.drawRect(player1Pos, (mHeight - paddleBuffer - playerHeight),
		// (player1Pos + player1Size), (mHeight - paddleBuffer), paint);

		// draw player 2
		// test for out of bounds
		// if ((player2Pos + player2Size) > mWidth) {
		// player2Pos = mWidth - player2Size - 1;
		// }
		// canvas.drawRect(player2Pos, (paddleBuffer), (player2Pos +
		// player2Size),
		// (paddleBuffer + playerHeight), paint);

		// draw ball
		// if (ballBitmap == null) {
		// ballSize = mWidth / 5;
		// if we can't load somebody else's bitmap
		// if (selectedImageUri == null) {
		// Bitmap _scratch = BitmapFactory.decodeResource(getResources(),
		// R.drawable.megusta);

		// if (_scratch == null) {
		// Toast.makeText(getContext(), "WTF", Toast.LENGTH_SHORT)
		// .show();
		// }

		// now scale the bitmap using the scale value
		// ballBitmap = Bitmap.createScaledBitmap(_scratch, ballSize,
		// ballSize, false);
		// } else {
		// // TODO decodefile function is at bottom of this file, use it
		// THIS IS WHERE YOU LOAD FILE URIS AT
		// InputStream photoStream = null;

		// Context context = getContext();
		// try {
		// photoStream = context.getContentResolver().openInputStream(
		// selectedImageUri);
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// int scaleSize = decodeFile(photoStream, ballSize, ballSize);

		// try {
		// photoStream = context.getContentResolver().openInputStream(
		// selectedImageUri);
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// BitmapFactory.Options o = new BitmapFactory.Options();
		// o.inSampleSize = scaleSize;

		// Bitmap photoBitmap = BitmapFactory.decodeStream(photoStream, null,
		// o);
		// ballBitmap = Bitmap.createScaledBitmap(photoBitmap, ballSize,
		// ballSize, true);
		// photoBitmap.recycle();

		// }

		// canvas.drawCircle(ballPos.x + (ballSize / 2), ballPos.y
		// + (ballSize / 2), (ballSize / 2), paint);
		// }

		// draw the ball bitmap
		// /canvas.drawBitmap(ballBitmap, ballPos.x, ballPos.y, paint);

		// draw score 1
		// paint.setColor(scoreColor);
		// canvas.drawText(String.valueOf(player1score) + " points", mWidth
		// - scorexbuffer, mHeight - 2, paint);
		// draw score 2
		// canvas.drawText(String.valueOf(player2score) + " points", mWidth
		// - scorexbuffer, 9, paint);

		// draw game over if game over
		if (gameOver == true) {

			paint.setTextSize(20);
			canvas.drawText("Game Over", (mWidth / 2) - 50, mHeight / 4, paint);
		}

		/*
		 * if (saveScore) { holdState = true; generateBoard = false; drawReady =
		 * false;
		 * 
		 * int width = canvas.getWidth(); int height = canvas.getHeight();
		 * 
		 * paint.setColor(Color.BLACK); canvas.drawText("Game Over", (width / 2)
		 * - 30, height / 2, paint);
		 * 
		 * // clear out the scoreboard to all white paint.setColor(Color.WHITE);
		 * canvas.drawRect(width - scorexbuffer, height - scoreybuffer, width,
		 * height, paint);
		 * 
		 * // paint the scoreboard with the score paint.setColor(Color.BLACK);
		 * canvas.drawText(String.valueOf(score) + " points", width -
		 * scorexbuffer, height - 4, paint);
		 * 
		 * return; }
		 * 
		 * if (generateBoard == true) {
		 * 
		 * backingBitmap = Bitmap.createBitmap(canvas.getWidth(),
		 * canvas.getHeight(), Bitmap.Config.ARGB_8888);
		 * 
		 * canvas.setBitmap(backingBitmap); canvas.drawColor(Color.WHITE);
		 * 
		 * // clear score score = 0; lastX = 0; lastY = 0;
		 * 
		 * // update score view scoreChanged = true; holdState = false;
		 * 
		 * // clear all x and y values xvalues.clear(); yvalues.clear();
		 * playerPoints.clear(); initialPoints.clear();
		 * 
		 * // add initial random dots to board generateInitialLines(canvas);
		 * 
		 * generateBoard = false; }
		 * 
		 * // canvas.drawBitmap(kangoo, 10, 10, null); else if (drawReady ==
		 * true) {
		 * 
		 * canvas.setBitmap(backingBitmap);
		 * 
		 * int numItems = xvalues.size(); int width = canvas.getWidth(); int
		 * height = canvas.getHeight(); Random myrandom = new Random();
		 * 
		 * // draw then connect all points that have yet to be connected for
		 * (int i = 0; i < numItems; i++) {
		 * 
		 * // retrieve the point, this is the one to draw int newx = (Integer)
		 * xvalues.get(i); int newy = (Integer) yvalues.get(i);
		 * 
		 * paint.setColor(playerColor);
		 * 
		 * // draw the new point, or connect line to last one if ((lastX == 0)
		 * && (lastY == 0)) { canvas.drawPoint(newx, newy, paint); score += 100;
		 * } else { calcualteanddrawline(canvas, lastX, lastY, newx, newy,
		 * paint, false); // canvas.drawLine(lastX, lastY, newx, newy, paint); }
		 * lastX = newx; lastY = newy;
		 * 
		 * // canvas.drawRect(newx, newy + 2, newx + 2, newy, paint);
		 * scoreChanged = true;
		 * 
		 * } xvalues.clear(); yvalues.clear();
		 * 
		 * drawReady = false; }
		 * 
		 * if (scoreChanged == true) { int width = canvas.getWidth(); int height
		 * = canvas.getHeight();
		 * 
		 * // clear out the scoreboard to all white paint.setColor(Color.WHITE);
		 * canvas.drawRect(width - scorexbuffer, height - scoreybuffer, width,
		 * height, paint);
		 * 
		 * // paint the scoreboard with the score paint.setColor(Color.BLACK);
		 * canvas.drawText(String.valueOf(score) + " points", width -
		 * scorexbuffer, height - 4, paint); }
		 * 
		 * // update screen canvas.drawBitmap(backingBitmap, 0, 0, paint);
		 * 
		 * // here we call a canvas operation function to add all the cats //
		 * drawCatsFromVectors(singleUseCanvas);
		 * 
		 * // since we drew to the bitmap, display it //
		 * canvas.drawBitmap(lastGoodBitmap, 0, 0, null);
		 */
	}

	// decodes image and scales it to reduce memory consumption
	private int decodeFile(InputStream photostream, int h, int w) {
		// Decode image size
		BitmapFactory.Options o = new BitmapFactory.Options();
		o.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(photostream, null, o);

		// Find the correct scale value. It should be the power of 2.
		int width_tmp = o.outWidth, height_tmp = o.outHeight;
		int scale = 1;
		while (true) {
			if (width_tmp / 2 < w || height_tmp / 2 < h)
				break;
			width_tmp /= 2;
			height_tmp /= 2;
			scale *= 2;
		}

		return scale;
	}

} // end class