package org.example.service;

import org.example.model.ArtistSeedRecommendationStrategy;
import org.example.model.GenreRecommendationStrategy;
import org.example.model.InstrumentalGenerationStrategy;
import org.example.model.MoodRecommendationStrategy;
import org.example.model.MusicGenerationStrategy;
import org.example.model.RecommendationStrategy;

public class MusicServiceFactory {

    private final SpotifyService spotifyService;
    private final SunoService sunoService;

    public MusicServiceFactory(SpotifyService spotifyService, SunoService sunoService) {
        this.spotifyService = spotifyService;
        this.sunoService = sunoService;
    }

    // Recommendation modes for the Strategy pattern
    public enum RecommendationMode {
        MOOD,
        GENRE,
        ARTIST
    }

    // Generation modes for the Strategy pattern
    public enum GenerationMode {
        INSTRUMENTAL
    }

    // Existing factory method for recommendation strategies
    public RecommendationStrategy createRecommendationStrategy(RecommendationMode mode) {
        return switch (mode) {
            case MOOD -> new MoodRecommendationStrategy(spotifyService);
            case GENRE -> new GenreRecommendationStrategy(spotifyService);
            case ARTIST -> new ArtistSeedRecommendationStrategy(spotifyService);
        };
    }

    // NEW: factory method for music generation strategies
    public MusicGenerationStrategy createGenerationStrategy(GenerationMode mode) {
        return switch (mode) {
            case INSTRUMENTAL -> new InstrumentalGenerationStrategy(sunoService);
        };
    }
}
