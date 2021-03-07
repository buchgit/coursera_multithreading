package com.example.coursera_multithreading;

import android.Manifest;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

/*
1. DownloadManager  getSystemService(Context.DOWNLOAD_SERVICE)
2. register receiver (new BroadcastReceiver(),new IntentFilter(...))
3. new DownloadManager.Request
4. NotificationCompat.Builder + NotificationManager
 */

public class MainActivity extends AppCompatActivity {

    public static final int WRITE_EXTERNAL_FILES = 123;
    private Button button2;
    private EditText editText;
    private BroadcastReceiver broadcastReceiver;
    private String downloadSubPath;
    private DownloadManager downloadManager;
    private final ArrayList<Long> listOfRef = new ArrayList<>();
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.iv_main);

        button2 = findViewById(R.id.btn2_main);
        button2.setEnabled(false);

        editText = findViewById(R.id.et_main);
        //for example
        editText.setText("http://www.gadgetsaint.com/wp-content/uploads/2016/11/cropped-web_hi_res_512.png");
        //1
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        //2
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Long refId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                listOfRef.remove(refId);
                if (listOfRef.isEmpty()) {
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
                    builder.setContentTitle("load file...")
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentText("*>*>*>*>");

                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.notify(455, builder.build());

                    button2.setEnabled(true);
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        registerReceiver(broadcastReceiver, intentFilter);
        //3
        downloadSubPath = "/Downloads/sample.png";

    }

    private void downloadFile() {
        String stringUri = editText.getText().toString();
        if (!stringUri.isEmpty() && (stringUri.contains(".bmp") || stringUri.contains(".jpeg") || stringUri.contains(".png"))) {
            Uri downloadUri = Uri.parse(stringUri);

            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setTitle("Download pictures")
                    .setDescription("-Test downloading-")
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                    .setAllowedOverRoaming(false)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downloadSubPath);

            long referenceId = downloadManager.enqueue(request);
            listOfRef.add(referenceId);

        }
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1_main:
                if (isPermissionGranted()) {
                    downloadFile();
                } else {
                    requestForPermission();
                    return;
                }
                break;
            case R.id.btn2_main:
                imageView.setImageDrawable(Drawable.createFromPath(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+Environment.DIRECTORY_DOWNLOADS+downloadSubPath));
                break;
        }
    }

    private void requestForPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setMessage("Разрешение необходимо для записи во внешний файл.")
                    .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_FILES);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_FILES);
        }

    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != WRITE_EXTERNAL_FILES) return;
        if (grantResults.length != 1) return;
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            downloadFile();
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}

