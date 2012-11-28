package com.hunterdavis.fiveseconds.ui;

import com.hunterdavis.fiveseconds.R;
import com.hunterdavis.fiveseconds.R.drawable;
import com.hunterdavis.fiveseconds.R.layout;
import com.hunterdavis.fiveseconds.R.menu;
import com.hunterdavis.fiveseconds.R.raw;
import com.hunterdavis.fiveseconds.title.TitleScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

// The game select screen is the main 'hub' of 5 seconds
// TODO: write a credits class that can be re-used, takes (raw txt file reference)


public class GameSelectScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_select_screen);
        
		// create the new title screen intent
		Intent titleIntent = new Intent(this, TitleScreen.class);
		titleIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		titleIntent.putExtra(TitleScreen.wavReferenceIDString, R.raw.titletheme);
		titleIntent.putExtra(TitleScreen.imageReferenceIDString, R.drawable.fivesecondstitle);
		titleIntent.putExtra(TitleScreen.exitOnWavePlayBooleanID, true);
		
		// start title screen.
		startActivity(titleIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game_select_screen, menu);
        return true;
    }
}
