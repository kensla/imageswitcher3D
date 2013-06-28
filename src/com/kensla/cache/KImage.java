package com.kensla.cache;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

public interface KImage {
    public BitmapDrawable getBitmap(String name);
    public Bitmap getBitmap(Context context);
}