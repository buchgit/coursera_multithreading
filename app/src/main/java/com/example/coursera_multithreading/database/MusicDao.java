package com.example.coursera_multithreading.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
import java.util.UUID;

@Dao
public interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSongs(List<Song> songs);

    @Query("SELECT * from song")
    List<Song> getSongs();

    @Query("DELETE FROM song where id = :songId")
    void deleteSongById(int songId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbums(List<Album>albums);

    @Query("select * from album")
    List<Album> getAlbums();

    @Query("select * from album")
    Cursor getAlbumsCursor();

    @Query("select * from album where album.id= :albumId")
    Cursor getAlbumWithIdCursor(Integer albumId);

    @Query("delete from album where album.id = :albumId")
    int deleteAlbum(Integer albumId);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbumSong(List<AlbumSong>albumSongs);

    @Query("select * from album_song")
    List<AlbumSong>getAlbumSongs();

    @Query("select * from song inner join album_song on song.id = album_song.song_id where album_song.album_id =:albumId")
    List<Song> getSongsByAlbumId(Integer albumId);


}
