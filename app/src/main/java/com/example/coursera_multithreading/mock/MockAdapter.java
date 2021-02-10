package com.example.coursera_multithreading.mock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.coursera_multithreading.R;

import java.util.ArrayList;
import java.util.List;

public class MockAdapter extends RecyclerView.Adapter<MockHolder> {

    private final List<Mock> mocks = new ArrayList<>();

    @NonNull
    @Override
    public MockHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflater делает View из xml - разметки
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MockHolder(inflater.inflate(R.layout.mock_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MockHolder holder, int position) {
        holder.bind(mocks.get(position));
    }

    @Override
    public int getItemCount() {
        return mocks.size();
    }

    public void addData(List<Mock> list, boolean refresh) {
        if (refresh) {
            mocks.clear();
        }
        mocks.addAll(list);
        notifyDataSetChanged();//уведомить систему, что данные изменились
    }
}
