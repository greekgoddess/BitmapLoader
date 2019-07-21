package com.ding.learn.imageloader;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by jindingwei on 2019/7/15.
 */

public class Dispatcher {
    public static final int BITMAP_LOAD_COMPLETE = 101;
    public static final int BITMAP_LOAD_FAILED = 102;

    private ThreadPoolExecutor mExecutor;
    private Map<String, BitmapLoader> mLoaderMap;
    private ImageLoader mImageLoader;

    public Dispatcher(ThreadPoolExecutor executor, ImageLoader imageLoader) {
        mExecutor = executor;
        mImageLoader = imageLoader;
        mLoaderMap = new LinkedHashMap<>();
    }

    private Handler mMainThreadHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BITMAP_LOAD_COMPLETE:
                    if (msg.obj instanceof BitmapLoader) {
                        BitmapLoader loader = (BitmapLoader) msg.obj;
                        mImageLoader.bitmapLoadComplete(loader);
                    }
                    break;
                case BITMAP_LOAD_FAILED:
                    if (msg.obj instanceof BitmapLoader) {
                        BitmapLoader loader = (BitmapLoader) msg.obj;
                        mImageLoader.bitmapLoadFailed(loader);
                    }
                    break;
            }
        }
    };

    public void executorAction(Action action) {
        String key = action.getKey();
        BitmapLoader bitmapLoader = mLoaderMap.get(key);
        if (bitmapLoader != null) {
            bitmapLoader.addAction(action);
            return;
        }
        if (mExecutor.isShutdown()) {
            return;
        }
        for (RequestHandler requestHandler : mImageLoader.getRequestHandlerList()) {
            if (requestHandler.canHandlerRequest(action.getRequest())) {
                bitmapLoader = new BitmapLoader(mImageLoader, action.getRequest(), requestHandler, Dispatcher.this);
                bitmapLoader.addAction(action);
                mLoaderMap.put(key, bitmapLoader);
                Future future = mExecutor.submit(bitmapLoader);
                bitmapLoader.setFuture(future);
            }
        }
    }

    public void dispatchLoadComplete(BitmapLoader loader) {
        mLoaderMap.remove(loader.getKey());
        Message message = Message.obtain();
        message.what = BITMAP_LOAD_COMPLETE;
        message.obj = loader;
        mMainThreadHandler.sendMessage(message);
    }

    public void dispatchLoadFailed(BitmapLoader loader) {
        mLoaderMap.remove(loader.getKey());
        Message message = Message.obtain();
        message.what = BITMAP_LOAD_FAILED;
        message.obj = loader;
        mMainThreadHandler.sendMessage(message);
    }

    public void dispatchCancleAction(Action action) {
        String key = action.getKey();
        BitmapLoader bitmapLoader = mLoaderMap.get(key);
        if (bitmapLoader != null) {
            bitmapLoader.removeAction(action);
            if (bitmapLoader.isEmptyActionList() && bitmapLoader.cancle()) {
                System.out.println("ding---cancle");
                mLoaderMap.remove(bitmapLoader);
            }
        }
    }
}
