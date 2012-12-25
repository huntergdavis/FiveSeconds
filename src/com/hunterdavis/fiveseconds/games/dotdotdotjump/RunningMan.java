package com.hunterdavis.fiveseconds.games.dotdotdotjump;

import java.util.Vector;

import com.hunterdavis.gameutils.glrendering.XYZTuple;

/*
 * RunningMan is a game object class representing a
 * running character and its position state
 */
public class RunningMan {

	// body position
	private XYZTuple bodyBottom;
	private XYZTuple bodyTop;

	// neck position
	private XYZTuple neckBottom;
	private XYZTuple neckTop;

	// head position
	private XYZTuple headCenter;
	private float headSize;

	// arms, legs
	private RunningLimb leftArm;
	private RunningLimb rightArm;
	private RunningLimb leftLeg;
	private RunningLimb rightLeg;

	public void RunningMan() {
		bodyBottom = new XYZTuple();
		bodyTop = new XYZTuple();
		neckBottom = new XYZTuple();
		neckTop = new XYZTuple();
		headCenter = new XYZTuple();
		headSize = 0;
		leftArm = new RunningLimb();
		leftLeg = new RunningLimb();
		rightArm = new RunningLimb();
		rightLeg = new RunningLimb();
	}

	public void updatePlayerModelPosition() {
		calculateBodyPosition();

		calculateHeadAndNeckPosition();

		calculateLeftArmPosition();
		calculateRightArmPosition();

		calculateLeftLegPosition();
		calculateRightLegPosition();
	}

	private void calculateBodyPosition() {

	}

	private void calculateHeadAndNeckPosition() {
		calculateNeckPosition();
		calculateHeadPosition();
	}

	private void calculateHeadPosition() {

	}

	private void calculateNeckPosition() {

	}

	private void calculateRightLegPosition() {

	}

	private void calculateLeftLegPosition() {

	}

	private void calculateRightArmPosition() {

	}

	private void calculateLeftArmPosition() {

	}
}
