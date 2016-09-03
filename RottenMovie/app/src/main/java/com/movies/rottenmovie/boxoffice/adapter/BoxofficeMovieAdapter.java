package com.movies.rottenmovie.boxoffice.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.movies.rottenmovie.R;
import com.movies.rottenmovie.boxoffice.model.Movie;
import com.movies.rottenmovie.boxoffice.viewmodel.MovieViewModel;
import com.movies.rottenmovie.databinding.MovieItemRowBinding;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deepak Pawar on 8/25/2016.
 */

public class BoxofficeMovieAdapter extends RecyclerView.Adapter<BoxofficeMovieAdapter.BindingHolder> implements Filterable, ActorDirectorFilter.IProvideFilterCastsMovies
{
    private List<Movie> blockbusterMoviesList, blockbusterMoviesFilteredList;
    private WeakReference<Context> mContext;
    private ActorDirectorFilter actorDirectorFilter;

    public BoxofficeMovieAdapter(Context context)
    {
        mContext = new WeakReference<>(context);
        blockbusterMoviesList = new ArrayList<>();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MovieItemRowBinding postBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_item_row, parent, false);
        return new BindingHolder(postBinding);
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position)
    {
        MovieItemRowBinding postBinding = holder.binding;
        postBinding.setMovieModel(new MovieViewModel(mContext.get(), blockbusterMoviesFilteredList.get(position)));
    }

    @Override
    public int getItemCount()
    {
        return blockbusterMoviesFilteredList.size();
    }

    public void setItems(List<Movie> moviesListData)
    {
        blockbusterMoviesList.clear();
        blockbusterMoviesList = moviesListData;
        blockbusterMoviesFilteredList = moviesListData;
        notifyDataSetChanged();
        if (!blockbusterMoviesList.isEmpty())
        {
            getFilter();
        }
    }

    public void addItem(Movie movieItem)
    {
        if (!blockbusterMoviesList.contains(movieItem))
        {
            blockbusterMoviesList.add(movieItem);
            notifyItemInserted(blockbusterMoviesList.size() - 1);
        }
        else
        {
            blockbusterMoviesList.set(blockbusterMoviesList.indexOf(movieItem), movieItem);
            notifyItemChanged(blockbusterMoviesList.indexOf(movieItem));
        }
    }

    @Override
    public Filter getFilter()
    {
        if (actorDirectorFilter == null)
        {
            actorDirectorFilter = new ActorDirectorFilter(this, blockbusterMoviesList);
        }
        return actorDirectorFilter;
    }

    @Override
    public void showCastsMoviesAndNotify(ArrayList<Movie> castsMoviesList)
    {
        blockbusterMoviesFilteredList = castsMoviesList;
        notifyDataSetChanged();
    }

    public static class BindingHolder extends RecyclerView.ViewHolder
    {
        private MovieItemRowBinding binding;

        public BindingHolder(MovieItemRowBinding binding)
        {
            super(binding.cardView);
            this.binding = binding;
        }
    }

}
