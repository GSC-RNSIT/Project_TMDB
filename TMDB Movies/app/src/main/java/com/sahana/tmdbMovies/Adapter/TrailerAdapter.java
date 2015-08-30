package com.sahana.tmdbMovies.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sahana on 18-Jul-15.
 */
public class TrailerAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> videoURLs;
    private int resource;

    public TrailerAdapter(Context context, int resource, List<String> videoURLs) {
        super(context, resource, videoURLs);
        this.context = context;
        this.resource = resource;
        this.videoURLs = videoURLs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, parent, false);
        ((TextView)convertView.findViewById(android.R.id.text1)).setText("Trailer " + (position + 1));
        convertView.setTag(videoURLs.get(position));
        return convertView;
    }
}
