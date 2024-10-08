package ru.zzbo.concretemobile.utils.ftp.callback;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This callback support calculate speed base {@link OnEZFtpDataTransferCallback}
 */
public abstract class EZFtpTransferSpeedCallback implements OnEZFtpDataTransferCallback {

    private static final long CALC_TIME = 1000;

    private long startTime, endTime;
    private long totalSize, tempTotalSize;
    private ScheduledExecutorService executors = Executors.newSingleThreadScheduledExecutor();
    private boolean isFinish = false;

    private Runnable calcSpeedTask = new Runnable() {
        @Override
        public void run() {
            //Вычислите, сколько байт было передано за одну секунду, и переведите в КБ/с
            final long totalSize1 = totalSize;
            final long transferredSize = totalSize1 - tempTotalSize;
            final double speed = transferredSize / 1024.0 / 1000;
            double averageSpeed = 0.00d;
            if (isFinish) {
                //Расчет средней скорости передачи данных
                final long transferredTime = endTime - startTime;
                averageSpeed = totalSize1 / 1024.0 / transferredTime / 1000;
            }

            onTransferSpeed(isFinish, startTime, endTime, speed, averageSpeed);

            tempTotalSize = totalSize1;
        }
    };

    @Override
    public void onStateChanged(int state) {
        switch (state) {
            case START:
                startTime = System.currentTimeMillis();
                executors.scheduleWithFixedDelay(
                        calcSpeedTask,
                        CALC_TIME,
                        CALC_TIME,
                        TimeUnit.MILLISECONDS);
                break;
            case ERROR:
            case COMPLETED:
            case ABORTED:
                isFinish = true;
                endTime = System.currentTimeMillis();
                executors.shutdown();
                break;
            default:
                break;
        }
    }

    @Override
    public void onTransferred(long fileSize, int transferredSize) {
        totalSize += transferredSize;
    }

    @Override
    public void onErr(int code, String msg) {

    }

    /**
     *
     * callback transfer data speed
     *
     * @param isFinished   whether is finished the  task
     * @param startTime the task start time
     * @param endTime the task end time
     * @param speed KB/S
     * @param averageSpeed KB/S
     */
    public abstract void onTransferSpeed(boolean isFinished, long startTime, long endTime, double speed, double averageSpeed);

}
