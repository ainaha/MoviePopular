package com.example.acer.filmpopuler.features.main.model.model;

import com.example.acer.filmpopuler.R;

/**
 * Created by ACER on 24/10/2017.
 */

public class HeaderItem implements MainItem {
    public final String headerTitle;

    public HeaderItem(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    @Override
    public int getType() {
        return R.layout.main_header_item;
    }

    @Override
    public int getItemSize() {
        return 2;
    }
}
