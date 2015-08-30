package com.sahana.tmdbMovies.Fragment;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sahana.tmdbMovies.Movie;
import com.sahana.tmdbMovies.R;
import com.sahana.tmdbMovies.Adapter.TrailerAdapter;
import com.squareup.picasso.Picasso;

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
public class DetailFragment extends Fragment {
    private TextView titleView;
    private ImageView posterView;
    private TextView year;
    private TextView rating;
    private TextView overviewView;
    private ListView listView;
    private JSONArray jsonArray;
    private String API_KEY = "10a315e85dbf93204f393b7a373a6035";
    private String POSTER = "http://image.tmdb.org/t/p/w500";
    private String URL = "http://api.themoviedb.org/3/movie/%s/videos?api_key=" + API_KEY;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.from(container.getContext()).inflate(R.layout.fragment_detail, container, false);

        titleView = (TextView) view.findViewById(R.id.title);
        posterView = (ImageView) view.findViewById(R.id.poster);
        year = (TextView) view.findViewById(R.id.year);
        rating = (TextView) view.findViewById(R.id.rating);
        overviewView = (TextView) view.findViewById(R.id.overview);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setEmptyView(view.findViewById(R.id.progressbar));

        Movie movie = getActivity().getIntent().getParcelableExtra("MOVIE");
        titleView.setText(movie.getTitle());
        Picasso.with(getActivity()).load(POSTER + movie.getPoster()).into(posterView);
        year.setText(movie.getRelease());
        rating.setText(movie.getVote() + "/10");
        overviewView.setText(movie.getOverview());

        new fetchData().execute(String.format(URL, movie.getId()));

        return view;
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
                List<String> videoURLs = new ArrayList<String>();
                for(int i = 0; i<jsonArray.length() - 1; i++) {
                    videoURLs.add(((JSONObject) jsonArray.get(i)).get("key").toString());
                }
                listView.setAdapter(new TrailerAdapter(getActivity(), android.R.layout.simple_list_item_1, videoURLs));
            } catch (JSONException e) {
                e.printStackTrace();
            } catch(Exception e ) {
                e.printStackTrace();
            }
        }
    }
}
