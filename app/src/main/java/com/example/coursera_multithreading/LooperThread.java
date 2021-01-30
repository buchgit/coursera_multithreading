package com.example.coursera_multithreading;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class LooperThread extends Thread{

    public static final String TAG = Looper.class.getSimpleName() + " ###== ";
    public static final int LOOPER_THREAD_MESSAGE = 10;
    private WeakReference <MainActivity> weakReference_activity;

    public void setActivity(MainActivity activity) {
        weakReference_activity = new WeakReference<>(activity);
    }

    private Handler handler;

    @SuppressLint("HandlerLeak")
    @Override
    public void run() {
        Looper.prepare();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case LOOPER_THREAD_MESSAGE:
                        Log.d(TAG, "run() in LooperThread");
                        handler.post(hardTaskLooperThreadClass);
                        weakReference_activity.get().runOnUiThread(hardTaskLooperThreadClass);//сработало!
                        break;
                    case MainActivity.MESSAGE_1:
                        Log.d(TAG, "run() in LooperThread MESSAGE_1 = "+ msg.obj);
                        break;
                    case MainActivity.MESSAGE_2:
                        Log.d(TAG, "run() in LooperThread MESSAGE_2 = "+ msg.obj);
                        break;
                    default:break;
                }

            }
        };

        Looper.loop();
    }

    public Handler getHandler() {
        return handler;
    }

    Runnable hardTaskLooperThreadClass = new Runnable() {
        @Override
        public void run() {
            for (int i=0;i<10;i++){
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "hardTaskLooperThreadClass is done by thread: "+Thread.currentThread().getName() + "; hashCode: " + Thread.currentThread().hashCode());
        }
    };

}
