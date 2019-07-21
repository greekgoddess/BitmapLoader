package com.ding.learn.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.ding.learn.imageloader.diskcache.DiskCacheHelper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by jindingwei on 2019/7/15.
 */

public class BitmapLoader implements Runnable {
    private LinkedList<Action> mActionList;
    private Request mRequest;
    private RequestHandler mRequestHandler;
    private Bitmap mResult;
    private Dispatcher mDispatcher;
    private DiskCacheHelper mDiskCache;
    private ImageLoader mImageLoader;
    private Future mFuture;

    public BitmapLoader(ImageLoader imageLoader, Request request, RequestHandler requestHandler,
                        Dispatcher dispatcher) {
        mImageLoader = imageLoader;
        mActionList = new LinkedList<>();
        mRequest = request;
        mRequestHandler = requestHandler;
        mDispatcher = dispatcher;
        mDiskCache = new DiskCacheHelper(Utils.hashKeyForDisk(mRequest.getStaticKey()));
    }

    @Override
    public void run() {
        ResultWrapper resultWrapper = null;
        if (mImageLoader.config.enableDiskCache) {
            resultWrapper = loadBitmapFromDiskCache();
        }
        if (resultWrapper == null) {
            resultWrapper = loadBitmapFromHandler();
        }
        if (resultWrapper != null) {
            if (resultWrapper.bitmap != null) {
                mResult = resultWrapper.bitmap;
            } else if (resultWrapper.ins != null) {
                mResult = decodeStream(resultWrapper.ins);
                try {
                    resultWrapper.ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (mResult != null) {
                    mResult = transformResult(mResult);
                }
            }
        } else {
            mDispatcher.dispatchLoadFailed(BitmapLoader.this);
        }
        if (mResult != null) {
            mDispatcher.dispatchLoadComplete(BitmapLoader.this);
        } else {
            mDispatcher.dispatchLoadFailed(BitmapLoader.this);
        }
    }

    public void setFuture(Future future) {
        this.mFuture = future;
    }

    public boolean cancle() {
        if (mFuture != null) {
            return mFuture.cancel(false);
        }
        return false;
    }

    private ResultWrapper loadBitmapFromHandler() {
        ResultWrapper resultWrapper = null;
        if (mRequestHandler != null) {
            resultWrapper = mRequestHandler.load(mRequest);
            if (resultWrapper != null && resultWrapper.ins != null) {
                if (mImageLoader.config.enableDiskCache && resultWrapper.needCache) {
                    resultWrapper.ins.setWriteToDisk(true, mDiskCache);
                }
            }
        }
        return resultWrapper;
    }

    private ResultWrapper loadBitmapFromDiskCache() {
        InputStreamWrapper inputStream = null;
        if (mDiskCache != null) {
            inputStream = mDiskCache.get();
        }
        if (inputStream != null) {
            ResultWrapper resultWrapper = new ResultWrapper();
            resultWrapper.ins = inputStream;
            return resultWrapper;
        }
        return null;
    }

    public Bitmap getResult() {
        return mResult;
    }

    public String getKey() {
        return mRequest.getKey();
    }

    private Bitmap decodeStream(InputStreamWrapper inputStream) {
        if (inputStream != null) {
            inputStream.mark(65535);
            BitmapFactory.Options options = BitmapDecodeHelper.createOptionsByRequest(mRequest);
            if (options.inJustDecodeBounds) {
                BitmapFactory.decodeStream(inputStream, null, options);
                options.inJustDecodeBounds = false;
                BitmapDecodeHelper.calculateInSampleSize(mRequest.getTargetWidth(), mRequest.getTargetHeight(), mRequest, options);
            }
            inputStream.reset();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            return bitmap;
        }
        return null;
    }

    private Bitmap transformResult(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }

        Matrix matrix = new Matrix();

        if (mRequest.getRotationDegrees() != 0) {
            matrix.setRotate(mRequest.getRotationDegrees());
        }

        int bmpWidth = bitmap.getWidth();
        int bmpHeight = bitmap.getHeight();
        int targetWidth = mRequest.getTargetWidth();
        int targetHeight = mRequest.getTargetHeight();
        int drawWidth = bmpWidth;
        int drawHeight = bmpHeight;
        int drawX = 0;
        int drawY = 0;
        if (mRequest.isCenterCrop()) {
            float radiox = targetWidth / (float) bmpWidth;
            float radioy = targetHeight / (float) bmpHeight;
            if (radiox > radioy) {
                drawHeight = (int) (targetHeight * (1 / radiox));
                drawY = (bmpHeight - drawHeight) / 2;
                matrix.preScale(radiox, radiox);
            } else {
                drawWidth = (int) (targetWidth * (1 / radioy));
                drawX = (bmpWidth - drawWidth) / 2;
                matrix.preScale(radioy, radioy);
            }
        } else if (mRequest.isCenterInside()) {
            float radiox = targetWidth / (float) bmpWidth;
            float radioy = targetHeight / (float) bmpHeight;
            if (radiox > radioy) {
                matrix.preScale(radioy, radioy);
            } else {
                matrix.preScale(radiox, radiox);
            }
        } else if ((targetWidth != 0 || targetHeight != 0) && (targetHeight != bmpHeight || targetWidth != bmpWidth)) {
            float sx = targetWidth != 0 ? targetWidth / (float)bmpWidth : targetHeight / (float)bmpHeight;
            float sy = targetHeight != 0 ? targetHeight / (float)bmpHeight : targetWidth / (float)bmpWidth;
            matrix.preScale(sx, sy);
        }

        Bitmap newBitmap = Bitmap.createBitmap(bitmap, drawX, drawY, drawWidth, drawHeight, matrix, true);
        if (newBitmap != bitmap) {
            bitmap.recycle();
            bitmap = newBitmap;
        }
        return bitmap;
    }

    public void addAction(Action action) {
        mActionList.add(action);
    }

    public void removeAction(Action action) {
        if (action != null) {
            mActionList.remove(action);
        }
    }

    public boolean isEmptyActionList() {
        return mActionList == null || mActionList.isEmpty();
    }

    public List<Action> getActions() {
        return mActionList;
    }
}
