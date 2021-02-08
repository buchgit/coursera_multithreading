package com.example.coursera_multithreading;

import android.media.Image;

public class ListObject {

    private String text;
    private int icon;

    public ListObject(String text, int icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public int getIcon() {
        return icon;
    }
}
