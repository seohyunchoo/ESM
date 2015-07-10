package com.example.jennachoo.balancedcampus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.content.Intent;
import android.preference.PreferenceActivity;
import com.aware.Aware;
import com.aware.Aware;

public class rebootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Aware.startPlugin(context, context.getPackageName());
        Intent apply = new Intent(Aware.ACTION_AWARE_REFRESH);
        context.sendBroadcast(apply);
    }


}
