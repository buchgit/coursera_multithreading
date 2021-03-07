package com.example.coursera_multithreading.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "album_song", foreignKeys = {
        @ForeignKey(entity = Album.class, parentColumns = "id", childColumns = "album_id"),
        @ForeignKey(entity = Song.class, parentColumns = "id", childColumns = "song_id")
})
public class AlbumSong {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "album_id")
    private int albumId;

    @ColumnInfo(name = "song_id")
    private int songId;

    public AlbumSong() {
    }

    public AlbumSong(int id, int mAlbumId, int mSongId) {
        this.id = id;
        this.albumId = mAlbumId;
        this.songId = mSongId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int mAlbumId) {
        this.albumId = mAlbumId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int mSongId) {
        this.songId = mSongId;
    }
}
