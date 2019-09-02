package com.shen.runtime.utils;

import android.os.Bundle;

/*
 * created by shen at 2019/9/2 14:49
 */
public class BundleUtils {
    public static <T> T get(Bundle bundle, String key) {
        return (T) bundle.get(key);
    }

    public static <T> T get(Bundle bundle, String key, Object defaultValue) {
        Object object = bundle.get(key);
        if(object == null){
            object = defaultValue;
        }
        return (T) object;
    }
}
