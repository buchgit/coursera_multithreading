package com.example.coursera_multithreading.mock;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coursera_multithreading.ContactsAdapter;
import com.example.coursera_multithreading.R;

public class MockHolder extends RecyclerView.ViewHolder {

    private TextView mName;
    private TextView mValue;

    public MockHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.tv_name);
        mValue = itemView.findViewById(R.id.tv_value);
    }

    public void bind(Mock mock) {
        mName.setText(mock.getmName());
        mValue.setText(mock.getmValue());
    }

    public void setListener(final ContactsAdapter.onItemClickListener listener) {
        //забавная хрень. itemView передается в конструктор, поэтому доступна и здесь.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(); //вызываем метод нашего листенера. Релизация в MainActivity
            }
        });
    }
}
