package com.example.coursera_multithreading;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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

    public static final int REQUEST_CODE = 123;//уникальный код запроса на конкретное разрешение
    private EditText mInput;
    private Button mWrite;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(this, "text is empty", Toast.LENGTH_SHORT).show();
        } else {
            writeFile(text);
        }
    }

    private void writeFile(String text) {
        if (isPermissionGranted()) {
            writeOk(text);
        } else {
            requestWritePermissions();
        }
    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestWritePermissions() {
        //нужно ли показывать объяснение?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setMessage("Без разрешения невозможна запись в файл")
                    .setPositiveButton("Agree", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                        }
                    }).show();
        } else {//делаем запрос на разрешение
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }
    }

    private void writeOk(String text) {
        Toast.makeText(this, text + " - writing to file ...", Toast.LENGTH_SHORT).show();
    }

    //обработка ответа на запрос разрешения на запись во внешний файл
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != REQUEST_CODE) return;
        if (grantResults.length != 1) return;
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {//user does not give permission
            new AlertDialog.Builder(this)
                    .setMessage("Вы отказали в разрешении на запись файла. Можете установить позже в настройках приложения.")
                    .setPositiveButton("Понятно", null)
                    .show();
        } else {//permission is granted
            String text = mInput.getText().toString();
            writeToFile(text);
        }
    }
}



