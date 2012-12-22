package com.hunterdavis.fiveseconds.games.dotdotdotjump;

import android.content.Context;
import android.os.Handler;

import com.hunterdavis.fiveseconds.gameutils.glrendering.GLGameSurfaceViewRenderer;

public class DDDJGLRenderer extends GLGameSurfaceViewRenderer {

	public DDDJGLRenderer(Context contexta, Handler handlera,
			int[] texturesToLoad, DDDJSharedGameData shareData, Boolean drawFPS) {
		super(contexta, handlera, texturesToLoad, shareData, drawFPS);
	}

}
