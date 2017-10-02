package com.project.android.moviedb;

/**
 * Model Class for a typical movie data retrieved from Movie DB
 */

public class Movie {
    private static  final String BASE_IMAGE_URL ="http://image.tmdb.org/t/p/w342/";
    public  int getId() {
        return id;
    }


    public  double getVote_average() {
        return vote_average;
    }



    public  String getTitle() {
        return title;
    }



    public  String getPoster_path() {
        return poster_path;
    }



    private   int id;
    private   double vote_average;
    private   String title;
    private   String poster_path;


    public Movie(int id, double vote_average, String title, String poster_path){
        this.id = id;
        this.vote_average = vote_average;
        this.poster_path = BASE_IMAGE_URL+poster_path;
        this.title = title;
    }



}
