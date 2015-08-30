package com.sahana.tmdbMovies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sahana.tmdbMovies.Movie;
import com.sahana.tmdbMovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sahana on 09-Jul-15.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private String POSTER = "http://image.tmdb.org/t/p/w500";
    private List<Movie> movies;

    public ImageAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_poster, parent, false);
        Picasso.with(context).load(POSTER + ((Movie) getItem(position)).getPoster()).into(view);
        return view;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}