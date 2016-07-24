package com.example.android.popularmoviesp1.controller;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmoviesp1.R;
import com.example.android.popularmoviesp1.model.domain.Movie;

public class PopularMovieDetailsFragment extends Fragment {

    private static final String TAG=PopularMovieDetailsFragment.class.getSimpleName();
    private static final String ARG_MOVIE="Movie";
    private static final String ARG_POSTER="Poster";
    private Movie mMovie;
    private Bitmap mPoster;

    private ImageView mImageView;
    private TextView mOriginalTitleValueTextView;
    private TextView mPlotSynopsisValueTextView;
    private TextView mUserRatingValueTextView;
    private TextView mReleaseDateValueTextView;

    public static Fragment newInstance(Movie movie, Bitmap poster){
        Bundle args=new Bundle();
        args.putParcelable(ARG_MOVIE,movie);
        args.putParcelable(ARG_POSTER,poster);

        PopularMovieDetailsFragment fragment=new PopularMovieDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovie=getArguments().getParcelable(ARG_MOVIE);
        mPoster=getArguments().getParcelable(ARG_POSTER);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root=inflater.inflate(R.layout.fragment_popular_movie_details,container,false);

        mImageView= (ImageView) root.findViewById(R.id.movie_poster_thumbnail_image_view);
        mImageView.setImageDrawable(new BitmapDrawable(mPoster));

        mOriginalTitleValueTextView=(TextView) root.findViewById(R.id.original_title_value_text_view);
        mOriginalTitleValueTextView.setText(mMovie.getOriginalTitle());

        mPlotSynopsisValueTextView=(TextView) root.findViewById(R.id.plot_synopsis_value_text_view);
        mPlotSynopsisValueTextView.setText(mMovie.getPlotOverview());

        mUserRatingValueTextView=(TextView) root.findViewById(R.id.user_rating_value_text_view);
        double voteAverage=mMovie.getVoteAverage();
        mUserRatingValueTextView.setText(Double.toString(voteAverage));
        mReleaseDateValueTextView=(TextView) root.findViewById(R.id.release_date_value_text_view);
        mReleaseDateValueTextView.setText(mMovie.getReleaseDate());

        return root;
    }
}
