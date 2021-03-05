package com.example.coursera_multithreading;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.*;
import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {

    public static final String FILENAME = "myfile";
    public static final int REQUEST_CODE = 123;
    public static final int REQUEST_CODE1 = 123;
    private TextView mFromInternal;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch mIsExternalSwitch;
    private String mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

    }

    private void initUI() {
        mFromInternal = findViewById(R.id.tv_from_internal_file);
        mIsExternalSwitch = findViewById(R.id.switch_is_external);
    }

    public void onClickInternal(View view) throws IOException {
        switch (view.getId()) {
            case (R.id.btn_save):
                String text = "this is the text for internal file";
                String textIntoFile = text + "\n";
                FileOutputStream outputStream = openFileOutput(FILENAME, MODE_APPEND);
                outputStream.write(textIntoFile.getBytes(StandardCharsets.UTF_8));
                outputStream.close();
                Toast.makeText(this, "file" + FILENAME + " is written", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.btn_read):
                FileInputStream inputStream = openFileInput(FILENAME);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s;
                StringBuilder stringBuilder = new StringBuilder();
                while ((s = bufferedReader.readLine()) != null) {
                    stringBuilder.append(s).append("#" + "\t");
                }
                mFromInternal.setText(stringBuilder.toString());
                break;
            default:
                break;
        }
    }

    public void onClickExternal(View view) throws IOException {

        String ext_mounted = Environment.getExternalStorageState();
        if (!ext_mounted.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "Нет внешних накопителей", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (view.getId()) {
            case (R.id.btn_save_ext):
                if (!isPermissionGranted()) {
                    requestForPermission();
                    return;
                }
                ;
                writeToExternalFile();
                break;
            case (R.id.btn_read_ext):
                if (!isPermissionGranted()) {
                    requestForPermission();
                    return;
                }
                ;
                readFromExternalFile(FILENAME);
                break;
            default:
                break;
        }
    }

    private void writeToExternalFile() throws IOException {

        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        FileOutputStream outputStream = new FileOutputStream(file, true);

        String text = "this is the text for external file" + System.currentTimeMillis();
        String textIntoFile = text + "\n";

        outputStream.write(textIntoFile.getBytes(StandardCharsets.UTF_8));
        Toast.makeText(this, "is written to external file", Toast.LENGTH_SHORT).show();
    }

    private void readFromExternalFile(String filename) throws IOException {
        String path = Environment.getExternalStorageDirectory().getPath();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path + "/" + filename));
        String s;
        StringBuilder builder = new StringBuilder();
        while ((s = bufferedReader.readLine()) != null) {
            builder.append(s).append("\n");
        }
        mFromInternal.setText(builder.toString());
    }

    public void requestForPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setMessage("Необходимо разрешение для записи файлов на внешний носитель")
                    .setPositiveButton("Разрешить", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE1);
                        }
                    })
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != REQUEST_CODE1) return;
        if (grantResults.length != 1) return;
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                writeToExternalFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public void onDelete(View view) {
        if (!isPermissionGranted()) {
            requestForPermission();
            return;
        }
        ;
        if (mIsExternalSwitch.isChecked()) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), FILENAME);
            if (file.delete()) {
                Toast.makeText(this, "external file is deleted", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (deleteFile(FILENAME)) {
                Toast.makeText(this, "internal file is deleted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}



