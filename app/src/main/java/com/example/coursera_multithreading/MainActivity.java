package com.example.coursera_multithreading;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
Сохраняем данные в Bundle, при смене активити достаем из Bundle
 */

public class MainActivity extends AppCompatActivity  {

    private final String TAG = MainActivity.class.getSimpleName() + " ###== ";

    Button buttonPerform;
    Button buttonMessage;

    TextView textView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initUI();



    }

    void onClick(View view){
        switch (view.getId()){
            case R.id.btn_message:
                Log.d(TAG, "onClick: buttonMessage");
                break;
            case R.id.btn_perform:
                Log.d(TAG, "onClick: buttonPerform");
                break;
            default:break;
        }
    }

    private void initUI(){
        buttonMessage = findViewById(R.id.btn_message);
        buttonPerform = findViewById(R.id.btn_perform);
        textView = findViewById(R.id.tv_main);
        progressBar = findViewById(R.id.pr_bar);
    }

}