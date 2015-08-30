package com.sahana.tmdbMovies;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sahana on 19-Jul-15.
 */
public class Movie implements Parcelable {
    private String title;
    private String release;
    private String vote;
    private String overview;
    private String posterURL;
    private String id;

    protected Movie(Parcel in) {
        title = in.readString();
        release = in.readString();
        vote = in.readString();
        overview = in.readString();
        posterURL = in.readString();
        id = in.readString();
    }

    public Movie(JSONObject json) {
        try {
            title = json.get("original_title").toString();
            release = json.get("release_date").toString();
            vote = json.get("vote_average").toString();
            overview = json.get("overview").toString();
            posterURL = json.get("poster_path").toString();
            id = json.get("id").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(release);
        dest.writeString(vote);
        dest.writeString(overview);
        dest.writeString(posterURL);
        dest.writeString(id);
    }

    public String getTitle() {
        return title;
    }

    public String getRelease() {
        return release;
    }

    public String getVote() {
        return vote;
    }

    public String getOverview() {
        return overview;
    }

    public String getPoster() {
        return posterURL;
    }

    public String getId() {
        return id;
    }
}
