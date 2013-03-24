package com.hunterdavis.fiveseconds.games.dotdotdotjump;

import java.util.Random;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

import com.hunterdavis.easyaudiomanager.EasyAudioManager;
import com.hunterdavis.fiveseconds.R;
import com.hunterdavis.gameutils.rendering.UIThreadMessages;
import com.hunterdavis.gameutils.time.FPSClockTimer;
import com.hunterdavis.gameutils.title.TitleScreen;

public class DotDotDotJump extends SimpleBaseGameActivity {

	/** The Constant numberToMatch. */
	public static final String DIFFICULTYID = "difficulty";
	private static int mDifficultyLevel = 1;
	public static final float ZDEPTH = (float) 0.5;

	public static final int DEFAULT_WIDTH = 1280;
	public static final int DEFAULT_HEIGHT = 720;

	/** The audio manager. */
	EasyAudioManager audioManager;

	// the gl surfaceview
	private DDDJSharedGameData sharedGameData;
	private DDDJGameThread gameThread;

	/** The times resumed. */
	private int timesResumed = 0;


	/**
	 * Start dotdotdotjump screen.
	 * 
	 * @param context
	 *            the context
	 * @param setDifficulty
	 *            what difficulty level to use
	 */
	public static final void startDotDotDotJumpScreen(Context context,
			int difficultyLevel) {
		// create the new title screen intent
		Intent dotIntent = new Intent(context, DotDotDotJump.class);
		dotIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		dotIntent.putExtra(DIFFICULTYID, difficultyLevel);

		// start title screen.
		context.startActivity(dotIntent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent difficultyIntent = getIntent();
		Bundle extras = difficultyIntent.getExtras();
		int diffLevel = extras.getInt(DIFFICULTYID, -1);
		if (diffLevel > 0) {
			mDifficultyLevel = diffLevel;
		}

		// Set window fullscreen and remove title bar
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// create a title screen and throw it up
		TitleScreen.startTitleScreen(getApplicationContext(),
				R.raw.popxcolorballoonstitletheme, R.drawable.jumptitle,
				true/* touchToExit */, false /* exitOnWavComplete */,
				3000/* timeout */, true /* landscape mode */);

		sharedGameData = null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();

		// if (popManyBaloonPanel != null) {
		// popManyBaloonPanel.terminateThread();
		// }
		if ((audioManager != null) && (audioManager.songPlaying)) {
			audioManager.pauseSong();
		}
		System.gc();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		timesResumed++;
		if (timesResumed > 1) {

			// Create our surface view and set it as the background content of
			// our
			// Activity
			//setContentView(R.layout.dotdotdotjump);
			sharedGameData = new DDDJSharedGameData();

			// create the audioManager
			int[] soundBites = new int[1];
			soundBites[0] = R.raw.balloonpop;
			audioManager = new EasyAudioManager(this, soundBites);
			audioManager.setSongAndOnComplete(this,
					R.raw.popxcolorballoonsgametheme,
					new OnCompletionListener() {

						@Override
						public void onCompletion(MediaPlayer mp) {
							mp.seekTo(0);
							mp.start();
						}

					});
			audioManager.playSong();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (audioManager != null) {
			if (audioManager.songPlaying) {
				audioManager.pauseSong();
			}
			audioManager = null;
		}
	}


	@Override
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED,
				new RatioResolutionPolicy(DEFAULT_WIDTH, DEFAULT_HEIGHT),
				camera);
	}

	@Override
	protected Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());

		final Scene scene = new Scene();
		scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));

		final Random random = new Random(123213);

		final VertexBufferObjectManager vertexBufferObjectManager = this
				.getVertexBufferObjectManager();
		for (int i = 0; i < 30; i++) {
			final float x1 = random.nextFloat() * DEFAULT_WIDTH;
			final float x2 = random.nextFloat() * DEFAULT_WIDTH;
			final float y1 = random.nextFloat() * DEFAULT_HEIGHT;
			final float y2 = random.nextFloat() * DEFAULT_HEIGHT;
			final float lineWidth = random.nextFloat() * 5;

			final Line line = new Line(x1, y1, x2, y2, lineWidth,
					vertexBufferObjectManager);

			line.setColor(random.nextFloat(), random.nextFloat(),
					random.nextFloat());

			scene.attachChild(line);
		}
		return scene;
	}


	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		
	}


}
