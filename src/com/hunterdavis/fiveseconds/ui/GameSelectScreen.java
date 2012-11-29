package com.hunterdavis.fiveseconds.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.hunterdavis.fiveseconds.R;
import com.hunterdavis.fiveseconds.credits.CreditsScreen;
import com.hunterdavis.fiveseconds.title.TitleScreen;

// The game select screen is the main 'hub' of 5 seconds

public class GameSelectScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_select_screen);

		Button testCreditsButton = (Button) findViewById(R.id.testcreditsbutton1);
		testCreditsButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CreditsScreen.startCreditScreen(getApplicationContext(), R.raw.compressedtitletheme, R.raw.fivesecondscredits);
			}
		});
		
		Button testTitleButton = (Button) findViewById(R.id.testtitlebutton1);
		testTitleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TitleScreen.startTitleScreen(getApplicationContext(), R.raw.compressedtitletheme, R.drawable.fivesecondstitle, false/*touchToExit*/, true/*exitOnWavComplete*/, -1/*timeout*/);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game_select_screen, menu);
        return true;
    }
}
