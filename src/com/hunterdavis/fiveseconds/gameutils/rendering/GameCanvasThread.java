package com.hunterdavis.fiveseconds.gameutils.rendering;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameCanvasThread extends Thread {
	private SurfaceHolder _surfaceHolder;
	private GameSurfaceView _panel;
	private boolean _run = false;
	// for consistent rendering
	private long sleepTime;
	// amount of time to sleep for (in milliseconds)
	private long delay = 35;

	public GameCanvasThread(SurfaceHolder surfaceHolder, GameSurfaceView panel,
			long renderDelay) {
		_surfaceHolder = surfaceHolder;
		_panel = panel;
		delay = renderDelay;
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
				if (c != null) {
					synchronized (_surfaceHolder) {
						// This is where we draw the game engine.
						_panel.onDraw(c);
					}
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
					GameCanvasThread.sleep(sleepTime);
				}
			} catch (InterruptedException ex) {

			}
		}

	}

}
