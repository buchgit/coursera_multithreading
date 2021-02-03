package com.example.coursera_multithreading;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import androidx.appcompat.app.AppCompatActivity;


/* Все работает
Урок 101.
Создаем свой ContentProvider

Uri можно представить в таком виде: content://<authority>/<path>/<id>

 */

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName() + " ###== ";

    final Uri CONTACT_URI = Uri
            .parse("content://com.example.coursera_multithreading.MyContentProvider/contacts");

    final String CONTACT_NAME = "name";
    final String CONTACT_EMAIL = "email";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor = getContentResolver().query(CONTACT_URI, null, null,
                null, null);
        startManagingCursor(cursor);

        String from[] = { "name", "email" };
        int to[] = { android.R.id.text1, android.R.id.text2 };
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, cursor, from, to);

        ListView lvContact = (ListView) findViewById(R.id.lvContact);
        lvContact.setAdapter(adapter);

    }

    public void onClickInsert(View v) {
        ContentValues cv = new ContentValues();
        cv.put(CONTACT_NAME, "name 4");
        cv.put(CONTACT_EMAIL, "email 4");
        ContentResolver contentResolver = getContentResolver();
        Uri newUri = contentResolver.insert(CONTACT_URI, cv);
        Log.d(TAG, "insert, result Uri : " + newUri.toString());
    }

    public void onClickUpdate(View v) {
        ContentValues cv = new ContentValues();
        cv.put(CONTACT_NAME, "name 5");
        cv.put(CONTACT_EMAIL, "email 5");
        Uri uri = ContentUris.withAppendedId(CONTACT_URI, 2);
        int cnt = getContentResolver().update(uri, cv, null, null);
        Log.d(TAG, "update, count = " + cnt);
    }

    public void onClickDelete(View v) {
        Uri uri = ContentUris.withAppendedId(CONTACT_URI, 4);
        int cnt = getContentResolver().delete(uri, null, null);
        Log.d(TAG, "delete, count = " + cnt);
    }

    public void onClickError(View v) {
        Uri uri = Uri.parse("content://ru.startandroid.providers.MyContentProvider/phones");
        try {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        } catch (Exception ex) {
            Log.d(TAG, "Error: " + ex.getClass() + ", " + ex.getMessage());
        }

    }

}
