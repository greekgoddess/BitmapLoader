package com.ding.learn.imageloader;

import android.graphics.Bitmap;

/**
 * Created by jindingwei on 2019/7/12.
 */

public interface CallBack {

    void onSuccess(Bitmap bitmap);

    void onFailed();
}
