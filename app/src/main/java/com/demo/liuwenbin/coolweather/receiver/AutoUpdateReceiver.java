package com.demo.liuwenbin.coolweather.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.demo.liuwenbin.coolweather.service.AutoUpdateService;

/**
 * Created by WinB on 2016/5/27.
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, AutoUpdateService.class);
        context.startService(i);
    }
}
