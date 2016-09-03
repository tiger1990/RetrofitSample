package com.movies.rottenmovie.network;

import com.movies.rottenmovie.boxoffice.BoxOfficeMoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Deepak Pawar on 8/25/2016.
 */

public interface RestApiInterface
{
    @GET("lists/movies/box_office.json?")
    Call<BoxOfficeMoviesResponse> getBoxOfficeMovies(@Query("apikey") String apikey);

}
