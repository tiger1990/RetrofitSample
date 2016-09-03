package com.movies.rottenmovie.boxoffice.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Deepak Pawar on 8/26/2016.
 */

public class ReleaseDetail implements Parcelable
{
    @SerializedName("theater")
    public String releasedDate;

    public String getReleasedDate()
    {
        return parseDate(releasedDate);
    }

    public String parseDate(String releasedDate)
    {
        SimpleDateFormat orignalDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat convertedDateFormat = new SimpleDateFormat("MMMM dd, yyyy", Locale.ENGLISH);
        Date date = null;
        try
        {
            date = orignalDateFormat.parse(releasedDate);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date == null ? releasedDate : convertedDateFormat.format(date);
    }


    @Override
    public int describeContents()
    {
        return 0;
    }

    protected ReleaseDetail(Parcel in)
    {
        releasedDate = in.readString();
    }

    public ReleaseDetail()
    {

    }


    @Override
    public void writeToParcel(Parcel parcel, int i)
    {
        parcel.writeString(releasedDate);
    }

    public static final Creator<ReleaseDetail> CREATOR = new Creator<ReleaseDetail>()
    {
        @Override
        public ReleaseDetail createFromParcel(Parcel in)
        {
            return new ReleaseDetail(in);
        }

        @Override
        public ReleaseDetail[] newArray(int size)
        {
            return new ReleaseDetail[size];
        }
    };
}
