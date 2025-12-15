package org.example.model;

import org.example.service.SpotifyService;

import java.io.IOException;
import java.util.List;

public class MoodRecommendationStrategy implements RecommendationStrategy {

    private final SpotifyService spotifyService;

    public MoodRecommendationStrategy(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @Override
    public List<Track> getRecommendations(UserQuery query) throws IOException {
        // Uses mood if present, otherwise falls back to query text
        return spotifyService.getRecommendationsForMood(query);
    }
}
