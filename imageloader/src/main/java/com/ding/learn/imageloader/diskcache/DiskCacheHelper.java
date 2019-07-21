package com.ding.learn.imageloader.diskcache;

import com.ding.learn.imageloader.ImageLoader;
import com.ding.learn.imageloader.InputStreamWrapper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by jindingwei on 2019/7/16.
 */

public class DiskCacheHelper {

    private DiskLruCache mDiskCache;
    private OutputStream mDiskOps;
    private DiskLruCache.Editor mEditor;
    private String mDiskCacheKey;

    public DiskCacheHelper(String key) {
        mDiskCacheKey = key;
        File dir = new File(ImageLoader.with().config.diskCachePath);
        try {
            if (!dir.exists()) {
                dir.mkdirs();
            }
            mDiskCache = DiskLruCache.open(dir, 1, 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(int result) {
        try {
            if (mDiskOps == null) {
                mEditor = mDiskCache.edit(mDiskCacheKey);
                mDiskOps = mEditor.newOutputStream(0);
            }
            mDiskOps.write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InputStreamWrapper get() {
        DiskLruCache.Snapshot snapshot = null;
        try {
            snapshot = mDiskCache.get(mDiskCacheKey);
            if (snapshot != null) {
                InputStreamWrapper ins = new InputStreamWrapper(snapshot.getInputStream(0));
                return ins;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        try {
            if (mDiskOps != null) {
                mDiskOps.close();
                mDiskOps.flush();
                mDiskOps = null;
            }
            if (mEditor != null) {
                mEditor.commit();
                mEditor = null;
            }
            if (mDiskCache != null) {
                mDiskCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
