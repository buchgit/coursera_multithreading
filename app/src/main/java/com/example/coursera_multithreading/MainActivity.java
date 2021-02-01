package com.example.coursera_multithreading;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

/*
Урок 101.
Создаем свой ContentProvider

Uri можно представить в таком виде: content://<authority>/<path>/<id>


 */

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName() + " ###== ";
    private final int LOADER_WITH_THREAD = 1;

    Button buttonPerform;
    Button buttonMessage;

    TextView textView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();





    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_message:
                Log.d(TAG, "onClick: buttonMessage");
                textView.setText("onClick: buttonMessage");
                break;
            case R.id.btn_perform:
                Log.d(TAG, "onClick: buttonPerform");
                textView.setText("onClick: buttonPerform");
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



    Runnable hardTask = new Runnable() {
        @Override
        public void run() {
            try {
                Log.d(TAG, "start: hardTask");
                TimeUnit.SECONDS.sleep(10);
                Log.d(TAG, "finish: hardTask");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
}