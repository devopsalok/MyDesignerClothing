package com.mydesignerclothing.mobile.android.login.loginmain.services;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;
import com.mydesignerclothing.mobile.android.commons.util.StringUtils;

import org.codehaus.jackson.JsonNode;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class BaseResponse {

  private static final String TAG = BaseResponse.class.getSimpleName();
  private static final String NO_MAPPING_FOUND_FOR = "No mapping found for ";
  @Expose
  private String errorCode;
  @Expose
  private String status;
  @Expose
  private String errorMessage;
  @Expose
  private String errorHeader;
  @Expose
  private String errorStatus;
  private String errorType;
  @Expose
  private String reason;
  private final ArrayList<String> responseJSON = new ArrayList<>();

  /**
   * the errorCode property of the JSON object that is comes back from the services.
   */
  public static final String ERROR_CODE = "errorCode";
  /**
   * the status property of the JSON object that is comes back from the services.
   */
  public static final String STATUS = "status";
  /**
   * the errorMessage property of the JSON object that is comes back from the services.
   */
  public static final String ERROR_MESSAGE = "errorMessage";
  /**
   * the errorMessage property of the JSON object that is comes back from the services.
   */
  public static final String ERROR_HEADER = "errorHeader";
  /**
   * the errorType property of the JSON object that is comes back from the services.
   */
  public static final String ERROR_TYPE = "errorType";
  /**
   * the errorStatus property of the JSON object that is comes back from the services.
   */
  public static final String ERROR_STATUS = "errorStatus";
  /**
   * the reason property of the JSON object that is comes back from the services.
   */
  public static final String REASON = "reason";
  private long retryTimeout;

  public BaseResponse() {

  }

  /**
   * Get the error code that came from the services
   *
   * @return Returns the error code
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * Set the error code from the JSON object that came from the services
   *
   * @param jsonObject - JSON object returned
   */
  public void setErrorCode(JSONObject jsonObject) {
    if (jsonObject.has(ERROR_CODE)) {
      this.setErrorCode(jsonObject.optString(ERROR_CODE));
    } else {
      DMLog.log(TAG, NO_MAPPING_FOUND_FOR + ERROR_CODE, Log.DEBUG);
    }
  }

  /**
   * Set the error code from a JSON node
   *
   * @param jsonNode - JSON Node to get the error code from
   */
  public void setErrorCode(JsonNode jsonNode) {
   // this.setErrorCode(JSONResponseFactory.getTextValue(jsonNode, ERROR_CODE, null));
  }

  /**
   * Set the error code that came from the services
   *
   * @param errorCode - Error code to set
   */
  public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * Get the status that came from the services
   *
   * @return Returns the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * Set the status from the JSON object that came from the services
   *
   * @param jsonObject - JSON Object that came from the services
   */
  public void setStatus(JSONObject jsonObject) {
    if (jsonObject.has(STATUS)) {
      this.setStatus(jsonObject.optString(STATUS));
    } else {
      DMLog.log(TAG, NO_MAPPING_FOUND_FOR + STATUS, Log.DEBUG);
    }
  }

  /**
   * Set the status from a JSON node
   *
   * @param jsonNode - JSON node to get the status from
   */
  public void setStatus(JsonNode jsonNode) {
   // this.setStatus(JSONResponseFactory.getTextValue(jsonNode, STATUS, null));
  }

  /**
   * Set the status that came from the services
   *
   * @param status - Status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * Get the error message that came from the services
   *
   * @return Returns the error message that came from the services
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * Set the error message from the JSON object that came from the services
   *
   * @param jsonObject - JSON object that came from the services
   */
  public void setErrorMessage(JSONObject jsonObject) {
    if (jsonObject.has(ERROR_MESSAGE)) {
      this.setErrorMessage(jsonObject.optString(ERROR_MESSAGE));
    } else {
      DMLog.log(TAG, NO_MAPPING_FOUND_FOR + ERROR_MESSAGE, Log.DEBUG);
    }
  }

  /**
   * Set the error message from a JSON node
   *
   * @param jsonNode - JSON node to get the error message from
   */
  public void setErrorMessage(JsonNode jsonNode) {
    //this.setErrorMessage(JSONResponseFactory.getTextValue(jsonNode, ERROR_MESSAGE, null));
  }

  /**
   * Set the error message that came from the services
   *
   * @param errorMessage - Error message that came from the services
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * Set the error header from the JSON object that came from the services
   *
   * @param jsonObject - JSON object that came from the services
   */
  public void setErrorHeader(JSONObject jsonObject) {
    if (jsonObject.has(ERROR_HEADER)) {
      this.setErrorHeader(jsonObject.optString(ERROR_HEADER));
    } else {
      DMLog.log(TAG, NO_MAPPING_FOUND_FOR + ERROR_HEADER, Log.DEBUG);
    }
  }

  /**
   * Set the error header from a JSON node
   *
   * @param jsonNode - JSON node to get the error header from
   */
  public void setErrorHeader(JsonNode jsonNode) {
   // this.setErrorHeader(JSONResponseFactory.getTextValue(jsonNode, ERROR_HEADER, null));
  }

  /**
   * Set the error header that came from the services
   *
   * @param errorHeader - Error header that came from the services
   */
  public void setErrorHeader(String errorHeader) {
    this.errorHeader = errorHeader;
  }

  /**
   * Get the error title that came from the services
   *
   * @return Returns the error title that came from the services
   */
  public String getErrorTitle() {
    return errorHeader != null ? errorHeader : ERROR_MESSAGE;
  }


  /**
   * Get the error status that came from the services
   *
   * @return - Returns the error status that came from the services
   */
  public String getErrorStatus() {
    return errorStatus;
  }

  /**
   * Set the error status from a JSON node
   *
   * @param jsonNode - JSON node to get the error status from
   */
  public void setErrorStatus(JsonNode jsonNode) {
   // this.setErrorStatus(JSONResponseFactory.getTextValue(jsonNode, ERROR_STATUS, null));
  }

  /**
   * Set the error status that came from the services
   *
   * @param errorStatus - Error status that came from the services
   */
  public void setErrorStatus(String errorStatus) {
    this.errorStatus = errorStatus;
  }

  /**
   * Get the reason that came from the services
   *
   * @return Returns the reasons that came from the services
   */
  public String getReason() {
    return reason;
  }

  /**
   * Set the reason from the JSON object that came from the services
   *
   * @param jsonObject - JSON object that came from the services
   */
  public void setReason(JSONObject jsonObject) {
    if (jsonObject.has(REASON)) {
      this.setReason(jsonObject.optString(REASON));
    } else {
      DMLog.log(TAG, NO_MAPPING_FOUND_FOR + REASON, Log.DEBUG);
    }
  }

  /**
   * Set the reason from a JSON node
   *
   * @param jsonNode - JSON node to get the error reason from
   */
  public void setReason(JsonNode jsonNode) {
    //this.setReason(JSONResponseFactory.getTextValue(jsonNode, REASON, null));
  }

  /**
   * Set the reason that came from the services
   *
   * @param reason - Reason that came from the services
   */
  public void setReason(String reason) {
    this.reason = reason;
  }

  /**
   * Set the base elements of the response from the JSON object that came from the services.
   * This interface will set the following:
   * <ul>
   * <li>status</li>
   * <li>error code</li>
   * <li>error message</li>
   * <li>error status</li>
   * <li>reason</li>
   * </ul>
   *
   * @param objectMap - objectMap that has been processed
   */
  public void setBaseResponseElements(Map<String, Object> objectMap) {
    setStatus((String) objectMap.get(STATUS));
    setErrorCode((String) objectMap.get(ERROR_CODE));
    setErrorMessage((String) objectMap.get(ERROR_MESSAGE));
    setErrorHeader((String) objectMap.get(ERROR_HEADER));
    setErrorStatus((String) objectMap.get(ERROR_STATUS));
    setReason((String) objectMap.get(REASON));
    setRetryTimeout((Integer) objectMap.get("retryAfter"));
  }

  /**
   * Set the base elements of the response from the a JSON node.
   * This interface will set the following:
   * <ul>
   * <li>status</li>
   * <li>error code</li>
   * <li>error message</li>
   * <li>error status</li>
   * <li>reason</li>
   * </ul>
   *
   * @param jsonNode - JSON node to set base properties from
   */
  public void setBaseResponseElements(JsonNode jsonNode) {
    setStatus(jsonNode);
    setErrorCode(jsonNode);
    setErrorMessage(jsonNode);
    setErrorHeader(jsonNode);
    setErrorStatus(jsonNode);
    setReason(jsonNode);
  }

  /**
   * Set a default error.  Use this when the JSON response is null, this will in turn generate a generic "technical difficulties" error.
   */
  public void setDefaultError() {
   // this.status = ErrorBase.STATUS_ERROR;
    this.errorCode = StringUtils.EMPTY_STRING;
  }

  /**
   * get the ArrayList of response JSON for this response
   *
   * @return Returns the ArrayList JSON for this response
   */
  public ArrayList<String> getResponseJSON() {
    return responseJSON;
  }

  /**
   * Add a JSON for this response.  Should only be done when a copy of the object is needed to be stored on the device
   *
   * @param responseJSON - Response JSON to add.
   */
  public void addResponseJSON(String responseJSON) {
    getResponseJSON().add(responseJSON);
  }

  public boolean hasError() {
    return true;
  }

  public void setRetryTimeout(Integer retryTimeout) {
    this.retryTimeout = (retryTimeout == null) ? 300000 : retryTimeout;
  }

  public long getRetryTimeout() {
    return retryTimeout;
  }
}
