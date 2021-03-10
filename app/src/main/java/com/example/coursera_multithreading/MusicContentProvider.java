package com.example.coursera_multithreading;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import androidx.room.Room;
import com.example.coursera_multithreading.database.DataBase;
import com.example.coursera_multithreading.database.MusicDao;

/*
1. onCreate() - доступ к объекту DAO
2. getType() - возврат mime-типа объекта: dir или item
"vnd.android.cursor.dir/"
"vnd.android.cursor.item/"
3. query() - возвращает курсор
4.
5.
6.

 */

public class MusicContentProvider extends ContentProvider {

    private final static String TAG = MusicContentProvider.class.getSimpleName();

    private final static String AUTHORITY = "com.example.musicprovider";

    private final static String TABLE_ALBUM = "album";

    private final static UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

    public static final int TABLE_ALBUM_CODE = 100;

    public static final int ITEM_ALBUM_CODE = 101;

    private MusicDao musicDao;

    static {
        //для каждой таблицы провайдера добавлять строки состояний
        matcher.addURI(AUTHORITY,TABLE_ALBUM, TABLE_ALBUM_CODE);//для запроса всей таблицы
        matcher.addURI(AUTHORITY,TABLE_ALBUM+"/*", ITEM_ALBUM_CODE);//для запроса строки таблицы
    }

    public MusicContentProvider() {
    }

    //тут получаем доступ к базе данных
    @Override
    public boolean onCreate() {
        if (getContext() != null) {
            DataBase dataBase = Room.databaseBuilder(getContext().getApplicationContext(), DataBase.class, "music_song").build();
            musicDao = dataBase.getMusicDao();
            return true;
        }
        return false;
    }
    //Mime тип возвращаемых данных: dir or item
    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)){
            case TABLE_ALBUM_CODE:
                return "vnd.android.cursor.dir/"  + AUTHORITY + "." + TABLE_ALBUM;
            case ITEM_ALBUM_CODE:
                return "vnd.android.cursor.item/" + AUTHORITY + "." + TABLE_ALBUM;
            default:
                throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    //возвращает Cursor
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        switch (matcher.match(uri)){
            case TABLE_ALBUM_CODE:
                return musicDao.getAlbumsCursor();
            case ITEM_ALBUM_CODE:
                //return musicDao.getAlbumWithIdCursor(Integer.parseInt(selectionArgs[0]));
                return musicDao.getAlbumWithIdCursor((int) ContentUris.parseId(uri));
            default:throw new UnsupportedOperationException("Not yet implemented");
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        switch (matcher.match(uri)){
            case ITEM_ALBUM_CODE:
                return musicDao.deleteAlbum((int)ContentUris.parseId(uri));
            default:throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    //не доделано
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (matcher.match(uri)){
            case ITEM_ALBUM_CODE:
                return uri;
            default:throw new UnsupportedOperationException("Not yet implemented");
        }
    }
    //не доделано
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (matcher.match(uri)){
            case ITEM_ALBUM_CODE:
                return 0;
            default:throw new UnsupportedOperationException("Not yet implemented");
        }
    }
}
