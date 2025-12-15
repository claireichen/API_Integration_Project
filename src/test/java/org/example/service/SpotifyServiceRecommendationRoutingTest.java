package org.example.service;

import org.example.model.APIClient;
import org.example.model.Track;
import org.example.model.UserQuery;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SpotifyServiceRecommendationRoutingTest {

    static class RecordingSpotifyService extends SpotifyService {

        UserQuery lastSearchQuery;

        RecordingSpotifyService() {
            super(APIClient.getInstance());
        }

        @Override
        public List<Track> searchTracks(UserQuery query) {
            this.lastSearchQuery = query;
            return Collections.emptyList();
        }
    }

    @Test
    void moodRecommendationsUseMoodAsSearchTextWhenPresent() throws IOException {
        RecordingSpotifyService service = new RecordingSpotifyService();

        UserQuery q = new UserQuery()
                .setText("ignore text")
                .setMood("chill mood");

        service.getRecommendationsForMood(q);

        assertNotNull(service.lastSearchQuery);
        assertEquals("chill mood", service.lastSearchQuery.getText());
    }

    @Test
    void genreRecommendationsUseGenreAsSearchTextWhenPresent() throws IOException {
        RecordingSpotifyService service = new RecordingSpotifyService();

        UserQuery q = new UserQuery()
                .setText("ignore text")
                .setGenre("jazz");

        service.getRecommendationsForGenre(q);

        assertNotNull(service.lastSearchQuery);
        assertEquals("jazz", service.lastSearchQuery.getText());
    }

    @Test
    void artistRecommendationsUseArtistAsSearchTextWhenPresent() throws IOException {
        RecordingSpotifyService service = new RecordingSpotifyService();

        UserQuery q = new UserQuery()
                .setText("ignore text")
                .setArtist("Radiohead");

        service.getRecommendationsForArtist(q);

        assertNotNull(service.lastSearchQuery);
        assertEquals("Radiohead", service.lastSearchQuery.getText());
    }
}
