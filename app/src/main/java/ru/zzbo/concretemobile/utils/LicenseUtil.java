package ru.zzbo.concretemobile.utils;

import static ru.zzbo.concretemobile.utils.Constants.configList;
import static ru.zzbo.concretemobile.utils.Constants.plcMac;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import ru.zzbo.concretemobile.db.DBConstants;
import ru.zzbo.concretemobile.db.DBUtilGet;
import ru.zzbo.concretemobile.db.builders.ConfigBuilder;
import ru.zzbo.concretemobile.models.DroidConfig;
import ru.zzbo.concretemobile.models.ScadaConfig;
import ru.zzbo.concretemobile.protocol.profinet.com.sourceforge.snap7.moka7.S7;
import ru.zzbo.concretemobile.protocol.profinet.commands.CommandDispatcher;
import ru.zzbo.concretemobile.protocol.profinet.models.Tag;

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

    public static boolean chkLicense(Context context) {
        try {
            configList = new ConfigBuilder().buildScadaParameters(new DBUtilGet(context).getFromParameterTable(DBConstants.TABLE_NAME_CONFIG));
            String key = new CryptoUtil(configList.getHardKey()).decrypt().trim();
            return key.equals(plcMac.getStringValueIf().substring(0,17));
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean chkPCLicense(Context context) {
        try {
            String req = OkHttpUtil.getPCConfig();
            ScadaConfig cfgPC = new Gson().fromJson(req, ScadaConfig.class);
            DroidConfig cfgDroid = new ConfigBuilder().buildScadaParameters(new DBUtilGet(context).getFromParameterTable(DBConstants.TABLE_NAME_CONFIG));

            String droidKey = cfgDroid.getHardKey();
            String pcKey = cfgPC.getHardKey();

            droidKey = new CryptoUtil(droidKey).decrypt().trim();
            pcKey = new CryptoUtil(pcKey).decrypt().trim();

            return pcKey.equals(droidKey);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
    }
}
