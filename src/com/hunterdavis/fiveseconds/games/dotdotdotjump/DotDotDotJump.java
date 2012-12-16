package com.hunterdavis.fiveseconds.games.dotdotdotjump;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.hunterdavis.easyaudiomanager.EasyAudioManager;
import com.hunterdavis.fiveseconds.R;
import com.hunterdavis.fiveseconds.title.TitleScreen;

public class DotDotDotJump extends Activity {

	/** The Constant numberToMatch. */
	public static final String DIFFICULTYID = "difficulty";
	private static int mDifficultyLevel = 1;
	
	/** The audio manager. */
	EasyAudioManager audioManager;

	/** The times resumed. */
	private int timesResumed = 0;	
	
	
	/**
	 * Start pop x color baloons screen.
	 * 
	 * @param context
	 *            the context
	 * @param numberBaloonsToMatch
	 *            the number baloons to match
	 */
	public static final void startDotDotDotJumpScreen(Context context,
			int setDifficulty) {
		// create the new title screen intent
		Intent baloonsIntent = new Intent(context, DotDotDotJump.class);
		baloonsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		baloonsIntent.putExtra(DIFFICULTYID, setDifficulty);

		// start title screen.
		context.startActivity(baloonsIntent);
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
						false /* exitOnWavComplete */, 3000/* timeout */);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		//if (popManyBaloonPanel != null) {
		//	popManyBaloonPanel.terminateThread();
		//}
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
		if (timesResumed > 1) {
			setContentView(R.layout.generic_opengl_surfaceview);
			
			// at this point the layout should be inflated, so
			// do some openGL stuff here
			
			// create the audioManager
			int[] soundBites = new int[1];
			soundBites[0] = R.raw.balloonpop;
			audioManager = new EasyAudioManager(this, soundBites);
			audioManager.setSongAndOnComplete(this, R.raw.popxcolorballoonsgametheme,
					new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.seekTo(0);
							mp.start();
						}

					});
			audioManager.playSong();

			//popManyBaloonPanel.setAudioManager(audioManager);
			//if (popManyBaloonPanel.surfaceCreated == true) {
			//	popManyBaloonPanel.createThread(popManyBaloonPanel.getHolder());
			//}
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
