package com.demo.liuwenbin.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.demo.liuwenbin.coolweather.receiver.AutoUpdateReceiver;
import com.demo.liuwenbin.coolweather.util.HttpUtil;
import com.demo.liuwenbin.coolweather.util.Utility;

import org.json.JSONException;

/**
 * Created by WinB on 2016/5/27.
 */
public class AutoUpdateService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                updateWeather();
            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour = 8 * 60 * 60 * 1000; // 这是8小时的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AutoUpdateReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    //更新天气信息。
    private void updateWeather() {
        SharedPreferences prefs = PreferenceManager. getDefaultSharedPreferences(this);
        String weatherCode = prefs.getString("weather_code", "");
        String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
        HttpUtil.sendHttpRequest(address, new HttpUtil.HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                try {
                    Utility.handleWeatherResponse(AutoUpdateService.this, response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
}