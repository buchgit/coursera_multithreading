package com.example.coursera_multithreading;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/*
реализация нажатий на элементы RecyclerView
 */

public class MainActivity extends AppCompatActivity implements ContactsAdapter.onItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState==null){ //при повороте фрагмент сохраняется, поэтому создаем только при запуске приложения
            getSupportFragmentManager().beginTransaction().replace(R.id.fr_main,RecyclerFragment.newInstance()).commit();
        }

    }

    @Override
    public void onItemClick(String id) {

        //добавляем бизнес-логику, к примеру набор телефона
        //так как в предыдущем контент провайдере нет номеров телефонов
        //то вызываем другой контент провайдер
        Cursor cursor = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},
                ContactsContract.CommonDataKinds.Phone._ID + "=? And " + ContactsContract.CommonDataKinds.Phone.TYPE + "=?",
                new String[]{id, String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)},
                null
        );

        if (cursor!=null && cursor.moveToFirst()){
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            cursor.close(); //!!!!!!! закрыть курсор

            Intent intent = new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:"+number));
            startActivity(intent);
        }

        Toast.makeText(this, "Обработка нажатий на элемент RecyclerView id: "+ id, Toast.LENGTH_SHORT).show();
    }
}



