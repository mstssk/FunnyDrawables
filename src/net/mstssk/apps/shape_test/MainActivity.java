package net.mstssk.apps.shape_test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.robot_layout);
		findViewById(R.id.robot_view).setOnTouchListener(new OnScaleGestureListener(this));
	}

	/**
	 * for Pinch-in-out gesture
	 */
	private static class OnScaleGestureListener implements View.OnTouchListener {
		private ScaleGestureDetector detector;
		private View view;

		private OnScaleGestureListener(final Context context) {
			detector = new ScaleGestureDetector(context, listenr);
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			this.view = v;
			return detector.onTouchEvent(event);
		}

		private ScaleGestureDetector.SimpleOnScaleGestureListener listenr = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
			public boolean onScale(ScaleGestureDetector detector) {
				float newScale = detector.getScaleFactor();
				view.setScaleX(view.getScaleX() * newScale);
				view.setScaleY(view.getScaleY() * newScale);
				view.invalidate();
				return true;
			};
		};
	}

}
