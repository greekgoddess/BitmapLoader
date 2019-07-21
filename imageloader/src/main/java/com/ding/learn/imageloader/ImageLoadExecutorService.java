package com.ding.learn.imageloader;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jindingwei on 2019/7/15.
 */

public class ImageLoadExecutorService extends ThreadPoolExecutor {
    private static final int THREAD_COUNT = 3;

    public ImageLoadExecutorService() {
        super(THREAD_COUNT, THREAD_COUNT, 0,
                TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
    }

    @Override
    public void execute(Runnable command) {
        super.execute(command);
    }
}
