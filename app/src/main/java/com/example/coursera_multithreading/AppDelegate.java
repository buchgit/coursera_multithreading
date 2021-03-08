package com.example.coursera_multithreading;

import android.app.Application;
import androidx.room.Room;
import com.example.coursera_multithreading.database.DataBase;

public class AppDelegate extends Application {

    private DataBase dataBase;

    public DataBase getDataBase() {
        return dataBase;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        dataBase = Room.databaseBuilder(getApplicationContext(), DataBase.class, "music_song")
                .allowMainThreadQueries() //выполнять в главном потоке запросы к базе данных, но это плохая практика
                .build();
    }

}
