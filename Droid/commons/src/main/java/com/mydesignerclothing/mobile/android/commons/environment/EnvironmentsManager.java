package com.mydesignerclothing.mobile.android.commons.environment;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.ConnectivityManager;
import android.util.Log;

import com.mydesignerclothing.mobile.android.commons.core.collections.Function;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.network.NetworkStateIdentifier;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig.PRODUCTION;
import static com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig.PRODUCTION_A;
import static com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig.PRODUCTION_B;
import static com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig.PRODUCTION_C;
import static com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig.RC;
import static com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig.V3;
import static com.mydesignerclothing.mobile.android.commons.environment.EnvironmentConfig.V4;

public class EnvironmentsManager {
  public static final String LOGIN = "login";

  private static final String TAG = EnvironmentsManager.class.getSimpleName();
  private static final String INSTANCE_MAPPING_ENDPOINT = "v2_instance_mapping";
  private static final String API_MAPPING_FILE_NAME = "droid_api_preferences.properties";
  private static final String DEFAULT_API_INSTANCE = "/api/mobile";
  private static final String URL_STARTING_FORWARD_SLASH = "/";
  public static final String DEVICE_LOCAL_KEY = "device_local";

  private EnvironmentConfig environmentConfig;
  private JSONArray environments;
  @VisibleForTesting
  public Map<String, String> instanceMapping;


  public EnvironmentsManager(Context context, EnvironmentConfig environmentConfig) {
    init(environmentConfig);

    loadInstanceMapping(context);
  }

  public EnvironmentsManager(EnvironmentConfig environmentConfig) {
    init(environmentConfig);
  }

  public List<String> availableKeys() {
    ArrayList<String> availableKeys = new ArrayList<>();
    for (int index = 0; index < environments.length(); index++) {
      try {
        String key = environments.getJSONObject(index).keys().next();
        availableKeys.add(key);
      } catch (JSONException e) {
        DMLog.log(TAG, e, Log.ERROR);
      }
    }
    return availableKeys;
  }

  public String endpoint(String type) {
    return environmentConfig.endpoint(type);
  }

  public String productionEndpoint(String type) {
    return environmentConfig.productionEndpoint(type);
  }

  public void replaceIP(@ServerType String server, String newIpAddress) throws JSONException {
    environmentConfig.replaceIP(server, newIpAddress);
  }

  public String getV3IP() {
    return endpoint(V3);
  }

  public String getV4IP() {
    return endpoint(V4);
  }

  @VisibleForTesting
  @NonNull
  String sanitizeInstanceMappingKey(@NonNull String key) {
    return key.startsWith(URL_STARTING_FORWARD_SLASH) ? key.substring(1) : key;
  }

  /**
   * Maps the path to the JVM Split end point based on the config downloaded.
   * The <code>droid_api_preferences.properties</code> file that exists in <code>/main/assets/</code> is the default used
   * @param path - the path we are checking to be in the configuration will only rewrite the encoded path when the returned value is non-null.
   * @return Will return the modified path if found in the preferences file. If the path is not found in the preferences or does not start with a forward slash, it will return null.
   * @see okhttp3.HttpUrl.Builder#encodedPath(String) - null or paths that do not start with a forward slash will throw exceptions.
   * Due to this, null is returned here when the mapping does not exist or does not start with a forward slash.
   * @see com.mydesignerclothing.mobile.android.network.LazyCallFactory#newCall will only re-write the encodedPath when the value is non null.
   */
  @Nullable
  public String jvmSplitMappedEndPoint(String path) {
    String keyToCheck = sanitizeInstanceMappingKey(path);
    String mappedEndpoint = instanceMapping.containsKey(keyToCheck) ? instanceMapping.get(keyToCheck) : null;
    return mappedEndpoint != null && mappedEndpoint.startsWith(URL_STARTING_FORWARD_SLASH) ? mappedEndpoint : null;
  }


  public String getCurrentEnvironment() {
    return environmentConfig.getCurrentEnv();
  }

  public void updateCurrentEnvironment(String currentEnv) {
    environmentConfig.updateCurrentEnv(currentEnv);
  }

  public boolean isDevEnvironment() {
    String currentEnvironment = getCurrentEnvironment();
    //assume dev mode as false if env is not available
    if (currentEnvironment == null) return false;

    //assume dev mode if other than production/rc
    return !Arrays.asList(RC, PRODUCTION, PRODUCTION_A, PRODUCTION_B, PRODUCTION_C).contains(currentEnvironment.toLowerCase(Locale.US));
  }

  private Map<String, String> defaultAPIMappings(AssetManager assetManager) {
    Map<String, String> mappings = new HashMap<>();
    Properties properties = new Properties();
    try (InputStream inputStream = assetManager.open(API_MAPPING_FILE_NAME)) {
      properties.load(inputStream);
      for (String key : properties.stringPropertyNames()) {
        String value = (String) properties.get(key);
        mappings.put(key, value);
      }

    } catch (IOException e) {
      DMLog.log(TAG, e, Log.ERROR);
    }
    return mappings;

  }

  private void loadInstanceMapping(Context context) {
    instanceMapping = defaultAPIMappings(context.getAssets());
    String endpoint = this.endpoint(INSTANCE_MAPPING_ENDPOINT);
    endpoint = endpoint + API_MAPPING_FILE_NAME;
    if (NetworkStateIdentifier.isConnectedToInternet((ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE))) {
      Function<Map<String, String>> environmentManagerCallback = futureMap -> {
        if (futureMap != null) {
          instanceMapping.putAll(futureMap);
        }
      };

      new LoadMappingsAsyncTask(endpoint, environmentManagerCallback).load();

    }
  }
  private void init(EnvironmentConfig config) {
    this.environments = config.getEnvironments();
    this.environmentConfig = config;
  }
}

