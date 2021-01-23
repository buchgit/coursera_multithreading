package com.example.coursera_multithreading;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;

/*
1. В треде должно быть поле хэндлера
2. Создать хэндлер, привязать к луперу треда через метод getLooper()
3. Можно создать поле для активити, чтобы управлять ее элементами
Интересный момент:
Если в данном классе создать внутренний класс-интерфейс , к примеру CallbackActivity,
потом любую активити имплементировать от MyHandlerThread.CallbackActivity
то тогда поле для хранения активити в данном классе можно сделать типа CallbackActivity
и создать сеттер для этого поля

можно инициализировать хэндлер в onLooperPrepared(), но эксперимент показал, что иногда функция onLooperPrepared()
вызывается позже, чем метод postTask, а значит при выполнении этой функции хэндлер равен null
если же пользоваться вызовом handler через свой метод prepareHandler(), то postTask - всегда после определения хэндлера

Если дать задержку (в коде = 1 сек) после onLooperPrepared() , то тогда программа не крэшится.
 */

public class MyHandlerThread extends HandlerThread {

    private final String TAG = MyHandlerThread.class.getSimpleName() + " ###== ";
    private Handler handler;
    private CallbackActivity activity;
    public static final int HANDLER_TAG = 1;

    public MyHandlerThread(String name) {
        super(name);
    }

    @Override
    public Looper getLooper() {
        return super.getLooper();
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        handler = new Handler(getLooper());
        Log.d(TAG, "onLooperPrepared: handler.hashCode() " +handler.hashCode());

    }

    //здесь поместить код , который выполняет тред
    @Override
    public void run() {
        super.run();

    }

    //собственно добавленные функции
    //НО лучше инициализировать хэндлер в отдельной процедуре
    public void prepareHandler(){
        handler = new Handler(getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == HANDLER_TAG){
                    activity.setText("THREAD " + Thread.currentThread().getName() + " made HANDLER_TAG");
                    Log.d(TAG, "THREAD \" + Thread.currentThread().getName() + \" made HANDLER_TAG");
                }
            }
        };
        Log.d(TAG, "prepareHandler: handler.hashCode() " +handler.hashCode());
    }

    //работа с местным хэндлером
    public void postTask(Runnable task){
        Log.d(TAG, "postTask handler.hashCode() in use "+handler.hashCode());
        handler.post(task);
    }

    //можно хэндлер отдавать через get()
    public Handler getHandler() {
        return handler;
    }

    //работаем с элементом активити
    void setTextView(String text){
        //наверняка знаем, что метод setText у активити есть, так как она имплемент interface CallbackActivity
        activity.setText(text);
    }

    static interface CallbackActivity{
        //тут можно прописать какую-нибудь  полезную функцию, которая будет доступна в активити через implements CallbackActivitye
        void setText(String text);
    }

    //можно инициализировать поле CallbackActivity activity через сеттер родительского класса MyHandlerThread
    public void setActivity(CallbackActivity activity) {
        this.activity = activity;
    }
}
