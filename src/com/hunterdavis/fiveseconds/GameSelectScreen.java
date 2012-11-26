package com.hunterdavis.fiveseconds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;


public class GameSelectScreen extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_select_screen);
        
		// create the new intent
		Intent titleIntent = new Intent(this, TitleScreen.class);
		titleIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		// start title screen.
		startActivity(titleIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game_select_screen, menu);
        return true;
    }
}
