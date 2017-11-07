package com.example.acer.filmpopuler.features.main.model.loader;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.acer.filmpopuler.data.db.DatabaseHelper;
import com.example.acer.filmpopuler.data.db.contract.MovieRepository;
import com.example.acer.filmpopuler.data.model.MovieData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACER on 07/11/2017.
 */

public class FavoriteMovieListLoader extends AsyncTaskLoader<List<MovieData>> {
    public static final int FAVORITE_MOVIE_LIST_LOADER_ID = 201;
    private static final String TAG = "FavoriteMovieLoader";
    MovieRepository movieRepository;
    List<MovieData> movieDataList;

    public FavoriteMovieListLoader(Context context) {
        super(context);
        movieRepository = DatabaseHelper.getInstance(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (movieDataList != null) {
            deliverResult(movieDataList);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<MovieData> loadInBackground() {
        List<MovieData> results = new ArrayList<>();
        try {
            results.addAll(movieRepository.getFavoriteMovie());
        } catch (Exception e) {
            Log.e(TAG, "loadInBackground: ", e);
        }
        return results;
    }

    @Override
    public void deliverResult(List<MovieData> data) {
        movieDataList = data;
        super.deliverResult(data);
    }
}