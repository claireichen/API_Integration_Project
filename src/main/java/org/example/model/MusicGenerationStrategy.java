package org.example.model;

import java.io.IOException;

public interface MusicGenerationStrategy {

    GenerationResult generate(UserQuery query) throws IOException;
}
