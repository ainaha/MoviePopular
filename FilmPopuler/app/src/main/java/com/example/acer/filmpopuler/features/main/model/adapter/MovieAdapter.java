package com.example.acer.filmpopuler.features.main.model.adapter;

import android.content.Context;
import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.acer.filmpopuler.R;
import com.example.acer.filmpopuler.features.main.model.view.BaseViewHolder;
import com.example.acer.filmpopuler.features.main.model.view.ListViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ACER on 24/10/2017.
 */

public class MovieAdapter   {
   /* private List<Movie> movies;
    private int rowLayout;
    private Context context;



    public MovieAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ListViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {
        holder.movieTitle.setText(movies.get(position).getTitle());
        holder.data.setText(movies.get(position).getReleaseDate());
        holder.movieDescription.setText(movies.get(position).getOverview());
        holder.rating.setText(movies.get(position).getVoteAverage().toString());
        Picasso.with(context).load("https://image.tmdb.org/t/p/w300_and_h450_bestv2" + movies.get(position).getBackdropPath()).resize(200, 250).into(holder.backbg);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

*/
}
