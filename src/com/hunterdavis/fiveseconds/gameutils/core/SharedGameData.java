package com.hunterdavis.fiveseconds.gameutils.core;

public abstract class SharedGameData {

	private long frames = 0;
	
	public synchronized void updateFrames() {
		frames++;
	}
	public synchronized long getFrames(){
		return frames;
	}
	
	public abstract Object getGameData();
}
