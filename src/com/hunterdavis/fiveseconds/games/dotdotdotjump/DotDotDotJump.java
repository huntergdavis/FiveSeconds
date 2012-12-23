package com.hunterdavis.fiveseconds.games.dotdotdotjump;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.hunterdavis.easyaudiomanager.EasyAudioManager;
import com.hunterdavis.fiveseconds.R;
import com.hunterdavis.fiveseconds.title.TitleScreen;
import com.hunterdavis.gameutils.rendering.UIThreadMessages;
import com.hunterdavis.gameutils.time.FPSClockTimer;

public class DotDotDotJump extends Activity {

	/** The Constant numberToMatch. */
	public static final String DIFFICULTYID = "difficulty";
	private static int mDifficultyLevel = 1;

	/** The audio manager. */
	EasyAudioManager audioManager;

	// the gl surfaceview
	private GLSurfaceView glSurfaceView;
	private DDDJGLRenderer glRenderer;
	private DDDJSharedGameData sharedGameData;
	private DDDJGameThread gameThread;
	
	// the fps view
	private FPSClockTimer fpsClockTimer;

	/** The times resumed. */
	private int timesResumed = 0;

	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == UIThreadMessages.SCREENRESIZED.value()) {

			}
			super.handleMessage(msg);
		}
	};

	/**
	 * Start dotdotdotjump screen.
	 * 
	 * @param context
	 *            the context
	 * @param setDifficulty
	 *            what difficulty level to use
	 */
	public static final void startDotDotDotJumpScreen(Context context,
			int difficultyLevel) {
		// create the new title screen intent
		Intent dotIntent = new Intent(context, DotDotDotJump.class);
		dotIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		dotIntent.putExtra(DIFFICULTYID, difficultyLevel);

		// start title screen.
		context.startActivity(dotIntent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent difficultyIntent = getIntent();
		Bundle extras = difficultyIntent.getExtras();
		int diffLevel = extras.getInt(DIFFICULTYID, -1);
		if (diffLevel > 0) {
			mDifficultyLevel = diffLevel;
		}

		// Set window fullscreen and remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// create a title screen and throw it up
		TitleScreen
				.startTitleScreen(getApplicationContext(),
						R.raw.popxcolorballoonstitletheme,
						R.drawable.jumptitle, true/* touchToExit */,
						false /* exitOnWavComplete */, 3000/* timeout */, true /*landscape mode*/);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if (glSurfaceView != null) {
			glSurfaceView.onPause();
		}
		
		if(gameThread != null) {
			gameThread.setRunning(false);
		}
		// if (popManyBaloonPanel != null) {
		// popManyBaloonPanel.terminateThread();
		// }
		if ((audioManager != null) && (audioManager.songPlaying)) {
			audioManager.pauseSong();
		}
		System.gc();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		timesResumed++;
		if (glSurfaceView != null) {
			glSurfaceView.onResume();
		}
		if (timesResumed > 1) {

			// Create our surface view and set it as the background content of
			// our
			// Activity
			setContentView(R.layout.dotdotdotjump);
			glSurfaceView = (GLSurfaceView) findViewById(R.id.glESView);
			sharedGameData = new DDDJSharedGameData();
			glRenderer = new DDDJGLRenderer(this, handler, null,
					sharedGameData, true);
			glSurfaceView.setRenderer(glRenderer);
			
			fpsClockTimer = new FPSClockTimer(500000, 100, this, R.id.fpsCounter,sharedGameData);
			fpsClockTimer.start();
			
			gameThread = new DDDJGameThread(sharedGameData, 10);
			gameThread.run();
			
			// create the audioManager
			int[] soundBites = new int[1];
			soundBites[0] = R.raw.balloonpop;
			audioManager = new EasyAudioManager(this, soundBites);
			audioManager.setSongAndOnComplete(this,
					R.raw.popxcolorballoonsgametheme,
					new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.seekTo(0);
							mp.start();
						}

					});
			audioManager.playSong();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (audioManager != null) {
			if (audioManager.songPlaying) {
				audioManager.pauseSong();
			}
			audioManager = null;
		}
	}

}
