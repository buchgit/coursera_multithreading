package com.example.coursera_multithreading;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.coursera_multithreading.database.Album;
import com.example.coursera_multithreading.database.DataBase;
import com.example.coursera_multithreading.database.MusicDao;

import java.util.ArrayList;
import java.util.List;

/* room
1. build.gradle app (dependencies)
2. Room.databaseBuilder
3. @Entity, @Database, @Dao
4. Также можно создать inMemoryDatabase - базу, которая существует до тех пор, пока процесс не будет уничтожен. Работает как кеш
5.
 */

public class MainActivity extends AppCompatActivity {

    DataBase dataBase;
    MusicDao musicDao;
    Button buttonGet;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonGet = findViewById(R.id.btn_get);

        AppDelegate appDelegate = (AppDelegate)getApplicationContext();
        dataBase = appDelegate.getDataBase();

        musicDao = dataBase.getMusicDao();

        musicDao.insertAlbums(getAlbumList());

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_get:
               List<Album> albums = musicDao.getAlbums();
               printAlbums(albums);
               break;
        }
    }

    private void printAlbums(List<Album> albums) {
        for (Album album:albums){
            Toast.makeText(this,album.toString(),Toast.LENGTH_LONG).show();
        }
    }

    private List<Album> getAlbumList() {
        List<Album>list = new ArrayList<>(10);
        for (int i = 0; i<10; i++){
            list.add(new Album(i,"name"+i,""+System.currentTimeMillis()));
        }
        return list;
    }
}



