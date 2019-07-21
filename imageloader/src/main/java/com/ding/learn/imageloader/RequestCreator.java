package com.ding.learn.imageloader;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by jindingwei on 2019/7/12.
 */

public class RequestCreator {
    private Request.Builder mRequestBuilder;
    private int memoryPolicy;
    private ImageLoader mImageLoader;
    private int placeHolderResId;

    public RequestCreator(ImageLoader imageLoader, String url) {
        mImageLoader = imageLoader;
        mRequestBuilder = new Request.Builder();
        mRequestBuilder.setUrl(url);
    }

    public RequestCreator(ImageLoader imageLoader, int resourceId) {
        mImageLoader = imageLoader;
        mRequestBuilder = new Request.Builder();
        mRequestBuilder.setResourceId(resourceId);
    }

    public RequestCreator setMemoryPolicy(int memoryPolicy) {
        this.memoryPolicy = memoryPolicy;
        return this;
    }

    public RequestCreator setPlaceHolderResId(int placeHolderResId) {
        this.placeHolderResId = placeHolderResId;
        return this;
    }

    public RequestCreator setErrorResId(int errorResId) {
        mRequestBuilder.setErrorResId(errorResId);
        return this;
    }

    public RequestCreator setCenterCrop(boolean centerCrop) {
        mRequestBuilder.setCenterCrop(centerCrop);
        return this;
    }

    public RequestCreator setRotationDegrees(float rotationDegrees) {
        mRequestBuilder.setRotationDegrees(rotationDegrees);
        return this;
    }

    public RequestCreator setCenterInside(boolean centerInside) {
        mRequestBuilder.setCenterInside(centerInside);
        return this;
    }

    public RequestCreator resize(int targetWidth, int targetHeight) {
        mRequestBuilder.setTargetWidth(targetWidth);
        mRequestBuilder.setTargetHeight(targetHeight);
        return this;
    }

    public RequestCreator setConfig(Bitmap.Config config) {
        mRequestBuilder.setConfig(config);
        return this;
    }

    public void into(ImageView target) {
        if (target == null) {
            return;
        }
        into(target, null);
    }

    public void into(ImageView target, CallBack callBack) {

        Request request = mRequestBuilder.build();
        String requestKey = Utils.createKey(request);
        request.setKey(requestKey);

        if (MemoryPolicy.canReadFromMemory(memoryPolicy)) {
            Bitmap bitmap = mImageLoader.readFromMemoryCache(requestKey);
            if (bitmap != null) {
                if (callBack != null) {
                    callBack.onSuccess(bitmap);
                }
            }
        }

        if (placeHolderResId != 0) {
            target.setImageDrawable(mImageLoader.getContext().getResources().getDrawable(placeHolderResId));
        }

        Action action = new ImageViewAction(mImageLoader, request, target, callBack);
        mImageLoader.submitAction(action);
    }

    public void loadBitmap(CallBack callBack) {
        Request request = mRequestBuilder.build();
        String key = Utils.createKey(request);
        request.setKey(key);

        if (MemoryPolicy.canReadFromMemory(memoryPolicy)) {
            Bitmap bitmap = mImageLoader.readFromMemoryCache(key);
            if (bitmap != null) {
                if (callBack != null) {
                    callBack.onSuccess(bitmap);
                }
            }
        }

        Action action = new LoadBitmapAction(mImageLoader, request, callBack);
        mImageLoader.submitAction(action);
    }
}
