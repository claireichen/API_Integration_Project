package org.example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppModel {

    private final List<Track> currentTracks = new ArrayList<>();
    private GenerationResult lastGenerationResult;
    private UserQuery lastQuery;   // <-- NEW

    public List<Track> getCurrentTracks() {
        return Collections.unmodifiableList(currentTracks);
    }

    public void setCurrentTracks(List<Track> tracks) {
        currentTracks.clear();
        if (tracks != null) {
            currentTracks.addAll(tracks);
        }
    }

    public GenerationResult getLastGenerationResult() {
        return lastGenerationResult;
    }

    public void setLastGenerationResult(GenerationResult lastGenerationResult) {
        this.lastGenerationResult = lastGenerationResult;
    }

    public UserQuery getLastQuery() {           // <-- NEW
        return lastQuery;
    }

    public void setLastQuery(UserQuery lastQuery) {  // <-- NEW
        this.lastQuery = lastQuery;
    }
}
