package com.movies.rottenmovie.utility;

import com.movies.rottenmovie.boxoffice.model.Casts;
import com.movies.rottenmovie.boxoffice.model.Movie;
import com.movies.rottenmovie.boxoffice.model.Poster;
import com.movies.rottenmovie.boxoffice.model.ReleaseDetail;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Deepak Pawar on 8/27/2016.
 */

public class BlockBusterProviderUtil
{

    public static Long generateRandomLong()
    {
        return new Random().nextLong();
    }

    public static String generateRandomString()
    {
        return UUID.randomUUID().toString();
    }

    public static Movie produceMovie()
    {
        //movie model
        Movie movie = new Movie();
        movie.movieId = String.valueOf(generateRandomLong());
        movie.movieTitle = generateRandomString();
        movie.movieYear = "2016";
        movie.movieSynopsis = generateRandomString();

        //poster model
        movie.posterDetail = createPosterDetail();

        //release detail
        movie.releaseSummary = createReleseDetail();

        //casts details
        movie.movieCasts = generateCastsList(5);
        return movie;
    }

    public static ArrayList<Casts> generateCastsList(int count)
    {
        ArrayList<Casts> castsList = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            castsList.add(createCastDetail());
        }
        return castsList;
    }

    private static Casts createCastDetail()
    {
        Casts casts = new Casts();
        casts.castName = generateRandomString();
        casts.castId = String.valueOf(generateRandomLong());
        casts.characters = generateCharacters(2);

        return casts;
    }

    private static ArrayList<String> generateCharacters(int count)
    {
        ArrayList<String> characterPlayed = new ArrayList<>();
        for (int i = 0; i < count; i++)
        {
            characterPlayed.add(generateRandomString());
        }
        return characterPlayed;
    }


    public static Poster createPosterDetail()
    {
        Poster moviePoster = new Poster();
        moviePoster.movieThumbnail = "https://resizing.flixster.com/TJ4b-9w0IulKxCZHSQNT4TnVZmY=/54x80/v1.bTsxMjA2MTc4MTtqOzE3MDg1OzIwNDg7NjQ4Ozk2MA";
        return moviePoster;
    }

    public static ReleaseDetail createReleseDetail()
    {
        ReleaseDetail releaseDetail = new ReleaseDetail();
        releaseDetail.releasedDate = "2016-08-05";

        return releaseDetail;
    }

}
