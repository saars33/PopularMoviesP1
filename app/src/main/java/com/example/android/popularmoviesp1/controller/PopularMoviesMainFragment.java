package com.example.android.popularmoviesp1.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.popularmoviesp1.R;
import com.example.android.popularmoviesp1.model.domain.Movie;
import com.example.android.popularmoviesp1.model.services.movieservice.IMovieService;
import com.example.android.popularmoviesp1.model.services.movieservice.MovieServiceException;
import com.example.android.popularmoviesp1.model.services.movieservice.MovieServiceImpl;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ARVIND on 7/18/2016.
 */
public class PopularMoviesMainFragment extends Fragment{

    private static final String TAG=PopularMoviesMainFragment.class.getSimpleName();
    private static final String SAVED_MOVIE_LIST="movielist";
    private RecyclerView mRecyclerView;

    private List<Movie> mMovieList=new ArrayList<>();;
    public static Fragment newInstance(){
        return new PopularMoviesMainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if(savedInstanceState==null){
            displayMoviesByPopularity();
        }else{
            mMovieList=savedInstanceState.getParcelableArrayList(SAVED_MOVIE_LIST);
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root=inflater.inflate(R.layout.fragment_popular_movies_main,container,false);
        mRecyclerView= (RecyclerView) root.findViewById(R.id.fragment_popular_movies_main_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        setupAdapter();
//        Log.i(TAG,"onCreateView called : " + mMovieList.toString());
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVED_MOVIE_LIST,new ArrayList<Movie>(mMovieList));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_popular_movies_main,menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_item_sort_by_popularity:
                displayMoviesByPopularity();
                return true;
            case R.id.menu_item_sort_by_top_rated:
                displayMoviesByTopRated();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private void setupAdapter(){
        if(this.isAdded()){
            //Log.i(TAG,"getMoviesByPopularity: " + mMovieList.toString());
            mRecyclerView.setAdapter(new MovieAdapter(mMovieList));
        }
    }

    private void displayMoviesByTopRated(){
        new FetchMoviesTask().execute(getString(R.string.sort_by_top_rated_kw)); //get the movie list from the database and update the adapter
    }

    private void displayMoviesByPopularity(){
        new FetchMoviesTask().execute(getString(R.string.sort_by_popularity_kw)); //get the movie list from the database and update the adapter
    }

    private class FetchMoviesTask extends AsyncTask<String,Void,List<Movie>> {

        @Override
        protected List<Movie> doInBackground(String... strings) {
            List<Movie> movieList=null;
            String sortBy=getString(R.string.sort_by_popularity_kw);
            if(strings.length>0){
                sortBy=strings[0].trim().toLowerCase();
            }
            IMovieService movieService=new MovieServiceImpl(getActivity());
            Log.i(this.getClass().getSimpleName(),"test1");
            try {
                if (sortBy.equals(getString(R.string.sort_by_top_rated_kw))){
                    movieList=movieService.getMoviesByTopRated();
                }else{
                    movieList=movieService.getMoviesByPopularity();
                }

                //Log.i(this.getClass().getSimpleName(),"getMoviesByPopularity: " + movieList.toString());
                //Log.i(this.getClass().getSimpleName(),"getMoviesByTopRated: " + movieList.toString());

            } catch (MovieServiceException e) {
                Log.e(TAG,"MovieServiceException thrown in the doInBackground method of FetchMoviesTask for sortBy: " + sortBy,e);
                e.printStackTrace();
            }
            return movieList;
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            //super.onPostExecute(movieList);
            mMovieList=movieList;
            setupAdapter();

        }
    }


    private class MovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView mImageView;
        private Movie mMovie;

        public MovieHolder(View view) {
            super(view);
            mImageView=(ImageView) itemView.findViewById(R.id.list_item_movie_image_view);
            itemView.setOnClickListener(this);
        }

        private void bindMovie(Movie movie){
            mMovie=movie;
            String posterPath=mMovie.getPosterPath();
            String imageurl= Uri.parse(getString(R.string.tmdb_base_image_url)).buildUpon().appendPath(getString(R.string.tmdb_base_image_size)).appendEncodedPath(posterPath).build().toString();
            //Log.i(this.getClass().getSimpleName(),"posterpathfull : " + baseurl);
            Picasso.with(mImageView.getContext()).load(imageurl).error(R.drawable.ic_movie_list_item_placeholder_image).into(mImageView);

        }

        @Override
        public void onClick(View view) {
            Bitmap poster=((BitmapDrawable) mImageView.getDrawable()).getBitmap();
            //Bitmap drawable=((BitmapDrawable) getResources().getDrawable(R.mipmap.ic_testdrawable)).getBitmap();
            startActivity(PopularMovieDetailsActivity.newIntent(getActivity(),mMovie,poster));
        }
    }

    private class MovieAdapter extends RecyclerView.Adapter<MovieHolder>{

        private List<Movie> mMovieList;
        public MovieAdapter(List<Movie> movieList){
            mMovieList=movieList;

        }
        @Override
        public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(getActivity());
            View view=inflater.inflate(R.layout.list_item_movie,parent,false);
            MovieHolder movieHolder=new MovieHolder(view);
            return movieHolder;
        }

        @Override
        public void onBindViewHolder(MovieHolder holder, int position) {
            Movie movie=mMovieList.get(position);
            holder.bindMovie(movie);
        }

        @Override
        public int getItemCount() {
            return mMovieList.size();
        }
    }
}
