package com.example.jennachoo.balancedcampus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.util.Log;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.utils.Aware_Plugin;
import com.aware.ESM;
import com.aware.providers.ESM_Provider.*;
import java.util.Calendar;


public class Plugin extends Aware_Plugin {
    private ESMStatusListener esm_statuses;
    private AlarmManager alarmManager;
    private final int morningIntentRC = 123123; //request code for next bid
    private PendingIntent morningIntent = null;

    @Override
    public void onCreate() {
        super.onCreate();
        TAG = "BALANCEDCAMPUS";
        DEBUG = Aware.getSetting(this, Aware_Preferences.DEBUG_FLAG).equals("true");
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, true);
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_SCREEN, true);
        if (DEBUG) Log.d(TAG, "Balanced Campus ESM running");
        IntentFilter esm_filter = new IntentFilter();
        esm_filter.addAction(ESM.ACTION_AWARE_ESM_DISMISSED);
        esm_filter.addAction(ESM.ACTION_AWARE_ESM_EXPIRED);
        esm_filter.addAction(ESM.ACTION_AWARE_ESM_ANSWERED);
        registerReceiver(esm_statuses, esm_filter);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        scheduleMorningQuestionnaire();

    }

    private void scheduleMorningQuestionnaire() {
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);
        cal.set(Calendar.MINUTE, 00);
        cal.set(Calendar.SECOND, 00);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.HOUR_OF_DAY,13);
        cal2.set(Calendar.MINUTE,00);
        cal2.set(Calendar.SECOND, 00);
        Calendar cal3 = Calendar.getInstance();
        cal3.set(Calendar.HOUR_OF_DAY,17);
        cal3.set(Calendar.MINUTE,00);
        cal3.set(Calendar.SECOND,00);
        Calendar cal4 = Calendar.getInstance();
        cal4.set(Calendar.HOUR_OF_DAY,21);
        cal4.set(Calendar.MINUTE,00);
        cal4.set(Calendar.SECOND,00);
        morningIntent = PendingIntent.getBroadcast(getApplicationContext(), morningIntentRC, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY ,morningIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal2.getTimeInMillis(), AlarmManager.INTERVAL_DAY, morningIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal3.getTimeInMillis(), AlarmManager.INTERVAL_DAY, morningIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal4.getTimeInMillis(), AlarmManager.INTERVAL_DAY, morningIntent);
        Log.d(TAG, "Set get next bid alarm for :" + cal.getTimeInMillis() + " " + cal2.getTimeInMillis() +
                " " + cal3.getTimeInMillis() + " " + cal4.getTimeInMillis());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (DEBUG) Log.d(TAG, "Balanced Campus ESM terminating.");
        unregisterReceiver(esm_statuses);
        Aware.setSetting(getApplicationContext(), Aware_Preferences.STATUS_ESM, false);
        sendBroadcast(new Intent(Aware.ACTION_AWARE_REFRESH));
    }


    public class ESMStatusListener extends BroadcastReceiver {

        public void onReceive(Context context, Intent intent) {

            String trigger = null;
            String ans = null;

            Cursor esm_data = context.getContentResolver().query(ESM_Data.CONTENT_URI, null, null, null, null);

            if (esm_data != null && esm_data.moveToLast()) {
                ans = esm_data.getString(esm_data.getColumnIndex(ESM_Data.ANSWER));
                trigger = esm_data.getString(esm_data.getColumnIndex(ESM_Data.TRIGGER));
            }
            if (esm_data != null) {
                esm_data.close();
            }
            if (trigger != null && !trigger.contains("com.example.jennachoo.balancedcampus")) {
                Log.d(TAG, "Somebody else initiated the ESM, no need to react, returning.");
                return;
            }


            if (intent.getAction().equals(ESM.ACTION_AWARE_ESM_EXPIRED)) {
                //scheduleMorningQuestionnaire();
            } else if (intent.getAction().equals(ESM.ACTION_AWARE_ESM_DISMISSED)) {
                //scheduleMorningQuestionnaire();
            } else if (intent.getAction().equals(ESM.ACTION_AWARE_ESM_ANSWERED)) {
                //scheduleMorningQuestionnaire();
            }
        }
    }
}
