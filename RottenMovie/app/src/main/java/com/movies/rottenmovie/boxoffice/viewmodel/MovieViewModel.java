package com.movies.rottenmovie.boxoffice.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.movies.rottenmovie.MovieDetailActivity;
import com.movies.rottenmovie.R;
import com.movies.rottenmovie.boxoffice.model.Casts;
import com.movies.rottenmovie.boxoffice.model.Movie;
import com.movies.rottenmovie.boxoffice.model.ReleaseDetail;
import com.squareup.picasso.Picasso;
import java.lang.ref.WeakReference;
import java.util.Iterator;

/**
 * Created by Deepak Pawar on 8/25/2016.
 */

public class MovieViewModel extends BaseObservable
{
    private WeakReference<Context> context;
    private Movie movieDetail;

    public MovieViewModel(Context mContext, Movie movie)
    {
        context = new WeakReference<>(mContext);
        movieDetail = movie;
    }

    public String getMovieTitle()
    {
        return movieDetail.getMovieTitle();
    }

    public String getMovieReleaseDate()
    {
        ReleaseDetail releaseDetail = movieDetail.getReleaseSummary();
        return context.get().getString(R.string.releaseDate) + releaseDetail.getReleasedDate();
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

    public View.OnClickListener onThumbnailClick()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchDetailActivity(true);
            }
        };
    }

    private void launchDetailActivity(boolean showCrausal)
    {
        context.get().startActivity(MovieDetailActivity.getStartDetailIntent(context.get(), showCrausal, movieDetail));
    }

    public View.OnClickListener onClickMovieRow()
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                launchDetailActivity(false);
            }
        };
    }

    public String getImageUrl() {

        return movieDetail.getPosterDetail().getMovieThumbnail();
    }

    @BindingAdapter("bind:imageUrl")
    public static void setImageUrl(ImageView view, String imageUrl)
    {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher)
                .resizeDimen(R.dimen.movietileWidth, R.dimen.movietileHeight)
                .into(view);
    }

}
