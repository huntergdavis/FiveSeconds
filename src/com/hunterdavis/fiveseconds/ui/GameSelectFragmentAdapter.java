package com.hunterdavis.fiveseconds.ui;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

public class GameSelectFragmentAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {

	private int mCount = GameSelectScreenResources.GAMETITLES.length;

	public GameSelectFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public GameSelectScreenFragment getItem(int position) {
		int screen = GameSelectScreenResources.GAMESCREENS[position % GameSelectScreenResources.GAMEICONS.length];
		return GameSelectScreenFragment.newInstance(GameSelectScreenResources.GAMETITLES[position
				% GameSelectScreenResources.GAMETITLES.length], screen,
				GameSelectScreenResources.getOnClickListenerForGameScreen(screen));
	}

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "";
	}

	public CharSequence getTitleForPageAtPosition(int position) {
		return GameSelectScreenResources.GAMETITLES[position
				% GameSelectScreenResources.GAMETITLES.length];
	}

	@Override
	public int getIconResId(int index) {
		return GameSelectScreenResources.GAMEICONS[index % GameSelectScreenResources.GAMEICONS.length];
	}

	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			mCount = count;
			notifyDataSetChanged();
		}
	}
	
}