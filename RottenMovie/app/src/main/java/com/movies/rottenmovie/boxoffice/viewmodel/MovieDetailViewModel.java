package com.movies.rottenmovie.boxoffice.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.movies.rottenmovie.R;
import com.movies.rottenmovie.boxoffice.model.Casts;
import com.movies.rottenmovie.boxoffice.model.Movie;
import com.movies.rottenmovie.boxoffice.model.ReleaseDetail;
import com.squareup.picasso.Picasso;
import java.lang.ref.WeakReference;
import java.util.Iterator;

/**
 * Created by Deepak Pawar on 8/26/2016.
 */

public class MovieDetailViewModel extends BaseObservable
{
    private WeakReference<Context> context;
    private Movie movieDetail;
    private boolean toShowCraousal;

    public MovieDetailViewModel(Context mContext, Movie movie, boolean showCraousal)
    {
        context = new WeakReference<>(mContext);
        movieDetail = movie;
        toShowCraousal = showCraousal;
    }

    public String getMovieTitle()
    {
        return movieDetail.getMovieTitle();
    }

    public String getMovieReleaseDate()
    {
        ReleaseDetail releaseDetail = movieDetail.getReleaseSummary();
        return context.get().getString(R.string.releaseDate) + String.valueOf(releaseDetail.getReleasedDate());
    }

    public String getMovieSynopsis()
    {
        return movieDetail.getMovieSynopsis();
    }

    private boolean isCarausal()
    {
        return toShowCraousal;
    }

    public int getCarousalVisibility()
    {
        return isCarausal() ? View.VISIBLE : View.GONE;
    }

    public boolean showCarousel() {
        return isCarausal();
    }

    public String getMovieCasts()
    {
        StringBuilder castsBuilder = new StringBuilder();
        Iterator<Casts> iterator = movieDetail.getMovieCasts().iterator();
        while (iterator.hasNext())
        {
            Casts casts = iterator.next();
            castsBuilder.append(" " + casts.getCastName());
            if (iterator.hasNext())
            {
                castsBuilder.append(" ,");
            }
            else
            {
                castsBuilder.append(".");
            }
        }
        return castsBuilder.toString();
    }

    public String getImageUrl()
    {
        return movieDetail.getPosterDetail().getMovieThumbnail();
    }

    @BindingAdapter("bind:imageUrl")
    public static void setImageUrl(ImageView view, String imageUrl)
    {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher)
                .into(view);
    }
}
