package com.ding.learn.imageloader;

import com.ding.learn.imageloader.diskcache.DiskCacheHelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jindingwei on 2019/7/15.
 */

public class InputStreamWrapper extends InputStream {
    private InputStream ins;
    private boolean writeToDisk;
    private DiskCacheHelper mCacheHelper;
    private int hasSaveOffset;
    private int offset;
    private int markPos;

    public InputStreamWrapper(InputStream in) {
        ins = new BufferedInputStream(in);
    }

    public void setWriteToDisk(boolean writeToDisk, DiskCacheHelper diskCacheHelper) {
        this.writeToDisk = writeToDisk;
        this.mCacheHelper = diskCacheHelper;
    }

    public void mark(int readLimit) {
        ins.mark(readLimit);
        markPos = offset;
    }

    public void reset() {
        try {
            ins.reset();
            offset = markPos;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int read() throws IOException {
        int result = ins.read();
        offset++;
        if (writeToDisk) {
            if (hasSaveOffset < offset) {
                hasSaveOffset++;
                mCacheHelper.write(result);
            }
        }
        return result;
    }

    @Override
    public void close() throws IOException {
        if (mCacheHelper != null) {
            mCacheHelper.close();
        }
        super.close();
    }

    @Override
    public boolean markSupported() {
        return true;
    }
}

