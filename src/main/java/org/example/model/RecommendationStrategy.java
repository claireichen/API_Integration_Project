package org.example.model;

import java.io.IOException;
import java.util.List;

public interface RecommendationStrategy {

    List<Track> getRecommendations(UserQuery query) throws IOException;
}
