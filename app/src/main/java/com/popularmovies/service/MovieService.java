package com.popularmovies.service;

import com.popularmovies.model.Movies;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieService {

    @GET("movie/popular")
    Call<Movies> popular();

    @GET("movie/top_rated")
    Call<Movies> topRated();
}
