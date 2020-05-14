package com.mydesignerclothing.mobile.android.commons.network;

import android.util.Log;

import com.mydesignerclothing.mobile.android.commons.logger.DMLog;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;


public class IPUtil {

  private static final String TAG = IPUtil.class.getSimpleName();
  private static final String EMPTY_STRING = "";

  public static boolean isValidIP4Address(String address) {
    try {
      InetAddress inetAddress = InetAddress.getByName(address);
      return inetAddress instanceof Inet4Address;
    } catch (Exception e) {
      DMLog.log(TAG, e, Log.DEBUG);
      return false;
    }
  }

  public static String getHostFromIp(String ip){
    try {
      URL url = new URL(ip);
      return url.getHost();
    } catch (MalformedURLException e) {
      DMLog.log(TAG, e, Log.ERROR);
      return EMPTY_STRING;
    }
  }

}
