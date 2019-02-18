package com.example.firsthomework;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.LocalBroadcastManager;

import java.util.ArrayList;


public class MyIntentService extends IntentService {

    public static final String contact = "contact";
    public final static String get_contacts = "get_contacts";
    public final static String broad_get_contacts = "broad_get_contacts";
    public MyIntentService() {
        super("MyIntentService");
    }

    public static void startGetContactAction(Context context) {
        Intent intent = new Intent(context, MyIntentService.class);
        intent.setAction(broad_get_contacts);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String getAction = intent.getAction();
            if (broad_get_contacts.equals(getAction)) {
                ArrayList<String> contacts = getContacts();
                LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
                Intent intent1 = new Intent(get_contacts);
                intent1.putStringArrayListExtra(contact, contacts);
                localBroadcastManager.sendBroadcast(intent1);
            }
        }
    }

    private ArrayList<String> getContacts() {
        ArrayList<String> contacts = new ArrayList<>();
        Cursor cursor = getRequest();
        if (cursor != null){
            while (cursor.moveToNext()){
                contacts.add(cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME)));
            }
        }
        if(cursor!=null){ cursor.close(); }
        return contacts;
    }

    private Cursor getRequest(){
        String[] cursor = new String[]{ContactsContract.Contacts.DISPLAY_NAME};
        return getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                cursor, null, null, null);
    }
}
