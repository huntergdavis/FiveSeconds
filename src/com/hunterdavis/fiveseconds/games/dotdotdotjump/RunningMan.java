package com.hunterdavis.fiveseconds.games.dotdotdotjump;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.RectF;
import android.util.Log;

import com.hunterdavis.gameutils.glrendering.XYZTuple;

/*
 * RunningMan is a game object class representing a
 * running character and its position state
 */
public class RunningMan {

	// body position
	private XYZTuple bodyBottom;
	private XYZTuple bodyTop;
	private float bodyWidth;

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

	// bounding box
	private RectF mBoundingBox;

	// int currentModelTick
	int currentModelTick;

	private void initiateAllMembers() {
		currentModelTick = 0;
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
		mBoundingBox = new RectF();
		bodyWidth = 10;
	}

	public RunningMan(RectF boundingBox) {
		// initiate all members
		initiateAllMembers();

		mBoundingBox = boundingBox;
		calculateInitialBodyPositionFromBoundingBox(boundingBox);
	}

	public RunningMan() {
		initiateAllMembers();
	}

	public void draw(GL10 gl) {

		// draw the body
		drawBody(gl);
	}

	private void drawBody(GL10 gl) {
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

		gl.glLineWidth(bodyWidth);

		// Point to our body vertex buffer
		FloatBuffer vertexBuffer; // buffer holding the vertices

		float vertices[] = { bodyTop.x, bodyTop.y, bodyTop.z, // x1,y1,z1
				bodyBottom.x, bodyBottom.y, bodyBottom.z // x2,y2,z2
		};

		// Log.e("BODY BOTTOM", "X = " + bodyBottom.x);
		// Log.e("BODY BOTTOM", "Y = " + bodyBottom.y);
		// Log.e("BODY BOTTOM", "Z = " + bodyBottom.z);

		// Log.e("BODY TOP", "X = " + bodyTop.x);
		// Log.e("BODY TOP", "Y = " + bodyTop.y);
		// Log.e("BODY TOP", "Z = " + bodyTop.z);

		// a float has 4 bytes so we allocate for each coordinate 4 bytes
		ByteBuffer vertexByteBuffer = ByteBuffer
				.allocateDirect(vertices.length * 4);
		vertexByteBuffer.order(ByteOrder.nativeOrder());

		// allocates the memory from the byte buffer
		vertexBuffer = vertexByteBuffer.asFloatBuffer();

		// fill the vertexBuffer with the vertices
		vertexBuffer.put(vertices);

		// set the cursor position to the beginning of the buffer
		vertexBuffer.position(0);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);

		// Create Index Buffer
		ByteBuffer iBuffer = ByteBuffer.allocateDirect(2 * 2);
		iBuffer.order(ByteOrder.nativeOrder());
		ShortBuffer indexBuffer = iBuffer.asShortBuffer();
		// Specify the order the triangle is to be drawn in
		short[] indices = { 0, 1 };
		indexBuffer.put(indices);
		indexBuffer.position(0);

		// set the color for the body
		gl.glColor4f(1.0f, 0.5f, 0.5f, 0.5f);
		gl.glDrawElements(GL10.GL_LINES, 2,
				GL10.GL_UNSIGNED_SHORT, indexBuffer);

		// Draw the vertices as line
		//gl.glDrawArrays(GL10.GL_LINES, 0, 2); // last parameter = number of
												// points to draw

		// Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

	}

	private void calculateInitialBodyPositionFromBoundingBox(RectF boundingBox) {
		// Log.e("BOUNDING BOX", "TOP = " + boundingBox.top);
		// Log.e("BOUNDING BOX", "BOTTOM = " + boundingBox.bottom);
		// Log.e("BOUNDING BOX", "LEFT = " + boundingBox.left);
		// Log.e("BOUNDING BOX", "RIGHT = " + boundingBox.right);

		// origin = midpoint
		bodyTop.y = (boundingBox.top - ((boundingBox.top - boundingBox.bottom) / 2));
		bodyTop.x = (boundingBox.right - ((boundingBox.right - boundingBox.left) / 2));
		bodyTop.z = (DotDotDotJump.ZDEPTH);

		// bottom = midpoint - height / 4;
		bodyBottom.z = (bodyTop.z);
		bodyBottom.y = (bodyTop.y - ((boundingBox.top - boundingBox.bottom) / 4));
		bodyBottom.x = (bodyTop.x);

	}

	public void updateBoundingBox(RectF boundingBox) {
		mBoundingBox = boundingBox;
		calculateInitialBodyPositionFromBoundingBox(boundingBox);
	}

	public void updatePlayerModelPositionATick() {
		currentModelTick++;
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
