package com.example.acer.filmpopuler.features.main.model.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.acer.filmpopuler.R;
import com.example.acer.filmpopuler.data.api.TmdbConstant;
import com.example.acer.filmpopuler.features.main.model.adapter.MainAdapter;
import com.example.acer.filmpopuler.features.main.model.model.MainItem;
import com.example.acer.filmpopuler.features.main.model.model.MovieItem;
import com.example.acer.filmpopuler.features.main.model.contract.MovieItemClickListener;
import com.example.acer.filmpopuler.features.main.model.model.StandardMovieItem;
import com.squareup.picasso.Picasso;

/**
 * Created by ACER on 19/10/2017.
 */

public class MovieViewHolder extends BaseViewHolder {
    private static final String SMALL_POSTER_SIZE = "w342/";
    private static final String MEDIUM_POSTER_SIZE = "w500/";

    private ImageView posterImageView;
    private MovieItemClickListener listener;
    private String imageSize;

    public MovieViewHolder(View itemview, MovieItemClickListener itemListener) {
        super(itemview);
        this.listener = itemListener;

        setupViews(itemview);
    }


    private void setupViews(View itemview) {
        posterImageView = itemview.findViewById(R.id.movie_item_poster_image);
        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pos = getAdapterPosition();
                if (listener != null && pos != RecyclerView.NO_POSITION) {
                    listener.onMovieItemClicked(pos);
                }
            }
        });
    }

    private void setImageSize(int position){
        imageSize = positionDivisibleByFive(position) ? MEDIUM_POSTER_SIZE :SMALL_POSTER_SIZE;
    }
    private boolean positionDivisibleByFive(int position){
        return position !=  RecyclerView.NO_POSITION &&(position % 5==0);

    }
    @Override
    public void bindView(MainItem items){
        StandardMovieItem standardMovieItem = (StandardMovieItem) items;
        setImageSize(getAdapterPosition());
        Picasso.with(itemView.getContext())
                .load(TmdbConstant.IMAGE_BASE_URL+ imageSize +standardMovieItem.getPosterPath())
                .placeholder(R.drawable.ic_image_black_24dp)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(posterImageView);

    }

}
