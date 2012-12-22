package com.hunterdavis.fiveseconds.games.dotdotdotjump;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.hunterdavis.fiveseconds.gameutils.glrendering.GLGameSurfaceViewRenderer;

public class DDDJGLRenderer extends GLGameSurfaceViewRenderer {

	public DDDJGLRenderer(Context contexta, Handler handlera,
			int[] texturesToLoad, DDDJSharedGameData shareData, Boolean drawFPS) {
		super(contexta, handlera, texturesToLoad, shareData, drawFPS);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		super.onSurfaceCreated(gl, config);

		// gl.glEnable(GL10.GL_TEXTURE_2D); // Enable Texture Mapping
		gl.glShadeModel(GL10.GL_SMOOTH); // Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 1.0f, 1.0f); // Blue Background
		gl.glClearDepthf(1.0f); // Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST); // Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL); // The Type Of Depth Testing To Do

		// perspective calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		super.onDrawFrame(gl);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	}

}
