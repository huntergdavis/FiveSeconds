package com.hunterdavis.fiveseconds.gameutils.rendering;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceView;

public abstract class GameSurfaceView extends SurfaceView {

	public GameSurfaceView(Context context, AttributeSet attrs) {
		super( context, attrs);
	}
	
	public abstract void updateGameState();
	public abstract void onDraw(Canvas c);

}
