package com.example.coursera_multithreading;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Song.class,Albums.class}, version = 1)
public abstract class DataBase extends RoomDatabase {
    public abstract MusicDao getMusicDao();
}
