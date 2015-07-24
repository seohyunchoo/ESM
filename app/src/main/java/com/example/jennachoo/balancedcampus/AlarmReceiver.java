package com.example.jennachoo.balancedcampus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;


import com.aware.ESM;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver{
    private static final String ONE = "{'esm':{" +
            "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
            "'esm_title': 'Balanced Campus'," +
            "'esm_instructions': 'How have you been feeling the past few hours?'," +
            "'esm_radios':['happy','sad','neutral','irritated/angry/frustrated', confused']," +
            "'esm_submit': 'Next'," +
            "'esm_expiration_threshold': 300," +
            "'esm_trigger': 'com.example.jennachoo.balancedcampus'" +
            "}}";
    private static final String TWO = "{'esm':{" +
            "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
            "'esm_title': 'Balanced Campus'," +
            "'esm_instructions': 'How energetic have you been the past few hours?'," +
            "'esm_radios':['1 (not at all)', '2 (a little)', '3 (so so)', '4 (a lot)', '5 (very much/extensive)' ]," +
            "'esm_submit': 'Next'," +
            "'esm_expiration_threshold': 60," +
            "'esm_trigger': 'com.example.jennachoo.balancedcampus'" +
            "}}";
    private static final String THREE = "{'esm':{" +
            "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
            "'esm_title': 'Balanced Campus'," +
            "'esm_instructions': 'How productive have you been the past few hours?'," +
            "'esm_radios':['1 (not at all)', '2 (a little)', '3 (so so)', '4 (a lot)', '5 (very much/extensive)' ]," +
            "'esm_submit': 'Next'," +
            "'esm_expiration_threshold': 60," +
            "'esm_trigger': 'com.example.jennachoo.balancedcampus'" +
            "}}";
    private static final String FOUR = "{'esm':{" +
            "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
            "'esm_title': 'Balanced Campus'," +
            "'esm_instructions': 'What was your workload the past few hours?'," +
            "'esm_radios':['1 (not at all)', '2 (a little)', '3 (so so)', '4 (a lot)', '5 (very much/extensive)' ]," +
            "'esm_submit': 'Next'," +
            "'esm_expiration_threshold': 60," +
            "'esm_trigger': 'com.example.jennachoo.balancedcampus'" +
            "}}";
    private static final String FIVE2 = "{'esm':{" +
            "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
            "'esm_title': 'Balanced Campus'," +
            "'esm_instructions': 'Did you sleep in the past few hours?'," +
            "'esm_radios':['Yes', 'No']," +
            "'esm_submit': 'Submit'," +
            "'esm_expiration_threshold': 60," +
            "'esm_trigger': 'com.example.jennachoo.balancedcampus'" +
            "}}";

    private static final String FIVE = "{'esm':{" +
            "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
            "'esm_title': 'Balanced Campus'," +
            "'esm_instructions': 'What time did you go to bed?'," +
            "'esm_radios':['9PM','10PM','11PM','12AM','1AM','2AM','3AM','4AM','5AM','6AM','7AM','8AM','9AM','I did not sleep last night']," +
            "'esm_submit': 'Next'," +
            "'esm_expiration_threshold': 60," +
            "'esm_trigger': 'com.example.jennachoo.balancedcampus'" +
            "}}";
    private static final String SIX = "{'esm':{" +
            "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
            "'esm_title': 'Balanced Campus'," +
            "'esm_instructions': 'What time did you get up?'," +
            "'esm_radios':['6AM','7AM','8AM','9AM','10AM','11AM','12PM','1PM','2PM','3PM','4PM','5PM','6PM','I did not sleep last night']," +
            "'esm_submit': 'Next'," +
            "'esm_expiration_threshold': 60," +
            "'esm_trigger': 'com.example.jennachoo.balancedcampus'" +
            "}}";
    private static final String SEVEN = "{'esm':{" +
            "'esm_type':" + ESM.TYPE_ESM_RADIO + "," +
            "'esm_title': 'Balanced Campus'," +
            "'esm_instructions': 'How well did you sleep?'," +
            "'esm_radios':['very bad','bad','ok','well','very well']," +
            "'esm_submit': 'Submit'," +
            "'esm_expiration_threshold': 60," +
            "'esm_trigger': 'com.example.jennachoo.balancedcampus'" +
            "}}";

    private static final String NOTMORNINGJSON = "[" + ONE + "," + TWO + "," +  THREE + "," +
            FOUR + "," + FIVE2 +"]";
    private static final String MORNINGJSON = "[" + ONE + "," + TWO + "," +  THREE + "," +
            FOUR + "," + FIVE + "," + SIX + "," + SEVEN + "]";
    private PendingIntent nextIntent;
    private AlarmManager alarmManager;

    public AlarmReceiver() {
    }

    public void scheduleNextQuestionnaire(Context context) {
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        Calendar cal = Calendar.getInstance();
        if(cal.get(Calendar.HOUR_OF_DAY) < 20) {
            cal.add(Calendar.DAY_OF_YEAR, 0);
            cal.add(Calendar.HOUR_OF_DAY, 4);
            cal.set(Calendar.MINUTE,00);
            cal.set(Calendar.SECOND, 00);
            Log.d("BALANCEDCAMPUS", "Same day " + cal.get(Calendar.HOUR_OF_DAY));
        } else {
            cal.add(Calendar.DAY_OF_YEAR,1);
            cal.set(Calendar.HOUR_OF_DAY,9);
            cal.set(Calendar.MINUTE, 00);
            cal.set(Calendar.SECOND,00);
            Log.d("BALANCEDCAMPUS", "Next day " + cal.get(Calendar.HOUR_OF_DAY));
        }
        nextIntent = PendingIntent.getBroadcast(context, 123124,
                alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), nextIntent);
        Log.d("ALARMRECEIVER", "Next Alarm:" + cal.getTimeInMillis());
    }
    /*public void scheduleNextQuestionnaire(Context context){
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE,3);
        cal.set(Calendar.SECOND, 00);
        nextIntent = PendingIntent.getBroadcast(context,123125,
                alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), nextIntent);
        Log.d("ALARMRECEIVER", "Next Alarm:" + cal.getTimeInMillis());
    }

    @Override
    public void onReceive(Context context,Intent intent){
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
        Intent queue_esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
        String esmJSON;
        Calendar currCal = Calendar.getInstance();
        if(currCal.get(Calendar.HOUR_OF_DAY) < 13) {
            esmJSON = MORNINGJSON;
        } else {
            esmJSON = NOTMORNINGJSON;
        }
        queue_esm.putExtra(ESM.EXTRA_ESM, esmJSON);
        context.sendBroadcast(queue_esm);
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        scheduleNextQuestionnaire(context);
    } */

    @Override
    public void onReceive(Context context, Intent intent){
        Vibrator vibrator = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
        Intent queue_esm = new Intent(ESM.ACTION_AWARE_QUEUE_ESM);
        String esmJSON;
        Calendar currCal = Calendar.getInstance();
        if (currCal.get(Calendar.HOUR_OF_DAY) < 10) {
            esmJSON  = MORNINGJSON;
        } else {
            esmJSON  = NOTMORNINGJSON;
        }
        queue_esm.putExtra(ESM.EXTRA_ESM, esmJSON);
        context.sendBroadcast(queue_esm);
        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        scheduleNextQuestionnaire(context);
    }
}
