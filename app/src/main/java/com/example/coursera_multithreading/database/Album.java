package com.example.coursera_multithreading.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Album {
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "release_date")
    private String releaseDate;

    public Album(int mId, String mName, String mReleaseDate) {
        this.id = mId;
        this.name = mName;
        this.releaseDate = mReleaseDate;
    }

    public Album() {
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.releaseDate = mReleaseDate;
    }
}
