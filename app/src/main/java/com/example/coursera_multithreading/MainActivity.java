package com.example.coursera_multithreading;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
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
    private TextView textView2;
    private ProgressBar mProgressBar;
    private ImageProcessThread mImageProcessThread;
    private MyHandlerThread myHandlerThread;
    public static final int MAX = 100;
    private Button btnMessage;
    private MyHandlerThread myHandlerThread_2;
    private LooperThread looperThread;

    public static final int MESSAGE_1 = 0;
    public static final int MESSAGE_2 = 1;

    public static final String PROGRESS_VALUE = "progress bar value";//ключ для сохранения значения прогресса в RetainedFragment
    public static final String TEXT_VIEW_TEXT = "text of textView";
    public static final String TEXT_VIEW_TEXT_2 = "text on textView 2";

    private RetainedFragment retainedFragment;//для сохранения данных при уничтожении активити

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button performBtn = findViewById(R.id.btn_perform);
        textView = findViewById(R.id.tv_main);
        textView2 = findViewById(R.id.tv_main_2);
        mProgressBar = findViewById(R.id.progress);
        mProgressBar.setMax(MAX);
        btnMessage = findViewById(R.id.btn_message);

        startRetainedFrag();//запускаем/восстанавливаем RetainedFragment для/или сохраненными данными

        String text = (String)retainedFragment.getObject(TEXT_VIEW_TEXT);
        String text2 = (String)retainedFragment.getObject(TEXT_VIEW_TEXT_2);
        Integer progress = (Integer)retainedFragment.getObject(PROGRESS_VALUE);

        if (text != null) {
            textView.setText(text);
        }
        if (text2 != null) {
            textView2.setText(text2);
        }
        if (progress != null) {
            mProgressBar.setProgress(progress);
        }

        //создаем/восстанавливаем фоновые потоки
        recoveryThreads();
//
//        //создаем новый экземпляр фонового потока
//        mImageProcessThread = new ImageProcessThread("Background ");
//        //запускаем поток и инициализируем Looper
//        mImageProcessThread.start();
//        mImageProcessThread.getLooper();  // -> вызовется onLooperPrepared()
//        mImageProcessThread.setCallback(this); //because MainActivity implements Callback interface

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
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //myHandlerThread.prepareHandler();//создали хэндлер с привязанным к нему лупером
                myHandlerThread.postTask(hardTask);//передаем задачу в фоновый поток

                myHandlerThread.prepareHandler();
                myHandlerThread.setActivity(MainActivity.this);//можно передеть текущую активити в поток, для работы с ее элементами
                myHandlerThread.setTextView("Hi, people!");

                //даем паузу
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //создаем хэндлер через собственную функцию getHandler()
                myHandlerThread.getHandler().obtainMessage(MyHandlerThread.HANDLER_TAG).sendToTarget();

//                MyHandlerThread myThread = new MyHandlerThread(TAG);
//                Handler h = myThread.getThreadHandler();                      //почему метод  getThreadHandler() недоступен?

                //здесь из ui- потока закинем задачу в другой фоновый поток
                myHandlerThread_2 = new MyHandlerThread("my_thread_2");
                myHandlerThread_2.start();
                Handler handler = new Handler(myHandlerThread_2.getLooper());//получили хэндлер, привязанный к луперу фонового потока
                handler.post(hardTask);

                //use LooperThread class
                looperThread = new LooperThread();
                looperThread.start();
                looperThread.setActivity(MainActivity.this);
                //без задержки хэндлер не отрабатывает
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Handler looperThreadHandler = looperThread.getHandler();
                looperThreadHandler.obtainMessage(LooperThread.LOOPER_THREAD_MESSAGE,"hello from LooperThread").sendToTarget();

                //без задержки не отрабатывает
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //из главной активити посылаем сообщение в фоновый поток и там исполняем
                Message msg = new Message();
                msg.what = MESSAGE_1;
                looperThreadHandler.obtainMessage(MESSAGE_1,"hello from MainActivity by LooperThread").sendToTarget();

                //запускаем выполнение в определенное время
                Message msg2 = new Message();
                msg2.what = MESSAGE_2;
                msg2.obj = "is done MESSAGE_2";

                looperThreadHandler.sendMessageAtTime(msg2,2000);

                //run with UI thread
                runOnUiThread(hardTask);

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
        String tname = mImageProcessThread.getName();
        //mImageProcessThread.quit();
        Log.d(TAG, "onDestroy: quit: " + tname);
        //гасим поток myHandlerThread
        tname = myHandlerThread.getName();
        //myHandlerThread.quit();
        Log.d(TAG, "onDestroy: quit: " + tname);
        //myHandlerThread_2.quit();

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        retainedFragment.putObject(PROGRESS_VALUE,mProgressBar.getProgress());
        retainedFragment.putObject(TEXT_VIEW_TEXT,textView.getText().toString());
        retainedFragment.putObject(ImageProcessThread.TAG,mImageProcessThread);
        retainedFragment.putObject(MyHandlerThread.TAG,myHandlerThread);
        retainedFragment.putObject(LooperThread.TAG,looperThread);
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
            Log.d(TAG, "hardTask is done by thread: "+Thread.currentThread().getName() + "; hashCode: " + Thread.currentThread().hashCode());
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
            setText("fine...button is not blocked");
        }
    }

    private void startRetainedFrag (){
        //RetainedFragment retainedFragment = (RetainedFragment) getFragmentManager().findFragmentByTag(RetainedFragment.TAG);
        retainedFragment = (RetainedFragment) getFragmentManager().findFragmentByTag(RetainedFragment.TAG);
        if (retainedFragment==null){
            retainedFragment = new RetainedFragment();
            getFragmentManager().beginTransaction().add(retainedFragment,RetainedFragment.TAG).commit();
        }
    }

    private void recoveryThreads(){
        mImageProcessThread = (ImageProcessThread) retainedFragment.getObject(ImageProcessThread.TAG);
        if (mImageProcessThread==null) {

            //создаем новый экземпляр фонового потока
            mImageProcessThread = new ImageProcessThread("Background ");
            //запускаем поток и инициализируем Looper
            mImageProcessThread.start();
            mImageProcessThread.getLooper();  // -> вызовется onLooperPrepared()
        }
        mImageProcessThread.setCallback(this); //because MainActivity implements Callback interface
        myHandlerThread = (MyHandlerThread) retainedFragment.getObject(MyHandlerThread.TAG);
        looperThread = (LooperThread) retainedFragment.getObject(LooperThread.TAG);
        if (looperThread != null) {
            looperThread.setActivity(this);
        }
    }

}