package com.example.coursera_multithreading;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import java.nio.charset.StandardCharsets;

/*
1. В манифесте разрешение, но лучше использовать runtime permissions,
2. Environment.getExternalStorageState() возвращает строку "mounted" - состояние внешнего хранилища
3. EditText метод onEditorAction - обработчик ввода строки после нажания галки или enter
 */

public class MainActivity extends AppCompatActivity {

    public static final String FILENAME = "myfile";
    public static final int REQUEST_CODE = 123;
    public static final int REQUEST_CODE1 = 123;
    private EditText mInput;
    private TextView mFromInternal;
    private TextView mFromExternal;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch mIsExternalSwitch;
    private Button mDelete;
    private String mText;
    private Button buttonSave;
    private Button buttonRead;
    private Button buttonSaveExt;
    private Button buttonReadExt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

//        mIsExternalSwitch.setEnabled(isExternalStorageAvailable());
//
//        mInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_DONE) {
//                    String text = v.getText().toString();
//                    mText = text;
//                    saveToFile(text, mIsExternalSwitch.isChecked());
//                    updateTextViews();
//                }
//                return false;
//            }
//        });
//
//        mDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                deleteFile(mIsExternalSwitch.isChecked());
//                updateTextViews();
//            }
//        });



        //updateTextViews();

    }

//    private void deleteFile(boolean isExternal) {
//        if (isExternal) {
//            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), FILENAME);
//            if (file.delete()) {
//                Toast.makeText(this, "deleted from external", Toast.LENGTH_SHORT).show();
//            }
//
//        } else {
//            deleteFile(FILENAME);
//            Toast.makeText(this, "deleted from internal", Toast.LENGTH_SHORT).show();
//        }
//
//    }

//    private void updateTextViews() {
//        mFromInternal.setText(readFromInternalFileIfOption());
//        mFromExternal.setText(readFromExternalFileIfOption());
//    }

//    private String readFromExternalFileIfOption() {
//        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), FILENAME);
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
//            StringBuilder stringBuilder = new StringBuilder();
//            String string;
//            while ((string = reader.readLine()) != null) {
//                stringBuilder.append(string).append("\n");
//            }
//
//            return stringBuilder.toString();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Can't read from file", Toast.LENGTH_SHORT).show();
//        }
//        return "";
//
//    }

    private void initUI() {
        mFromInternal = findViewById(R.id.tv_from_internal_file);
        mFromExternal = findViewById(R.id.tv_from_external_file);
        mIsExternalSwitch = findViewById(R.id.switch_is_external);
        mInput = findViewById(R.id.et_some_text);
        mDelete = findViewById(R.id.btn_delete_file);
        buttonSave = findViewById(R.id.btn_save);
        buttonRead = findViewById(R.id.btn_read);
        buttonSaveExt = findViewById(R.id.btn_save_ext);
        buttonReadExt = findViewById(R.id.btn_read_ext);
    }

//    private void saveToFile(String text, boolean isInExternal) {
//        if (isInExternal) {
//            saveToExternalFile(text);
//        } else {
//            saveToInternalFile(text);
//        }
//    }
//
//    private boolean isExternalStorageAvailable() {
//        String state = Environment.getExternalStorageState();
//        return state.equals(Environment.MEDIA_MOUNTED);
//    }
//
//    private String readFromInternalFileIfOption() {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput(FILENAME)))) {
//            StringBuilder stringBuilder = new StringBuilder();
//            String string;
//            while ((string = reader.readLine()) != null) {
//                stringBuilder.append(string).append("\n");
//            }
//
//            return stringBuilder.toString();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Can't read from file", Toast.LENGTH_SHORT).show();
//        }
//        return "";
//    }
//
//    private void saveToInternalFile(String text) {
//        try {
//            String textToWrite = text + "\n";
//            FileOutputStream outputStream = openFileOutput(FILENAME, Context.MODE_APPEND);
//            outputStream.write(textToWrite.getBytes());
//            outputStream.close();
//            Toast.makeText(this, "written to internal", Toast.LENGTH_SHORT).show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void saveToExternalFile(String text) {
//        if (isPermissionGranted()) {
//            try {
//                String textToWrite = text + "\n";
//                File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
//                FileOutputStream outputStream = new FileOutputStream(file, true);
//                outputStream.write(textToWrite.getBytes());
//                outputStream.close();
//                Toast.makeText(this, "written to external", Toast.LENGTH_SHORT).show();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
//        }
//
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode != REQUEST_CODE) return;
//        if (grantResults.length != 1) return;
//        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            String text = mText;
//            saveToFile(text, mIsExternalSwitch.isChecked());
//            updateTextViews();
//        }
//    }

    public void onClickInternal(View view) throws IOException {
        switch (view.getId()){
            case (R.id.btn_save):
                String text = "this is the text for internal file";
                String textIntoFile = text + "\n";
                FileOutputStream outputStream = openFileOutput(FILENAME,MODE_APPEND);
                outputStream.write(textIntoFile.getBytes(StandardCharsets.UTF_8));
                outputStream.close();
                Toast.makeText(this,"file"+ FILENAME + " is written",Toast.LENGTH_SHORT).show();
                break;
            case (R.id.btn_read):
                FileInputStream inputStream = openFileInput(FILENAME);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String s;
                StringBuilder stringBuilder = new StringBuilder();
                while ((s=bufferedReader.readLine())!=null){
                    stringBuilder.append(s).append("#"+"\t");
                }
                mFromInternal.setText(stringBuilder.toString());
                break;
            default:break;
        }



    }

    public void onClickExternal(View view) throws IOException {
        switch (view.getId()){
            case (R.id.btn_save_ext):
                if (!isPermissionGranted()) {
                    requestForPermission();
                    return;
                };
                writeToExternalFile();
                break;
            case (R.id.btn_read_ext):
                break;
            default:break;
        }
    }

    private void writeToExternalFile() throws IOException {
        String ext_mounted = Environment.getExternalStorageState();
        if (!ext_mounted.equals(Environment.MEDIA_MOUNTED)) return;
        File file = new File(Environment.getExternalStorageDirectory(), FILENAME);
        FileOutputStream outputStream = new FileOutputStream(file,true);

        String text = "this is the text for external file";
        String textIntoFile = text + "\n";

        outputStream.write(textIntoFile.getBytes(StandardCharsets.UTF_8));
        Toast.makeText(this,"text is written to external file",Toast.LENGTH_SHORT).show();
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
        if(requestCode!=REQUEST_CODE1)return;
        if (grantResults.length != 1) return;
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            try {
                writeToExternalFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isPermissionGranted(){
        return ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED;
    }
}



