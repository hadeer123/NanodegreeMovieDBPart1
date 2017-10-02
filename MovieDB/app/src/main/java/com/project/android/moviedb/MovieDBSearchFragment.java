package com.project.android.moviedb;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.android.moviedb.utilities.MovieDBJsonUtils;
import com.project.android.moviedb.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDBSearchFragment extends Fragment {
    private static final String POPULAR_MOVIE_QUERY ="movie/popular?api_key=8934d5a9f489ef4ac9de9c6fb5aa6b7a&language=en-US&page=1";
    private static final String TOP_RATED_MOVIE_QUERY="movie/top_rated?api_key=8934d5a9f489ef4ac9de9c6fb5aa6b7a&language=en-US&page=1";
    private static final int TAB_ONE =0;
    private static final int TAB_TWO=1;

    private static final String TAG = MovieDBSearchFragment.class.toString();
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "TAB NO";
    private String Query;
    private RecyclerView mRecyclerView;
    private MovieDBAdaptor movieDBAdaptor;
    private RecyclerView.LayoutManager mLayoutManager;

    private void getMovieDBSearchQuery(String Query) throws ExecutionException, InterruptedException {
        URL getSearchURL = NetworkUtils.buildUrl(Query);
         new MovieQueryTask().execute(getSearchURL).get();
    }


    public MovieDBSearchFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MovieDBSearchFragment newInstance(int sectionNumber) {
        MovieDBSearchFragment fragment = new MovieDBSearchFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_dbmain, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getContext(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(movieDBAdaptor);

        switch (getArguments().getInt(ARG_SECTION_NUMBER)){
            case TAB_ONE:
                Query= POPULAR_MOVIE_QUERY;
                break;
            case TAB_TWO:
                Query =TOP_RATED_MOVIE_QUERY;
                break;
        }
        try {
            getMovieDBSearchQuery(Query);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public class MovieQueryTask extends AsyncTask<URL, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ArrayList<Movie> doInBackground(URL... params) {
            URL searchUrl = params[0];
            String SearchResults;
            try {
                SearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);

                return  MovieDBJsonUtils
                        .getSimpleMovieQueryStringFromJson(getActivity(), SearchResults);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                return null;
            }

        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movieSearchResults) {
            if (movieSearchResults != null ) {

                movieDBAdaptor = new MovieDBAdaptor(movieSearchResults);
                mRecyclerView.setAdapter(movieDBAdaptor);

                for(Movie movie: movieSearchResults){
                    Log.i(TAG, getArguments().getInt(ARG_SECTION_NUMBER)+movie.getTitle());
                }
                Log.i(TAG,"Done !");
            }
        }
    }
}
