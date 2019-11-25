package com.mili.camera2api;

import android.util.Patterns;

import org.json.JSONException;
import org.json.JSONObject;

class HelperClass {

    static boolean isDataOfTypeNumber(String data) {
        try {
            Double.parseDouble(data);
            return true;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean isDataOfTypeJson(String data) {
        try {
            new JSONObject(data);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    static boolean isDataAnUrl(String data) {
        return android.util.Patterns.WEB_URL.matcher(data).matches();
    }

    static boolean isDataAnEmailId(String data) {
        return Patterns.EMAIL_ADDRESS.matcher(data).matches();
    }


}
