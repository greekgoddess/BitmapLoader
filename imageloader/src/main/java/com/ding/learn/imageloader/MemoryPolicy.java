package com.ding.learn.imageloader;

/**
 * Created by jindingwei on 2019/7/12.
 */

public class MemoryPolicy {
    public static final int JUST_READ_FROM_MEMORY = 101;

    public static final int FORBID_READ_FROM_MEMORY = 102;

    public static boolean canReadFromMemory(int memoryPolicy) {
        return memoryPolicy == FORBID_READ_FROM_MEMORY;
    }
}
