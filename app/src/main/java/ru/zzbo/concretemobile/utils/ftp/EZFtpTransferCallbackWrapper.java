package ru.zzbo.concretemobile.utils.ftp;

import android.os.Handler;
import android.os.Looper;

import ru.zzbo.concretemobile.utils.ftp.callback.OnEZFtpDataTransferCallback;

/**
 * обертка для передачи данных, она изменила поток на поток ui
 */
public class EZFtpTransferCallbackWrapper implements OnEZFtpDataTransferCallback {

    private OnEZFtpDataTransferCallback callback;
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private final Object lock = new Object();

    public EZFtpTransferCallbackWrapper(OnEZFtpDataTransferCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onStateChanged(final int state) {
        synchronized (lock) {
            mainHandler.post(() -> {
                if (callback != null) {
                    callback.onStateChanged(state);
                }
            });
        }
    }

    @Override
    public void onTransferred(final long fileSize, final int transferredSize) {
        synchronized (lock) {
            mainHandler.post(() -> {
                if (callback != null) {
                    callback.onTransferred(fileSize,transferredSize);
                }
            });
        }
    }

    @Override
    public void onErr(final int code, final String msg) {
        synchronized (lock) {
            mainHandler.post(() -> {
                if (callback != null) {
                    callback.onErr(code, msg);
                }
            });
        }
    }
}
