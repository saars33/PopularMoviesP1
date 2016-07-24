package com.example.android.popularmoviesp1.model.services.movieservice;

import android.content.Context;
import android.net.Uri;

import com.example.android.popularmoviesp1.R;
import com.example.android.popularmoviesp1.model.domain.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ARVIND on 7/17/2016.
 */
public class MovieServiceImpl implements IMovieService {

    private static final String TAG = MovieServiceImpl.class.getSimpleName();

    private Context mContext;
    private String API_KEY_PARAM_VALUE;

    private static final String API_KEY_PARAM_KEY = "api_key";
    private static final String BASE_URL = "https://api.themoviedb.org/3";
    private static final String POPULAR_MOVIES_APPENDED_URL = "/movie/popular";
    private static final String TOP_RATED_MOVIES_APPENDED_URL = "/movie/top_rated";

    public MovieServiceImpl(Context context) {
        mContext=context;
        API_KEY_PARAM_VALUE=mContext.getResources().getString(R.string.api_key_value);
    }

    @Override
    public List<Movie> getMoviesByPopularity() throws MovieServiceException {
        List<Movie> movieList;

        String appendedUrl=BASE_URL + POPULAR_MOVIES_APPENDED_URL;
        String urlSpec=Uri.parse(appendedUrl).buildUpon().appendQueryParameter(API_KEY_PARAM_KEY,API_KEY_PARAM_VALUE).build().toString();
        try {
            movieList=getMovieListForUrl(urlSpec);
        } catch (IOException e) {
            throw new MovieServiceException("IOException thrown by getMovieListForUrl for urlSpec: " +urlSpec,e);
        } catch (JSONException e) {
            throw new MovieServiceException("JSONException thrown by getMovieListForUrl for urlSpec: " +urlSpec,e);
        }
        return movieList;
    }

    @Override
    public List<Movie> getMoviesByTopRated() throws MovieServiceException {

        List<Movie> movieList;
        String appendedUrl=BASE_URL + TOP_RATED_MOVIES_APPENDED_URL;
        String urlSpec=Uri.parse(appendedUrl).buildUpon().appendQueryParameter(API_KEY_PARAM_KEY,API_KEY_PARAM_VALUE).build().toString();
        try {
            movieList=getMovieListForUrl(urlSpec);
        } catch (IOException e) {
            throw new MovieServiceException("IOException thrown by getMovieListForUrl for urlSpec: " +urlSpec,e);
        } catch (JSONException e) {
            throw new MovieServiceException("JSONException thrown by getMovieListForUrl for urlSpec: " +urlSpec,e);
        }
        return movieList;
    }

    private List<Movie> getMovieListForUrl(String urlSpec) throws IOException, JSONException {
        List<Movie> movieList;
        String responseJSON=getResponsePayload(urlSpec);
        JSONObject responseJsonObject=new JSONObject(responseJSON);
        movieList=parseResponseAndReturnMovieList(responseJsonObject);

        return movieList;
    }

    private String getResponsePayload(String urlSpec) throws IOException {

        URL url = new URL(urlSpec);
        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
        InputStream inputStream =null;
        BufferedReader bufferedReader=null;
        try {

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            inputStream = httpURLConnection.getInputStream();
            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(httpURLConnection.getResponseMessage() + " :with " + urlSpec);
            }
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line).append("\n"); //Adding a new line isn't necessary but will provide more readability
            }

            if (builder.length() == 0) {
                return null;
            }

            return builder.toString();
        }
        finally {
            httpURLConnection.disconnect();

            if(bufferedReader!=null){
                bufferedReader.close();
            }

            if(inputStream!=null){
                inputStream.close();
            }
        }
    }

    private List<Movie> parseResponseAndReturnMovieList(JSONObject jsonBody) throws JSONException {
        ArrayList<Movie> movieArrayList = new ArrayList<>();

        JSONArray resultsJsonArray=jsonBody.getJSONArray("results");
        for (int itr=0;itr<resultsJsonArray.length();itr++){
            JSONObject movieJsonObject=resultsJsonArray.getJSONObject(itr);
            Movie movie=returnMovieForJSONObject(movieJsonObject);
            movieArrayList.add(movie);
        }
        return movieArrayList;
    }

    private Movie returnMovieForJSONObject(JSONObject jsonObject) throws JSONException {
        int id = jsonObject.getInt("id");
        String original_title = jsonObject.getString("original_title");
        String overview = jsonObject.getString("overview");
        double vote_average = jsonObject.getDouble("vote_average");
        double popularity=jsonObject.getDouble("popularity");
        String poster_path=jsonObject.getString("poster_path");
        String release_date=jsonObject.getString("release_date");

        return new Movie(poster_path, id, original_title, overview, popularity, release_date, vote_average);

    }
}
