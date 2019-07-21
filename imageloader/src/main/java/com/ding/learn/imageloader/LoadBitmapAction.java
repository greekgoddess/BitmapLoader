package com.ding.learn.imageloader;

import android.graphics.Bitmap;

/**
 * Created by jindingwei on 2019/7/17.
 */

public class LoadBitmapAction extends Action {

    public LoadBitmapAction(ImageLoader imageLoader, Request request, CallBack callBack) {
        super(imageLoader, request, null, callBack);
    }

    @Override
    public void complete(Bitmap bitmap) {
        if (isCancled()) {
            return;
        }
        if (bitmap != null) {
            if (mCallback != null) {
                mCallback.onSuccess(bitmap);
            }
        }
    }

    @Override
    public void error() {
        if (isCancled()) {
            return;
        }
        if (mCallback != null) {
            mCallback.onFailed();
        }
    }
}
