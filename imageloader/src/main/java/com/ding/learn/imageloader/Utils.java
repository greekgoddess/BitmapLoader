package com.ding.learn.imageloader;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jindingwei on 2019/7/15.
 */

public class Utils {

    public static String createKey(Request request) {
        StringBuilder builder = new StringBuilder();
        builder.append(hashKeyForDisk(request.getStaticKey()));
        builder.append(request.getTargetWidth() + "*" + request.getTargetHeight());
        builder.append("centerCrop" + request.isCenterCrop());
        builder.append("centerInside" + request.isCenterInside());
        return builder.toString();
    }

    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(key.getBytes());
            cacheKey = bytesToHexString(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
