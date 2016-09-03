package com.movies.rottenmovie.boxoffice.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

/**
 * Created by Deepak Pawar on 8/26/2016.
 */

public class Movie implements Parcelable
{
    @SerializedName("id")
    public String movieId;

    @SerializedName("title")
    public String movieTitle;

    @SerializedName("year")
    public String movieYear;

    @SerializedName("synopsis")
    public String movieSynopsis;

    public String getMovieId()
    {
        return movieId;
    }

    public String getMovieTitle()
    {
        return movieTitle;
    }

    public String getMovieYear()
    {
        return movieYear;
    }

    public String getMovieSynopsis()
    {
        return movieSynopsis;
    }

    /*****************/
    @SerializedName("release_dates")
    public ReleaseDetail releaseSummary;

    public ReleaseDetail getReleaseSummary()
    {
        return releaseSummary;
    }


    @SerializedName("posters")
    public Poster posterDetail;

    public Poster getPosterDetail()
    {
        return posterDetail;
    }

    @SerializedName("abridged_cast")
    public ArrayList<Casts> movieCasts;

    public ArrayList<Casts> getMovieCasts()
    {
        return movieCasts;
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.movieId);
        dest.writeString(this.movieTitle);
        dest.writeString(this.movieYear);
        dest.writeString(this.movieSynopsis);
        dest.writeParcelable(releaseSummary, flags);
        dest.writeTypedList(movieCasts);
        dest.writeParcelable(posterDetail, flags);
    }

    public Movie()
    {

    }

    private Movie(Parcel in)
    {
        this.movieId=in.readString();
        this.movieTitle = in.readString();
        this.movieYear = in.readString();
        this.movieSynopsis = in.readString();
        releaseSummary = in.readParcelable(ReleaseDetail.class.getClassLoader());

        movieCasts=new ArrayList<>();
        in.readTypedList(movieCasts, Casts.CREATOR);
        posterDetail = in.readParcelable(Poster.class.getClassLoader());
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>()
    {
        public Movie createFromParcel(Parcel source)
        {
            return new Movie(source);
        }

        public Movie[] newArray(int size)
        {
            return new Movie[size];
        }
    };


    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        Movie movie = (Movie) o;

        if (movieTitle != null ? !movieTitle.equals(movie.movieTitle) : movie.movieTitle != null)
        {
            return false;
        }
        if (movieId != null ? !movieId.equals(movie.movieId) : movie.movieId != null)
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int result = movieTitle != null ? movieTitle.hashCode() : 0;
        result = 31 * result + (movieId != null ? movieId.hashCode() : 0);
        return result;
    }
}
