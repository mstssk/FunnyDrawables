package net.mstssk.apps.funny_drawables;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vvakame_layout);
		OnScaleGestureListener gestureListener = new OnScaleGestureListener(this);
		View view = findViewById(R.id.vvakame_view);
		view.setOnTouchListener(gestureListener);
		view.setOnLongClickListener(gestureListener);
	}

	private static class ReplacingState {

		private int index = 0;
		private int[] ids;

		public ReplacingState(int... ids) {
			this.ids = ids;
		}

		public int next() {
			if (index >= ids.length) {
				index = 0;
			}
			return ids[index++];
		}
	}

	/**
	 * Pinch-in-out zoom gesture, and Replace drawable 
	 */
	private static class OnScaleGestureListener implements View.OnTouchListener, View.OnLongClickListener {
		private ScaleGestureDetector detector;
		private View view;
		private long lastScaledTime = getNow();
		private ReplacingState state = new ReplacingState(R.drawable.robot_drawable, R.drawable.vvakame_drawable);

		private OnScaleGestureListener(final Context context) {
			detector = new ScaleGestureDetector(context, listenr);
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				this.view = v;
				return detector.onTouchEvent(event);
			}
			return false;
		}

		@Override
		public boolean onLongClick(View v) {
			// clearance
			if ((getNow() - lastScaledTime) > 200) {
				ImageView hoge = (ImageView) v;
				int next = state.next();
				hoge.setImageResource(next);
				hoge.invalidate();
				Log.d("mstssk", "long touch:" + next);
				return true;
			}
			return false;
		}

		private ScaleGestureDetector.SimpleOnScaleGestureListener listenr = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
			public boolean onScale(ScaleGestureDetector detector) {
				float newScale = detector.getScaleFactor();
				view.setScaleX(view.getScaleX() * newScale);
				view.setScaleY(view.getScaleY() * newScale);
				view.invalidate();
				lastScaledTime = getNow();
				return true;
			};
		};

		private static long getNow() {
			return System.currentTimeMillis();
		}

	}

}
