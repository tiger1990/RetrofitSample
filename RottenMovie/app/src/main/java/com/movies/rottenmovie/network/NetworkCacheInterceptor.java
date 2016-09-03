package com.movies.rottenmovie.network;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Deepak Pawar on 8/13/2016.
 */

/**
 * Implementing network cache control interceptor for caching data part
 */
public class NetworkCacheInterceptor implements Interceptor
{
    @Override
    public Response intercept(Chain chain) throws IOException
    {
        return buildAndControlCache(chain, chain.request());
    }

    private Response buildAndControlCache(Chain chain, Request orignalRequest) throws IOException
    {
        CacheControl.Builder cacheBuilder = new CacheControl.Builder();

        //use this when yu want response always from network (fresh header need to be added )
//        Request.Builder request = orignalRequest.newBuilder();
//        if (orignalRequest.header("fresh") != null) {
//            request.cacheControl(CacheControl.FORCE_NETWORK);
//        }
        if (NetworkChangeReceiver.isOnline())
        {
            // 1 day
            cacheBuilder.onlyIfCached();
            cacheBuilder.maxAge(60, TimeUnit.SECONDS);  //read from cache for 1 minutes
            CacheControl cacheControlOnline = cacheBuilder.build();
            orignalRequest = orignalRequest.newBuilder()
                    .cacheControl(cacheControlOnline)
                    .build();
        }
        else
        {
            //Tolerate 7 days stale
            cacheBuilder.onlyIfCached();
            cacheBuilder.maxStale(7, TimeUnit.DAYS);
            CacheControl cacheControlOffline = cacheBuilder.build();
            orignalRequest = orignalRequest.newBuilder()
                    .cacheControl(cacheControlOffline)
                    .build();
        }

        Response originalResponse = chain.proceed(orignalRequest);

        return originalResponse.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", cacheControl())
                .build();
    }

    @NonNull
    private String cacheControl()
    {
        String cacheHeaderValue;
        if (NetworkChangeReceiver.isOnline())
        {
            long maxAge = 60 * 60; // read from cache 1 min
            cacheHeaderValue = "public, max-age=" + maxAge;
        }
        else
        {
            int maxStale = 28 * 24 * 60 * 60; // tolerate 4-weeks stale
            cacheHeaderValue = "public, only-if-cached, max-stale=" + maxStale;
        }
        return cacheHeaderValue;
    }
}
