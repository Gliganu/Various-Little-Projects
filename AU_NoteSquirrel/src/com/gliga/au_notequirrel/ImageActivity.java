package com.gliga.au_notequirrel;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageActivity extends Activity implements PointCollectorListener {

	private static final String PASSWORD_SET = "PASSWORD_SET";
	private static final int POINT_CLOSENESS = 40;
	private PointCollector pointCollector = new PointCollector();
	private Database database = new Database(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image);

		addTouchListener();

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		boolean passpointsSet = preferences.getBoolean(PASSWORD_SET, false);

		if (!passpointsSet) {
			showPrompt(R.string.touch_points);
		}

		Bundle extras = getIntent().getExtras();
		
		if (extras != null) {

			Log.d(MainActivity.DEBUGTAG, "Extras not null...");
			Boolean resetPasspoints = extras
					.getBoolean(MainActivity.RESET_PASSPOINTS);

			if (resetPasspoints!= null && resetPasspoints == true) {
				showPrompt(R.string.create_passpoint_sequence);
				preferences = getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean(PASSWORD_SET, false);
				editor.commit();
			}
			
			String imagePath = extras.getString(MainActivity.NEW_IMAGE_PATH);
			
			if(imagePath != null){
				
				ImageView imageView = (ImageView) findViewById(R.id.touch_image);
				imageView.setImageURI(Uri.parse(imagePath));
				//imageView.setImageDrawable(getResources().getDrawable(R.drawable.udemy_icon));
				Toast.makeText(this, imagePath, Toast.LENGTH_LONG).show();
			}

		}

		pointCollector.setListener(this);

	}

	private void showPrompt(int messageID) {
		AlertDialog.Builder builder = new Builder(this);

		builder.setPositiveButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});

		builder.setTitle(R.string.configuration).setMessage(messageID);

		AlertDialog dialog = builder.create();
		dialog.show();

	}

	private void addTouchListener() {
		ImageView imageView = (ImageView) findViewById(R.id.touch_image);
		imageView.setOnTouchListener(pointCollector);
	}

	private void savePasspoints(final List<Point> pointList) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.storing_data);

		final AlertDialog dialog = builder.create();

		dialog.show();

		AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				database.storePoints(pointList);
				Log.d(MainActivity.DEBUGTAG,
						"Points saved: " + pointList.size());
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				SharedPreferences preferences = getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor editor = preferences.edit();

				editor.putBoolean(PASSWORD_SET, true);
				editor.commit();

				pointCollector.clearSavedPoints();
				dialog.dismiss();
			}
		};

		task.execute();
		showPrompt(R.string.passpoints_saved);
	}

	private void verifyPasspoints(final List<Point> touchedPoints) {

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Checking passpoints...");
		final AlertDialog dialog = builder.create();
		dialog.show();

		AsyncTask<Void, Void, Boolean> task = new AsyncTask<Void, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(Void... params) {

				List<Point> savedPoints = database.getPoints();
				Log.d(MainActivity.DEBUGTAG,
						"Saved points: " + savedPoints.size());

				if (savedPoints.size() != PointCollector.NUM_POINTS
						|| touchedPoints.size() != PointCollector.NUM_POINTS) {
					return false;
				}

				for (int i = 0; i < PointCollector.NUM_POINTS; i++) {
					Point savedPoint = savedPoints.get(i);
					Point touchedPoint = touchedPoints.get(i);

					int xDiff = savedPoint.x - touchedPoint.x;
					int yDiff = savedPoint.y - touchedPoint.y;

					int distanceSquared = xDiff * xDiff + yDiff * yDiff;
					Log.d(MainActivity.DEBUGTAG, "Distance squared: "
							+ distanceSquared);
					if (distanceSquared > POINT_CLOSENESS * POINT_CLOSENESS) {
						return false;
					}
				}

				return true;
			}

			@Override
			protected void onPostExecute(Boolean pass) {
				dialog.dismiss();
				pointCollector.clearSavedPoints();

				if (pass == true) {
					Intent intent = new Intent(ImageActivity.this,
							MainActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(ImageActivity.this, "Access Denied",
							Toast.LENGTH_LONG).show();
				}

			}
		};

		task.execute();
	}

	@Override
	public void pointsCollected(final List<Point> pointList) {

		SharedPreferences preferences = getPreferences(MODE_PRIVATE);
		boolean passpointsSet = preferences.getBoolean(PASSWORD_SET, false);

		if (!passpointsSet) {
			Log.d(MainActivity.DEBUGTAG, "Saving passpoints...");
			savePasspoints(pointList);
		} else {
			Log.d(MainActivity.DEBUGTAG, "Verifying passpoints...");
			verifyPasspoints(pointList);
		}

	}

}
