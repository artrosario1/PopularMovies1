package com.softcap.artrosario.popularmovies.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.softcap.artrosario.popularmovies.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView titleTextView = findViewById(R.id.tv_movie_title);
        TextView descriptionTextView = findViewById(R.id.tv_movie_desc);
        TextView dateTextView =  findViewById(R.id.tv_movie_date);
        TextView ratingTextView = findViewById(R.id.tv_movie_rating);
        ImageView backgroundImageView = findViewById(R.id.movieBackground);
        ImageView horizontalImageView = findViewById(R.id.movieBackdrop);
        horizontalImageView.setTranslationX(1500);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        String mTitle = extras.getString(getString(R.string.EXTRA_TITLE));
        titleTextView.setText(mTitle);
        String mDesc = extras.getString(getString(R.string.EXTRA_DESC));
        descriptionTextView.setText(mDesc);
        String mDate = extras.getString(getString(R.string.EXTRA_DATE));
        dateTextView.setText(mDate);
        String mRating = extras.getString(getString(R.string.EXTRA_RATING));
        ratingTextView.setText(mRating);

        String mPosterUrl = extras.getString(getString(R.string.EXTRA_POSTER));
        String mBackdropUrl = extras.getString(getString(R.string.EXTRA_BACKDROP));

        Picasso.with(this)
                .load(mPosterUrl)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(backgroundImageView);
        Picasso.with(this)
                .load(mBackdropUrl)
                .placeholder(android.R.drawable.sym_def_app_icon)
                .error(android.R.drawable.sym_def_app_icon)
                .into(horizontalImageView);

        horizontalImageView.animate().translationXBy(-1500).setDuration(900);

    }
}
