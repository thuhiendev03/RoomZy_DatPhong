package com.app.roomzy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.app.roomzy.Models.LocationModel;
import com.app.roomzy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LocationAdapter  extends ArrayAdapter<LocationModel> {

    public LocationAdapter(Context context, ArrayList<LocationModel> locations) {
        super(context, 0, locations);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, R.layout.spinner_item);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, R.layout.spinner_item);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent, int resource) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        LocationModel location = getItem(position);
        if (location != null) {
            ImageView imageView = view.findViewById(R.id.imageView);
            TextView textView = view.findViewById(R.id.textView);

            Picasso.get().load(location.getImageUrl()).into(imageView);
            textView.setText(location.getName());
        }

        return view;
    }
}