package com.example.android.popularmoviesp1.model.services.movieservice;

import com.example.android.popularmoviesp1.model.domain.Movie;

import java.util.List;

/**
 * Created by ARVIND on 7/17/2016.
 */
public interface IMovieService {

    public final String NAME="IMovieService";

    public List<Movie> getMoviesByPopularity() throws MovieServiceException;

    public  List<Movie> getMoviesByTopRated() throws MovieServiceException;

}
