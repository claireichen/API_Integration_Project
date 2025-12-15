package org.example.model;

import java.util.List;

public class Session {

    private UserQuery query;
    private List<Track> tracks;

    public Session() {
    }

    public Session(UserQuery query, List<Track> tracks) {
        this.query = query;
        this.tracks = tracks;
    }

    public UserQuery getQuery() {
        return query;
    }

    public void setQuery(UserQuery query) {
        this.query = query;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
