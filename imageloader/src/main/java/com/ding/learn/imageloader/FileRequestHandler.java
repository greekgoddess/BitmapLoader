package com.ding.learn.imageloader;

import android.text.TextUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by jindingwei on 2019/7/17.
 */

public class FileRequestHandler extends RequestHandler {
    private static final String FILE_SCHEME = "file:";

    public FileRequestHandler() {

    }

    @Override
    public ResultWrapper load(Request request) {
        String path = request.getUrl().substring(FILE_SCHEME.length());
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        try {
            FileInputStream ins = new FileInputStream(path);
            InputStreamWrapper inputStreamWrapper = new InputStreamWrapper(ins);
            ResultWrapper resultWrapper = new ResultWrapper();
            resultWrapper.ins = inputStreamWrapper;
            return resultWrapper;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean canHandlerRequest(Request request) {
        if (request != null) {
            String uri = request.getUrl();
            if (!TextUtils.isEmpty(uri) && uri.startsWith(FILE_SCHEME)) {
                return true;
            }
        }
        return false;
    }
}
