package com.hunterdavis.fiveseconds.title;

import com.hunterdavis.easyaudiomanager.EasyAudioManager;
import com.hunterdavis.fiveseconds.R;
import com.hunterdavis.fiveseconds.R.id;
import com.hunterdavis.fiveseconds.R.layout;
import com.hunterdavis.fiveseconds.R.raw;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class TitleScreen extends Activity implements
		MediaPlayer.OnCompletionListener {
	MediaPlayer mediaPlayer;

	public static final String wavReferenceIDString = "wavreference";
	public static final String imageReferenceIDString = "imgreference";
	public static final String touchToExitBooleanID = "touchToExit";
	public static final String exitOnWavePlayBooleanID = "exitOnWavPlay";
	public static final String timeoutIntegerID = "timeout";

	private int imgReference = -1;
	private int wavReference = -1;
	private boolean touchToExit = true;
	private boolean exitOnWavPlay = false;
	private int timeout = -1;
	private EasyAudioManager audioManager;

	public static final void startTitleScreen(Context context, int wavRefId,
			int imageRefId, boolean touchToExit, boolean exitOnWavComplete,
			int timeout) {
		// create the new title screen intent
		Intent titleIntent = new Intent(context, TitleScreen.class);
		titleIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		titleIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (wavRefId != -1) {
			titleIntent.putExtra(TitleScreen.wavReferenceIDString, wavRefId);
		}
		if (imageRefId != -1) {
			titleIntent
					.putExtra(TitleScreen.imageReferenceIDString, imageRefId);
		}

		titleIntent.putExtra(TitleScreen.exitOnWavePlayBooleanID,
				exitOnWavComplete);
		titleIntent.putExtra(TitleScreen.touchToExitBooleanID, touchToExit);
		if (timeout > 0) {
			titleIntent.putExtra(TitleScreen.timeoutIntegerID, timeout);
		}

		// start title screen.
		context.startActivity(titleIntent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent startIntent = getIntent();
		if (startIntent != null) {
			imgReference = startIntent.getIntExtra(imageReferenceIDString, -1);
			wavReference = startIntent.getIntExtra(wavReferenceIDString, -1);
			touchToExit = startIntent.getBooleanExtra(touchToExitBooleanID,
					true);
			exitOnWavPlay = startIntent.getBooleanExtra(
					exitOnWavePlayBooleanID, false);
			timeout = startIntent.getIntExtra(timeoutIntegerID, -1);
		}

		// Set window fullscreen and remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.titlescreen);

		// at this point the layout should be inflated, so
		// maximize the title screen logo here
		if (imgReference != -1) {
			ImageView imageView = (ImageView) findViewById(R.id.titlescreen);
			imageView.setImageResource(imgReference);
			imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			if (touchToExit) {
				OnClickListener buttonClick = new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				};

				imageView.setOnClickListener(buttonClick);
			}
		}

		if (wavReference != -1) {
			// create the audioManager
			audioManager = new EasyAudioManager(this);
			audioManager.setSongAndOnComplete(this, R.raw.compressedtitletheme,
					this);
			audioManager.playSong();
		}
		if (timeout > 0) {
			TitleCountDown localTitleCounter = new TitleCountDown(timeout, 1000);
			localTitleCounter.start();
		}

	}

	public void onCompletion(MediaPlayer arg0) {
		if (exitOnWavPlay) {
			finish();
		}
	}

	// countdowntimer is an abstract class, so extend it and fill in methods
	public class TitleCountDown extends CountDownTimer {
		public TitleCountDown(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			finish();
		}

		@Override
		public void onTick(long millisUntilFinished) {

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mediaPlayer = null;
	}

}
