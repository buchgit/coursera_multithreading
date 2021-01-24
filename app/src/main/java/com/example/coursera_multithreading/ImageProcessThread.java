package com.example.coursera_multithreading;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class ImageProcessThread extends HandlerThread {

    private static final String TAG = ImageProcessThread.class.getSimpleName() + " ###== ";

    private static final int MESSAGE_CONVERT = 0;

    private Handler mMainHandler;
    private Handler mBackgroundHandler;
    private Callback mCallback;


    public ImageProcessThread(String name) {
        super(name);
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @SuppressLint("HandlerLeak")  // находимся в хендлер треде, утечки не будет
    @Override
    protected void onLooperPrepared() {//метод аналогичный onCreate для активити, ... есть еще, конечно, конструктор
        //создаем хендлер, связанный с мейнтредом
        mMainHandler = new Handler(Looper.getMainLooper());

        //также создаем хендлер, связанный с текущим тредом
        mBackgroundHandler = new Handler() {//по умолчанию получает Looper текущего потока
            //и указываем ему, что делать в случае получения сообщения с нашим what значением
            // это будет выполнено в фоновом потоке
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_CONVERT: {
                        try {

                            process();
                            Log.d(TAG, " process done by thread: "+Thread.currentThread().getName() + "; hashCode: " + Thread.currentThread().hashCode());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
    }

    private void process() throws InterruptedException {
        for (int i = 0; i <= mCallback.getProgressMaxValue(); i++) {
            TimeUnit.SECONDS.sleep(1);
            //постим в мейнтред через мейнхендлер
            final int finalI = i;
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    // этот код уже выполняется в главном потоке
                    mCallback.sendProgress(finalI);
                }
            });

            //постим в мейнтред
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    //этот код так же исполнится в мейнтреде
                    mCallback.onCompleted("value is:" + finalI);
                }
            });
        }
    }

    //этот метод будет вызываться  из главного потока
    public void performOperation() {
        // создаем Message от BackgroundHandler'а, зааписываем в него Bitmap и отправляем в очередь
        mBackgroundHandler
                .obtainMessage(MESSAGE_CONVERT)
                .sendToTarget();
        // созданное сообщение попадает в очередь фонового потока,
        // так как хендлер связан с лупером фонового потока
        // сейчас вызовется метод handleMessage(),
        // который переопределен выше, в методе onLooperPrepared
    }

    public interface Callback {
        void sendProgress(int progress);
        void onCompleted(String string);
        int getProgressMaxValue();
    }

}
