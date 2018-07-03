package com.softcap.artrosario.popularmovies.activity;


import android.annotation.TargetApi;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.softcap.artrosario.popularmovies.R;
import com.softcap.artrosario.popularmovies.adapter.ReviewsAdapter;
import com.softcap.artrosario.popularmovies.adapter.TrailersAdapter;
import com.softcap.artrosario.popularmovies.database.AddFavoriteViewModel;
import com.softcap.artrosario.popularmovies.database.AddFavoriteViewModelFactory;
import com.softcap.artrosario.popularmovies.database.AppExecutors;
import com.softcap.artrosario.popularmovies.database.FavoritesDatabase;
import com.softcap.artrosario.popularmovies.model.Movie;
import com.softcap.artrosario.popularmovies.model.Review;
import com.softcap.artrosario.popularmovies.model.Trailer;
import com.softcap.artrosario.popularmovies.rest.MovieApiService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity implements TrailersAdapter.TrailerAdapterOnClickHandler{

    private final static String API_KEY = "16ea02a5778193995fc7bd10805a4827";
    private static final String BASE_URL = "http://api.themoviedb.org/";
    private static Retrofit retrofit;
    private RecyclerView trailers_rv;
    private RecyclerView reviews_rv;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView dateTextView;
    private TextView ratingTextView;
    private ImageView backgroundImageView;
    private ImageView horizontalImageView;
    private ArrayList<Trailer> trailersList;
    private ArrayList<Review> reviewsList;
    private FloatingActionButton favoritesButton;
    private TrailersAdapter.TrailerAdapterOnClickHandler mTrailersHandler;
    private static final String BACKGROUND_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342/";
    private RatingBar ratingbar;

    public volatile boolean isFavorite;
    private FavoritesDatabase mDb;

    private String mTitle;
    private String mDesc;
    private String mDate;
    private String mRating;
    private String mPosterUrl;
    private String mBackdropUrl;
    private String mId;

    private Movie thisMovie;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDb = FavoritesDatabase.getsInstance(getApplicationContext());

        titleTextView = findViewById(R.id.tv_movie_title);
        descriptionTextView = findViewById(R.id.tv_movie_desc);
        dateTextView =  findViewById(R.id.tv_movie_date);
        ratingTextView = findViewById(R.id.tv_movie_rating);
        backgroundImageView = findViewById(R.id.movieBackground);
        horizontalImageView = findViewById(R.id.movieBackdrop);
        favoritesButton = findViewById(R.id.favoritesButton);
        ratingbar = findViewById(R.id.avgRatingBar);

        //Adds animation to move imageView to the left
        horizontalImageView.setTranslationX(1500);

        //Trailers
        trailers_rv = findViewById(R.id.rv_trailers);
        LinearLayoutManager trailersLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailers_rv.setLayoutManager(trailersLayoutManager);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(trailers_rv);
        mTrailersHandler = this;

        //Reviews
        reviews_rv = findViewById(R.id.rv_reviews);
        LinearLayoutManager reviewsLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviews_rv.setLayoutManager(reviewsLayoutManager);


        //Create Movie from intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        thisMovie = intent.getParcelableExtra("ThisMovie");

        if(thisMovie != null) {

            mId = thisMovie.getId();
            connectAndGetVideos(mId);
            connectAndGetReviews(mId);

            mTitle = thisMovie.getTitle();
            titleTextView.setText(mTitle);
            mDesc = thisMovie.getOverview();
            descriptionTextView.setText(mDesc);
            mDate = thisMovie.getReleaseDate();
            dateTextView.setText(mDate);
            mRating = thisMovie.getVoteAverage().toString();
            ratingTextView.setText(mRating);
            mPosterUrl = thisMovie.getPosterPath();
            mBackdropUrl = thisMovie.getBackdropPath();

        }
        //Check if Movie is from Favorites Database to update UI
        thisMovie.setFavorite(checkIfInFavorites(mDb,mId));

        //Images
        Picasso.with(this)
                .load(BACKGROUND_URL_BASE_PATH + mPosterUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(backgroundImageView);
        Picasso.with(this)
                .load(BACKGROUND_URL_BASE_PATH + mBackdropUrl)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(horizontalImageView);
        horizontalImageView.animate().translationXBy(-1500).setDuration(900);

        //Rating Bar
        ratingbar.setIsIndicator(true);
        ratingbar.setStepSize(0.1f);
        Float rating = Float.parseFloat(mRating)/2;
        ratingbar.setRating(rating);

        //OnClick function for Favorites button
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (thisMovie.getFavorite()){

                    favoritesButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorWhite)));
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.favoritesDao().deleteById(thisMovie.getId());
                            thisMovie.setFavorite(false);
                        }
                    });


                }else {

                    favoritesButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPink)));
                    Double ratingNumber = Double.parseDouble(mRating);
                    final Date updatedAt = new Date();

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            mDb.favoritesDao().insertFavorite(thisMovie);
                            thisMovie.setFavorite(true);
                        }
                    });

                }

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private boolean checkIfInFavorites(FavoritesDatabase mDb, String mId) {
        AddFavoriteViewModelFactory factory = new AddFavoriteViewModelFactory(mDb, mId);
        final AddFavoriteViewModel viewModel = ViewModelProviders.of(this,
                factory).get(AddFavoriteViewModel.class);
                viewModel.getFavoriteMovie().observe(this, new Observer<Movie>() {
                    @Override
                    public void onChanged(@Nullable Movie movie) {
                        if (movie != null) {
                            thisMovie.setFavorite(true);
                            favoritesButton.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPink)));
                        }
                    }
                });
       return isFavorite;
    }


    private void connectAndGetVideos(String id){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

        Call<Trailer> call = movieApiService.getVideos(id, API_KEY);
        call.enqueue(new Callback<Trailer>() {

            @Override
            public void onResponse(Call<Trailer> call, Response<Trailer> response) {
                trailersList = response.body().getResults();
                trailers_rv.setAdapter(new TrailersAdapter(trailersList, R.layout.trailer_item, getApplicationContext(),mTrailersHandler));

            }
            @Override
            public void onFailure(Call<Trailer> call, Throwable t) {

            }
        });
    }

    private void connectAndGetReviews(String id){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

        Call<Review> call = movieApiService.getReviews(id, API_KEY);
        call.enqueue(new Callback<Review>() {

            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                reviewsList = response.body().getResults();
                reviews_rv.setAdapter(new ReviewsAdapter(reviewsList, R.layout.review_item, getApplicationContext()));
            }
            @Override
            public void onFailure(Call<Review> call, Throwable t) {
            }
        });
    }

    //OnClick function for Trailers
    @Override
    public void onClick(String id) {
        Context context = this;
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + id)));
    }
}
