package com.movies.rottenmovie.boxoffice.adapter;

import android.widget.Filter;

import com.movies.rottenmovie.boxoffice.model.Casts;
import com.movies.rottenmovie.boxoffice.model.Movie;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deepak Pawar on 8/27/2016.
 */
public class ActorDirectorFilter extends Filter
{
    private ArrayList<Movie> orignalBlockBusterList;
    private WeakReference<IProvideFilterCastsMovies> boxOfficeFilterInterfaceRef;

    interface IProvideFilterCastsMovies
    {
        void showCastsMoviesAndNotify(ArrayList<Movie> values);
    }

    public ActorDirectorFilter(IProvideFilterCastsMovies boxofficeMovieAdapter, List<Movie> blockbusterMoviesList)
    {
        orignalBlockBusterList = (ArrayList<Movie>) blockbusterMoviesList;
        boxOfficeFilterInterfaceRef = new WeakReference<>(boxofficeMovieAdapter);
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint)
    {
        FilterResults filterResults = new FilterResults();
        if (constraint != null && constraint.length() > 0)
        {
            ArrayList<Movie> tempList = new ArrayList<>();
            // search content in friend list
            for (Movie movie : orignalBlockBusterList)
            {
                boolean castMatched=false;
                for (Casts movieCasts : movie.getMovieCasts())
                {
                    if (movieCasts.getCastName().toLowerCase().contains(constraint.toString().toLowerCase()))
                    {
                        castMatched=true;
                        break;
                    }
                }
                if(castMatched)
                {
                    tempList.add(movie);

                }
            }
            filterResults.count = tempList.size();
            filterResults.values = tempList;
        }
        else
        {
            filterResults.count = orignalBlockBusterList.size();
            filterResults.values = orignalBlockBusterList;
        }
        return filterResults;
    }

    /**
     * Notify about filtered list to ui
     *
     * @param constraint text
     * @param results    filtered result
     */
    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results)
    {
        boxOfficeFilterInterfaceRef.get().showCastsMoviesAndNotify((ArrayList<Movie>)results.values);
    }
}
