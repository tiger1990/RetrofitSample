package com.movies.rottenmovie;

import android.content.Context;

import com.movies.rottenmovie.boxoffice.model.Movie;
import com.movies.rottenmovie.boxoffice.viewmodel.MovieDetailViewModel;
import com.movies.rottenmovie.util.DefaultConfig;
import com.movies.rottenmovie.utility.BlockBusterProviderUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import java.net.URLEncoder;
import static junit.framework.Assert.assertEquals;

/**
 * Created by Deepak Pawar on 8/27/2016.
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK, manifest = DefaultConfig.MANIFEST)
public class MovieDetailViewModelTest
{
    private Context mContext;
    private MovieDetailViewModel movieViewModel;
    private Movie movieProduced;

    @Before
    public void setUp()
    {
        mContext = RuntimeEnvironment.application;
        movieProduced = BlockBusterProviderUtil.produceMovie();
        movieViewModel = new MovieDetailViewModel(mContext, movieProduced,true);
    }

    @Test
    public void shouldGetMovieTitle() throws Exception
    {
        assertEquals(movieViewModel.getMovieTitle(), movieProduced.movieTitle);
    }

    @Test
    public void shouldGetMovieThumbnail() throws Exception
    {
        assertEquals(URLEncoder.encode(movieViewModel.getImageUrl(),"UTF-8"), URLEncoder.encode(movieProduced.posterDetail.movieThumbnail,"UTF-8"));
    }

    @Test
    public void shouldGetMovieReleaseDate() throws Exception
    {
        String movieProducedReleaseDate = String.format("%s%s", mContext.getString(R.string.releaseDate), movieProduced.releaseSummary.parseDate(movieProduced.releaseSummary.releasedDate));
        assertEquals(movieViewModel.getMovieReleaseDate(), movieProducedReleaseDate);
    }


}
