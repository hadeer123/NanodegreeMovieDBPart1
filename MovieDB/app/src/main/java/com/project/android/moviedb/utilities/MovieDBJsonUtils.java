package com.project.android.moviedb.utilities;

import android.content.Context;
import android.util.Log;

import com.project.android.moviedb.Movie;
import com.project.android.moviedb.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Class to Parse Json objects to Movie objects
 */

public class MovieDBJsonUtils {


    private static  String TAG = MovieDBJsonUtils.class.toString();
    private static ArrayList <Movie> parsedMoviesData = new ArrayList<>();

    private static Movie createNewMovieFromJson(Context context, JSONObject movieObj){
        Movie newMovie;
        try {
            newMovie= new Movie(movieObj.getInt(context.getString(R.string.json_movie_id)),
                    movieObj.getDouble(context.getString(R.string.json_movie_vote_avrg)),
                    movieObj.getString(context.getString(R.string.json_movie_title)),
                    movieObj.getString(context.getString(R.string.json_movie_poster_path)));
        }catch (JSONException e){
            Log.e(TAG,e.getMessage());
            return  null;
        }


        return newMovie;
    }


    public static ArrayList<Movie> getSimpleMovieQueryStringFromJson(Context context, String movieString)
            throws JSONException {

        JSONObject moviesJson = new JSONObject(movieString);

        // Handle possible Query error
        if(moviesJson.has(context.getString(R.string.json_movie_success))){
            Boolean isSuccessful = moviesJson.getBoolean(context.getString(R.string.json_movie_success));
            String  error_message = moviesJson.getString(context.getString(R.string.json_error_message));

            if(!isSuccessful){
                Log.e(TAG, error_message);
                return null;
            }
        }

        JSONArray movieArray = moviesJson.getJSONArray( context.getString(R.string.json_movie_results_obj));

        for(int i =0; i<movieArray.length(); i++){

            JSONObject movie = movieArray.getJSONObject(i);
            parsedMoviesData.add(i,createNewMovieFromJson(context,movie));

        }


        return parsedMoviesData;
    }


}


