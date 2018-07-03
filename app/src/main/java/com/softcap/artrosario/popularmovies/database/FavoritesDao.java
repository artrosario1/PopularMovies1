package com.softcap.artrosario.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import com.softcap.artrosario.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FavoritesDao {

    @Query("SELECT * FROM Favorites ORDER BY updated_at")
    LiveData<List<Movie>> loadFavorites();

    @Insert
    void insertFavorite(Movie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(Movie movie);

    @Delete
    void deleteFavorite(Movie movie);

    /***
     *  Used @Query instead of @Delete because of added Date parameter when clicked
    * */
    @Query("SELECT * FROM Favorites WHERE id = :mId")
    LiveData<Movie> findMovieById(String mId);

    @Query("DELETE FROM Favorites WHERE id = :mId")
    abstract void deleteById(String mId);
}
