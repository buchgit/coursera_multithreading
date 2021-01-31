package com.example.coursera_multithreading;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import java.util.concurrent.TimeUnit;

/*
через Loader запускается и выполняется 10 сек задача. При этом не блокируется работа кнопок.
 */

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

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

        //id = 1, bundle = null, context = this
        //getSupportLoaderManager().initLoader(LOADER_WITH_THREAD,null,this);

        for (int i = 0 ;i<5;i++){
            getSupportLoaderManager().restartLoader(LOADER_WITH_THREAD,null,this);
        }

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


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {
                //Think of this as AsyncTask doInBackground() method,
                // here you will actually initiate Network call,
                // or any work that need to be done on background
                Thread thread = new Thread(hardTask);
                thread.start();
                Log.d(TAG, "loadInBackground: ");
                return null;
            }

            @Override
            protected void onStartLoading() {
                //Think of this as AsyncTask onPreExecute() method,
                // you can start your progress bar,and at the end call forceLoad();
                Log.d(TAG, "onStartLoading: ");
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Log.d(TAG, "onLoadFinished: ");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d(TAG, "onLoaderReset: ");
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