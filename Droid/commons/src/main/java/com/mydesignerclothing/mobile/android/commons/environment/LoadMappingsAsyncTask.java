package com.mydesignerclothing.mobile.android.commons.environment;

import android.os.AsyncTask;
import android.util.Log;

import com.mydesignerclothing.mobile.android.commons.core.collections.Function;
import com.mydesignerclothing.mobile.android.commons.logger.DMLog;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class LoadMappingsAsyncTask extends AsyncTask<String, Void, Map<String, String>> {
    private static final String TAG = LoadMappingsAsyncTask.class.getSimpleName();
    private final String endpoint;
    private final Function<Map<String, String>> callback;

    public LoadMappingsAsyncTask(String endpoint, Function<Map<String, String>> callback){
        this.endpoint = endpoint;
        this.callback = callback;
    }

    public void load(){
        execute(endpoint);
    }

    @Override
    protected Map<String, String> doInBackground(String... params) {
        Map<String,String> propertyMap = new HashMap<String, String>();
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            URI website = new URI(endpoint);
            request.setURI(website);
            HttpResponse response = httpclient.execute(request);
            try(BufferedReader in = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()))) {
              if (in != null) {
                Properties properties = new Properties();
                properties.load(in);
                for (String key : properties.stringPropertyNames()) {
                  String value = (String) properties.get(key);
                  propertyMap.put(key, value);
                }
              }
            }

        } catch (Exception e) {
            DMLog.log(TAG, e, Log.ERROR);
        }
        return propertyMap;
    }

    @Override
    protected void onPostExecute(Map<String, String> stringStringMap) {
        callback.apply(stringStringMap);
    }
}
