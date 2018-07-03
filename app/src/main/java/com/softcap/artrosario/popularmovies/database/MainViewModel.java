package com.softcap.artrosario.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.softcap.artrosario.popularmovies.model.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<Movie>> favorites;
    public MainViewModel(@NonNull Application application) {
        super(application);
        FavoritesDatabase database = FavoritesDatabase.getsInstance(this.getApplication());
        Log.d("Database: ","receiving viewmodel");

        favorites= database.favoritesDao().loadFavorites();
    }

    public LiveData<List<Movie>> getFavorites() {

        return favorites;
    }
}
