package com.hunterdavis.fiveseconds;

import android.app.Activity;
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
	private int imgReference = -1;
	private int wavReference = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent startIntent = getIntent();
		if (startIntent != null) {
			imgReference = startIntent.getIntExtra(imageReferenceIDString, -1);
			wavReference = startIntent.getIntExtra(wavReferenceIDString, -1);
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
			OnClickListener buttonClick = new OnClickListener() {

				@Override
				public void onClick(View v) {
					finish();
				}
			};

			imageView.setOnClickListener(buttonClick);
		}

		if (wavReference != -1) {
			mediaPlayer = MediaPlayer
					.create(getBaseContext(), R.raw.titletheme);
			mediaPlayer.setOnCompletionListener(this);
			mediaPlayer.start();
		} else {
			if (imgReference != -1) {
				TitleCountDown localTitleCounter = new TitleCountDown(4000,
						1000);
				localTitleCounter.start();
			} else {
				finish();
			}
		}

	}

	public void onCompletion(MediaPlayer arg0) {
		finish();
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
