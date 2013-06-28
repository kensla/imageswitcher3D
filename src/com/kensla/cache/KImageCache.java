package com.kensla.cache;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;

import android.graphics.Bitmap;

public class KImageCache {

	private ConcurrentHashMap<String, SoftReference<Bitmap>> memoryCache;

	public KImageCache() {
		// Set up in-memory cache store
		memoryCache = new ConcurrentHashMap<String, SoftReference<Bitmap>>();
	}

	public Bitmap get(final String url) {
		Bitmap bitmap = null;

		// Check for image in memory
		bitmap = getBitmapFromMemory(url);
		return bitmap;
	}

	public void put(String url, Bitmap bitmap) {
		cacheBitmapToMemory(url, bitmap);
	}

	public void remove(String url) {
		if (url == null) {
			return;
		}

		// Remove from memory cache
		memoryCache.remove(getCacheKey(url));
	}

	public void clear() {
		// Remove everything from memory cache
		memoryCache.clear();
	}

	private void cacheBitmapToMemory(final String url, final Bitmap bitmap) {
		memoryCache.put(getCacheKey(url), new SoftReference<Bitmap>(bitmap));
	}

	private Bitmap getBitmapFromMemory(String url) {
		Bitmap bitmap = null;
		SoftReference<Bitmap> softRef = memoryCache.get(getCacheKey(url));
		if (softRef != null) {
			bitmap = softRef.get();
		}
		return bitmap;
	}

	private String getCacheKey(String url) {
		if (url == null) {
			throw new RuntimeException("Null url passed in");
		} else {
			return url.replaceAll("[.:/,%?&=]", "+").replaceAll("[+]+", "+");
		}
	}
}
