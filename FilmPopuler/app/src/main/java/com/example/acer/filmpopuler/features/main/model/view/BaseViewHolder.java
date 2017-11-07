package com.example.acer.filmpopuler.features.main.model.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.acer.filmpopuler.features.main.model.model.MainItem;

/**
 * Created by ACER on 19/10/2017.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    BaseViewHolder (View itemview) {super(itemview);}

    public abstract void bindView(MainItem item);
}
