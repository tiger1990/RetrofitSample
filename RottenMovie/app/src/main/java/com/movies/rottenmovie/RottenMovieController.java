package com.movies.rottenmovie;

import android.app.Application;
import android.content.Context;

import com.movies.rottenmovie.network.NetworkChangeReceiver;
import com.movies.rottenmovie.network.RestApiClient;
import com.movies.rottenmovie.network.RestApiInterface;

/**
 * Created by Deepak Pawar on 8/25/2016.
 */

public class RottenMovieController extends Application
{
    private static RottenMovieController _cbApplicationInstance;
    private static RestApiInterface _restApiClientInterface;
    public static final String DEVELOPMENT_BUILD = "development", PRODUCTION_BUILD = "production";

    public static RottenMovieController getApplicationInstance()
    {
        return _cbApplicationInstance;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        _cbApplicationInstance = this;
        _restApiClientInterface = RestApiClient.getRestInstance().getRetrofitApiInstance();
        NetworkChangeReceiver.initNetworkChange(getApplicationContext());
    }

    public static RestApiInterface getRestApiClientInterface()
    {
        if (_restApiClientInterface == null)
        {
            return _restApiClientInterface = RestApiClient.getRestInstance().getRetrofitApiInstance();
        }
        return _restApiClientInterface;
    }

    public Context getApplicationContext()
    {
        return this;
    }
}
