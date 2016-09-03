package com.movies.rottenmovie.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.movies.rottenmovie.BuildConfig;
import com.movies.rottenmovie.RottenMovieController;


public class NetworkChangeReceiver extends BroadcastReceiver
{
    public static final int NETWORK_NOT_CONNECTED = 100;
    public static final int NETWORK_CONNECTED = 101;
    private static ConnectivityManager _connectivityManager;
    protected Handler _handler;

    /**
     * @param _hHandler  ohandler for the thread which need callback
     */
    public NetworkChangeReceiver(Handler _hHandler)
    {
        _handler = _hHandler;
    }

    /***
     * Method will provide if device is connected to network or not
     ***/
    public static boolean isOnline()
    {
        NetworkInfo activeNetwork = _connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting())
        {
            return activeNetwork.isConnected();
        }
        return false;
    }

    public static final IntentFilter getNetworkChangeFilter()
    {
        IntentFilter networkChangeFilter = new IntentFilter();
        networkChangeFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        return networkChangeFilter;
    }

    /**
     * @return if wifi is connected or not
     */
    public static boolean isWiFiConnected()
    {
        boolean haveConnectedWifi = false;
        try
        {
            NetworkInfo activeNetworkInfo = _connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null) // connected to the internet
            {
                if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI && activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED)
                {
                    // connected to wifi
                    haveConnectedWifi = true;

                }
                else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE && activeNetworkInfo.getState() == NetworkInfo.State.CONNECTED)
                {
                    // connected to the mobile provider's data plan
                    haveConnectedWifi = false;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return haveConnectedWifi;
    }

    /****
     * initialize from ApplicationController
     ****/
    public static void initNetworkChange(Context context)
    {
        _connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public void onReceive(Context context, Intent intent)
    {
        try
        {
            if (BuildConfig.FLAVOR.equalsIgnoreCase(RottenMovieController.DEVELOPMENT_BUILD))
            {
                debugIntent(intent, "NetworkChangeLog");
            }
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION))
            {
                initNetworkChange(context);
                boolean noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
                Message msg = new Message();
                //Network is not connected
                if (noConnectivity)
                {
                    msg.what = NETWORK_NOT_CONNECTED;
                    _handler.sendMessage(msg);
                }
                else
                {
                    msg.what = NETWORK_CONNECTED;
                    _handler.sendMessage(msg);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
    }

    /**
     * @param intent got by change in connection by receiver
     * @param tag    tag for log
     */
    private void debugIntent(Intent intent, String tag)
    {
        Log.v(tag, "action: " + intent.getAction());
        Log.v(tag, "component: " + intent.getComponent());
        Bundle extras = intent.getExtras();
        if (extras != null)
        {
            for (String key : extras.keySet())
            {
                Log.v(tag, "key [" + key + "]: " +
                        extras.get(key));
            }
        }
        else
        {
            Log.v(tag, "no extras");
        }
    }
}

