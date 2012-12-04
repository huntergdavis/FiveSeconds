package com.hunterdavis.fiveseconds.games.baloons.popxcolorbaloons;

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

// TODO: Auto-generated Javadoc
/**
 * The Class PopXColorBaloons.
 */
public class PopXColorBaloons extends Activity {

	/** The Constant numberToMatch. */
	public static final String numberToMatch = "numberToMatch";

	/** The audio manager. */
	EasyAudioManager audioManager;

	/** The pop many baloon panel. */
	baloonPanel popManyBaloonPanel;

	/** The times resumed. */
	private int timesResumed = 0;

	/** The num baloons to match. */
	private int numBaloonsToMatch = 3;

	/**
	 * Start pop x color baloons screen.
	 * 
	 * @param context
	 *            the context
	 * @param numberBaloonsToMatch
	 *            the number baloons to match
	 */
	public static final void startPopXColorBaloonsScreen(Context context,
			int numberBaloonsToMatch) {
		// create the new title screen intent
		Intent baloonsIntent = new Intent(context, PopXColorBaloons.class);
		baloonsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		baloonsIntent.putExtra(numberToMatch, numberBaloonsToMatch);

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

		Intent baloonIntent = getIntent();
		Bundle extras = baloonIntent.getExtras();
		int baloonsToMatch = extras.getInt(numberToMatch, -1);
		if (baloonsToMatch > 0) {
			numBaloonsToMatch = baloonsToMatch;
		}

		// Set window fullscreen and remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// create a title screen and throw it up
		TitleScreen
				.startTitleScreen(getApplicationContext(),
						R.raw.popxcolorbaloonstitletheme,
						R.drawable.popxcolorbaloonstitle, true/* touchToExit */,
						true /* exitOnWavComplete */, -1/* timeout */);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		if (popManyBaloonPanel != null) {
			popManyBaloonPanel.terminateThread();
		}
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
			setContentView(R.layout.popxcolorbaloons);
			// at this point the layout should be inflated, so
			popManyBaloonPanel = (baloonPanel) findViewById(R.id.SurfaceView01);
			popManyBaloonPanel.setNumBaloonsToWin(numBaloonsToMatch);

			// create the audioManager
			int[] soundBites = new int[1];
			soundBites[0] = R.raw.baloonpop;
			audioManager = new EasyAudioManager(this, soundBites);
			audioManager.setSongAndOnComplete(this, R.raw.popxcolorbaloonsgametheme,
					new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.seekTo(0);
							mp.start();
						}

					});
			audioManager.playSong();

			popManyBaloonPanel.setAudioManager(audioManager);
			if (popManyBaloonPanel.surfaceCreated == true) {
				popManyBaloonPanel.createThread(popManyBaloonPanel.getHolder());
			}
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
