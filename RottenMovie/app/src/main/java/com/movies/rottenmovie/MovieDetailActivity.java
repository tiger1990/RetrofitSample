package com.movies.rottenmovie;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.movies.rottenmovie.appbase.BaseActivity;
import com.movies.rottenmovie.boxoffice.model.Movie;
import com.movies.rottenmovie.boxoffice.viewmodel.MovieDetailViewModel;
import com.movies.rottenmovie.databinding.ActivityMovieDetailBinding;

/**
 * Created by Deepak Pawar on 8/25/2016.
 */

public class MovieDetailActivity extends BaseActivity
{
    private static String MovieRowData = "MovieData";
    private static String ShowCarausal = "ShowCarausal";
    private boolean toShowCraousal;
    private static final int Movie_Detail_Activity = R.layout.activity_movie_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailBinding movieDetailBinding = DataBindingUtil.setContentView(this, Movie_Detail_Activity);
        Movie movieData = getIntent().getParcelableExtra(MovieRowData);
        if(getIntent().getBooleanExtra(ShowCarausal, false))
        {
            toShowCraousal=true;
        }else
        {
            toShowCraousal=false;
        }
        movieDetailBinding.setMovieDetail(new MovieDetailViewModel(this,movieData,toShowCraousal));
        initToolbar(movieDetailBinding.includedToolbar.toolbar);
        movieDetailBinding.includedToolbar.tvCustomActionbarHeader.setText("Movie Details");
    }

    public static Intent getStartDetailIntent(Context context, boolean showCrausal, Movie movieRow)
    {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(ShowCarausal, showCrausal);
        intent.putExtra(MovieRowData, movieRow);
        return intent;
    }

    @Override
    protected void onNetworkChange(boolean isNetworkConnected)
    {
        showNoNetworkBar(isNetworkConnected);
    }
}
