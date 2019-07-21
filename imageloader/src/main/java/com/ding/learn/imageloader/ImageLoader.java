package com.ding.learn.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by jindingwei on 2019/7/12.
 */

public class ImageLoader {
    private volatile static ImageLoader mInstance;
    private Context mContext;
    private MemoryCache mMemoryCache;
    private ImageLoadExecutorService mExecutor;
    private Dispatcher mDispatcher;
    private Map<ImageView, Action> mTargetToAction;
    private List<RequestHandler> mRequestHandlerList;
    public ImageLoaderConfig config;

    private ImageLoader() {
        mMemoryCache = new MemoryCache((int) (Runtime.getRuntime().maxMemory() / 8));
        mExecutor = new ImageLoadExecutorService();
        mDispatcher = new Dispatcher(mExecutor, ImageLoader.this);
        mTargetToAction = new WeakHashMap<>();
    }

    public void init(Context context, ImageLoaderConfig config) {
        mContext = context;
        this.config = config;
        if (this.config == null) {
            this.config = new ImageLoaderConfig();
        }

        mRequestHandlerList = new LinkedList<>();
        mRequestHandlerList.add(new NetRequestHandler());
        mRequestHandlerList.add(new FileRequestHandler());
        mRequestHandlerList.add(new ResourceRequestHandler(mContext));
    }

    public static ImageLoader with() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader();
                }
            }
        }
        return mInstance;
    }

    public Context getContext() {
        return mContext;
    }

    public void bitmapLoadComplete(BitmapLoader loader) {
        if (loader == null) {
            return;
        }
        Bitmap bitmap = loader.getResult();
        if (bitmap != null && bitmap != mMemoryCache.get(loader.getKey())) {
            mMemoryCache.put(loader.getKey(), bitmap);
        }
        for (Action action : loader.getActions()) {
            if (!action.isCancled()) {
                action.complete(bitmap);
            }
            mTargetToAction.remove(action.getTarget());
        }
    }

    public void bitmapLoadFailed(BitmapLoader loader) {
        if (loader == null) {
            return;
        }
        for (Action action : loader.getActions()) {
            if (!action.isCancled()) {
                action.error();
            }
            mTargetToAction.remove(action.getTarget());
        }
    }

    public RequestCreator load(String url) {
        return new RequestCreator(this, url);
    }

    public RequestCreator load(int resourceId) {
        return new RequestCreator(this, resourceId);
    }

    public Bitmap readFromMemoryCache(String key) {
        if (key == null) {
            throw new IllegalStateException("request key == null");
        }
        return mMemoryCache.get(key);
    }

    public List<RequestHandler> getRequestHandlerList() {
        return mRequestHandlerList;
    }

    public void submitAction(Action action) {
        if (action == null) {
            return;
        }
        ImageView target = action.getTarget();
        if (target != null && mTargetToAction.get(target) != action) {
            cancleExistingAction(target);
            mTargetToAction.put(target, action);
        }
        mDispatcher.executorAction(action);
    }

    public void cancleExistingAction(ImageView target) {
        Action action = mTargetToAction.remove(target);
        if (action != null) {
            action.cancle();
            mDispatcher.dispatchCancleAction(action);
        }
    }
}
