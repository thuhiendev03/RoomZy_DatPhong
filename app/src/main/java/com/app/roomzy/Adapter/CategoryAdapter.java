package com.app.roomzy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.roomzy.Models.CategoriesModel;
import com.app.roomzy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter  extends ArrayAdapter<CategoriesModel> {

    public CategoryAdapter(Context context, ArrayList<CategoriesModel> categories) {
        super(context, 0, categories);
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

        CategoriesModel category = getItem(position);
        if (category != null) {
            ImageView imageView = view.findViewById(R.id.imageView);
            TextView textView = view.findViewById(R.id.textView);

            Picasso.get().load(category.getImage()).into(imageView);
            textView.setText(category.getName());
        }

        return view;
    }
}