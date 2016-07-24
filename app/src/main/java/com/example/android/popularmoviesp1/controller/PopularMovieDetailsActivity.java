package com.example.android.popularmoviesp1.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;

import com.example.android.popularmoviesp1.model.domain.Movie;

public class PopularMovieDetailsActivity extends SingleFragmentActivity {

    private static final String TAG=PopularMovieDetailsActivity.class.getSimpleName();

    private static final String EXTRA_MOVIE ="com.example.android.popularmoviesp1.controller.movie";
    private static final String EXTRA_POSTER ="com.example.android.popularmoviesp1.controller.poster";

    public static Intent newIntent(Context context, Movie movie, Bitmap poster){
        Intent intent=new Intent(context,PopularMovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE,movie);
        intent.putExtra(EXTRA_POSTER,poster);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Movie movie=(Movie) getIntent().getParcelableExtra(EXTRA_MOVIE);
        Bitmap poster=(Bitmap) getIntent().getParcelableExtra(EXTRA_POSTER);
        return PopularMovieDetailsFragment.newInstance(movie,poster);

    }


}
