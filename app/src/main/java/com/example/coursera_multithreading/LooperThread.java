package com.example.coursera_multithreading;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;

public class LooperThread extends Thread{

    private final String TAG = Looper.class.getSimpleName() + " ###== ";
    public static final int LOOPER_THREAD_MESSAGE = 1;

    private Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    public void run() {
        Looper.prepare();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == LOOPER_THREAD_MESSAGE) {
                    Log.d(TAG, "run() in LooperThread");

                }
            }
        };
        Looper.loop();
    }

    public Handler getHandler() {
        return handler;
    }

}
