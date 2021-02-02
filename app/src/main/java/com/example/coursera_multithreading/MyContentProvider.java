package com.example.coursera_multithreading;

import android.content.*;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*
Константы провайдера
В приложении мы создали константы, и поместили туда значения из провайдера. Получился хардкод.
А правильнее было бы использовать эти константы прямо из класса провайдера.
Для этого создателю провайдера можно выделить все необходимые константы в отдельный класс,
создать из него библиотеку .jar и распространять ее. Разработчики добавят ее в свой проект,
и смогут оттуда использовать все необходимые им константы для работы с провайдером.

Метод: getWritableDatabase
Метод getWritableDatabase по причинам производительности не рекомендуется
вызывать в onCreate методе провайдера. Поэтому мы в onCreate только создавали DBHelper,
а в методах query, insert и прочих вызывали getWritableDatabase() и получали доступ к БД.

 */

public class MyContentProvider extends ContentProvider {

    public static final String TAG = MyContentProvider.class.getSimpleName() + " ###== ";

    private static final String DB_NAME = "myDb";
    private static final int DB_VERSION = 1;

    private static final String CONTACT_TABLE = "contacts";

    private static final String CONTACT_ID = "_id";
    private static final String CONTACT_NAME = "name";
    private static final String CONTACT_EMAIL = "email";

    private static final String DB_CREATE = "create table " + CONTACT_TABLE + "("
            + CONTACT_ID + " integer primary key autoincrement, "
            + CONTACT_NAME + " text, "
            + CONTACT_EMAIL + " text);";

    //authority
    private static final String  AUTHORITY = "com.example.coursera_multithreading.provider.AddressBook";
    //path
    private static final String CONTACT_PATH = "contacts";//same as contact table in this sample
    //Uri //пригодится для получения уведомлений об изменении в данном uri
    private static final Uri CONTACT_CONTENT_URI = Uri.parse("content://"
    + AUTHORITY + "/" + CONTACT_PATH);

    //data types
    //many rows
    private static final String CONTACT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "/" + CONTACT_PATH;
    //single row
    private static final String CONTACT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "/" + CONTACT_PATH;

    //UriMather
    //общий Uri
    private static final int URI_CONTACTS = 1;
    //Uri c id
    private static final int URI_CONTACTS_ID = 2;

    //create UriMather
    private static final UriMatcher uriMatcher;
    static {
       uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
       uriMatcher.addURI(AUTHORITY,CONTACT_PATH,URI_CONTACTS);
       uriMatcher.addURI(AUTHORITY,CONTACT_PATH + "/#",URI_CONTACTS_ID);
    }

    DBHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate ContentProvider");
        dbHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        switch (uriMatcher.match(uri)){
            case URI_CONTACTS:
                Log.d(TAG, "query: URI_CONTACTS");
                if (sortOrder.isEmpty()){
                    sortOrder = CONTACT_NAME + " ASC";
                }
                break;
            case URI_CONTACTS_ID:
                Log.d(TAG, "query: URI_CONTACTS_ID");
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    selection = CONTACT_ID + " = " + id;
                }else{
                    selection = selection + " AND " + CONTACT_ID + " = " + id;
                }

                break;
            default:
                Log.d(TAG, "query: Wrong query");
                throw new IllegalArgumentException("query: Wrong query");
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(CONTACT_TABLE,projection,selection,selectionArgs,null,null,sortOrder,null);

        //включаем напоминание курсору через Resolver, при изменениии в uri = CONTACT_CONTENT_URI
        cursor.setNotificationUri(getContext().getContentResolver(),CONTACT_CONTENT_URI);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case URI_CONTACTS:
                return CONTACT_CONTENT_TYPE;
            case URI_CONTACTS_ID:
                return CONTACT_CONTENT_ITEM_TYPE;
            default: return null;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri)!= URI_CONTACTS){
            throw new IllegalArgumentException("Wrong uri" + uri);
        }

        Log.d(TAG, "insert: ");

        db = dbHelper.getWritableDatabase();
        Long id = db.insert(CONTACT_TABLE,null,values);
        Uri newUri = ContentUris.withAppendedId(CONTACT_CONTENT_URI, id);

        //уведомляем ContentResolver, что данные по адресу newUri изменились
        getContext().getContentResolver().notifyChange(newUri,null);

        return newUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)){
            case URI_CONTACTS:
                Log.d(TAG, "delete: URI_CONTACTS");
                break;
            case URI_CONTACTS_ID:
                Log.d(TAG, "delete: URI_CONTACTS_ID");
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    selection = CONTACT_ID + " = " + id;
                }else{
                    selection = selection + " AND " + CONTACT_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong uri "+ uri);
        }

        db = dbHelper.getWritableDatabase();
        int result = db.delete(CONTACT_TABLE,selection,selectionArgs);

        //уведомляем ContentResolver, что данные по адресу uri изменились
        getContext().getContentResolver().notifyChange(uri,null);

        return result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)){
            case URI_CONTACTS:
                Log.d(TAG, "update: URI_CONTACTS");
                break;
            case URI_CONTACTS_ID:
                Log.d(TAG, "update: URI_CONTACTS_ID");
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)){
                    selection = CONTACT_ID + " = " + id;
                }else {
                    selection = selection + " AND " + CONTACT_ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong uri "+ uri);
        }
        db = dbHelper.getWritableDatabase();
        int result = db.update(CONTACT_TABLE,values,selection,selectionArgs);

        //уведомляем ContentResolver, что данные по адресу uri изменились
        getContext().getContentResolver().notifyChange(uri,null);

        return result;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(@Nullable Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.d(TAG, "onCreate: DBHelper");
            db.execSQL(DB_CREATE);
            ContentValues contentValues = new ContentValues();
            for (int i=1;i<=3;i++){
                Log.d(TAG, "onCreate:DBHelper execSQL ... row: " + i);
                contentValues.put("name","name"+i);
                contentValues.put("email","email"+i+"@gmail.com");
                db.insert(CONTACT_TABLE,null,contentValues);
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

    }
}

/*


 */