package com.ding.learn.imageloader;

import android.graphics.BitmapFactory;

/**
 * Created by jindingwei on 2019/7/16.
 */

public class BitmapDecodeHelper {

    public static BitmapFactory.Options createOptionsByRequest(Request request) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = request.hasSizeData();
        if (request.getConfig() != null) {
            options.inPreferredConfig = request.getConfig();
        }
        return options;
    }

    public static void calculateInSampleSize(int reqWidth, int reqHeight, Request request, BitmapFactory.Options options) {
        int inSampleSize = 1;
        if (options.outHeight > reqHeight || options.outWidth > reqWidth) {
            if (reqHeight == 0) {
                inSampleSize = (int) Math.floor(((float)(options.outWidth)) / reqWidth);
            } else if (reqWidth == 0) {
                inSampleSize = (int) Math.floor(((float)(options.outHeight)) / reqHeight);
            } else {
                int heightRadio = (int) Math.floor(((float)(options.outHeight)) / reqHeight);
                int widthRadio = (int) Math.floor(((float)(options.outWidth)) / reqWidth);
                inSampleSize = request.isCenterInside()
                        ? Math.max(heightRadio, widthRadio)
                        : Math.min(heightRadio, widthRadio);
            }
        }
        options.inSampleSize = inSampleSize;
    }
}
