package com.ding.learn.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by jindingwei on 2019/7/15.
 */

public abstract class Action {

    protected Request mRequest;
    protected ImageLoader mImageLoader;
    protected WeakReference<ImageView> mTarget;
    protected CallBack mCallback;

    private boolean isCancled;

    public Action(ImageLoader imageLoader, Request request, ImageView target, CallBack callBack) {
        mImageLoader = imageLoader;
        mRequest = request;
        mCallback = callBack;
        if (target == null) {
            mTarget = null;
        } else {
            mTarget = new WeakReference<>(target);
        }
    }

    public abstract void complete(Bitmap bitmap);

    public abstract void error();

    public boolean isCancled() {
        return isCancled;
    }

    public void cancle() {
        isCancled = true;
    }

    public ImageView getTarget() {
        if (mTarget == null) {
            return null;
        }
        return mTarget.get();
    }

    public String getKey() {
        return mRequest.getKey();
    }

    public Request getRequest() {
        return mRequest;
    }
}
