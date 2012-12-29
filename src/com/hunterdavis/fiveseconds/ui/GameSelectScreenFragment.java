package com.hunterdavis.fiveseconds.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class GameSelectScreenFragment extends Fragment {
	private static final String KEY_TITLE = "GameSelectScreen:Title";
	private static final String KEY_IMAGE = "GameSelectScreen:Image";

	private String mTitle = "";
	private int mTitleImage = -1;
	private View.OnClickListener mTitleOnClickListener;

	public static GameSelectScreenFragment newInstance(String title,
			int titleImageResource, View.OnClickListener onClick) {
		GameSelectScreenFragment gameSelectFragment = new GameSelectScreenFragment();
		gameSelectFragment.mTitle = title;
		gameSelectFragment.mTitleImage = titleImageResource;
		gameSelectFragment.mTitleOnClickListener = onClick;

		return gameSelectFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(KEY_TITLE)) {
				mTitle = savedInstanceState.getString(KEY_TITLE);
			}
			if (savedInstanceState.containsKey(KEY_IMAGE)) {
				mTitleImage = savedInstanceState.getInt(KEY_IMAGE);
				mTitleOnClickListener = GameSelectScreenResources
						.getOnClickListenerForGameScreen(mTitleImage);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View gameTitleLayout = inflater
				.inflate(
						com.hunterdavis.fiveseconds.R.layout.generic_game_title_fragment,
						null);

		// set our title image and onclick if we've got em
		if (mTitleImage > -1) {
			ImageButton gameTitleView = (ImageButton) gameTitleLayout
					.findViewById(com.hunterdavis.fiveseconds.R.id.gameTitleImage);
			gameTitleView.setImageDrawable(getResources().getDrawable(
					mTitleImage));
			if (mTitleOnClickListener != null) {
				gameTitleView.setOnClickListener(mTitleOnClickListener);
			}
		}
		return gameTitleLayout;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_TITLE, mTitle);
		outState.putInt(KEY_IMAGE, mTitleImage);
	}
}