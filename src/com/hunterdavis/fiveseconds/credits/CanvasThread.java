package com.hunterdavis.fiveseconds.credits;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.view.SurfaceHolder;

public class CanvasThread extends Thread {
	private SurfaceHolder _surfaceHolder;
	private CreditsPanel _panel;
	private boolean _run = false;
	// for consistent rendering
	private long sleepTime;
	// amount of time to sleep for (in milliseconds)
	private long delay = 70;

	public CanvasThread(SurfaceHolder surfaceHolder, CreditsPanel panel,
			Context context, Handler handler) {
		_surfaceHolder = surfaceHolder;
		_panel = panel;

	}

	public void setRunning(boolean run) {
		_run = run;
	}

	public void setDelay(long delayToSet) {
		delay = delayToSet;
	}

	public boolean getRunning() {
		return _run;
	}

	@Override
	public void run() {

		// UPDATE
		while (_run) {
			// time before update
			long beforeTime = System.nanoTime();
			// This is where we update the game engine
			_panel.updateGameState();

			// DRAW
			Canvas c = null;
			try {
				// lock canvas so nothing else can use it
				c = _surfaceHolder.lockCanvas(null);
				synchronized (_surfaceHolder) {
					Paint paint = new Paint();
					paint.setColor(Color.BLACK);
					// clear the screen with the black painter.
					c.drawRect(0, 0, c.getWidth(), c.getHeight(), paint);

					// This is where we draw the game engine.
					_panel.onDraw(c);
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (c != null) {
					_surfaceHolder.unlockCanvasAndPost(c);
				}
			}

			// SLEEP
			// Sleep time. Time required to sleep to keep game consistent
			// This starts with the specified delay time (in milliseconds) then
			// subtracts from that the
			// actual time it took to update and render the game. This allows
			// our game to render smoothly.
			this.sleepTime = delay
					- ((System.nanoTime() - beforeTime) / 1000000L);

			try {
				// actual sleep code
				if (sleepTime > 0) {
					CanvasThread.sleep(sleepTime);
				}
			} catch (InterruptedException ex) {

			}
		}

	}

}