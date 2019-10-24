package project.shen.dessert_life.task.Utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class Utils {
    private static String curProcessName = null;

    public static boolean isMainProcess(Context context) {
        String processName = getCurrentProcessName(context);
        if (processName != null && processName.contains(":")) {
            return false;
        }

        return (processName != null && processName.equals(context.getPackageName()));
    }

    @Nullable
    private static String getCurrentProcessName(Context context) {
        String processName = curProcessName;
        if (!TextUtils.isEmpty(processName)) {
            return processName;
        }

        try {
            int pid = Process.myPid();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

            for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    curProcessName = appProcess.processName;
                    return curProcessName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        curProcessName = getCurrentProcessNameFromPrc();
        return curProcessName;
    }

    private static String getCurrentProcessNameFromPrc() {
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(
                    "/proc/" + Process.myPid() + "/cmdline"
            ), "iso-8859-1"));

            int c;
            StringBuilder processName = new StringBuilder();
            while ((c = reader.read()) > 0) {
                processName.append(c);
            }
            return processName.toString();
        } catch (Throwable e) {
            //ignore
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Throwable e) {
                    //ignore
                }
            }
        }

        return null;
    }
}
