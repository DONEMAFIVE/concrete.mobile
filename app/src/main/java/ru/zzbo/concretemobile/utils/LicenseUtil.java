package ru.zzbo.concretemobile.utils;

import static ru.zzbo.concretemobile.db.DBConstants.TABLE_NAME_CONFIG;
import static ru.zzbo.concretemobile.utils.Constants.configList;
import static ru.zzbo.concretemobile.utils.Constants.plcMac;

import android.content.Context;

import com.google.gson.Gson;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.helpers.ConfigBuilder;
import ru.zzbo.concretemobile.models.DroidConfig;
import ru.zzbo.concretemobile.models.ScadaConfig;

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

    public static boolean chkLicense(Context context, int device) {
        switch (device) {
            case 0: { //PLC
                try {
                    String key = new CryptoUtil(configList.getHardKey()).decrypt().trim();
                    return key.equals(plcMac.getStringValueIf().substring(0, 17));
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            case 1: { //PC
                try {
                    String req = OkHttpUtil.getPCConfig();
                    ScadaConfig cfgPC = new Gson().fromJson(req, ScadaConfig.class);

                    String droidKey = new CryptoUtil(configList.getHardKey()).decrypt().trim();
                    String pcKey = new CryptoUtil(cfgPC.getHardKey()).decrypt().trim();

//                    Toast.makeText(context, "droid: " + droidKey, Toast.LENGTH_LONG).show();
//                    Toast.makeText(context, "pc: " + pcKey,  Toast.LENGTH_LONG).show();

                    return pcKey.equals(droidKey);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            default: return false;

        }
    }
}
