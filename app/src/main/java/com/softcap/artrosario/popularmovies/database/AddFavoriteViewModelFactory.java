package com.softcap.artrosario.popularmovies.database;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class AddFavoriteViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private final FavoritesDatabase mDb;
    private final String mMovieId;

    public AddFavoriteViewModelFactory(FavoritesDatabase mDb, String mMovieId) {
        this.mDb = mDb;
        this.mMovieId = mMovieId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        //
        return (T) new AddFavoriteViewModel(mDb, mMovieId);
    }
}
