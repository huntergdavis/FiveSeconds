package com.hunterdavis.fiveseconds.gameutils.rendering;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class GameSurfaceView extends SurfaceView {

	private GameCanvasThread canvasthread;
	
	
	public GameSurfaceView(Context context, AttributeSet attrs) {
		super( context, attrs);
	}
	
	public abstract void updateGameState();
	public abstract void onDraw(Canvas c);
	
	public void createThread(SurfaceHolder holder) {
		canvasthread = new GameCanvasThread(getHolder(), this, 35);
		canvasthread.setRunning(true);
		canvasthread.start();
	}
	

	public void terminateThread() {
		if (canvasthread != null) {
			canvasthread.setRunning(false);
			try {
				canvasthread.join();
			} catch (InterruptedException e) {

			}
		}
	}

}
