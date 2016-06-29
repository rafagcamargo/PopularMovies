package com.popularmovies.application;

import android.app.Application;

import com.popularmovies.R;
import com.popularmovies.service.TheMovieDbService;

public class PopularMoviesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        String apiKeyValue = getString(R.string.api_key);
        TheMovieDbService.init(apiKeyValue);
    }
}
