package com.example.jennachoo.balancedcampus;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.utils.Aware_Plugin;
import com.aware.ESM;
import com.aware.providers.ESM_Provider.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;



public class Plugin extends Aware_Plugin {
    private AlarmManager alarmManager;
    private PendingIntent morningIntent = null;

    @Override
    public void onCreate() {
        super.onCreate();
        TAG = "BALANCEDCAMPUS";
        DEBUG = Aware.getSetting(this, Aware_Preferences.DEBUG_FLAG).equals("true");
    }


    public void scheduleQuestionnaire() {
        Intent alarmIntent = new Intent(this,AlarmReceiver.class);
        Calendar cal = Calendar.getInstance();
        if(cal.get(Calendar.HOUR_OF_DAY) < 9){
            cal.add(Calendar.DAY_OF_YEAR, 0);
            cal.set(Calendar.HOUR_OF_DAY, 9);
            cal.set(Calendar.MINUTE, 00);
            cal.set(Calendar.SECOND, 00);
        } else if (cal.get(Calendar.HOUR_OF_DAY) < 13){
            cal.add(Calendar.DAY_OF_YEAR, 0);
            cal.set(Calendar.HOUR_OF_DAY, 13);
            cal.set(Calendar.MINUTE, 00);
            cal.set(Calendar.SECOND, 00);
        } else if (cal.get(Calendar.HOUR_OF_DAY) < 17){
            cal.add(Calendar.DAY_OF_YEAR, 0);
            cal.set(Calendar.HOUR_OF_DAY, 17);
            cal.set(Calendar.MINUTE, 00);
            cal.set(Calendar.SECOND, 00);
        } else if (cal.get(Calendar.HOUR_OF_DAY) < 21){
            cal.add(Calendar.DAY_OF_YEAR, 0);
            cal.set(Calendar.HOUR_OF_DAY, 21);
            cal.set(Calendar.MINUTE, 00);
            cal.set(Calendar.SECOND, 00);
        } else {
            cal.add(Calendar.DAY_OF_YEAR, 1);
            cal.set(Calendar.HOUR_OF_DAY, 9);
            cal.set(Calendar.MINUTE, 00);
            cal.set(Calendar.SECOND, 00);
        }
        morningIntent = PendingIntent.getBroadcast(getApplicationContext(),123123,
                alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), morningIntent);
        Log.d(TAG, "Alarm 1 :" + cal.getTimeInMillis());

    }

    /*public void scheduleQuestionnaire(){
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,14);
        cal.set(Calendar.MINUTE, 19);
        morningIntent = PendingIntent.getBroadcast(getApplicationContext(),123123,
                alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), morningIntent);
        Log.d(TAG, "Alarm 1 :" + cal.getTimeInMillis());
    }
*/
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (DEBUG) Log.d(TAG, "Balanced Campus ESM terminating.");
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, false);
        sendBroadcast(new Intent(Aware.ACTION_AWARE_REFRESH));
    }

    @Override
    public int onStartCommand(Intent intent, int x, int y){
        super.onStartCommand(intent, x, y);
        Log.d(TAG, "service started");
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, true);
        if (DEBUG) Log.d(TAG, "Balanced Campus ESM running");
        alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        scheduleQuestionnaire();
        return 1;
    }
}