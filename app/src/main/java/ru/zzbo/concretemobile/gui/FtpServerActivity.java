package ru.zzbo.concretemobile.gui;

import static ru.zzbo.concretemobile.utils.ConnectionUtil.isWifiApOpen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.NetworkUtils;

import java.lang.ref.WeakReference;

import ru.zzbo.concretemobile.R;
import ru.zzbo.concretemobile.utils.ftp.EZFtpServer;
import ru.zzbo.concretemobile.utils.ftp.FtpConfig;
import ru.zzbo.concretemobile.utils.ftp.user.EZFtpUser;
import ru.zzbo.concretemobile.utils.ftp.user.EZFtpUserPermission;

public class FtpServerActivity extends AppCompatActivity {
    private TextView tv_msg, info;
    private EditText userName, userPassword, sharePath, port;
    private Button btn_start_server, btn_stop_server;

    private static final String TAG = "FtpServerActivity";

    private ClickHolder clickHolder = new ClickHolder(this);

    public static final class ClickHolder {
        private WeakReference<FtpServerActivity> activity;
        private EZFtpServer ftpServer;

        public ClickHolder(FtpServerActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        public void startFtpServer(String login, String pass, String sharePath, int port) {
            Log.d(TAG, "startFtpServer: " + Thread.currentThread().getName());
            if (ftpServer == null) {
                ftpServer = new EZFtpServer.Builder()
                        .addUser(new EZFtpUser(login, pass, sharePath, EZFtpUserPermission.WRITE))
                        .setListenPort(port)
                        .create();
                ftpServer.start();
            } else {
                if (ftpServer.isStopped()) ftpServer.start();
            }

            final String serverIp = NetworkUtils.getIPAddress(true) + ":" + port;

            if (activity.get() != null) {
                activity.get().uploadMsg("Имя пользователя=" + login + "\n"
                        + "Пароль=" + pass + "\n"
                        + "Общий путь=" + sharePath + "\n"
                        + "IP сервера=" + serverIp + "\n\n"
                        + "FTP-сервер запущен!" + "\n"
                        + "Доступ к FTP открыт по адресу: ftp://" + serverIp + "\n");
            }
        }

        public void stopFtpServer() {
            if (ftpServer != null) ftpServer.stop();
            if (activity.get() != null) activity.get().uploadMsg("Ftp-сервер остановлен!\n");

        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftp_server);

        tv_msg = findViewById(R.id.tv_msg);
        info = findViewById(R.id.info);
        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        sharePath = findViewById(R.id.sharePath);
        port = findViewById(R.id.port);
        btn_start_server = findViewById(R.id.btn_start_server);
        btn_stop_server = findViewById(R.id.btn_stop_server);

        initData();
    }

    private void initData() {
        tv_msg.setMovementMethod(ScrollingMovementMethod.getInstance());
        new Handler(Looper.getMainLooper()).post(() -> {
            tv_msg.setText("Исторические сообщения:");
            userName.setText(FtpConfig.DEFAULT_USER);
            userPassword.setText(FtpConfig.DEFAULT_PASSWORD);
            port.setText(String.valueOf(FtpConfig.DEFAULT_PORT));
            sharePath.setText(FtpConfig.DEFAULT_SHARE_PATH);
        });

        btn_start_server.setOnClickListener(e -> {
            System.err.println(
                    "Имя пользователя=" + userName.getText().toString() + "\n"
                            + "Пароль=" + userPassword.getText().toString() + "\n"
                            + "Путь=" + sharePath.getText().toString() + "\n"
                            + "Порт=" + port.getText().toString() + "\n");
            clickHolder.startFtpServer(
                    userName.getText().toString(),
                    userPassword.getText().toString(),
                    sharePath.getText().toString(),
                    Integer.parseInt(port.getText().toString())
            );
        });

        btn_stop_server.setOnClickListener(e -> {
            clickHolder.stopFtpServer();
        });

        uploadMsg("Точка доступа открыта? " + isWifiApOpen(this));
    }

    private void uploadMsg(String msg) {
        String old = msg;
        tv_msg.setText(old + "\n");
    }


}
