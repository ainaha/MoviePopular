package com.example.acer.filmpopuler.features.main.model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.acer.filmpopuler.R;
import  com.example.acer.filmpopuler.data.api.TmdbService;
import  com.example.acer.filmpopuler.data.model.MovieData;
import  com.example.acer.filmpopuler.data.model.MovieDataResponse;
import com.example.acer.filmpopuler.features.contracts.AddOrDeleteFavoriteMovieContract;
import com.example.acer.filmpopuler.features.detail.MovieDetailActivity;
import com.example.acer.filmpopuler.features.main.model.contract.MainlistItemClickListener;
import com.example.acer.filmpopuler.features.main.model.loader.FavoriteMovieListLoader;
import com.example.acer.filmpopuler.features.main.model.model.MainItem;
import com.example.acer.filmpopuler.features.main.model.model.MovieItem;
import com.example.acer.filmpopuler.features.main.model.adapter.MainAdapter;
import com.example.acer.filmpopuler.utils.converter.MovieDataToMainItemConverter;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final int STATE_POPULAR_MOVIE = 0;
    public static final int STATE_FAVORITE_MOVIE = 1;
    public static final int STATE_NOW_PLAYING = 2;

    public static final String KEY_CURRENT_STATE = "CURRENT_STATE";

    TextView mainMessageField;
    ProgressBar mainProgressBar;
    RecyclerView mainListView;

    MainAdapter mainAdapter;

    MainlistItemClickListener mainListItemClickListener;
    GridLayoutManager layoutManager;

    BottomNavigationView bottomNavigationView;

    int currentState = STATE_POPULAR_MOVIE;

    FavoriteBroadcastReceiver favoriteBroadcastReceiver;

    LoaderManager.LoaderCallbacks<List<MovieData>> favoriteMovieLoaderCallback = new LoaderManager.LoaderCallbacks<List<MovieData>>() {
        @Override
        public Loader onCreateLoader(int i, Bundle bundle) {
            return new FavoriteMovieListLoader(MainActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<List<MovieData>> loader, List<MovieData> movies) {
            hideProgressBar();
            if (movies != null && !movies.isEmpty()) {
                showMovieList(
                        MovieDataToMainItemConverter.getMainItemList("Favorite Movies", movies)
                );
            } else {
                showEmptyMovieList();
            }
        }

        @Override
        public void onLoaderReset(Loader loader) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            currentState = savedInstanceState.getInt(KEY_CURRENT_STATE, STATE_POPULAR_MOVIE);
        }

        bindViews();
        setUpMainListView();
        if (currentState == STATE_POPULAR_MOVIE) {
            fetchPopularMovie();
        } else if (currentState == STATE_FAVORITE_MOVIE){
            fetchFavoriteMovie();
        } else {
            fetchNowPlaying();
        }

        favoriteBroadcastReceiver = new FavoriteBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AddOrDeleteFavoriteMovieContract.ACTION_ADD_FAVORITE);
        intentFilter.addAction(AddOrDeleteFavoriteMovieContract.ACTION_DELETE_FAVORITE);
        LocalBroadcastManager.getInstance(this).registerReceiver(favoriteBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(favoriteBroadcastReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CURRENT_STATE, currentState);
    }

    private void bindViews() {
        mainMessageField = findViewById(R.id.main_message);
        mainListView = findViewById(R.id.main_movie_listview);
        mainProgressBar = findViewById(R.id.main_progressbar);
        bottomNavigationView = findViewById(R.id.main_bottom_navigation);
    }

    private void setUpMainListView() {
        mainListItemClickListener = new MainlistItemClickListener() {
            @Override
            public void onMovieItemClick(MovieItem movieItem) {
                MovieDetailActivity.showMovieDetailPage(
                        MainActivity.this,
                        movieItem.getMovieId(),
                        movieItem.getMovieTitle(),
                        movieItem.getPosterPath(),
                        movieItem.getMovieData()
                );
            }
        };
        mainAdapter = new MainAdapter(mainListItemClickListener);
        mainListView.setAdapter(mainAdapter);
        mainListView.setItemAnimator(new DefaultItemAnimator());
        layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position != RecyclerView.NO_POSITION ? mainAdapter.getItemSize(position) : 1;
            }
        });
        mainListView.setLayoutManager(layoutManager);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView
                .OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                final int menuId = menuItem.getItemId();
                if (menuId == R.id.action_popular) {
                    currentState = STATE_POPULAR_MOVIE;
                    fetchPopularMovie();
                } else if (menuId == R.id.action_favorite){
                    currentState = STATE_FAVORITE_MOVIE;
                    fetchFavoriteMovie();
                } else
                    {
                        currentState = STATE_NOW_PLAYING;
                        fetchNowPlaying();
                    }
                    return true;
                }
        });
    }

    private void showMovieList(List<MainItem> mainItemList) {
        mainAdapter.setMainItemsList(mainItemList);
        mainListView.setVisibility(View.VISIBLE);
    }

    private void hideMovieList() {
        mainListView.setVisibility(View.GONE);
    }

    private void showEmptyMovieList() {
        mainAdapter.setMainItemsList(Collections.<MainItem>emptyList());
        showMessage("Empty movie list");
    }

    private void showProgressBar() {
        mainProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mainProgressBar.setVisibility(View.GONE);
    }

    private void showMessage(String message) {
        mainMessageField.setText(message);
        mainMessageField.setVisibility(View.VISIBLE);
    }

    private void hideMessage() {
        mainMessageField.setText(null);
        mainMessageField.setVisibility(View.GONE);
    }

    private void fetchPopularMovie() {
        showProgressBar();
        hideMessage();
        hideMovieList();
        Call<MovieDataResponse> call = TmdbService.open().getMostPopularMovies(1);
        call.enqueue(new Callback<MovieDataResponse>() {
            @Override
            public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                MovieDataResponse data = response.body();
                hideProgressBar();
                if (data != null) {
                    List<MovieData> movies = data.getResults();
                    if (movies != null && !movies.isEmpty()) {
                        showMovieList(
                                MovieDataToMainItemConverter.getMainItemList("Popular Movies", movies)
                        );
                    } else {
                        showEmptyMovieList();
                    }
                } else {
                    showMessage("Something wrong happened");
                }
            }

            @Override
            public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                hideProgressBar();
                showMessage(t.getMessage());
            }
        });
    }

    private void fetchFavoriteMovie() {
        showProgressBar();
        hideMessage();
        hideMovieList();
        getSupportLoaderManager().restartLoader(
                FavoriteMovieListLoader.FAVORITE_MOVIE_LIST_LOADER_ID,
                null,
                favoriteMovieLoaderCallback
        );
    }

    class FavoriteBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                final String action = intent.getAction();
                if (AddOrDeleteFavoriteMovieContract.ACTION_ADD_FAVORITE.equals(action) || AddOrDeleteFavoriteMovieContract.ACTION_DELETE_FAVORITE.equals(action)) {
                    if (currentState == STATE_FAVORITE_MOVIE) {
                        getSupportLoaderManager().restartLoader(
                                FavoriteMovieListLoader.FAVORITE_MOVIE_LIST_LOADER_ID,
                                null,
                                favoriteMovieLoaderCallback
                        );
                    }
                }
            }
        }
    }

    private void fetchNowPlaying() {
        showProgressBar();
        hideMessage();
        hideMovieList();
        Call<MovieDataResponse> call = TmdbService.open().getNowPlaying(1);
        call.enqueue(new Callback<MovieDataResponse>() {
            @Override
            public void onResponse(Call<MovieDataResponse> call, Response<MovieDataResponse> response) {
                MovieDataResponse data = response.body();
                hideProgressBar();
                if (data != null) {
                    List<MovieData> movies = data.getResults();
                    if (movies != null && !movies.isEmpty()) {
                        showMovieList(
                                MovieDataToMainItemConverter.getMainItemList("Now Playing", movies)
                        );
                    } else {
                        showEmptyMovieList();
                    }
                } else {
                    showMessage("Something wrong happened");
                }
            }

            @Override
            public void onFailure(Call<MovieDataResponse> call, Throwable t) {
                hideProgressBar();
                showMessage(t.getMessage());
            }
        });
    }

}