package com.example.coursera_multithreading;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coursera_multithreading.mock.Mock;
import com.example.coursera_multithreading.mock.MockHolder;

/*
адаптер не заработал, нет permitions
 */

public class ContactsAdapter extends RecyclerView.Adapter<MockHolder> {

    private Cursor mCursor;

    @NonNull
    @Override
    public MockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MockHolder(inflater.inflate(R.layout.mock_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MockHolder holder, int position) {
        if (mCursor.moveToPosition(position)){ //если успешна установка курсора в позицию, то выдергивам данные
            String name = mCursor.getString(mCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            int id = mCursor.getInt(mCursor.getColumnIndex(ContactsContract.Contacts._ID));
            holder.bind(new Mock(name,id));
        }
    }

    @Override
    public int getItemCount() {
        return mCursor!=null?mCursor.getCount():0;
    }

    public void swapCursor(Cursor cursor){ //"new" cursor
        if (cursor!=null && cursor != mCursor){
            if (mCursor!=null)mCursor.close(); //close "old" cursor
            mCursor = cursor;
            notifyDataSetChanged(); //send message to system about changed data
        }
    }
}
