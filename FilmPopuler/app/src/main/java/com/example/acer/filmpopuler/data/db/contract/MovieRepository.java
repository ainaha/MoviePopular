package com.example.acer.filmpopuler.data.db.contract;

import com.example.acer.filmpopuler.data.model.MovieData;

import java.util.List;

/**
 * Created by ACER on 26/10/2017.
 */

public interface MovieRepository {

    List<MovieData> getFavoriteMovie();

    void addFavoriteMovie(MovieData movieData);

    boolean isMovieFavored(String movieId);

    void updateFavoriteMovie(MovieData movieData);

    void removeFavoriteMovie(String movieId);
}
