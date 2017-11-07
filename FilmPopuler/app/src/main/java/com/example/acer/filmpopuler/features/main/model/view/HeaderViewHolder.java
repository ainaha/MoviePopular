package com.example.acer.filmpopuler.features.main.model.view;

import android.view.View;
import android.widget.TextView;

import com.example.acer.filmpopuler.R;
import com.example.acer.filmpopuler.features.main.model.model.HeaderItem;
import com.example.acer.filmpopuler.features.main.model.model.MainItem;

/**
 * Created by ACER on 24/10/2017.
 */

public class HeaderViewHolder extends BaseViewHolder {

    TextView headerField;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        headerField = itemView.findViewById(R.id.main_header_field);
    }

    @Override
    public void bindView(MainItem item) {
        final HeaderItem headerItem = (HeaderItem) item;
        headerField.setText(headerItem.getHeaderTitle());
    }
}
