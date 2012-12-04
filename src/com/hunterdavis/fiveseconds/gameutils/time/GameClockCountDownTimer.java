package com.hunterdavis.fiveseconds.gameutils.time;

import android.os.CountDownTimer;

// countdowntimer is an abstract class, so extend it and fill in methods
/**
 * The Class GameCountDown.
 */
public class GameClockCountDownTimer extends CountDownTimer {

	public float gameClock;
	public boolean gameOver = false;

	/**
	 * Instantiates a new title count down.
	 * 
	 * @param millisInFuture
	 *            the millis in future
	 * @param countDownInterval
	 *            the count down interval
	 */
	public GameClockCountDownTimer(long millisInFuture, long countDownInterval, float gameClockInintVal) {
		super(millisInFuture, countDownInterval);
		gameClock = gameClockInintVal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.CountDownTimer#onFinish()
	 */
	@Override
	public void onFinish() {
		gameOver = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.CountDownTimer#onTick(long)
	 */
	@Override
	public void onTick(long millisUntilFinished) {
		gameClock = millisUntilFinished;
	}
}
