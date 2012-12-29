package com.hunterdavis.fiveseconds.ui;

import java.util.Random;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;

import com.hunterdavis.fiveseconds.R;
import com.hunterdavis.fiveseconds.credits.CreditsScreen;
import com.hunterdavis.fiveseconds.games.balloons.popxcolorballoons.PopXColorBalloons;
import com.hunterdavis.fiveseconds.games.dotdotdotjump.DotDotDotJump;
import com.hunterdavis.fiveseconds.title.TitleScreen;

public class GameSelectScreenResources {
	public static final String[] GAMETITLES = new String[] { "Title Screen",
			"Pop Them Baloons", "...Jump", "Credits Screen", "About the Author" };
	public static final int[] GAMEICONS = new int[] {
			R.drawable.fivesecondstitleicon, R.drawable.popxcolorbaloonsicon,
			R.drawable.jumpscreenicon, R.drawable.creditsicon,
			R.drawable.abouttheauthoricon };

	public static final int[] GAMESCREENS = new int[] {
			R.drawable.fivesecondstitle, R.drawable.popxcolorbaloonstitle,
			R.drawable.jumptitle, R.drawable.creditstitle,
			R.drawable.abouttheauthor };

	public static final int[] GAMETITLEMUSIC = new int[] {
			R.raw.compressedtitletheme, R.raw.popxcolorballoonstitletheme,
			R.raw.compressedtitletheme, R.raw.popxcolorballoonsgametheme,
			R.raw.popxcolorballoonscreditstheme };

	public static int getMusicForPageAtPosition(int position) {
		return GAMETITLEMUSIC[position % GAMETITLEMUSIC.length];
	}

	public static OnClickListener getOnClickListenerForGameScreen(int icon) {
		switch (icon) {
		case R.drawable.popxcolorbaloonstitle: /* Pop X Color Baloons */
			return new OnClickListener() {
				@Override
				public void onClick(View v) {
					PopXColorBalloons.startPopXColorBaloonsScreen(
							v.getContext(), 3 + (new Random().nextInt(2)));
				}
			};
		case R.drawable.jumptitle: /* dotdotdotjump */
			return new OnClickListener() {
				@Override
				public void onClick(View v) {
					DotDotDotJump.startDotDotDotJumpScreen(v.getContext(), 1);
				}
			};
		case R.drawable.fivesecondstitle: /* Title Screen */
			return new OnClickListener() {
				@Override
				public void onClick(View v) {
					TitleScreen.startTitleScreen(v.getContext(),
							R.raw.compressedtitletheme,
							R.drawable.fivesecondstitle, false/* touchToExit */,
							true/* exitOnWavComplete */, -1/* timeout */,
							false /* landscape mode */);
				}
			};
		case R.drawable.creditsicon: /* Credits Screen */
			return new OnClickListener() {
				@Override
				public void onClick(View v) {
					CreditsScreen.startCreditScreen(v.getContext(),
							R.raw.compressedtitletheme,
							R.raw.fivesecondscredits, -1 /* no final image */,
							"Final Score: 50pts");
				}
			};
		case R.drawable.abouttheauthor: /* about the author */
			return new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hunterdavis.com"));
					v.getContext().startActivity(browserIntent);
				}
			};
		default:
			return new OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			};
		}
	}
}
