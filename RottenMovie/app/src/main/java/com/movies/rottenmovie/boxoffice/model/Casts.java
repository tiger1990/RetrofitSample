package com.movies.rottenmovie.boxoffice.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deepak Pawar on 8/26/2016.
 */

public class Casts implements Parcelable
{
    @SerializedName("name")
    public String castName;

    @SerializedName("id")
    public String castId;

    @SerializedName("characters")
    public List<String> characters;


    public String getCastName()
    {
        return castName;
    }

    public String getCastId()
    {
        return castId;
    }

    public List<String> getCharacters()
    {
        return characters;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(this.castName);
        dest.writeStringList(this.characters);
    }

    private Casts(Parcel in)
    {
        this.castName = in.readString();
        characters=new ArrayList<>();
        in.readStringList(characters);
    }

    public Casts()
    {

    }

    public static final Parcelable.Creator<Casts> CREATOR = new Parcelable.Creator<Casts>()
    {
        public Casts createFromParcel(Parcel source)
        {
            return new Casts(source);
        }

        public Casts[] newArray(int size)
        {
            return new Casts[size];
        }
    };
}
