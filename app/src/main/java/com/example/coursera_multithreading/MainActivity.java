package com.example.coursera_multithreading;

import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

/* room
1. build.gradle app
    implementation 'android.arch.persistence.room:runtime:1.1.1'
    annotationProcessor 'android.arch.persistence.room:compiler:1.1.1'
2. Room.databaseBuilder
3. @Entity, @Database, @Dao
4.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);

        DataBase  mDatabase = Room.databaseBuilder(getApplicationContext(), DataBase.class, "music_database") // название файла
                .fallbackToDestructiveMigration()  // дешевый способ миграции на новую версию
                .build();
    }
}



