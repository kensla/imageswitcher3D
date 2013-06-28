package com.kensla.cache;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

public class KenslaImage implements KImage {
	private static KImageCache webImageCache;
	private Context context;

	private String name;

	public KenslaImage(Context context) {
		// this.name = name;
		this.context = context;
	}

	@Override
	public Bitmap getBitmap(Context context) {
		// Don't leak context
		if (webImageCache == null) {
			webImageCache = new KImageCache();
		}
		this.context = context;

		// Try getting bitmap from cache first
		Bitmap bitmap = null;
		if (name != null) {
			bitmap = webImageCache.get(name);
			if (bitmap == null) {
				bitmap = getBitmapFromResource(name);
				if (bitmap != null) {
					webImageCache.put(name, bitmap);
				}
			}
		}

		return bitmap;
	}

	private Bitmap getBitmapFromResource(String name) {
		Bitmap bitmap = null;
		ApplicationInfo appInfo = context.getApplicationInfo();
		int resID = context.getResources().getIdentifier(name, "drawable",
				appInfo.packageName);
		if (resID != 0) {
			bitmap = BitmapFactory
					.decodeResource(context.getResources(), resID);
		}
		return bitmap;
	}

	public static void removeFromCache(String name) {
		if (webImageCache != null) {
			webImageCache.remove(name);
		}
	}

	@Override
	public BitmapDrawable getBitmap(String name) {
		if (webImageCache == null) {
			webImageCache = new KImageCache();
		}

		// Try getting bitmap from cache first
		Bitmap bitmap = null;
		if (name != null) {
			bitmap = webImageCache.get(name);
			if (bitmap == null) {
				bitmap = getBitmapFromResource(name);
				if (bitmap != null) {
					webImageCache.put(name, bitmap);
				}
			}
		}
		return new BitmapDrawable(context.getResources(), bitmap);
	}
}
