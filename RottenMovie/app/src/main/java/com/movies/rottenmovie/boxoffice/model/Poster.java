package com.movies.rottenmovie.boxoffice.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Deepak Pawar on 8/26/2016.
 */

public class Poster implements Parcelable
{
    @SerializedName("thumbnail")
    public String movieThumbnail;

    public String getMovieThumbnail()
    {
        return movieThumbnail;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.movieThumbnail);
    }

    private Poster(Parcel in)
    {
        this.movieThumbnail = in.readString();
    }

    public Poster()
    {

    }

    public static final Parcelable.Creator<Poster> CREATOR = new Parcelable.Creator<Poster>()
    {
        public Poster createFromParcel(Parcel source)
        {
            return new Poster(source);
        }

        public Poster[] newArray(int size)
        {
            return new Poster[size];
        }
    };
}
