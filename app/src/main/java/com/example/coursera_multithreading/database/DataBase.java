package com.example.coursera_multithreading.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Song.class, Album.class, AlbumSong.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract MusicDao getMusicDao();
}
