package com.example.coursera_multithreading;

import android.widget.ArrayAdapter;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import org.lucasr.twowayview.TwoWayView;

import java.util.ArrayList;

/*
формирование горизонтального списка

https://guides.codepath.com/android/implementing-a-horizontal-listview-guide

подтянуть библиотеку через gradle
в app/build.gradle проекта добавить
dependencies {
    ...
    compile 'org.lucasr.twowayview:twowayview:0.1.4'
}

 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> items = new ArrayList<>();
        items.add("Item 1");
        items.add("Item 2");
        items.add("Item 3");
        items.add("Item 4");

        ArrayAdapter<String> aItems = new ArrayAdapter<>(this, R.layout.my_simple_list_item_1, items);
        TwoWayView lvTest = findViewById(R.id.lvItems);
        lvTest.setAdapter(aItems);

    }

}



