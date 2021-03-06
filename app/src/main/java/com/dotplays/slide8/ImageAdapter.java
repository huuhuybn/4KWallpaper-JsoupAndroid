package com.dotplays.slide8;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.dotplays.slide8.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    ArrayList<Photo> list;

    public ImageAdapter(ArrayList<Photo> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row, viewGroup, false);

        Picasso.get()
                .load(list.get(i).getUrlThumb())
                .into((ImageView) view.findViewById(R.id.img));

        return view;
    }
}
