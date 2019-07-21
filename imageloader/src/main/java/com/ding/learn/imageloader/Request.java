package com.ding.learn.imageloader;

import android.graphics.Bitmap;
import android.text.TextUtils;

/**
 * Created by jindingwei on 2019/7/12.
 */

public class Request {
    private String mUrl;
    private String mKey;
    private int mTargetWidth;
    private int mTargetHeight;
    private Bitmap.Config mConfig;
    private int mErrorResId;
    private boolean mCenterCrop;
    private boolean mCenterInside;
    private float mRotationDegrees;
    private int mResourceId;
    private String staticKey;

    public Request(String url, int resourceId, int targetWidth, int targetHeight,
                   Bitmap.Config config, int errorResId, boolean centerCrop,
                   boolean centerInside, float rotationDegrees) {
        mUrl = url;
        if (!TextUtils.isEmpty(mUrl)) {
            staticKey = mUrl;
        }
        mResourceId = resourceId;
        if (TextUtils.isEmpty(staticKey)) {
            staticKey = String.valueOf(mResourceId);
        }
        mTargetWidth = targetWidth;
        mTargetHeight = targetHeight;
        mConfig = config;
        mErrorResId = errorResId;
        mCenterCrop = centerCrop;
        mCenterInside = centerInside;
        mRotationDegrees = rotationDegrees;
    }

    public String getStaticKey() {
        return staticKey;
    }

    public String getUrl() {
        return mUrl;
    }

    public int getResourceId() {
        return mResourceId;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public String getKey() {
        return mKey;
    }

    public int getTargetWidth() {
        return mTargetWidth;
    }

    public void setTargetWidth(int targetWidth) {
        this.mTargetWidth = targetWidth;
    }

    public int getTargetHeight() {
        return mTargetHeight;
    }

    public void setTargetHeight(int targetHeight) {
        this.mTargetHeight = targetHeight;
    }

    public Bitmap.Config getConfig() {
        return mConfig;
    }

    public int getErrorResId() {
        return mErrorResId;
    }

    public boolean hasSizeData() {
        return mTargetHeight > 0 || mTargetWidth > 0;
    }

    public boolean isCenterCrop() {
        return mCenterCrop;
    }

    public boolean isCenterInside() {
        return mCenterInside;
    }

    public float getRotationDegrees() {
        return mRotationDegrees;
    }

    public static class Builder {
        private String url;
        private int targetWidth;
        private int targetHeight;
        private Bitmap.Config config;
        private int errorResId;
        private boolean centerInside;
        private boolean centerCrop;
        private float rotationDegrees;
        private int resourceId;

        public Builder() {

        }

        public Request build() {
            return new Request(url, resourceId, targetWidth, targetHeight, config, errorResId,
                    centerCrop, centerInside, rotationDegrees);
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setResourceId(int resourceId) {
            this.resourceId = resourceId;
            return this;
        }

        public Builder setTargetWidth(int targetWidth) {
            this.targetWidth = targetWidth;
            return this;
        }

        public Builder setTargetHeight(int targetHeight) {
            this.targetHeight = targetHeight;
            return this;
        }

        public Builder setConfig(Bitmap.Config config) {
            this.config = config;
            return this;
        }

        public Builder setErrorResId(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public Builder setCenterInside(boolean centerInside) {
            this.centerInside = centerInside;
            return this;
        }

        public Builder setCenterCrop(boolean centerCrop) {
            this.centerCrop = centerCrop;
            return this;
        }

        public Builder setRotationDegrees(float rotationDegrees) {
            this.rotationDegrees = rotationDegrees;
            return this;
        }
    }
}
