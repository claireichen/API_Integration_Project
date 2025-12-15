package org.example.model;

import org.example.service.SpotifyService;

import java.io.IOException;
import java.util.List;

public class ArtistSeedRecommendationStrategy implements RecommendationStrategy {

    private final SpotifyService spotifyService;

    public ArtistSeedRecommendationStrategy(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @Override
    public List<Track> getRecommendations(UserQuery query) throws IOException {
        return spotifyService.getRecommendationsForArtist(query);
    }
}
