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

public class PopXColorBaloons extends Activity {
	EasyAudioManager audioManager;
	baloonPanel popManyBaloonPanel;
	private int timesResumed = 0;

	public static final void startPopXColorBaloonsScreen(Context context) {
		// create the new title screen intent
		Intent baloonsIntent = new Intent(context, PopXColorBaloons.class);
		baloonsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// start title screen.
		context.startActivity(baloonsIntent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set window fullscreen and remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);


		// create a title screen and throw it up
		TitleScreen
				.startTitleScreen(getApplicationContext(),
						R.raw.compressedtitletheme,
						R.drawable.popxcolorbaloonstitle, true/* touchToExit */,
						true /* exitOnWavComplete */, -1/* timeout */);

	}

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

	@Override
	protected void onResume() {
		super.onResume();
		timesResumed++;
		if (timesResumed > 1) {
			setContentView(R.layout.popxcolorbaloons);
			// at this point the layout should be inflated, so
			popManyBaloonPanel = (baloonPanel) findViewById(R.id.SurfaceView01);
			
			// create the audioManager
			audioManager = new EasyAudioManager(this);
			audioManager.setSongAndOnComplete(this, R.raw.compressedtitletheme,
					new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.seekTo(0);
							mp.start();
						}

					});
			audioManager.playSong();
			if (popManyBaloonPanel.surfaceCreated == true) {
				popManyBaloonPanel.createThread(popManyBaloonPanel.getHolder());
			}
		}

	}

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
