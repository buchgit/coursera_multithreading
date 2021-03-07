package com.example.coursera_multithreading.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Song {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "duration")
    private String duration;

    public Song() {
    }

    public Song(int mId, String mName, String mDuration) {
        this.id = mId;
        this.name = mName;
        this.duration = mDuration;
    }

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        this.id = mId;
    }

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        this.name = mName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String mDuration) {
        this.duration = mDuration;
    }
}
