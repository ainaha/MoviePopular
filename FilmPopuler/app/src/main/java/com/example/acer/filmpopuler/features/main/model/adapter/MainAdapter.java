package com.example.acer.filmpopuler.features.main.model.adapter;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.acer.filmpopuler.R;
import com.example.acer.filmpopuler.features.main.model.model.MainItem;
import com.example.acer.filmpopuler.features.main.model.model.MovieItem;
import com.example.acer.filmpopuler.features.main.model.contract.MainlistItemClickListener;
import com.example.acer.filmpopuler.features.main.model.view.BaseViewHolder;
import com.example.acer.filmpopuler.features.main.model.contract.MovieItemClickListener;
import com.example.acer.filmpopuler.features.main.model.view.BigMovieViewHolder;
import com.example.acer.filmpopuler.features.main.model.view.HeaderViewHolder;
import com.example.acer.filmpopuler.features.main.model.view.MovieViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ACER on 19/10/2017.
 */

public class MainAdapter  extends RecyclerView.Adapter<BaseViewHolder>{

    private List<Movie> movies;
    private List<MainItem> mainItemsList;
    private MainlistItemClickListener mainlistItemClickListener;
    private MovieItemClickListener movieItemClickListener;

    public MainAdapter(MainlistItemClickListener mainlistItemClickListener) {
        this.mainlistItemClickListener = mainlistItemClickListener;
        this.mainItemsList = new ArrayList<>();
        setupMovieItemClickListener();
    }

    private  void setupMovieItemClickListener(){
        movieItemClickListener = new MovieItemClickListener() {
            @Override
            public void onMovieItemClicked(int position) {
                MovieItem item = (MovieItem) mainItemsList.get(position);
                if(mainlistItemClickListener != null){
                    mainlistItemClickListener.onMovieItemClick(item);
                }
            }
        };

    }

    public void setMainItemsList(List<MainItem> mainItemsList) {
        this.mainItemsList = mainItemsList;
        notifyDataSetChanged();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {return mainItemsList.get(position).getType();}

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(viewType, viewGroup, false);
        if (viewType == R.layout.main_movie_item) {
            return new MovieViewHolder(view, movieItemClickListener);
        } else if (viewType == R.layout.main_big_movie_item) {
            return new BigMovieViewHolder(view, movieItemClickListener);
        } else if (viewType == R.layout.main_header_item) {
            return new HeaderViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int pos) {
        baseViewHolder.bindView(mainItemsList.get(pos));

    }

    @Override
    public int getItemCount() {
        return mainItemsList.size();
    }

    public int getItemSize(int position) {
        if (position >= mainItemsList.size() || position < 0) {
            return 0;
        } else {
            return mainItemsList.get(position).getItemSize();
        }
    }
}
