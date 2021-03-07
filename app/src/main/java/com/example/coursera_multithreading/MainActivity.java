package com.example.coursera_multithreading;

import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.coursera_multithreading.database.DataBase;

/* room
1. build.gradle app (dependencies)
2. Room.databaseBuilder
3. @Entity, @Database, @Dao
4. Также можно создать inMemoryDatabase - базу, которая существует до тех пор, пока процесс не будет уничтожен. Работает как кеш
5.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);

        DataBase mDatabase = Room.databaseBuilder(getApplicationContext(), DataBase.class, "music_database") // название файла
                .fallbackToDestructiveMigration()  // дешевый способ миграции на новую версию
                .build();
    }
}



