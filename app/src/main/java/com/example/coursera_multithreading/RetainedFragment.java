package com.example.coursera_multithreading;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;


import java.util.HashMap;
import java.util.Map;

public class RetainedFragment extends Fragment {
    public static final String TAG = RetainedFragment.class.getSimpleName() + " ###== ";
    private Map<String,Object> map = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void putObject(String key, Object obj){
        map.put(key,obj);
        Log.d(TAG, "object is saved into map");
    }

    public Object getObject(String key){
        Log.d(TAG, "get object from the map");
        return map.get(key);
    }
}
