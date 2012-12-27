package com.hunterdavis.fiveseconds.games.dotdotdotjump;

import android.util.Pair;

import com.hunterdavis.gameutils.glrendering.XYZTuple;

public class RunningLimb {
	// upper limb position
	private  Pair<XYZTuple, XYZTuple> mUpperLimbStartToEnd;

	// left lower limb position
	private Pair<XYZTuple, XYZTuple> mLowerLimbStartToEnd;

	// hand/foot position
	private XYZTuple mHandFootCenter;
	private float mHandFootSize;

	public void RunningLimb(XYZTuple upperStart, XYZTuple upperEnd,
			XYZTuple lowerStart, XYZTuple lowerEnd, XYZTuple handFootCenter,
			float handFootSize) {
		mUpperLimbStartToEnd = new Pair<XYZTuple, XYZTuple>(upperStart, upperEnd);
		mLowerLimbStartToEnd = new Pair<XYZTuple, XYZTuple>(lowerStart, lowerEnd);
		
		mHandFootCenter = handFootCenter;
		mHandFootSize = handFootSize;
	}

	public void RunningLimb() {
		mUpperLimbStartToEnd = new Pair<XYZTuple, XYZTuple>(new XYZTuple(),new XYZTuple());
		mLowerLimbStartToEnd = new Pair<XYZTuple, XYZTuple>(new XYZTuple(),new XYZTuple());
		
		mHandFootCenter = new XYZTuple();
		mHandFootSize = 0.0f;
	}

	public synchronized void setUpperLimbPosition(Pair<XYZTuple, XYZTuple> startToEnd) {
		mUpperLimbStartToEnd = startToEnd;
	}
	
	public synchronized Pair<XYZTuple, XYZTuple> getUpperLimbPositions() {
		return mUpperLimbStartToEnd;
	}

	public synchronized void setLowerLimbPosition(Pair<XYZTuple, XYZTuple> startToEnd) {
		mUpperLimbStartToEnd = startToEnd;
	}

	public synchronized Pair<XYZTuple, XYZTuple> getLowerLimbPositions() {
		return mLowerLimbStartToEnd;
	}

	public synchronized void setHandFootPosition(XYZTuple center, float size) {
		mHandFootCenter = center;
		mHandFootSize = size;
	}

	public synchronized float getHandFootSize() {
		return mHandFootSize;
	}

	public synchronized XYZTuple getHandFootCenter() {
		return mHandFootCenter;
	}

}
