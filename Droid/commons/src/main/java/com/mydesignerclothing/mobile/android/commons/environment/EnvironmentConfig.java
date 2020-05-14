package com.mydesignerclothing.mobile.android.commons.environment;

import android.util.Log;


import com.mydesignerclothing.mobile.android.commons.core.collections.CollectionUtilities;
import com.mydesignerclothing.mobile.android.commons.core.collections.Predicate;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.network.IPUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class EnvironmentConfig {
  private final List<ServerConfig> serverConfigs;

  public static final String V4 = "v4";
  public static final String V3 = "v3";
  public static final String V1 = "v1";
  public static final String V2 = "v2";
  public static final String PRODUCTION = "production";
  public static final String PRODUCTION_A = "productiona";
  public static final String PRODUCTION_B = "productionb";
  public static final String PRODUCTION_C = "productionc";
  public static final String RC = "rc";

  private JSONArray environments;
  private String currentEnv;

  private static final String TAG = EnvironmentConfig.class.getSimpleName();
  public static final String ENDPOINTS = "endpoints";
  private static final String HOST_PORT_SEPRATOR = ":";
  private static final String HTTP_PROTOCOL = "http://";
  public static final String AUTOMATION_KEY = "automation";

  private EnvironmentConfig(List<ServerConfig> serverConfigs, JSONArray environments, String currentEnv) {
    this.serverConfigs = serverConfigs;
    this.environments = environments;
    this.currentEnv = currentEnv;
  }

  public List<ServerConfig> getServerConfigs() {
    return serverConfigs;
  }

  public ServerConfig getServerConfig(@ServerType final String server) {
    return CollectionUtilities.filter(new Predicate<ServerConfig>() {
      @Override
      public boolean match(ServerConfig serverConfig) {
        return server.equals(serverConfig.getServer());
      }
    }, serverConfigs).get(0);
  }

  public JSONArray getEnvironments() {
    return environments;
  }

  public String getCurrentEnv() {
    return currentEnv;
  }

  public JSONObject environment(String key) throws JSONException {
    for (int index = 0; index < environments.length(); index++) {
      JSONObject environment = environments.getJSONObject(index);
      String keyToCheck = environment.keys().next();
      if (keyToCheck.equalsIgnoreCase(key)) {
        return environment.getJSONObject(keyToCheck);
      }
    }
    return null;
  }

  public void updateCurrentEnv(String currentEnv) {
    DMLog.withInfo(String.format("Called currentEnv with Value: %s", currentEnv), 2);
    this.currentEnv = currentEnv;
  }

  public String endpoint(String key) {
    return endpoint(key, getCurrentEnv());
  }

  public String productionEndpoint(String key) {
    return endpoint(key, PRODUCTION);
  }

  private String endpoint(String endpointKey, String environmentKey) {
    try {
      JSONObject environment = environment(environmentKey);
      if (environment != null) {
        return environment.getJSONObject(ENDPOINTS).getString(endpointKey);
      }
    } catch (JSONException e) {
      DMLog.log(TAG, e, Log.ERROR);
    }
    return null;
  }

  public void replaceAutomationIP(String ipaddress) throws JSONException {
    JSONObject endpoints = environment(AUTOMATION_KEY).getJSONObject(ENDPOINTS);
    for (ServerConfig serverConfig : getServerConfigs()) {
      endpoints.put(serverConfig.getServer(), addDefaultPortAndProtocol(serverConfig, ipaddress));
    }
  }

  public void replaceIP(@ServerType String server, String newIpAddress) throws JSONException {
    JSONObject endpoints = environment(getCurrentEnv()).getJSONObject(ENDPOINTS);
    ServerConfig serverConfig = getServerConfig(server);
    String newIpWithProtocolAndPort = addDefaultPortAndProtocol(serverConfig, newIpAddress);
    DMLog.d(TAG, "Updated " + serverConfig.getServer() + " endpoint " + newIpWithProtocolAndPort);
    endpoints.put(serverConfig.getServer(), newIpWithProtocolAndPort);
  }

  private String addDefaultPortAndProtocol(ServerConfig serverConfig, String ipAddress) {
    if (IPUtil.isValidIP4Address(ipAddress)) {
      return HTTP_PROTOCOL + ipAddress + HOST_PORT_SEPRATOR + serverConfig.getPort();
    } else if (!ipAddress.startsWith("http")) {
      return HTTP_PROTOCOL + ipAddress;
    }
    return ipAddress;
  }

  public static class Builder {
    private JSONArray environments;
    private List serverConfigs = new ArrayList();
    private String currentEnv;

    @NonNull
    public Builder withMobileFacadeToPort(@ServerType String server, String port) {
      serverConfigs.add(new ServerConfig(server, port));
      return this;
    }

    public Builder withEnvironments(String environments) {
      try {
        this.environments = new JSONArray(environments);
      } catch (JSONException e) {
        throw new InvalidConfigException(e);
      }
      return this;
    }

    public Builder withCurrentEnv(String currentEnv) {
      this.currentEnv = currentEnv;
      return this;
    }

    public EnvironmentConfig build() {
      if (environments == null) {
        //throw new IllegalStateException("Please make sure all required server configurations are initialized.");
      }
      return new EnvironmentConfig(serverConfigs, environments, currentEnv);
    }
  }

  public static class ServerConfig {
    private String server;
    private String port;

    private ServerConfig(@ServerType String server, String port) {
      this.server = server;
      this.port = port;
    }

    public String getServer() {
      return server;
    }

    public String getPort() {
      return port;
    }
  }
}
