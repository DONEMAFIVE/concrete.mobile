package ru.zzbo.concretemobile.utils.ftp;

import ru.zzbo.concretemobile.utils.ftp.user.EZFtpUser;
import ru.zzbo.concretemobile.utils.ftp.user.EZFtpUserPermission;

import java.util.ArrayList;
import java.util.List;

public class EZFtpServer implements IEZFtpServer {

    private IEZFtpServer ftpServerImpl;

    private EZFtpServer(List<EZFtpUser> users, int port) {
        ftpServerImpl = new EZFtpServerImpl(users, port);
    }

    @Override
    public void start() {
        ftpServerImpl.start();
    }

    @Override
    public void stop() {
        ftpServerImpl.stop();
    }

    @Override
    public boolean isStopped() {
        return ftpServerImpl.isStopped();
    }

    public static final class Builder {
        private List<EZFtpUser> users = new ArrayList<>();
        private int port;

        public Builder addUser(EZFtpUser user) {
            users.add(user);
            return this;
        }

        public Builder setListenPort(int port) {
            this.port = port;
            return this;
        }

        public EZFtpServer create() {
            return new EZFtpServer(users, port);
        }
    }

    public static EZFtpServer init() {
        EZFtpUser ftpUser = new EZFtpUser(FtpConfig.DEFAULT_USER, FtpConfig.DEFAULT_PASSWORD, FtpConfig.DEFAULT_SHARE_PATH, EZFtpUserPermission.WRITE);
        EZFtpServer ftpServer = new EZFtpServer.Builder()
                .addUser(ftpUser)
                .setListenPort(FtpConfig.DEFAULT_PORT)
                .create();
        return ftpServer;
    }
}
