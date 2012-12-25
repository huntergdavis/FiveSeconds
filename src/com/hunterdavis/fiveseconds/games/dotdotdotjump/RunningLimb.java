package com.hunterdavis.fiveseconds.games.dotdotdotjump;

import com.hunterdavis.gameutils.glrendering.XYZTuple;

public class RunningLimb {
	// upper limb position
	private XYZTuple mUpperLimbStart;
	private XYZTuple mUpperLimbEnd;

	// left lower limb position
	private XYZTuple mLowerLimbStart;
	private XYZTuple mLowerLimbEnd;

	// hand/foot position
	private XYZTuple mHandFootCenter;
	private float mHandFootSize;

	public void RunningLimb(XYZTuple upperStart, XYZTuple upperEnd,
			XYZTuple lowerStart, XYZTuple lowerEnd, XYZTuple handFootCenter,
			float handFootSize) {
		mUpperLimbStart = upperStart;
		mUpperLimbEnd = upperEnd;
		mLowerLimbStart = lowerStart;
		mLowerLimbEnd = lowerEnd;
		mHandFootCenter = handFootCenter;
		mHandFootSize = handFootSize;
	}

	public void RunningLimb() {
		mUpperLimbStart = new XYZTuple();
		mUpperLimbEnd = new XYZTuple();
		mLowerLimbStart = new XYZTuple();
		mLowerLimbEnd = new XYZTuple();
		mHandFootCenter = new XYZTuple();
		mHandFootSize = 0.0f;
	}

	public synchronized void setUpperLimbPosition(XYZTuple start, XYZTuple end) {
		mUpperLimbStart = start;
		mUpperLimbEnd = end;
	}

	public synchronized XYZTuple getUpperLimbStart() {
		return mUpperLimbStart;
	}

	public synchronized XYZTuple getUpperLimbEnd() {
		return mUpperLimbEnd;
	}

	public synchronized void setLowerLimbPosition(XYZTuple start, XYZTuple end) {
		mLowerLimbStart = start;
		mLowerLimbEnd = end;
	}

	public synchronized XYZTuple getLowerLimbStart() {
		return mLowerLimbStart;
	}

	public synchronized XYZTuple getLowerLimbEnd() {
		return mLowerLimbEnd;
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
