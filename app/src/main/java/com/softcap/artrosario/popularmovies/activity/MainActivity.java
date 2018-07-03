package com.softcap.artrosario.popularmovies.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.softcap.artrosario.popularmovies.R;
import com.softcap.artrosario.popularmovies.adapter.MoviesAdapter;
import com.softcap.artrosario.popularmovies.database.FavoritesDatabase;
import com.softcap.artrosario.popularmovies.database.MainViewModel;
import com.softcap.artrosario.popularmovies.model.Movie;
import com.softcap.artrosario.popularmovies.model.MovieResponse;
import com.softcap.artrosario.popularmovies.rest.MovieApiService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String BASE_URL = "http://api.themoviedb.org/";
    private static Retrofit retrofit;
    private RecyclerView recyclerView;
    private MoviesAdapter.MovieAdapterOnClickHandler mMoviesHandler;
    private ArrayList<Movie> moviesArrayList;


    private FavoritesDatabase mDb;
    private boolean isFavorite;


    //TODO: insert API key here
    private final static String API_KEY = "INSERT API KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mMoviesHandler = this;


        if(savedInstanceState == null || !savedInstanceState.containsKey("MoviesArray") ){
            connectAndGetData(getString(R.string.query_top_rated));
            mDb = FavoritesDatabase.getsInstance(getApplicationContext());
        }

        else{
            moviesArrayList = savedInstanceState.getParcelableArrayList("MoviesArray");
            recyclerView.setAdapter(new MoviesAdapter(moviesArrayList, R.layout.movie_item, getApplicationContext(), mMoviesHandler));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("MoviesArray", moviesArrayList);
        super.onSaveInstanceState(outState);
    }

    private void connectAndGetData(String category){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

        Call<MovieResponse> call = movieApiService.getMovies(category, API_KEY);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    moviesArrayList = Objects.requireNonNull(response.body()).getResults();
                }

                recyclerView.setAdapter(new MoviesAdapter(moviesArrayList, R.layout.movie_item, getApplicationContext(),mMoviesHandler));
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }


    public void setupViewModel(){
        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
            viewModel.getFavorites().observe(this, new Observer<List<Movie>>() {
                @Override

                public void onChanged(@Nullable List<Movie> movies) {
                    Log.d("Message: ", "Receiving list from LiveData inViewModel");
                    moviesArrayList = (ArrayList) movies;
                    recyclerView.setAdapter(new MoviesAdapter(moviesArrayList, R.layout.movie_item, getApplicationContext(), mMoviesHandler));
                }
            });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();

        if(menuItemSelected == R.id.item_popular_movies){
            connectAndGetData(getString(R.string.query_popular));
        } else if(menuItemSelected == R.id.item_top_rated_movies){
            connectAndGetData(getString(R.string.query_top_rated));
        } else if(menuItemSelected == R.id.item_favorite_movies){
            setupViewModel();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(View view, Movie movie) {
        Context context = getApplicationContext();
        Class destinationClass = DetailActivity.class;
        Intent intent = new Intent(context, destinationClass);

        String transitionName = getString(R.string.transitionName);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                view, transitionName);
        Movie thisMovie = movie;
        final String id = thisMovie.getId();

        intent.putExtra("ThisMovie", thisMovie);
        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

}
