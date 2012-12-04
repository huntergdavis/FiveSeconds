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
	
	float fdistance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
	
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
