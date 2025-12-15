package org.example.model;

import org.example.service.SunoService;

import java.io.IOException;

public class InstrumentalGenerationStrategy implements MusicGenerationStrategy {

    private final SunoService sunoService;

    public InstrumentalGenerationStrategy(SunoService sunoService) {
        this.sunoService = sunoService;
    }

    @Override
    public GenerationResult generate(UserQuery query) throws IOException {
        // 1) Start job
        GenerationResult initial = sunoService.requestInstrumental(query);

        // If the service is super fast and already returned an audioUrl, just return it.
        if (initial != null && initial.getAudioUrl() != null) {
            return initial;
        }

        // 2) Otherwise poll until completed / failed / timeout
        if (initial == null || initial.getTaskId() == null) {
            return new GenerationResult()
                    .setStatus("error")
                    .setAudioUrl(null);
        }

        return sunoService.pollGenerationStatus(initial.getTaskId());
    }
}
