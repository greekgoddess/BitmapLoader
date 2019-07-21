package com.ding.learn.imageloader;

import android.text.TextUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jindingwei on 2019/7/15.
 */

public abstract class RequestHandler {

    public RequestHandler() {

    }

    public abstract ResultWrapper load(Request request);

    public abstract boolean canHandlerRequest(Request request);
}
