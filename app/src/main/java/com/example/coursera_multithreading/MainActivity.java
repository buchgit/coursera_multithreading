package com.example.coursera_multithreading;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/*
Сохраняем данные в Bundle, при смене активити достаем из Bundle
Если компонентам присвоен id (для TextView, кнопок и т.п.), то о сохранении заботиться система, ничего делать не нужно //ЭТО НЕ РАБОТАЕТ
Если сохранить нужно свои объекты, то придется заботиться самому

ВАЖНО: нужно использовать метод с ОДНИМ параметром
protected void onSaveInstanceState(@NonNull Bundle outState) {...}
так как есть похожий метод с ДВУМЯ параметрами
public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {...}
, с ним не будет работать сохранение при смене активити (например, повороте)

Сохранение ссылки на объект, которую можно передать в новую активити
 */

public class MainActivity extends AppCompatActivity  {

    private final String TAG = MainActivity.class.getSimpleName() + " ###== ";

    public final String MY_OBJECT = "my object";

    Button buttonPerform;
    Button buttonMessage;

    TextView textView;
    ProgressBar progressBar;

    MyObject myObject;
    MyObject myObject2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myObject = new MyObject();

        if (myObject2 == null) {
            myObject2 = (MyObject) getLastCustomNonConfigurationInstance();
            if (myObject2 == null) {
                myObject2 = new MyObject();
                Log.d(TAG, " new myObject 2 hashCode" + myObject2.hashCode());
            }else {
                Log.d(TAG, " restored myObject 2 hashCode" + myObject2.hashCode());
            }
        }

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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "1/onSaveInstanceState: ");
        outState.putSerializable(MY_OBJECT, myObject);
        super.onSaveInstanceState(outState);
        Log.d(TAG, "2/onSaveInstanceState: ");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d(TAG, "1/onRestoreInstanceState: ");
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState!=null){
            myObject = (MyObject) savedInstanceState.getSerializable(MY_OBJECT);
            textView.setText(myObject.TAG);
            Log.d(TAG, "2/onRestoreInstanceState: ");
        }
        Log.d(TAG, "3/onRestoreInstanceState: ");
    }

    @Nullable
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return myObject2;
    }



}