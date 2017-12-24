package com.baogetv.app.net;

/**
 * Created by chalilayang on 2017/12/18.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.TextUtils;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.util.Locale;

public class NetWorkUtil {
    public static final int NETWORK_TYPE_UNKNOWN = -1;
    public static final int NETWORK_TYPE_UNAVAILABLE = 0;
    public static final int NETWORK_TYPE_WIFI = 1;
    public static final int NETWORK_MOBILE = 2;
    public static final int NETWORK_TYPE_2G = 3;
    public static final int NETWORK_TYPE_3G = 4;
    public static final int NETWORK_TYPE_4G = 5;
    private static final String STRING_CMWAP = "cmwap";
    private static final String STRING_CTWAP = "ctwap";
    private static final String STRING_UNIWAP = "uniwap";
    private static final String STRING_3GWAP = "3gwap";
    public static final String CP_NONE = "CP_NONE";
    public static final String CP_WIFI = "NET_WIFI";
    public static final String CP_CHINA_MOBILE = "CHINA_MOBILE";
    public static final String CP_CHINA_UNICOM = "CHINA_UNICOM";
    public static final String CP_CHINA_TELCOM = "CHINA_TELCOM";
    public static final String STRING_G3 = "3G";
    public static final String STRING_G2 = "2G";
    public static final String STRING_G4 = "4G";
    public static final String STRING_UNKNOWN = "Unknown";
    public static final String String_UNAVAILABLE = "unavailable";
    public static final String STRING_WIFI = "WiFi";
    public static final String STRING_MOBILE = "Mobile";
    public static final int OPERATOR_NONE = 0;
    public static final int OPERATOR_CHINA_MOBILE = 1;
    public static final int OPERATOR_CHINA_UNICOM = 2;
    public static final int OPERATOR_CHINA_TELECOM = 3;
    private static int networkType = -1;
    private static final String DEFAULT_MAC_ADDRESS = "02:00:00:00:00:00";

    public NetWorkUtil() {
    }

    public static final void initNetworkType(Context context) {
        NetworkInfo info = null;
        try {
            ConnectivityManager mConnectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            info = mConnectivity.getActiveNetworkInfo();
        } catch (Exception var3) {
            LogUtils.e(var3);
        }
        if (info != null && info.isAvailable()) {
            if (info.getTypeName().toLowerCase(Locale.ENGLISH).equals("wifi")) {
                networkType = 1;
            } else if (info.getTypeName().toLowerCase(Locale.ENGLISH).equals("mobile")) {
                networkType = getMobileType(context);
            }
        } else {
            networkType = 0;
        }
    }

    public static final int getNetworkType(Context context) {
        if (networkType == -1) {
            initNetworkType(context);
        }

        return networkType;
    }

    private static final int getMobileType(Context context) {
        int type;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case 0:
                type = 2;
                break;
            case 1:
                type = 3;
                break;
            case 2:
                type = 3;
                break;
            case 3:
                type = 4;
                break;
            case 4:
                type = 3;
                break;
            case 5:
                type = 4;
                break;
            case 6:
                type = 4;
                break;
            case 7:
                type = 4;
                break;
            case 8:
                type = 4;
                break;
            case 9:
                type = 4;
                break;
            case 10:
                type = 4;
                break;
            default:
                type = 2;
        }

        int sdkVersion = Integer.valueOf(VERSION.SDK).intValue();

        try {
            Class<?> tempClass = Class.forName("android.telephony.TelephonyManager");
            Field fieldHSPAP;
            if (sdkVersion >= 11) {
                fieldHSPAP = tempClass.getField("NETWORK_TYPE_LTE");
                if (fieldHSPAP != null && fieldHSPAP.get((Object) null) != null && fieldHSPAP.get((Object) null).toString().equals(String.valueOf(telephonyManager.getNetworkType()))) {
                    type = 5;
                }

                Field fieldEHRPD = tempClass.getField("NETWORK_TYPE_EHRPD");
                if (fieldEHRPD != null && fieldEHRPD.get((Object) null) != null && fieldEHRPD.get((Object) null).toString().equals(String.valueOf(telephonyManager.getNetworkType()))) {
                    type = 4;
                }
            }

            if (sdkVersion >= 9) {
                fieldHSPAP = tempClass.getField("NETWORK_TYPE_EVDO_B");
                if (fieldHSPAP != null && fieldHSPAP.get((Object) null) != null && fieldHSPAP.get((Object) null).toString().equals(String.valueOf(telephonyManager.getNetworkType()))) {
                    type = 4;
                }
            }

            if (sdkVersion >= 13) {
                fieldHSPAP = tempClass.getField("NETWORK_TYPE_HSPAP");
                if (fieldHSPAP != null && fieldHSPAP.get((Object) null) != null && fieldHSPAP.get((Object) null).toString().equals(String.valueOf(telephonyManager.getNetworkType()))) {
                    type = 4;
                }
            }
        } catch (Exception var7) {
            LogUtils.e(var7);
        }

        return type;
    }

    public static String getNetworkStringByType(int networkType) {
        String networkString = "Unknown";
        switch (networkType) {
            case 0:
                networkString = "unavailable";
                break;
            case 1:
                networkString = "WiFi";
                break;
            case 2:
                networkString = "Mobile";
                break;
            case 3:
                networkString = "2G";
                break;
            case 4:
                networkString = "3G";
                break;
            case 5:
                networkString = "4G";
                break;
            default:
                networkString = "Unknown";
        }

        return networkString;
    }

    public static final boolean is2G(Context context) {
        boolean ret = false;
        if (isMobileConnected(context)) {
            ret = getMobileType(context) == 3;
        }
        return ret;
    }

    public static final boolean is3G(Context context) {
        boolean ret = false;
        if (isMobileConnected(context)) {
            ret = getMobileType(context) == 4;
        }
        return ret;
    }

    public static final boolean is4G(Context context) {
        boolean ret = false;
        if (isMobileConnected(context)) {
            ret = getMobileType(context) == 5;
        }
        return ret;
    }

    public static final boolean isMobile(int networkType) {
        return networkType == 2 || networkType == 3 || networkType == 4 || networkType == 5 || networkType == -1;
    }

    public static final boolean isWifi(int networkType) {
        return networkType == 1;
    }

    public static final boolean isMobile(Context context) {
        return isMobile(getNetworkType(context));
    }

    public static final boolean isWifi(Context context) {
        return isWifi(getNetworkType(context));
    }

    public static final boolean isWifiConnected(Context context) {
        try {
            ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conn.getNetworkInfo(1);
            boolean ret = networkInfo != null && networkInfo.isConnected();
            return ret;
        } catch (Exception var4) {
            return false;
        }
    }

    public static final boolean isMobileConnected(Context context) {
        try {
            ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conn.getNetworkInfo(0);
            boolean ret = networkInfo != null && networkInfo.isConnected();
            return ret;
        } catch (Exception var4) {
            return false;
        }
    }

    public static final boolean isUnavailable(int networkType) {
        return networkType == 0;
    }

    public static final boolean isWap(Context context) {
        if (context == null) {
            return false;
        } else if (!isMobileConnected(context)) {
            return false;
        } else {
            ConnectivityManager mag = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mag != null) {
                NetworkInfo mobInfo = mag.getNetworkInfo(0);
                if (mobInfo != null) {
                    String extrainfo = mobInfo.getExtraInfo();
                    if (!TextUtils.isEmpty(extrainfo)) {
                        extrainfo = extrainfo.toLowerCase(Locale.ENGLISH);
                        if (extrainfo.equals("cmwap") || extrainfo.equals("3gwap") || extrainfo.equals("uniwap") || extrainfo.equals("ctwap")) {
                            return true;
                        }
                    }
                }
            }

            return false;
        }
    }

    public static final String getHostByWap(Context context) {
        String result = "";
        if (!isMobileConnected(context)) {
            return result;
        } else {
            ConnectivityManager mag = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mag != null) {
                NetworkInfo mobInfo = mag.getNetworkInfo(0);
                if (mobInfo != null) {
                    String extrainfo = mobInfo.getExtraInfo();
                    if (!TextUtils.isEmpty(extrainfo)) {
                        extrainfo = extrainfo.toLowerCase(Locale.ENGLISH);
                        if (!extrainfo.equals("cmwap") && !extrainfo.equals("3gwap") && !extrainfo.equals("uniwap")) {
                            if (extrainfo.toLowerCase(Locale.ENGLISH).contains("ctwap")) {
                                result = "10.0.0.200";
                            }
                        } else {
                            result = "10.0.0.172";
                        }
                    }
                }
            }

            return result;
        }
    }

    public static final boolean isOnline(Context context) {
        if (context == null) {
            return false;
        } else {
            NetworkInfo networkInfo = null;

            try {
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                networkInfo = connMgr.getActiveNetworkInfo();
            } catch (Exception var3) {
                LogUtils.e(var3);
            }

            return networkInfo != null && networkInfo.isConnected();
        }
    }

    public static String getNetTypeForUpload(Context context) {
        String netType = "WiFi";
        if (context != null) {
            int netState = getMobileType(context);
            if (netState != 1 && netState != -1) {
                String postfix = "";
                if (netState == 3) {
                    postfix = "2G";
                } else if (netState == 4) {
                    postfix = "3G";
                }

                try {
                    TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    if (telManager != null) {
                        String imsi = telManager.getSubscriberId();
                        if (imsi != null) {
                            StringBuilder builder = new StringBuilder();
                            if (getMobileOperator(context) != 0) {
                                netType = builder.append(imsi.substring(0, 3)).append("_").append(imsi.substring(3, 5)).append("_").append(postfix).toString();
                            }
                        }
                    }
                } catch (Exception var7) {
                    LogUtils.e(var7);
                }
            } else {
                netType = "WiFi";
            }
        }

        return netType;
    }

    public static int getMobileOperator(Context context) {
        if (context == null) {
            throw new IllegalArgumentException();
        } else {
            try {
                TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (telManager != null) {
                    String imsi = telManager.getSubscriberId();
                    if (imsi != null) {
                        if (!imsi.startsWith("46000") && !imsi.startsWith("46002")) {
                            if (!imsi.startsWith("46001") && !imsi.startsWith("46006")) {
                                if (!imsi.startsWith("46003") && !imsi.startsWith("46005")) {
                                    return 0;
                                }

                                return 3;
                            }

                            return 2;
                        }

                        return 1;
                    }
                }
            } catch (Exception var3) {
                LogUtils.e(var3);
            }

            return 0;
        }
    }

    public static String getNetProvider(Context context) {
        if (context == null) {
            return "CP_NONE";
        } else if (isWifi(context)) {
            return "NET_WIFI";
        } else {
            try {
                TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (telManager != null) {
                    String imsi = telManager.getSubscriberId();
                    if (imsi != null) {
                        if (imsi.startsWith("46000") || imsi.startsWith("46002")) {
                            return "CHINA_MOBILE";
                        }

                        if (imsi.startsWith("46001")) {
                            return "CHINA_UNICOM";
                        }

                        if (imsi.startsWith("46003")) {
                            return "CHINA_TELCOM";
                        }
                    }
                }
            } catch (Exception var3) {
                LogUtils.e(var3);
            }

            return "CP_NONE";
        }
    }

    public static Proxy detectProxy(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = null;

        try {
            ni = cm.getActiveNetworkInfo();
        } catch (Exception var6) {
            LogUtils.e(var6);
        }

        if (ni != null && ni.isAvailable() && ni.getType() == 0) {
            String proxyHost = android.net.Proxy.getDefaultHost();
            int port = android.net.Proxy.getDefaultPort();
            if (proxyHost != null) {
                InetSocketAddress sa = new InetSocketAddress(proxyHost, port);
                return new Proxy(Type.HTTP, sa);
            }
        }

        return null;
    }
}
