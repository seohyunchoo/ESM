package com.example.jennachoo.balancedcampus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.IBinder;
import android.os.Binder;
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
    private PendingIntent noonIntent = null;
    private PendingIntent afternoonIntent = null;
    private PendingIntent eveningIntent = null;
    private PendingIntent rescheduleIntent = null;

    @Override
    public void onCreate() {
        super.onCreate();
        TAG = "BALANCEDCAMPUS";
        DEBUG = Aware.getSetting(this, Aware_Preferences.DEBUG_FLAG).equals("true");
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, true);
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_SCREEN, true);
        if (DEBUG) Log.d(TAG, "Balanced Campus ESM running");
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        timer.schedule(dailyTask, 1000*60*0, 1000*60*12);
    }

    Timer timer = new Timer ();
    TimerTask dailyTask = new TimerTask () {
        @Override
        public void run () {
            Log.d(TAG,"rescheduled");
            scheduleQuestionnaire();
        }
    };


    public void scheduleQuestionnaire() {
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        Calendar cal = Calendar.getInstance();
        //cal.add(Calendar.DAY_OF_YEAR,1);
        //cal.set(Calendar.HOUR_OF_DAY, 11);
        cal.add(Calendar.MINUTE, 2);
        cal.set(Calendar.SECOND, 00);
        Calendar cal2 = Calendar.getInstance();
        //cal2.add(Calendar.DAY_OF_YEAR,1);
        //cal2.set(Calendar.HOUR_OF_DAY,14);
        cal2.add(Calendar.MINUTE, 4);
        cal2.set(Calendar.SECOND, 00);
        Calendar cal3 = Calendar.getInstance();
        //cal3.add(Calendar.DAY_OF_YEAR,1);
        //cal3.set(Calendar.HOUR_OF_DAY,14);
        cal3.add(Calendar.MINUTE,6);
        cal3.set(Calendar.SECOND,00);
        Calendar cal4 = Calendar.getInstance();
        //cal4.add(Calendar.DAY_OF_YEAR,1);
        cal4.set(Calendar.HOUR_OF_DAY,14);
        cal4.set(Calendar.MINUTE,20);
        cal4.set(Calendar.SECOND,00);


        morningIntent = PendingIntent.getBroadcast(getApplicationContext(), 123123, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        noonIntent = PendingIntent.getBroadcast(getApplicationContext(),123124, alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        afternoonIntent = PendingIntent.getBroadcast(getApplicationContext(),123125, alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        //eveningIntent = PendingIntent.getBroadcast(getApplicationContext(),123126, alarmIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), morningIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal2.getTimeInMillis(), noonIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal3.getTimeInMillis(), afternoonIntent);
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal4.getTimeInMillis(), eveningIntent);

        Log.d(TAG, "Alarm 1 :" + cal.getTimeInMillis());
        Log.d(TAG, "Alarm 2 :" + cal2.getTimeInMillis());
        Log.d(TAG, "Alarm 3 :" + cal3.getTimeInMillis());
        //Log.d(TAG, "Alarm 4 :" + cal4.getTimeInMillis());

        Date now = new Date();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (DEBUG) Log.d(TAG, "Balanced Campus ESM terminating.");
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, false);
        sendBroadcast(new Intent(Aware.ACTION_AWARE_REFRESH));
    }


}