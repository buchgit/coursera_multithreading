package com.example.coursera_multithreading;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

/*
1. ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
2. если пользователь нажал Deny
shouldShowRequestPermissionRationale() будет выдавать false, а в
onRequestPermissionsResult() будет получен результат PERMISSION_DENIED.
3.
этапы:
проверка, есть ли разрешение
запрос разрешения
объяснение (Rationale), зачем разрешение нужно
обработка полученного ответа на запрос разрешения
4.
5.
 */

public class MainActivity extends AppCompatActivity {

    private EditText mInput;
    private Button mWrite;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInput = findViewById(R.id.et_input);
        mWrite = findViewById(R.id.btn_write_file);

        mWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mInput.getText().toString();
                writeToFile(text);
            }


        });

    }

    private void writeToFile(String text) {
        if (TextUtils.isEmpty(text)){
            Toast.makeText(this,"text is empty",Toast.LENGTH_SHORT).show();
        }else{
            writeFile();
        }
    }

    private void writeFile() {
        if (isPermissionGranted()){
            writeOk();
        }else{
            requestWritePermissions();
        }
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED;
    }

    private void requestWritePermissions() {
    }

    private void writeOk() {
    }
}



