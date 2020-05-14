package com.mydesignerclothing.mobile.android.network.errors;

import android.util.Log;

import com.google.gson.Gson;
import com.mydesignerclothing.mobile.android.commons.core.optional.Optional;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ErrorHandler {

  protected static final String STATUS = "status";
  protected static final String FAIL_ERROR = "FAIL";

  public boolean isMobileFacadeError(String response) {
    HashMap<String, Object> responseAsMap = parseResponse(response);
    Object firstNode = firstNode(responseAsMap);
    if (firstNode instanceof Map) {
      String status = (String) ((Map) firstNode).get(STATUS);
      return FAIL_ERROR.equals(status);
    }
    return false;
  }

  public Optional<JSONObject> transformResponse(String response) {
    Object firstNode = firstNode(parseResponse(response));
    if (firstNode instanceof Map) {
      JSONObject jsonObject = new JSONObject((Map) firstNode);
      return Optional.of(jsonObject);
    }
    return Optional.absent();
  }

  private HashMap<String, Object> parseResponse(String response) {
    HashMap<String, Object> responseAsMap = new HashMap<>();
    try {
      responseAsMap = new Gson().fromJson(response, HashMap.class);
    } catch (Exception e) {
      responseAsMap.put(STATUS, FAIL_ERROR);
      DMLog.log(ErrorHandler.class.getSimpleName(), e, Log.INFO);
    }
    return responseAsMap;
  }

  private Object firstNode(HashMap responseAsMap) {
    if (responseAsMap != null) {
      if (responseAsMap.containsKey(STATUS)) return responseAsMap;
      return responseAsMap.get(firstKey(responseAsMap));
    }
    return responseAsMap;
  }

  private String firstKey(HashMap<String, ?> responseAsMap) {
    return responseAsMap.isEmpty() ? null : responseAsMap.keySet().iterator().next();
  }
}
