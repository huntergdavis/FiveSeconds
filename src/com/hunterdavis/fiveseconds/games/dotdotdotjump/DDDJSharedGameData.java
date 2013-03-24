package com.hunterdavis.fiveseconds.games.dotdotdotjump;

import android.graphics.RectF;

import com.hunterdavis.gameutils.core.SharedGameData;

public class DDDJSharedGameData extends SharedGameData {
	
	RunningMan runningMan;

	@Override
	public Object getGameData() {
		return null;
	}
	
	public RunningMan getRunningMan() {
		return runningMan;
	}
	
	public DDDJSharedGameData() {
		runningMan = new RunningMan();
	}
	
	public void updateRunningManBoundingBox(RectF newRect) {
		runningMan.updateBoundingBox(newRect);
	}

}
