package com.example.jennachoo.balancedcampus;

import android.os.Bundle;
import android.content.Intent;
import android.preference.PreferenceActivity;
import com.aware.Aware;


public class MainActivity extends PreferenceActivity {

    public static final String STATUS_PLUGIN_TEMPLATE = "status_plugin_template";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Aware.startPlugin(this, getPackageName());
        Intent apply = new Intent(Aware.ACTION_AWARE_REFRESH);
        sendBroadcast(apply);
    }

}