package project.shen.dessert_life.task.Utils;

import android.util.Log;

public class DebugLog {
    public static boolean isDebug = true;

    public static void logE(String tag, String value) {
        if (!isDebug) {
            return;
        } else {
            Log.e(tag, value);
        }
    }
    public static void logD(String tag, String value) {
        if (!isDebug) {
            return;

        } else {
            Log.d(tag, value);
        }

    }
    public static void logI(String tag, String value) {
        if (!isDebug) {
            return;
        } else {
            Log.i(tag, value);
        }

    }

    public static void logW(String tag, String value) {
        if (!isDebug) {
            return;
        } else {
            Log.w(tag, value);
        }
    }


    public static void logE(String tag, int value) {
        if (!isDebug) {
            return;
        } else {
            Log.e(tag, String.valueOf(value));
        }
    }
    public static void logD(String tag, int value) {
        if (!isDebug) {
            return;

        } else {
            Log.d(tag, String.valueOf(value));
        }

    }
    public static void logI(String tag, int value) {
        if (!isDebug) {
            return;
        } else {
            Log.i(tag, String.valueOf(value));
        }

    }

    public static void logW(String tag, int value) {
        if (!isDebug) {
            return;
        } else {
            Log.w(tag, String.valueOf(value));
        }
    }


    public static void logE(String tag, long value) {
        if (!isDebug) {
            return;
        } else {
            Log.e(tag, String.valueOf(value));
        }
    }
    public static void logD(String tag, long value) {
        if (!isDebug) {
            return;

        } else {
            Log.d(tag, String.valueOf(value));
        }

    }
    public static void logI(String tag, long value) {
        if (!isDebug) {
            return;
        } else {
            Log.i(tag, String.valueOf(value));
        }

    }

    public static void logW(String tag, long value) {
        if (!isDebug) {
            return;
        } else {
            Log.w(tag, String.valueOf(value));
        }
    }
}
