package com.example.coursera_multithreading;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/*
1. Добавить фрагмент
2. Добавить адаптер
3. Добавить холдер
4. Добавить заглушечные данные

 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState==null){ //при повороте фрагмент сохраняется, поэтому создаем только при запуске приложения
            getSupportFragmentManager().beginTransaction().replace(R.id.fr_main,RecyclerFragment.newInstance()).commit();
        }

    }

}



