package com.demo.liuwenbin.coolweather.util;

import org.json.JSONException;

/**
 * Created by WinB on 2016/5/27.
 */
public interface HttpCallbackListener {

    void onFinish(String response) throws JSONException;

    void onError(Exception e);

}

