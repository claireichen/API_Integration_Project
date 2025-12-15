package org.example;

import org.example.model.APIClient;
import org.example.model.Track;
import org.example.model.UserQuery;
import org.example.service.SpotifyService;

import java.io.IOException;
import java.util.List;

public class SpotifyQuickTest {

    public static void main(String[] args) {
        // Use command-line argument as query if present, otherwise default
        String queryText = args.length > 0 ? String.join(" ", args) : "lofi chill";

        System.out.println("Testing Spotify search with query: \"" + queryText + "\"");

        APIClient apiClient = APIClient.getInstance();
        SpotifyService spotifyService = new SpotifyService(apiClient);

        UserQuery query = new UserQuery().setText(queryText);

        try {
            List<Track> tracks = spotifyService.searchTracks(query);

            System.out.println("Received " + tracks.size() + " tracks.");
            for (int i = 0; i < Math.min(tracks.size(), 10); i++) {
                Track t = tracks.get(i);
                System.out.printf("%2d. %s — %s (%s)%n",
                        i + 1,
                        t.getName(),
                        t.getArtist(),
                        t.getAlbum());
            }

        } catch (IOException e) {
            System.err.println("I/O error while calling Spotify: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
