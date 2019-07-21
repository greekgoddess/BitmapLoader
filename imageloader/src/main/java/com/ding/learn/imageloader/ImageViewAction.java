package com.ding.learn.imageloader;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

/**
 * Created by jindingwei on 2019/7/15.
 */

public class ImageViewAction extends Action {

    public ImageViewAction(ImageLoader imageLoader, Request request, ImageView target, CallBack callBack) {
        super(imageLoader, request, target, callBack);
    }

    @Override
    public void complete(Bitmap bitmap) {
        if (isCancled()) {
            return;
        }
        if (bitmap != null) {
            if (mTarget.get() != null) {
                mTarget.get().setImageDrawable(new BitmapDrawable(mImageLoader.getContext().getResources(), bitmap));
            }
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
        if (mTarget.get() != null) {
            mTarget.get().setImageDrawable(mImageLoader.getContext().getResources().getDrawable(mRequest.getErrorResId()));
        }
        if (mCallback != null) {
            mCallback.onFailed();
        }
    }
}
