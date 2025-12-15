package org.example.service;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MusicServiceFactoryTest {

    @Test
    void createsMoodRecommendationStrategyForMoodMode() {
        MusicServiceFactory factory = new MusicServiceFactory(null, null);

        RecommendationStrategy strategy =
                factory.createRecommendationStrategy(MusicServiceFactory.RecommendationMode.MOOD);

        assertNotNull(strategy);
        assertTrue(strategy instanceof MoodRecommendationStrategy);
    }

    @Test
    void createsGenreRecommendationStrategyForGenreMode() {
        MusicServiceFactory factory = new MusicServiceFactory(null, null);

        RecommendationStrategy strategy =
                factory.createRecommendationStrategy(MusicServiceFactory.RecommendationMode.GENRE);

        assertNotNull(strategy);
        assertTrue(strategy instanceof GenreRecommendationStrategy);
    }

    @Test
    void createsArtistSeedRecommendationStrategyForArtistMode() {
        MusicServiceFactory factory = new MusicServiceFactory(null, null);

        RecommendationStrategy strategy =
                factory.createRecommendationStrategy(MusicServiceFactory.RecommendationMode.ARTIST);

        assertNotNull(strategy);
        assertTrue(strategy instanceof ArtistSeedRecommendationStrategy);
    }

    @Test
    void createsInstrumentalGenerationStrategyForInstrumentalMode() {
        MusicServiceFactory factory = new MusicServiceFactory(null, null);

        MusicGenerationStrategy strategy =
                factory.createGenerationStrategy(MusicServiceFactory.GenerationMode.INSTRUMENTAL);

        assertNotNull(strategy);
        assertTrue(strategy instanceof InstrumentalGenerationStrategy);
    }
}
