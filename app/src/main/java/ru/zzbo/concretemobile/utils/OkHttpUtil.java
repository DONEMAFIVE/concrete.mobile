package ru.zzbo.concretemobile.utils;

import static ru.zzbo.concretemobile.utils.Constants.configList;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * plc_data?retrieval=total
 * recepie?cmd=get
 * orgs?cmd=get
 * trans?cmd=get
 * config?cmd=get
 * disp?cmd=get
 * "report?dateBegin=" + startDate + "&dateEnd=" + endDate
 * "order?cmd=get&dateBegin=" + startDate + "&dateEnd=" + endDate + "&filter=" + state
 */
public class OkHttpUtil {

    private static final OkHttpClient httpClient = new OkHttpClient();

    public static String sendGet(String query) throws Exception {
        String url = "http://" + configList.getScadaIP() + ":5050/" + query;
        Request request = new Request.Builder().url(url).build();
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.code() == 400) return null;
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }

    public static String getRecipes() {
        try {
            return sendGet("recepie?cmd=get");
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String getOrganization() {
        try {
            return sendGet("orgs?cmd=get");
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String newOrganization(String json) {
        try {
            return sendGet("orgs?cmd=create&org=" + json);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String updOrganization(String json) {
        try {
            return sendGet("orgs?cmd=update&org=" + json);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String delOrganization(int id) {
        try {
            return sendGet("orgs?cmd=delete&orgID=" + id);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String getTransporters() {
        try {
            return sendGet("trans?cmd=get");
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String newTransporters(String json) {
        try {
            return sendGet("trans?cmd=create&trans=" + json);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String updTransporters(String json) {
        try {
            return sendGet("trans?cmd=update&trans=" + json);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String delTransporters(int id) {
        try {
            return sendGet("trans?cmd=delete&transID=" + id);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String getPlcData() {
        try {
            return sendGet("plc_data?retrieval=total");
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String getMixes(String startDate, String endDate) {
        try {
            String res = sendGet("report?dateBegin=" + startDate + "&dateEnd=" + endDate);
            if (res.equals(null)) return "[{}]";
            else return res;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static void newRecipe(String json) {
        try {
            sendGet("recepie?cmd=create&recepie=" + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void uplRecipe(int id) {
        try {
            sendGet("recepie?cmd=upload&id=" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delRecipe(int id) {
        try {
            sendGet("recepie?cmd=delete&id=" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updRecipe(String json) {
        try {
            sendGet("recepie?cmd=update&recepie=" + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getOrders(String startDate, String endDate, boolean state) {
        try {
            return sendGet("order?cmd=get&dateBegin=" + startDate + "&dateEnd=" + endDate + "&filter=" + state);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static void newOrder(String json) {
        try {
            sendGet("order?cmd=create&order=" + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void uplOrder(int id, float capacity) {
        try {
            sendGet("order?cmd=upload&partyCapacity=" + capacity + "&orderID=" + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updOrder(String json) {
        try {
            sendGet("order?cmd=update&order=" + json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String delOrder(int id) {
        try {
            return sendGet("order?cmd=delete&orderID=" + id);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static void rptOrder(int id, float capacity) {
        try {
            sendGet("order?cmd=repeat&orderID=" + id + "&partyCapacity=" + capacity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPCConfig() {
        try {
            return sendGet("config?cmd=get");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDispatcherStates() {
        try {
            return sendGet("disp?cmd=get");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void updOrgDispatcherStates(String code) {
        try {
            sendGet("disp?cmd=updateOrg&org=" + code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updTransDispatcherStates(String code) {
        try {
            sendGet("disp?cmd=updateTrans&trans=" + code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getCurrent() {
        try {
            return sendGet("current?cmd=get");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String updCurrent(int recipeId, int orderId) {
        try {
            return sendGet("current?cmd=updateRO&recepieID="+recipeId+"&orderID="+orderId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String updStateFactory(boolean stateFactory) {
        try {
            String state = "idle";
            if (stateFactory) state = "work";
            return sendGet("current?cmd=update_state&state=" + state);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
