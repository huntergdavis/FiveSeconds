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


// TODO: Auto-generated Javadoc
/**
 * The Class CreditsScreen.
 */
public class CreditsScreen extends Activity {
	
	/** The audio manager. */
	EasyAudioManager audioManager; 
	
	/** The credits panel. */
	CreditsPanel creditsPanel;

	/** The Constant wavReferenceIDString. */
	public static final String wavReferenceIDString = "wavreference";
	
	/** The Constant txtReferenceIDString. */
	public static final String txtReferenceIDString = "txtreference";
	
	/** The Constant finalScoreString. */
	public static final String finalScoreString = "finalScoreInfo";
	
	/** The txt reference. */
	private int txtReference = -1;
	
	/** The wav reference. */
	private int wavReference = -1;
	
	/** The final score info text. */
	private String finalScoreInfoText = "";
	
	/**
	 * Start credit screen.
	 *
	 * @param context the context
	 * @param wavRefId the wav ref id
	 * @param txtRefId the txt ref id
	 * @param finalScoreText the final score text
	 */
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

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
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
			audioManager.setSongAndOnComplete(this,wavReference, new OnCompletionListener(){

				@Override
				public void onCompletion(MediaPlayer mp) {
					mp.seekTo(0);
					mp.start();
				} 
			
			});
			audioManager.playSong();
		} 

	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		creditsPanel.terminateThread();
		audioManager.pauseSong();
		System.gc();
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		if (creditsPanel.surfaceCreated == true) {
			creditsPanel.createThread(creditsPanel.getHolder());
		}
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		audioManager.pauseSong();
		audioManager = null;
	}

}
