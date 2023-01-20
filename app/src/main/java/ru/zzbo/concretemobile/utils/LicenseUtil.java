package ru.zzbo.concretemobile.utils;

import static ru.zzbo.concretemobile.utils.Constants.configList;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import ru.zzbo.concretemobile.db.DBConstants;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.builders.ConfigBuilder;

public class LicenseUtil {

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getMacFromIP(String ipFinding) {
        if (ConnectionUtil.isIpConnected(ipFinding)) {
            Runtime runtime = Runtime.getRuntime();
            BufferedReader bufferedReader = null;
            try {
                Process ipProcess = runtime.exec("ip neigh show");
                bufferedReader = new BufferedReader(new InputStreamReader(ipProcess.getInputStream()));
                String ip[];
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(ipFinding)) {
                        ip = line.split(" ");
                        return ip[4].replaceAll(":", "-").trim();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return "00-00-00-00-00-00";
    }

    public static String getMacDevice() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }

    public static boolean chkLicense(Context context) {
        //TODO:Получить hardkey из БД
        configList = new ConfigBuilder().buildScadaParameters(new DBUtilGet(context).getFromParameterTable(DBConstants.TABLE_NAME_CONFIG));
        //TODO:Расшифровать hardkey из БД
        String key = "null";

        try {
            key = new CryptoUtil(configList.getHardKey()).decrypt().trim();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        //TODO:Получить mac устройства
        String mac = getMacFromIP(configList.getPlcIP());
        Log.e("LICENCE", key + " = " + mac);
        //TODO:Сравнить hardkey = mac
        return mac.equals(key);
    }
}
