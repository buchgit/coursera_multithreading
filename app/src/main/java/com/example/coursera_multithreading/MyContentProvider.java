package com.example.coursera_multithreading;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.PreparedStatement;

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
    //Uri
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

    


    @Override
    public boolean onCreate() {
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

/*


  DBHelper dbHelper;
  SQLiteDatabase db;

  public boolean onCreate() {
    Log.d(LOG_TAG, "onCreate");
    dbHelper = new DBHelper(getContext());
    return true;
  }

  // чтение
  public Cursor query(Uri uri, String[] projection, String selection,
      String[] selectionArgs, String sortOrder) {
    Log.d(LOG_TAG, "query, " + uri.toString());
    // проверяем Uri
    switch (uriMatcher.match(uri)) {
    case URI_CONTACTS: // общий Uri
      Log.d(LOG_TAG, "URI_CONTACTS");
      // если сортировка не указана, ставим свою - по имени
      if (TextUtils.isEmpty(sortOrder)) {
        sortOrder = CONTACT_NAME + " ASC";
      }
      break;
    case URI_CONTACTS_ID: // Uri с ID
      String id = uri.getLastPathSegment();
      Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id);
      // добавляем ID к условию выборки
      if (TextUtils.isEmpty(selection)) {
        selection = CONTACT_ID + " = " + id;
      } else {
        selection = selection + " AND " + CONTACT_ID + " = " + id;
      }
      break;
    default:
      throw new IllegalArgumentException("Wrong URI: " + uri);
    }
    db = dbHelper.getWritableDatabase();
    Cursor cursor = db.query(CONTACT_TABLE, projection, selection,
        selectionArgs, null, null, sortOrder);
    // просим ContentResolver уведомлять этот курсор
    // об изменениях данных в CONTACT_CONTENT_URI
    cursor.setNotificationUri(getContext().getContentResolver(),
        CONTACT_CONTENT_URI);
    return cursor;
  }

  public Uri insert(Uri uri, ContentValues values) {
    Log.d(LOG_TAG, "insert, " + uri.toString());
    if (uriMatcher.match(uri) != URI_CONTACTS)
      throw new IllegalArgumentException("Wrong URI: " + uri);

    db = dbHelper.getWritableDatabase();
    long rowID = db.insert(CONTACT_TABLE, null, values);
    Uri resultUri = ContentUris.withAppendedId(CONTACT_CONTENT_URI, rowID);
    // уведомляем ContentResolver, что данные по адресу resultUri изменились
    getContext().getContentResolver().notifyChange(resultUri, null);
    return resultUri;
  }

  public int delete(Uri uri, String selection, String[] selectionArgs) {
    Log.d(LOG_TAG, "delete, " + uri.toString());
    switch (uriMatcher.match(uri)) {
    case URI_CONTACTS:
      Log.d(LOG_TAG, "URI_CONTACTS");
      break;
    case URI_CONTACTS_ID:
      String id = uri.getLastPathSegment();
      Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id);
      if (TextUtils.isEmpty(selection)) {
        selection = CONTACT_ID + " = " + id;
      } else {
        selection = selection + " AND " + CONTACT_ID + " = " + id;
      }
      break;
    default:
      throw new IllegalArgumentException("Wrong URI: " + uri);
    }
    db = dbHelper.getWritableDatabase();
    int cnt = db.delete(CONTACT_TABLE, selection, selectionArgs);
    getContext().getContentResolver().notifyChange(uri, null);
    return cnt;
  }

  public int update(Uri uri, ContentValues values, String selection,
      String[] selectionArgs) {
    Log.d(LOG_TAG, "update, " + uri.toString());
    switch (uriMatcher.match(uri)) {
    case URI_CONTACTS:
      Log.d(LOG_TAG, "URI_CONTACTS");

      break;
    case URI_CONTACTS_ID:
      String id = uri.getLastPathSegment();
      Log.d(LOG_TAG, "URI_CONTACTS_ID, " + id);
      if (TextUtils.isEmpty(selection)) {
        selection = CONTACT_ID + " = " + id;
      } else {
        selection = selection + " AND " + CONTACT_ID + " = " + id;
      }
      break;
    default:
      throw new IllegalArgumentException("Wrong URI: " + uri);
    }
    db = dbHelper.getWritableDatabase();
    int cnt = db.update(CONTACT_TABLE, values, selection, selectionArgs);
    getContext().getContentResolver().notifyChange(uri, null);
    return cnt;
  }

  public String getType(Uri uri) {
    Log.d(LOG_TAG, "getType, " + uri.toString());
    switch (uriMatcher.match(uri)) {
    case URI_CONTACTS:
      return CONTACT_CONTENT_TYPE;
    case URI_CONTACTS_ID:
      return CONTACT_CONTENT_ITEM_TYPE;
    }
    return null;
  }

  private class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
      super(context, DB_NAME, null, DB_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
      db.execSQL(DB_CREATE);
      ContentValues cv = new ContentValues();
      for (int i = 1; i <= 3; i++) {
        cv.put(CONTACT_NAME, "name " + i);
        cv.put(CONTACT_EMAIL, "email " + i);
        db.insert(CONTACT_TABLE, null, cv);
      }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
  }
}

 */