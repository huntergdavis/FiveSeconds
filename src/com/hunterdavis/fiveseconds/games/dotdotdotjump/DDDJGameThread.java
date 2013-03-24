package com.hunterdavis.fiveseconds.games.dotdotdotjump;

import com.hunterdavis.gameutils.core.GameThread;

public class DDDJGameThread extends GameThread {

	public DDDJGameThread(DDDJSharedGameData shareData, long renderDelay) {
		super(shareData, renderDelay);
	}

	@Override
	protected void updateGameState() {
		// update our model a tick
		((DDDJSharedGameData) getSharedGameData()).getRunningMan().updatePlayerModelPositionATick();
	}

}
