package com.sahana.tmdbMovies.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sahana.tmdbMovies.Activity.DetailActivity;
import com.sahana.tmdbMovies.Adapter.ImageAdapter;
import com.sahana.tmdbMovies.Movie;
import com.sahana.tmdbMovies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sahana on 09-Jul-15.
 */
public class DiscoverFragment extends Fragment implements AdapterView.OnItemClickListener {
    private String API_KEY = "10a315e85dbf93204f393b7a373a6035";
    private String URL = "http://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY;
    private JSONArray jsonArray;
    private GridView grid;
    private ImageAdapter imageAdapter;
    private List<Movie> movies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.from(container.getContext()).inflate(R.layout.fragment_discover, container, false);

        grid = (GridView) view.findViewById(R.id.gridview);
        grid.setVisibility(View.INVISIBLE);
        grid.setOnItemClickListener(this);
        grid.setEmptyView(view.findViewById(R.id.progressbar));

        if(savedInstanceState == null)
            new fetchData().execute(URL);
        else {
            ArrayList<Parcelable> parcelables = savedInstanceState.getParcelableArrayList("MOVIES");
            movies = new ArrayList<Movie>(parcelables.size());
            for(Parcelable p : parcelables) {
                movies.add((Movie)p);
            }
            imageAdapter = new ImageAdapter(getActivity(), movies);
            grid.setAdapter(imageAdapter);
        }

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("MOVIE", (Movie)imageAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("MOVIES", (ArrayList<? extends Parcelable>) movies);
    }

    private class fetchData extends AsyncTask<String, Integer, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject json = null;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Accept", "application/json");

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(in));

                StringBuilder builder = new StringBuilder();
                String line;
                while((line = br.readLine()) != null)
                    builder.append(line);

                json = new JSONObject(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                jsonArray = (JSONArray)json.get("results");
                movies = new ArrayList<>(jsonArray.length());
                for(int i = 0; i<jsonArray.length() - 1; i++) {
                    movies.add(new Movie(jsonArray.getJSONObject(i)));
                }
                imageAdapter = new ImageAdapter(getActivity(), movies);
                grid.setAdapter(imageAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            } catch(Exception e ) {
                e.printStackTrace();
            }
        }
    }
}
