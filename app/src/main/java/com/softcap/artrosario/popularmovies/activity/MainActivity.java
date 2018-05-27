package com.softcap.artrosario.popularmovies.activity;



import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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

import com.softcap.artrosario.popularmovies.BuildConfig;
import com.softcap.artrosario.popularmovies.R;
import com.softcap.artrosario.popularmovies.adapter.MoviesAdapter;
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
    private ArrayList<Movie> movies;

    //insert API key here
    private final static String API_KEY = "API_KEY";

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
        }else{
            movies = savedInstanceState.getParcelableArrayList("MoviesArray");
            recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.movie_item, getApplicationContext(), mMoviesHandler));

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("MoviesArray", movies);
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
                    movies = Objects.requireNonNull(response.body()).getResults();
                }
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.movie_item, getApplicationContext(),mMoviesHandler));
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                Log.e(TAG, t.toString());
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(String titleForMovie , String descForMovie, String posterUrl,
                        String backdropUrl, View view, String dateForMovie, String ratingForMovie )
    {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentSegue = new Intent(context, destinationClass);
        String transitionName = getString(R.string.transitionName);

        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                view, transitionName);
        Bundle extras = new Bundle();
        extras.putString(getString(R.string.EXTRA_TITLE) , titleForMovie);
        extras.putString(getString(R.string.EXTRA_DESC), descForMovie);
        extras.putString(getString(R.string.EXTRA_POSTER), posterUrl);
        extras.putString(getString(R.string.EXTRA_BACKDROP), backdropUrl);
        extras.putString(getString(R.string.EXTRA_DATE), dateForMovie);
        extras.putString(getString(R.string.EXTRA_RATING), ratingForMovie);

        intentSegue.putExtras(extras);

        ActivityCompat.startActivity(this, intentSegue, options.toBundle());
    }
}
