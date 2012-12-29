package com.hunterdavis.fiveseconds.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.crittercism.app.Crittercism;
import com.hunterdavis.easyaudiomanager.EasyAudioManager;
import com.hunterdavis.fiveseconds.R;
import com.hunterdavis.fiveseconds.title.TitleScreen;
import com.viewpagerindicator.TabPageIndicator;

// TODO: Auto-generated Javadoc
// The game select screen is the main 'hub' of 5 seconds

/**
 * The Class GameSelectScreen.
 */
public class GameSelectScreen extends FragmentActivity {

	// view pager members
	android.support.v4.view.ViewPager mPager;
	TabPageIndicator mIndicator;
	GameSelectFragmentAdapter mAdapter;

	/** The times resumed. */
	private int timesResumed = 0;

	/** The audio manager. */
	EasyAudioManager audioManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Set window fullscreen and remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// create a title screen and throw it up
		TitleScreen.startTitleScreen(getApplicationContext(),
				R.raw.compressedtitletheme, R.drawable.fivesecondstitle,
				true/* touchToExit */, false /* exitOnWavComplete */,
				3000/* timeout */, false /* force landscape mode */);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if ((audioManager != null) && (audioManager.songPlaying)) {
			audioManager.pauseSong();
		}
		System.gc();
	}

	@Override
	protected void onResume() {
		super.onResume();
		timesResumed++;
		if (timesResumed == 2) {

			// crittercism
			Crittercism.init(getApplicationContext(),
					"50c2a35f866b8466c9000005");

			setContentView(R.layout.activity_game_select_screen);

			mAdapter = new GameSelectFragmentAdapter(
					getSupportFragmentManager());

			mPager = (android.support.v4.view.ViewPager) findViewById(R.id.gameSelectPager);
			mPager.setAdapter(mAdapter);

			mIndicator = (TabPageIndicator) findViewById(R.id.gameSelectTitlePageIndicator);
			mIndicator.setViewPager(mPager);
			

			mIndicator.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {
					playGameThemeMusic(getApplicationContext(), arg0);
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {

				}
			});

		}

	}

	public void playGameThemeMusic(Context context, int pageId) {
		if (audioManager == null) {
			// create the audioManager
			audioManager = new EasyAudioManager(this, null);
		}
		if (audioManager.songPlaying) {
			audioManager.pauseSong();
		}
		audioManager.setSong(
				context,
				GameSelectScreenResources.getMusicForPageAtPosition(pageId
						% GameSelectScreenResources.GAMETITLEMUSIC.length));
		audioManager.playSong();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_game_select_screen, menu);
		return true;
	}
}
