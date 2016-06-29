package com.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.popularmovies.R;
import com.popularmovies.fragments.MovieDetailsFragment;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discovery);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieDetailsFragment())
                    .commit();
        }
    }

    @Override
    protected void onPause() {
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        super.onPause();
    }
}
