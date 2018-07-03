package com.softcap.artrosario.popularmovies.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.softcap.artrosario.popularmovies.model.Movie;

import java.util.List;

public class AddFavoriteViewModel extends ViewModel {
    private LiveData<Movie> favoriteMovie;

    public AddFavoriteViewModel(FavoritesDatabase database, String movieId){
        favoriteMovie = database.favoritesDao().findMovieById(movieId);
    }

    public LiveData<Movie> getFavoriteMovie() {
        return favoriteMovie;
    }
}
