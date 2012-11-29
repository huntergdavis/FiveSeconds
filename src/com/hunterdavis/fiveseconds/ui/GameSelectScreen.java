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
				// create the new credits screen intent
				Intent creditsIntent = new Intent(getApplicationContext(), CreditsScreen.class);
				creditsIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				creditsIntent.putExtra(CreditsScreen.wavReferenceIDString, R.raw.compressedtitletheme);
				//creditsIntent.putExtra(CreditsScreen.imageReferenceIDString, R.drawable.fivesecondstitle);
				creditsIntent.putExtra(CreditsScreen.txtReferenceIDString, R.raw.fivesecondscredits);
				//creditsIntent.putExtra(TitleScreen.exitOnWavePlayBooleanID, true);		
				// start credits screen.
				startActivity(creditsIntent);
			}
		});
		
		Button testTitleButton = (Button) findViewById(R.id.testtitlebutton1);
		testTitleButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// create the new title screen intent
				Intent titleIntent = new Intent(getApplicationContext(), TitleScreen.class);
				titleIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
				titleIntent.putExtra(TitleScreen.wavReferenceIDString, R.raw.compressedtitletheme);
				titleIntent.putExtra(TitleScreen.imageReferenceIDString, R.drawable.fivesecondstitle);
				titleIntent.putExtra(TitleScreen.exitOnWavePlayBooleanID, true);
				
				// start title screen.
				startActivity(titleIntent);
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game_select_screen, menu);
        return true;
    }
}
