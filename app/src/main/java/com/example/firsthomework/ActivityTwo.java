package com.example.firsthomework;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityTwo extends AppCompatActivity {

    private static final int read_contacts = 200;
    public final static String get_contacts = "get_contacts";

    @TargetApi(Build.VERSION_CODES.M)
    private void requestContacts(){
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.COMPONENT_ENABLED_STATE_DEFAULT) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, read_contacts);
        } else { MyIntentService.startGetContactAction(this); }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        LocalBroadcastManager.getInstance(this).registerReceiver(contactsReceiver, new IntentFilter(get_contacts));
        requestContacts();
    }

    private BroadcastReceiver contactsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ActivityTwo.this.setResult(0, intent);
            finish();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == read_contacts) {
            if (grantResults[0] == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT) {
                requestContacts();
            }
        }
    }
}
