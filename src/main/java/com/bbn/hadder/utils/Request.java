package com.bbn.hadder.utils;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author Skidder / GregTCLTK
 */

public class Request {

    public static String get(String url) {

        OkHttpClient caller = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();

        try {
            Response response = caller.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            try {
                JSONObject data = json.getJSONObject("data");
                JSONObject response1 = data.getJSONObject("response");
                return response1.toString().replace("{\"url\":\"", "").replace("\"}", "");
            } catch (Exception ignore) {
                return json.getString("url");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
