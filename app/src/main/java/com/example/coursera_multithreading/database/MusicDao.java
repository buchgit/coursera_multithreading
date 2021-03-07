package com.example.coursera_multithreading.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSongs(List<Song> songs);

    @Query("SELECT * from song")
    List<Song> getSongs();

    @Query("DELETE FROM song where id = :songId")
    void deleteSongById(int songId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbum(List<Album>albums);

    @Query("select * from album")
    List<Album> getAlbums();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbumSong(List<AlbumSong>albumSongs);

    @Query("select * from album_song")
    List<AlbumSong>getAlbumSongs();

    @Query("select * from song inner join album_song on song.id = album_song.song_id where album_song.album_id =:albumId")
    List<Song> getSongsByAlbumId(Integer albumId);


}
