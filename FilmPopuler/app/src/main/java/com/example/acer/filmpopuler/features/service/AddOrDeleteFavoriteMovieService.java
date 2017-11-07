package com.example.acer.filmpopuler.features.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.example.acer.filmpopuler.data.db.DatabaseHelper;
import com.example.acer.filmpopuler.data.db.contract.MovieRepository;
import com.example.acer.filmpopuler.data.model.MovieData;

import static com.example.acer.filmpopuler.features.contracts.AddOrDeleteFavoriteMovieContract.ACTION_ADD_FAVORITE;
import static com.example.acer.filmpopuler.features.contracts.AddOrDeleteFavoriteMovieContract.ACTION_DELETE_FAVORITE;
import static com.example.acer.filmpopuler.features.contracts.AddOrDeleteFavoriteMovieContract.EXTRA_MOVIE_DATA;
import static com.example.acer.filmpopuler.features.contracts.AddOrDeleteFavoriteMovieContract.EXTRA_MOVIE_ID;

/**
 * Created by ACER on 07/11/2017.
 */

public class AddOrDeleteFavoriteMovieService extends IntentService {
    private static final String TAG = "AddOrDeleteFavoriteMovi";
    private MovieRepository movieRepository;

    public AddOrDeleteFavoriteMovieService() {
        super(TAG);
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public AddOrDeleteFavoriteMovieService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        movieRepository = DatabaseHelper.getInstance(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            Log.d(TAG, "onHandleIntent: " + action);
            if (ACTION_ADD_FAVORITE.equals(action)) {
                MovieData movieData = intent.getParcelableExtra(EXTRA_MOVIE_DATA);
                if (movieData != null) {
                    movieRepository.addFavoriteMovie(movieData);
                }
            } else if (ACTION_DELETE_FAVORITE.equals(action)) {
                String movieId = intent.getStringExtra(EXTRA_MOVIE_ID);
                if (!TextUtils.isEmpty(movieId)) {
                    movieRepository.removeFavoriteMovie(movieId);
                }
            }
            LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(action));
        }
    }
}