package com.example.coursera_multithreading;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListViewAdapter extends RecyclerView.Adapter{
    @NonNull
    @Override
    public ListObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListObjectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.bind(mListObjects.get(position));   //.bind  - нет такого метода
    }


    @Override
    public int getItemCount() {
        return 0;
    }
}
