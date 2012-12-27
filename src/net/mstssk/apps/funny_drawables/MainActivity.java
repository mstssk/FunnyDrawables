package net.mstssk.apps.funny_drawables;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);

		FragmentManager manager = this.getFragmentManager();
		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(new MyAdapter(manager));
	}

	private class MyAdapter extends FragmentStatePagerAdapter {

		private final int[] layoutIds = { R.layout.robot_layout, R.layout.vvakame_layout };

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return MyFragment.newInstance(layoutIds[position % layoutIds.length]);
		}

		@Override
		public int getCount() {
			return layoutIds.length;
		}

	}

	public static class MyFragment extends Fragment {

		private int layoutId;

		static MyFragment newInstance(int layoutId) {
			MyFragment fragment = new MyFragment();
			Bundle args = new Bundle();
			args.putInt("layoutId", layoutId);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			layoutId = getArguments().getInt("layoutId");
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View view = inflater.inflate(this.layoutId, container, false);
			view.findViewById(R.id.drawable_view).setOnTouchListener(new OnScaleGestureListener(inflater.getContext()));
			return view;
		}

	}

	/**
	 * Pinch-in-out zoom gesture, and Replace drawable 
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
