package com.mydesignerclothing.mobile.android.network.models.v2;


import com.mydesignerclothing.mobile.android.commons.api.ProguardJsonMappable;
import com.mydesignerclothing.mobile.android.commons.core.AppInfo;
import com.mydesignerclothing.mobile.android.commons.core.DeviceInfo;
import com.mydesignerclothing.mobile.android.commons.util.MyDesignerClothingUtilConstants;

import static com.mydesignerclothing.mobile.android.commons.util.StringUtils.EMPTY_STRING;

public class RequestInfo implements ProguardJsonMappable {

  private String applicationVersion;
  private String channel;
  private String deviceName;
  private String deviceType;
  private String appId;
  private String transactionId;
  private String responseType;
  private String osName;
  private String osVersion;
  private String build;

  public String getApplicationVersion() {
    return applicationVersion;
  }

  public void setApplicationVersion(String applicationVersion) {
    this.applicationVersion = applicationVersion;
  }


  public String getDeviceName() {
    return deviceName;
  }

  public void setDeviceName(String deviceName) {
    this.deviceName = deviceName;
  }

  public String getDeviceType() {
    return deviceType;
  }

  public void setDeviceType(String deviceType) {
    this.deviceType = deviceType;
  }

  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  public String getResponseType() {
    return responseType;
  }

  public void setResponseType(String responseType) {
    this.responseType = responseType;
  }

  public String getOsName() {
    return osName;
  }

  public void setOsName(String osName) {
    this.osName = osName;
  }

  public String getOsVersion() {
    return osVersion;
  }

  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getBuild() {
    return build;
  }

  public void setBuild(String build) {
    this.build = build;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RequestInfo that = (RequestInfo) o;

    if (applicationVersion != null ? !applicationVersion.equals(that.applicationVersion) : that.applicationVersion != null)
      return false;
    if (channel != null ? !channel.equals(that.channel) : that.channel != null) return false;
    if (deviceName != null ? !deviceName.equals(that.deviceName) : that.deviceName != null)
      return false;
    if (deviceType != null ? !deviceType.equals(that.deviceType) : that.deviceType != null)
      return false;
    if (appId != null ? !appId.equals(that.appId) : that.appId != null) return false;
    if (transactionId != null ? !transactionId.equals(that.transactionId) : that.transactionId != null)
      return false;
    if (responseType != null ? !responseType.equals(that.responseType) : that.responseType != null)
      return false;
    if (osName != null ? !osName.equals(that.osName) : that.osName != null) return false;
    if (osVersion != null ? !osVersion.equals(that.osVersion) : that.osVersion != null)
      return false;
    return build != null ? build.equals(that.build) : that.build == null;

  }

  @Override
  public int hashCode() {
    int result = applicationVersion != null ? applicationVersion.hashCode() : 0;
    result = 31 * result + (channel != null ? channel.hashCode() : 0);
    result = 31 * result + (deviceName != null ? deviceName.hashCode() : 0);
    result = 31 * result + (deviceType != null ? deviceType.hashCode() : 0);
    result = 31 * result + (appId != null ? appId.hashCode() : 0);
    result = 31 * result + (transactionId != null ? transactionId.hashCode() : 0);
    result = 31 * result + (responseType != null ? responseType.hashCode() : 0);
    result = 31 * result + (osName != null ? osName.hashCode() : 0);
    result = 31 * result + (osVersion != null ? osVersion.hashCode() : 0);
    result = 31 * result + (build != null ? build.hashCode() : 0);
    return result;
  }

  public static RequestInfo create(AppInfo appInfo, DeviceInfo deviceInfo){
    return new RequestInfoFactory().requestInfo(appInfo, deviceInfo);
  }

   static class RequestInfoFactory {
      private static final String MOBILE = "mobile";

      public RequestInfo requestInfo(AppInfo appInfo, DeviceInfo deviceInfo) {
        RequestInfo requestInfo = new RequestInfo();

        requestInfo.setApplicationVersion(appInfo.getVersionName());
        requestInfo.setDeviceName(MyDesignerClothingUtilConstants.DEVICE_NAME);
        requestInfo.setOsName(MyDesignerClothingUtilConstants.OS_NAME);
        requestInfo.setOsVersion(deviceInfo.getAndroidOsVersion());
        requestInfo.setResponseType("json");
        requestInfo.setTransactionId(EMPTY_STRING);

        requestInfo.setChannel(MOBILE);
        requestInfo.setAppId(MOBILE);
        return requestInfo;
      }
    }
}
