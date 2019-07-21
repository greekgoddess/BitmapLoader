package com.ding.learn.imageloader;


import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by jindingwei on 2019/7/1.
 */

public class MemoryCache extends LruCache<String, Bitmap> {

    public MemoryCache(int maxSize) {
        super(maxSize);
    }

    @Override
    protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
        oldValue.recycle();
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        if (value == null) {
            return 0;
        }
        return value.getRowBytes() * value.getHeight();
    }
}
