package ru.zzbo.concretemobile.utils.ftp;


import ru.zzbo.concretemobile.utils.ftp.exceptions.EZFtpNoInitException;
import ru.zzbo.concretemobile.utils.ftp.user.EZFtpUser;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.Authority;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * FTP server implement
 */
public class EZFtpServerImpl implements IEZFtpServer {

    private static final String TAG = "EZFtpServerImpl";

    private FtpServer ftpServer;
    private final Object lock = new Object();
    private boolean isInit = false;

    /**
     * create a ftp server
     *
     * @param users support user list(need login)
     * @param port  ftp server listen port
     */
    EZFtpServerImpl(List<EZFtpUser> users, int port) {
        //параметры конфигурации
        FtpServerFactory serverFactory = new FtpServerFactory();
        //Установите имя пользователя и пароль для доступа и общий путь
        for (EZFtpUser user : users) {
            BaseUser baseUser = new BaseUser();
            baseUser.setName(user.getName());
            baseUser.setPassword(user.getPassword());
            baseUser.setHomeDirectory(user.getSharedPath());
            List<Authority> authorities = new ArrayList<>();
            authorities.add(user.getPermission().getAuthority());
            baseUser.setAuthorities(authorities);
            try {
                serverFactory.getUserManager().save(baseUser);
            } catch (FtpException e) {
                e.printStackTrace();
            }
        }
        //Настройка порта прослушивания
        ListenerFactory factory = new ListenerFactory();
        factory.setPort(port);
        serverFactory.addListener("default", factory.createListener());
        //Создайте экземпляр службы FTP
        ftpServer = serverFactory.createServer();
        isInit = true;
    }

    /**
     * make sure server is init
     */
    private void checkInit() {
        synchronized (lock) {
            if (!isInit) {
                throw new EZFtpNoInitException("Сервер Ftp не запущен или был остановлен!");
            }
        }
    }

    /**
     * release ftp server
     */
    private void release() {
        synchronized (lock) {
            if (ftpServer != null && !ftpServer.isStopped()) {
                ftpServer.stop();
            }
            isInit = false;
        }
    }

    /**
     * start ftp server
     */
    @Override
    public void start() {
        checkInit();
        try {
            ftpServer.start();
        } catch (FtpException e) {
            e.printStackTrace();
        }
    }

    /**
     * stop ftp server
     */
    @Override
    public void stop() {
        checkInit();
        ftpServer.stop();
    }

    /**
     * whether is stopped the ftp server
     * @return true is stopped,false is not.
     */
    @Override
    public boolean isStopped() {
        return ftpServer.isStopped();
    }
}
