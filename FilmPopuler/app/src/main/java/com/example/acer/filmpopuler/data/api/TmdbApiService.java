package com.example.acer.filmpopuler.data.api;


import com.example.acer.filmpopuler.data.model.MovieDataResponse;
import com.example.acer.filmpopuler.data.model.MovieDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by ACER on 18/10/2017.
 */

public interface TmdbApiService {
    @GET("movie/popular")
    Call<MovieDataResponse> getMostPopularMovies(@Query("page") int page);

    @GET("movie/{movie_id}")
    Call<MovieDetail> getMovieDetails(@Path("movie_id") String movieid);

    @GET("movie/now_playing")
    Call<MovieDataResponse> getNowPlaying (@Query("page") int page);



}
