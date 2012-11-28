package com.hunterdavis.fiveseconds.credits;

import com.hunterdavis.fiveseconds.R;
import com.hunterdavis.fiveseconds.R.id;
import com.hunterdavis.fiveseconds.R.layout;
import com.hunterdavis.fiveseconds.R.raw;

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
import com.hunterdavis.easyaudiomanager.EasyAudioManager;


public class CreditsScreen extends Activity {
	MediaPlayer mediaPlayer;
	EasyAudioManager audioManager; 

	public static final String wavReferenceIDString = "wavreference";
	public static final String txtReferenceIDString = "txtreference";
	private int txtReference = -1;
	private int wavReference = -1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent startIntent = getIntent();
		if (startIntent != null) {
			txtReference = startIntent.getIntExtra(txtReferenceIDString, -1);
			wavReference = startIntent.getIntExtra(wavReferenceIDString, -1);
		}

		// Set window fullscreen and remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.creditsscreen);

		// at this point the layout should be inflated, so
		// maximize the title screen logo here

		if (wavReference != -1) {
			// create the audioManager
			audioManager = new EasyAudioManager(this);
			audioManager.setSong(this,R.raw.titletheme);
			audioManager.playSong();
		} 

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mediaPlayer = null;
	}

}
