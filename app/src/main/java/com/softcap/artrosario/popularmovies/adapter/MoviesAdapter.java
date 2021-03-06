package com.softcap.artrosario.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.softcap.artrosario.popularmovies.R;
import com.softcap.artrosario.popularmovies.activity.DetailActivity;
import com.softcap.artrosario.popularmovies.activity.MainActivity;
import com.softcap.artrosario.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>
{
    private final List<Movie> movies;
    private final int rowLayout;
    private final Context mContext;
    private static final String BACKGROUND_URL_BASE_PATH = "http://image.tmdb.org/t/p/w342/";

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler{
        void onItemClick(View view, Movie movie);
    }

    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context, MovieAdapterOnClickHandler clickHandler){
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.mContext = context;
        this.mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(rowLayout,parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        String image_url = BACKGROUND_URL_BASE_PATH + movies.get(position).getPosterPath();
        Picasso.with(mContext)
                .load(image_url)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(holder.movieImage);
        ViewCompat.setTransitionName(holder.movieImage,"transition_movie_image");
    }

    public List<Movie> getMovies() {
        return movies;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final LinearLayout moviesLayout;
        final ImageView movieImage;

        MovieViewHolder(View view){
            super(view);
            moviesLayout = view.findViewById(R.id.movies_layout);
            movieImage = view.findViewById(R.id.movie_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Movie thisMovie = movies.get(adapterPosition);
            mClickHandler.onItemClick(view, thisMovie);
        }
    }
}
