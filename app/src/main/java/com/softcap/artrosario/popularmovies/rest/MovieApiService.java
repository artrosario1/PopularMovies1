package com.softcap.artrosario.popularmovies.rest;

import com.softcap.artrosario.popularmovies.model.MovieResponse;
import com.softcap.artrosario.popularmovies.model.Review;
import com.softcap.artrosario.popularmovies.model.Trailer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApiService {

    @GET("/3/movie/{category}")
    Call<MovieResponse> getMovies(
            @Path("category") String category,
            @Query("api_key") String apiKey
    );

    @GET("/3/movie/{id}/videos")
    Call<Trailer> getVideos(
            @Path("id") String id,
            @Query("api_key") String apiKey
    );

    @GET("/3/movie/{id}/reviews")
    Call<Review> getReviews(
            @Path("id") String id,
            @Query("api_key") String apiKey
    );
}

