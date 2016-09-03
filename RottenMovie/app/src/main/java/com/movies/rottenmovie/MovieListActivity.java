package com.movies.rottenmovie;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.movies.rottenmovie.appbase.BaseActivity;
import com.movies.rottenmovie.boxoffice.BoxOfficeMoviesResponse;
import com.movies.rottenmovie.boxoffice.adapter.BoxofficeMovieAdapter;
import com.movies.rottenmovie.boxoffice.model.Movie;
import com.movies.rottenmovie.network.RestApiClient;
import com.movies.rottenmovie.network.RestApiInterface;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.movies.rottenmovie.network.NetworkStatus.STATUS_LOADING;
import static com.movies.rottenmovie.network.NetworkStatus.STATUS_SUCCESS;

public class MovieListActivity extends BaseActivity
{
    @BindView(R.id.moviesRecyclerView) RecyclerView moviesRecyclerView;
    @BindView(R.id.searchView) SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        _unbinder = ButterKnife.bind(this);
        initViewData();
        executeListMovieTask();
    }

    private void initViewData()
    {
        searchView.onActionViewExpanded();
        searchView.setQueryHint("Search Actor/Director etc.");
        View v = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
        v.setBackgroundColor(Color.parseColor("#00000000"));
        BoxofficeMovieAdapter boxOfficeMovieAdapter = new BoxofficeMovieAdapter(this);
        setupRecyclerView(boxOfficeMovieAdapter);
        searchView.setOnQueryTextListener(searchQueryTextListner);
    }

    private void setupRecyclerView(BoxofficeMovieAdapter boxOfficeMovieAdapter)
    {
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        moviesRecyclerView.setHasFixedSize(true);
        boxOfficeMovieAdapter.setItems(new ArrayList<Movie>());
        moviesRecyclerView.setAdapter(boxOfficeMovieAdapter);
    }

    private void executeListMovieTask()
    {
        showHideProgress(STATUS_LOADING);
        RestApiInterface restApiService = RottenMovieController.getRestApiClientInterface();
        Call<BoxOfficeMoviesResponse> call = restApiService.getBoxOfficeMovies(RestApiClient.Rotton_Api_Key);
        call.enqueue(new Callback<BoxOfficeMoviesResponse>()
        {
            @Override
            public void onResponse(Call<BoxOfficeMoviesResponse> call, Response<BoxOfficeMoviesResponse> response)
            {
               /* int statusCode = response.code(); */
                showHideProgress(STATUS_SUCCESS);
                Log.e("NetworkApiCall", "" + response.body().toString());
                showMovieList(response.body().getMoviesList());
            }

            @Override
            public void onFailure(Call<BoxOfficeMoviesResponse> call, Throwable t)
            {
                // Log error here since request failed
                showHideProgress(STATUS_SUCCESS);
                Log.e("NetworkApiCall", t.toString());
            }
        });

    }

    private void showMovieList(List<Movie> moviesList)
    {
        ((BoxofficeMovieAdapter) moviesRecyclerView.getAdapter()).setItems(moviesList);
    }


    //*** setOnQueryTextListener ***
    SearchView.OnQueryTextListener searchQueryTextListner = new SearchView.OnQueryTextListener()
    {
        @Override
        public boolean onQueryTextSubmit(String query)
        {
            Toast.makeText(getBaseContext(), query, Toast.LENGTH_SHORT).show();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText)
        {
            Toast.makeText(getBaseContext(), newText, Toast.LENGTH_SHORT).show();
            ((BoxofficeMovieAdapter) moviesRecyclerView.getAdapter()).getFilter().filter(newText);

            return true;
        }
    };


    @Override
    protected void onResume()
    {
        super.onResume();
        searchView.clearFocus();
    }

    @Override
    protected void onNetworkChange(boolean isNetworkConnected)
    {
        showNoNetworkBar(isNetworkConnected);
    }
}
