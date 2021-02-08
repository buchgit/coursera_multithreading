package com.example.coursera_multithreading;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.collection.CircularArray;
import androidx.recyclerview.widget.RecyclerView;

public class ListViewAdapter extends RecyclerView.Adapter{

    private CircularArray<ListObject> mListObjects;

    public void setmListObjects(CircularArray<ListObject> mListObjects) {
        this.mListObjects = mListObjects;
    }

    @NonNull
    @Override
    public ListObjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ListObjectViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListObjectViewHolder myHolder = (ListObjectViewHolder)holder;

        myHolder.bind(mListObjects.get(position));
    }


    @Override
    public int getItemCount() {
        return mListObjects.size();
    }
}
