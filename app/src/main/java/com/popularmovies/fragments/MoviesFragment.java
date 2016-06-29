package com.popularmovies.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popularmovies.R;
import com.popularmovies.adapter.MoviesAdapter;
import com.popularmovies.model.Movie;
import com.popularmovies.model.Movies;
import com.popularmovies.service.TheMovieDbService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragment extends Fragment {

    private InteractionListener interactionListener;
    private ArrayList<Movie> movies;

    public interface InteractionListener {
        void details();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            interactionListener = (InteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement InteractionListener.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        movies = new ArrayList<>();
        final MoviesAdapter moviesAdapter = new MoviesAdapter(movies);
        recyclerView.setAdapter(moviesAdapter);

        Call<Movies> moviesCall = TheMovieDbService.getInstance().moviesService().popular();
        moviesCall.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                movies.clear();
                movies.addAll(response.body().getResults());
                moviesAdapter.notifyItemRangeInserted(0, movies.size());
                for (Movie movie: response.body().getResults()) {
                    System.out.println("movie: " + movie);
                }
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }
        });

        return view;
    }
}
