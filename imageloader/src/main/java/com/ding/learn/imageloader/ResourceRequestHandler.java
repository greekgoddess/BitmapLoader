package com.ding.learn.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by jindingwei on 2019/7/17.
 */

public class ResourceRequestHandler extends RequestHandler {
    private Context mContext;

    public ResourceRequestHandler(Context context) {
        mContext = context;
    }

    @Override
    public ResultWrapper load(Request request) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), request.getResourceId());
        if (bitmap != null) {
            ResultWrapper resultWrapper = new ResultWrapper();
            resultWrapper.bitmap = bitmap;
            return resultWrapper;
        }
        return null;
    }

    @Override
    public boolean canHandlerRequest(Request request) {
        if (request != null) {
            if (request.getResourceId() != 0) {
                return true;
            }
        }
        return false;
    }
}
