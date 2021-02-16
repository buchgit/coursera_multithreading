package com.example.coursera_multithreading;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/*
реализация нажатий на элементы RecyclerView
 */

public class MainActivity extends AppCompatActivity implements ContactsAdapter.onItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState==null){ //при повороте фрагмент сохраняется, поэтому создаем только при запуске приложения
            getSupportFragmentManager().beginTransaction().replace(R.id.fr_main,RecyclerFragment.newInstance()).commit();
        }

    }

    @Override
    public void onItemClick() {
        Toast.makeText(this, "Обработка нажатий на элемент RecyclerView", Toast.LENGTH_SHORT).show();
    }
}



