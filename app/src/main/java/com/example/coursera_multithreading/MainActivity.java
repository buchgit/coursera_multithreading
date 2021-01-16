package com.example.coursera_multithreading;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/*
InputStream так и не заработал
 */

public class MainActivity extends AppCompatActivity {

    private MyTask myTask;
    private final String url = "http://i0.kym-cdn.com/entries/icons/mobile/000/013/564/doge.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonStart = findViewById(R.id.btn_start);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTask = new MyTask(MainActivity.this);
                myTask.execute(url);
            }
        });

        Button buttonCancel = findViewById(R.id.btn_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myTask!=null){
                    myTask.cancel(true);
                }
            }
        });
    }

    public ImageView getImageView (){
        return findViewById(R.id.imv);
    }

    public ProgressBar getProgressBar(){
        return findViewById(R.id.prb_main_activity);
    }

    private static class MyTask extends AsyncTask<String,Void, Bitmap> {

        private final String TAG = MyTask.class.getSimpleName()+ " ###== ";

        WeakReference<MainActivity> mainActivityWeakReference;

        MyTask(MainActivity activity){
            mainActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            MainActivity activity = (MainActivity) mainActivityWeakReference.get();
            if (activity!=null){
                activity.getProgressBar().setVisibility(View.VISIBLE);
            }

        }

//    @Override
//    protected Bitmap doInBackground (String... strings) {
//        String url = strings[0];
//
//        try {
//            TimeUnit.SECONDS.sleep(5);
//            if (isCancelled()){
//                return null;
//            }
//            InputStream inputStream = null;
//            inputStream = (InputStream) new URL(url).getContent();
//            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//            inputStream.close();
//            return bitmap;
//        } catch (Exception e) {
//            return null;
//        }
//    }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return getBitmap(strings[0]);
        }

        private Bitmap getBitmap(String url) {
            //final String url1 = "http://i0.kym-cdn.com/entries/icons/mobile/000/013/564/doge.jpg";
            try {

                TimeUnit.SECONDS.sleep(7);
                if (isCancelled()) {
                    return null;
                }
                Log.d(TAG, "getBitmap: 1 ###==");
                //InputStream is = (InputStream) new URL(url).getContent();
                InputStream is = (InputStream) new URL(url).getContent();
                Log.d(TAG, "getBitmap: 2 ###==");
                Bitmap d = BitmapFactory.decodeStream(is);
                is.close();

                return d;
            } catch (Exception e) {
                Log.d(TAG, "getBitmap: 3 ###==");
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            MainActivity activity = mainActivityWeakReference.get();
            if (activity!=null){
                Log.d(TAG, "onPostExecute: 4 ###==");
                activity.getImageView().setImageBitmap(bitmap);
                Toast.makeText(activity,"show image",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            MainActivity activity = mainActivityWeakReference.get();
            if (activity!=null){
                Log.d(TAG, "onCancelled: 5 ###==");
                activity.getProgressBar().setVisibility(View.INVISIBLE);
                Toast.makeText(activity,R.string.cancel,Toast.LENGTH_SHORT).show();
            }
        }
    }

}



