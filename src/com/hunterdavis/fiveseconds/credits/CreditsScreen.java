package com.hunterdavis.fiveseconds.credits;

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
import com.hunterdavis.fiveseconds.credits.CreditsPanel.creditsLineItem;


public class CreditsScreen extends Activity {
	EasyAudioManager audioManager; 
	CreditsPanel creditsPanel;

	public static final String wavReferenceIDString = "wavreference";
	public static final String txtReferenceIDString = "txtreference";
	public static final String finalScoreString = "finalScoreInfo";
	private int txtReference = -1;
	private int wavReference = -1;
	private String finalScoreInfoText = "";
	
	public static final void startCreditScreen(Context context, int wavRefId,
			int txtRefId, String finalScoreText) {
		// create the new title screen intent
		Intent creditsIntent = new Intent(context, CreditsScreen.class);
		creditsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		creditsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		if (wavRefId != -1) {
			creditsIntent.putExtra(CreditsScreen.wavReferenceIDString, wavRefId);
		}
		if (txtRefId != -1) {
			creditsIntent
					.putExtra(CreditsScreen.txtReferenceIDString, txtRefId);
		}
		if(null != finalScoreText) {
			creditsIntent
			.putExtra(CreditsScreen.finalScoreString, finalScoreText);
		}
		
		// start title screen.
		context.startActivity(creditsIntent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent startIntent = getIntent();
		if (startIntent != null) {
			txtReference = startIntent.getIntExtra(txtReferenceIDString, -1);
			wavReference = startIntent.getIntExtra(wavReferenceIDString, -1);
			finalScoreInfoText = startIntent.getStringExtra(finalScoreString);
			
		} 

		// Set window fullscreen and remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.creditsscreen);

		// at this point the layout should be inflated, so
		creditsPanel = (CreditsPanel) findViewById(R.id.SurfaceView01);
		creditsPanel.readInCreditsTxt(txtReference, finalScoreInfoText);
		
		if (wavReference != -1) {
			// create the audioManager
			audioManager = new EasyAudioManager(this);
			audioManager.setSongAndOnComplete(this,R.raw.compressedtitletheme, new OnCompletionListener(){

				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.seekTo(0);
					mp.start();
				} 
			
			});
			audioManager.playSong();
		} 

	}
	@Override
	protected void onPause() {
		super.onPause();
		creditsPanel.terminateThread();
		audioManager.pauseSong();
		System.gc();
	}
	@Override
	protected void onResume() {
		super.onResume();
		if (creditsPanel.surfaceCreated == true) {
			creditsPanel.createThread(creditsPanel.getHolder());
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		audioManager.pauseSong();
		audioManager = null;
	}

}
