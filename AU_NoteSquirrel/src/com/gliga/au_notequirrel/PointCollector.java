package com.gliga.au_notequirrel;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class PointCollector implements OnTouchListener {

	public static final int NUM_POINTS = 4;
	private List<Point> pointList = new ArrayList<>();
	private PointCollectorListener listener;
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		int x = (int) event.getX();
		int y = (int) event.getY();
		String message = "Coordinates: " + x + " - " + y;
		Log.d(MainActivity.DEBUGTAG, message);

		pointList.add(new Point(x, y));

		if (pointList.size() == NUM_POINTS && listener != null) {
			listener.pointsCollected(pointList);
		}
		return false;
	}

	public void setListener(PointCollectorListener listener) {
		this.listener = listener;
	}
	
	public void clearSavedPoints(){
		pointList.clear();
	}
	
	

}
