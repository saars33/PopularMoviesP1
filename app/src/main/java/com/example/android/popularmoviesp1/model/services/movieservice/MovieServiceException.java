package com.example.android.popularmoviesp1.model.services.movieservice;

/**
 * Created by ARVIND on 7/17/2016.
 */
public class MovieServiceException extends Exception {

    public MovieServiceException(final String message) {
        super(message);
    }

    public MovieServiceException(final String message,final Throwable cause) {
        super(message, cause);
    }
}
