package com.example.acer.filmpopuler.features.main.model.model;

import com.example.acer.filmpopuler.R;
import com.example.acer.filmpopuler.data.model.MovieData;

/**
 * Created by ACER on 19/10/2017.
 */

public interface MovieItem extends MainItem {
    String getMovieId();

    String getMovieTitle();

    String getPosterPath();

    MovieData getMovieData();
}
