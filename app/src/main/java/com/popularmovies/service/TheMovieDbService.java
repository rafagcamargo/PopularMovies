package com.popularmovies.service;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TheMovieDbService {

    private static final String API_URL = "http://api.themoviedb.org/3/";
    private static final String API_KEY = "api_key";
    //private static final String API_VALUE = "f00e6b9bb55e066f84d070bccc8514dc";

    private Retrofit retrofit;
    private String apiKeyValue;

    private static TheMovieDbService theMovieDbService;

    private TheMovieDbService(String apiKeyValue) {
        this.apiKeyValue = apiKeyValue;
    }

    public static void init(String apiKeyValue) {
        theMovieDbService = new TheMovieDbService(apiKeyValue);
    }

    public static TheMovieDbService getInstance() {
        if (theMovieDbService == null) {
            throw new RuntimeException("TheMovieDbService not initialized. You should call init() first.");
        }

        return theMovieDbService;
    }

    private Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.interceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    HttpUrl httpUrl = chain.request().url();
                    httpUrl = httpUrl.newBuilder().addQueryParameter(API_KEY, apiKeyValue).build();

                    Request request = chain.request().newBuilder().url(httpUrl).build();

                    return chain.proceed(request);
                }
            });

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(builder.build())
                    .build();
        }

        return retrofit;
    }

    public MovieService moviesService() {
        return getRetrofit().create(MovieService.class);
    }
}