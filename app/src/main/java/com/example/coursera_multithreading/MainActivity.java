package com.example.coursera_multithreading;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.*;

/*
1. В манифесте разрешение, но лучше использовать runtime permissions,
2. Environment.getExternalStorageState() возвращает строку "mounted" - состояние внешнего хранилища
3. EditText метод onEditorAction - обработчик ввода строки после нажания галки или enter
4.
5.
 */

public class MainActivity extends AppCompatActivity {

    public static final String FILENAME = "myfile";
    public static final int REQUEST_CODE = 123;
    private EditText mInput;
    private TextView mFromInternal;
    private TextView mFromExternal;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch mIsExternalSwitch;
    private Button mDelete;
    private String mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        mIsExternalSwitch.setEnabled(isExternalStorageAvailable());

        mInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String text = v.getText().toString();
                    mText = text;
                    saveToFile(text, mIsExternalSwitch.isChecked());
                    updateTextViews();
                }
                return false;
            }
        });

        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFile(mIsExternalSwitch.isChecked());
                updateTextViews();
            }
        });

        updateTextViews();

    }

    private void deleteFile(boolean isExternal) {
        if (isExternal) {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), FILENAME);
            if (file.delete()) {
                Toast.makeText(this, "deleted from external", Toast.LENGTH_SHORT).show();
            }

        } else {
            deleteFile(FILENAME);
            Toast.makeText(this, "deleted from internal", Toast.LENGTH_SHORT).show();
        }

    }

    private void updateTextViews() {
        mFromInternal.setText(readFromInternalFileIfOption());
        mFromExternal.setText(readFromExternalFileIfOption());
    }

    private String readFromExternalFileIfOption() {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), FILENAME);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            StringBuilder stringBuilder = new StringBuilder();
            String string;
            while ((string = reader.readLine()) != null) {
                stringBuilder.append(string).append("\n");
            }

            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Can't read from file", Toast.LENGTH_SHORT).show();
        }
        return "";

    }

    private void initUI() {
        mFromInternal = findViewById(R.id.tv_from_internal_file);
        mFromExternal = findViewById(R.id.tv_from_external_file);
        mIsExternalSwitch = findViewById(R.id.switch_is_external);
        mInput = findViewById(R.id.et_some_text);
        mDelete = findViewById(R.id.btn_delete_file);
    }

    private void saveToFile(String text, boolean isInExternal) {
        if (isInExternal) {
            saveToExternalFile(text);
        } else {
            saveToInternalFile(text);
        }
    }

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    private String readFromInternalFileIfOption() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)))) {
            StringBuilder stringBuilder = new StringBuilder();
            String string;
            while ((string = reader.readLine()) != null) {
                stringBuilder.append(string).append("\n");
            }

            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Can't read from file", Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    private void saveToInternalFile(String text) {
        try {
            String textToWrite = text + "\n";
            FileOutputStream outputStream = openFileOutput(FILENAME, Context.MODE_APPEND);
            outputStream.write(textToWrite.getBytes());
            outputStream.close();
            Toast.makeText(this, "written to internal", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveToExternalFile(String text) {
        if (isPermissionGranted()) {
            try {
                String textToWrite = text + "\n";
                File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
                FileOutputStream outputStream = new FileOutputStream(file, true);
                outputStream.write(textToWrite.getBytes());
                outputStream.close();
                Toast.makeText(this, "written to external", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
        }

    }

    private boolean isPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != REQUEST_CODE) return;
        if (grantResults.length != 1) return;
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            String text = mText;
            saveToFile(text, mIsExternalSwitch.isChecked());
            updateTextViews();
        }
    }
}



