package com.example.coursera_multithreading;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements ImageProcessThread.Callback, MyHandlerThread.CallbackActivity {

    private final String TAG = MainActivity.class.getSimpleName() + " ###== ";

    //весь код ниже выполняется в мейнтреде
    private TextView textView;
    private ProgressBar mProgressBar;
    private ImageProcessThread mImageProcessThread;
    private MyHandlerThread myHandlerThread;
    public static final int MAX = 30;
    private Button btnMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button performBtn = findViewById(R.id.btn_perform);
        textView = findViewById(R.id.tv_main);
        mProgressBar = findViewById(R.id.progress);
        mProgressBar.setMax(MAX);
        btnMessage = findViewById(R.id.btn_message);

        //создаем новый экземпляр фонового потока потока
        mImageProcessThread = new ImageProcessThread("Background ");
        //запускаем поток и инициализируем Looper
        mImageProcessThread.start();
        mImageProcessThread.getLooper();  // -> вызовется onLooperPrepared()
        mImageProcessThread.setCallback(this); //because MainActivity implements Callback interface
        //теперь в фоновом потоке будет крутиться лупер и ждать задач
        performBtn.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                //выдергиваем Bitmap из ImageView и скармливаем в наш поток,
                //просто вызывая метод на нем
                mImageProcessThread.performOperation();


                //здесь поработаем с потоком MyHandlerThread
                myHandlerThread = new MyHandlerThread("my_tread");
                myHandlerThread.start();//запускаем, иначе ни хэндлера, ни лупера не получим
                myHandlerThread.prepareHandler();//создали хэндлер с привязанным к нему лупером
                myHandlerThread.postTask(hardTask);//передаем задачу в фоновый поток
                myHandlerThread.setActivity(MainActivity.this);//можно передеть текущую активити в поток, для работы с ее элементами
                myHandlerThread.setTextView("Hi, people!");
            }

        });
    }

    // методы колбека из интерфейса

    @Override
    public void sendProgress(int progress) {
        mProgressBar.setProgress(progress);
    }

    @Override
    public void onCompleted(String message) {
        textView.setText(message);
    }

    @Override
    protected void onDestroy() {
        //гасим фоновый поток, не мусорим
        mImageProcessThread.quit();
        //гасим поток myHandlerThread
        myHandlerThread.quit();
        super.onDestroy();
    }

    Runnable hardTask = new Runnable() {
        @Override
        public void run() {
            for (int i=0;i<10;i++){
                try {
                    TimeUnit.MILLISECONDS.sleep(500);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "job thread is: "+ Thread.currentThread().getName());
        }
    };

    @Override
    public void setText(String text) {
        textView.setText(text);
    }

    @Override
    public int getProgressMaxValue() {
        return MAX;
    }

    public void onClickMessage(View view){
        if (view.getId() == R.id.btn_message){
            setText("fine,fine,fine...button is not blocked");
        }
    }
}