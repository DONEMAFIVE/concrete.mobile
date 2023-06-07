package ru.zzbo.concretemobile.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.MainThread;

public class MultiClickFilterUtil {
    private static final int DEFAULT_LOCK_TIME_MS = 500;
    private static final Handler uiHandler = new Handler(Looper.getMainLooper());

    private static boolean locked = false;

    @MainThread
    public static boolean isClickLocked(int lockTimeMs) {
        if (locked) return true;
        locked = true;
        uiHandler.postDelayed(() -> locked = false, lockTimeMs);
        return false;
    }

    @MainThread
    public static boolean isClickLocked() {
        return isClickLocked(DEFAULT_LOCK_TIME_MS);
    }
}
