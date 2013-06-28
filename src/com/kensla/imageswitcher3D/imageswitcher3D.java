package com.kensla.imageswitcher3D;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.kensla.cache.KenslaImage;

public class imageswitcher3D extends Activity {
	
	String[] imageUrl = new String[] {"a","b","c","d"};
	
	View[] views = new View[4];
	ImageSwitcher imswitcher;
	GestureDetector mGestureDetector;
	KenslaImage webImage ;
	int i = 0;

	Runnable r;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello);
		
		webImage =new KenslaImage(this);
		
		imswitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher1);
		imswitcher.setFactory(new ViewFactory() {
			@Override
			public View makeView() {
				ImageView imageView = new ImageView(imageswitcher3D.this);
				imageView.setBackgroundColor(0xff0000);
				imageView.setScaleType(ImageView.ScaleType.FIT_START);
				imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
						android.view.ViewGroup.LayoutParams.FILL_PARENT, android.view.ViewGroup.LayoutParams.FILL_PARENT));
				return imageView;
			}
		});
		
		//imswitcher.setImageResource(R.drawable.a);
		imswitcher.setImageDrawable(webImage.getBitmap("a"));
		View v1 = findViewById(R.id.view1);
		View v2 = findViewById(R.id.view2);
		View v3 = findViewById(R.id.view3);
		View v4 = findViewById(R.id.view4);
		views[0] = v1;
		views[1] = v2;
		views[2] = v3;
		views[3] = v4;

		mGestureDetector = new GestureDetector(this, new MyGestureListener());
		imswitcher.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				mGestureDetector.onTouchEvent(event);
				return true;
			}
		});

		DownloadTask dTask = new DownloadTask();
		dTask.execute(100);
	}

	public void setpic(int m) {

		for (int i = 0; i < views.length; i++) {
			if (i == m) {
				views[i].setBackgroundColor(0xffb50202);
			} else {
				views[i].setBackgroundColor(0xffebeaea);
			}

		}

	}

	private class MyGestureListener implements
			GestureDetector.OnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			if (velocityX > 0) {

				float halfWidth = imswitcher.getWidth() / 2.0f;
				float halfHeight = imswitcher.getHeight() / 2.0f;
				int duration = 500;

				Rotate3D rdin = new Rotate3D(-75, 0, 0, halfWidth, halfHeight);
				rdin.setDuration(duration);
				rdin.setFillAfter(true);
				imswitcher.setInAnimation(rdin);
				Rotate3D rdout = new Rotate3D(15, 90, 0, halfWidth, halfHeight);

				rdout.setDuration(duration);
				rdout.setFillAfter(true);
				imswitcher.setOutAnimation(rdout);

				i = (i - 1);

				Log.i("i��ֵ", String.valueOf(i));
				int p = i % 4;
				Log.i("p��ֵ", String.valueOf(p));
				if (p >= 0) {
					setpic(p);
					//imswitcher.setImageResource(imageIds[p]);
					imswitcher.setImageDrawable(webImage.getBitmap(imageUrl[p]));

				} else {

					int k = 4 + p;
					setpic(k);
					//imswitcher.setImageResource(imageIds[k]);
					imswitcher.setImageDrawable(webImage.getBitmap(imageUrl[k]));

				}

			}
			if (velocityX < 0) {

				float halfWidth = imswitcher.getWidth() / 2.0f;
				float halfHeight = imswitcher.getHeight() / 2.0f;
				int duration = 500;

				Rotate3D rdin = new Rotate3D(75, 0, 0, halfWidth, halfHeight);
				rdin.setDuration(duration);
				rdin.setFillAfter(true);
				imswitcher.setInAnimation(rdin);
				Rotate3D rdout = new Rotate3D(-15, -90, 0, halfWidth,
						halfHeight);

				rdout.setDuration(duration);
				rdout.setFillAfter(true);
				imswitcher.setOutAnimation(rdout);

				i = (i + 1);
				int p = i % 4;

				if (p >= 0) {
					setpic(p);
					//imswitcher.setImageResource(imageIds[p]);
					imswitcher.setImageDrawable(webImage.getBitmap(imageUrl[p]));

				} else {

					int k = 4 + p;
					setpic(k);
					//imswitcher.setImageResource(imageIds[k]);
					imswitcher.setImageDrawable(webImage.getBitmap(imageUrl[k]));

				}
			}
			return true;
		}

		@Override
		public void onLongPress(MotionEvent e) {

		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {

		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {

			int p = i % 4;

			if (p >= 0) {

				Toast.makeText(getApplicationContext(), String.valueOf(p),
						Toast.LENGTH_SHORT).show();
			} else {

				int k = 4 + p;
				Toast.makeText(getApplicationContext(), String.valueOf(k),
						Toast.LENGTH_SHORT).show();
			}

			return true;
		}

	}

	class DownloadTask extends AsyncTask<Object, Object, Object> {

		@Override
		protected Object doInBackground(Object... arg0) {

			for (int i = 0; i < Integer.MAX_VALUE; i++) {
				try {
					Thread.sleep(6000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				publishProgress(arg0);

			}

			return null;
		}

		@Override
		protected void onProgressUpdate(Object... values) {
			super.onProgressUpdate(values);

			float halfWidth = imswitcher.getWidth() / 2.0f;
			float halfHeight = imswitcher.getHeight() / 2.0f;
			int duration = 500;

			Rotate3D rdin = new Rotate3D(75, 0, 0, halfWidth, halfHeight);
			rdin.setDuration(duration);
			rdin.setFillAfter(true);
			imswitcher.setInAnimation(rdin);
			Rotate3D rdout = new Rotate3D(-15, -90, 0, halfWidth, halfHeight);

			rdout.setDuration(duration);
			rdout.setFillAfter(true);
			imswitcher.setOutAnimation(rdout);

			i = (i + 1);
			int p = i % 4;
  
			if (p >= 0) {
				setpic(p);
				//imswitcher.setImageResource(imageIds[p]);
				imswitcher.setImageDrawable(webImage.getBitmap(imageUrl[p]));
			} else {

				int k = 4 + p;
				setpic(k);
				//imswitcher.setImageResource(imageIds[k]);
				imswitcher.setImageDrawable(webImage.getBitmap(imageUrl[k]));
			}

		}

	}
}
