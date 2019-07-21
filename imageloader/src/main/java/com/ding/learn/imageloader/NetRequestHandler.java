package com.ding.learn.imageloader;

import android.text.TextUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jindingwei on 2019/7/17.
 */

public class NetRequestHandler extends RequestHandler {
    private static final String HTTP_SCHEME = "http";
    private static final String HTTPS_SCHEME = "https";

    public NetRequestHandler() {

    }

    @Override
    public ResultWrapper load(Request request) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(request.getUrl()).openConnection();
            connection.setConnectTimeout(4000);
            connection.setReadTimeout(4000);
            connection.setRequestMethod("GET");
            connection.connect();
            if (connection.getResponseCode() == 200) {
                InputStreamWrapper inputStream = new InputStreamWrapper(connection.getInputStream());
                if (inputStream != null) {
                    ResultWrapper resultWrapper = new ResultWrapper();
                    resultWrapper.needCache = true;
                    resultWrapper.ins = inputStream;
                    return resultWrapper;
                }
            } else {
                connection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean canHandlerRequest(Request request) {
        if (request != null) {
            String uri = request.getUrl();
            if (!TextUtils.isEmpty(uri)) {
                if (uri.startsWith(HTTP_SCHEME) || uri.startsWith(HTTPS_SCHEME)) {
                    return true;
                }
            }
        }
        return false;
    }
}
