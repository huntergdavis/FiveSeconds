package com.hunterdavis.fiveseconds.ui;

import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.hunterdavis.fiveseconds.R;
import com.hunterdavis.fiveseconds.credits.CreditsScreen;
import com.hunterdavis.fiveseconds.games.balloons.popxcolorballoons.PopXColorBalloons;
import com.hunterdavis.fiveseconds.title.TitleScreen;

// TODO: Auto-generated Javadoc
// The game select screen is the main 'hub' of 5 seconds

/**
 * The Class GameSelectScreen.
 */
public class GameSelectScreen extends Activity {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_select_screen);

		Button testCreditsButton = (Button) findViewById(R.id.testcreditsbutton1);
		testCreditsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CreditsScreen.startCreditScreen(getApplicationContext(),
						R.raw.compressedtitletheme, R.raw.fivesecondscredits,
						-1 /* no final image */, "Final Score: 50pts");
			}
		});

		Button testTitleButton = (Button) findViewById(R.id.testtitlebutton1);
		testTitleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TitleScreen.startTitleScreen(getApplicationContext(),
						R.raw.compressedtitletheme,
						R.drawable.fivesecondstitle, false/* touchToExit */,
						true/* exitOnWavComplete */, -1/* timeout */);
			}
		});

		Button testPopXColorBaloonsButton = (Button) findViewById(R.id.testopopxcolorbaloonsbutton1);
		testPopXColorBaloonsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				PopXColorBalloons.startPopXColorBaloonsScreen(
						getApplicationContext(), 3 + (new Random().nextInt(2)));
			}
		});

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
