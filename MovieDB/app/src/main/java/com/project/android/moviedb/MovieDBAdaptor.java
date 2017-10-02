package com.project.android.moviedb;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fg7cpt on 9/30/2017.
 */

public class MovieDBAdaptor extends RecyclerView.Adapter<MovieDBAdaptor.MovieDBViewHolder>{
    private static final String TAG = MovieDBAdaptor.class.toString();
    private List <Movie> movies;
    private Context context;



    public MovieDBAdaptor(@NonNull List<Movie> objects) {
        if(objects == null ){
            throw new IllegalArgumentException("model must not be null");
        }
        movies = objects;
    }



    @Override
    public MovieDBAdaptor.MovieDBViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewGroup layout = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent,false);
        MovieDBViewHolder movieDBViewHolder = new MovieDBViewHolder(layout);
        return movieDBViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieDBAdaptor.MovieDBViewHolder holder, int position) {
        Picasso.with(context).load(movies.get(position).getPoster_path()).into(holder.posterImage);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public static class MovieDBViewHolder extends RecyclerView.ViewHolder {
        public ImageView posterImage;
        public MovieDBViewHolder(ViewGroup view) {
            super(view);
            posterImage= (ImageView)view.findViewById(R.id.poster_image);
        }
    }
}
