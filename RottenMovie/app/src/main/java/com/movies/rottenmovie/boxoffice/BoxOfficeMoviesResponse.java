package com.movies.rottenmovie.boxoffice;

import com.google.gson.annotations.SerializedName;
import com.movies.rottenmovie.boxoffice.model.Movie;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Deepak Pawar on 8/25/2016.
 */

public class BoxOfficeMoviesResponse implements Serializable
{
    @SerializedName("movies")
    private List<Movie> moviesList;

    public List<Movie> getMoviesList()
    {
        return moviesList;
    }

}
