package com.example.flashlight;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.matthynes.flashlight.R;

public class MainActivity extends Activity {

	ImageButton switch_;
	ToggleButton strobe;
	EditText strobeTime;
	MediaPlayer mp;
	private Camera camera;
	private boolean isFlashOn;
	private boolean hasFlash;
	Parameters params;
	final Handler handler = new Handler();
	Timer timer;
	TimerTask timerTask;
	Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		switch_ = (ImageButton) findViewById(R.id.btn_onoff);
		strobe = (ToggleButton) findViewById(R.id.strobeToggle);

		strobeTime = (EditText) findViewById(R.id.strobeTime);

		hasFlash = getApplicationContext().getPackageManager()
				.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

		toast = Toast.makeText(this, "Incorrect value", Toast.LENGTH_SHORT);

		if (!hasFlash) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("There was an error")
					.setMessage("Your device does support flash!")
					.setCancelable(false)
					.setNegativeButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									dialog.cancel();
								}
							});
			AlertDialog alert = builder.create();
			alert.show();
			return;
		}

		getCamera();
		toggleButtonImage();

		switch_.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (isFlashOn) {
					turnOffFlash();
				} else {
					turnOnFlash();
				}
			}
		});

		// TODO Auto-generated method stub
	}

	private void getCamera() {
		if (camera == null) {
			try {
				camera = Camera.open();
				params = camera.getParameters();
			} catch (RuntimeException e) {
				Log.e("Camera error. Failed to open. Error: ", e.getMessage());
			}
		}
	}

	public void onToggleClicked(View view) {
		boolean on = ((ToggleButton) view).isChecked();

		if (strobeTime.getText().toString().length() > 0) {
			long period = (Long.parseLong((strobeTime.getText().toString())));

			if (on) {
				timer = new Timer(false);

				timerTask = new TimerTask() {
					public void run() {
						handler.post(new Runnable() {
							public void run() {
								if (isFlashOn) {
									turnOffFlash();
								} else {
									turnOnFlash();
								}
							}
						});
					}
				};

				try {
					timer.scheduleAtFixedRate(timerTask, 0, period);
				} catch (IllegalStateException e) {
					Log.e("Timer error", e.getMessage());
				}

			} else {
				timer.cancel();
				timer.purge();
			}
		} else if (on && strobeTime.getText().toString().length() <= 0) {
			toast.show();

		}
	}

	private void turnOnFlash() {
		if (!isFlashOn) {
			if (camera == null || params == null) {
				return;
			}

			playSound();
			params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			camera.setParameters(params);
			camera.startPreview();
			isFlashOn = true;

			toggleButtonImage();
		}
	}

	private void turnOffFlash() {
		if (isFlashOn) {
			if (camera == null || params == null) {
				return;
			}

			playSound();
			params = camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(params);
			camera.stopPreview();
			isFlashOn = false;

			toggleButtonImage();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void toggleButtonImage() {
		if (isFlashOn) {
			switch_.setImageResource(R.drawable.onbutton);
		} else {
			switch_.setImageResource(R.drawable.offbutton);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		turnOffFlash();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		if (hasFlash) {
			turnOnFlash();
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

		if (camera != null) {
			camera.release();
			camera = null;
		}
		if (timer != null) {
			timer.cancel();
			timer.purge();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if (timer != null) {
			timer.cancel();
			timer.purge();
		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		getCamera();
	}

	private void playSound() {
		if (isFlashOn) {
			mp = MediaPlayer.create(this, R.raw.switch_off);
		} else {
			mp = MediaPlayer.create(this, R.raw.switch_on);
		}
		mp.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.release();
			}
		});
		mp.start();
	}
}
