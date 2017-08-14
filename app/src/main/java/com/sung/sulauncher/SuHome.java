package com.sung.sulauncher;

import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SuHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        getSupportActionBar().hide();
    }
}
