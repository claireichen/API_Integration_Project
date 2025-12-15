package org.example.model;

public class GenerationResult {

    private String taskId;
    private String status;
    private String audioUrl;

    public GenerationResult() {
    }

    public GenerationResult(String taskId, String status, String audioUrl) {
        this.taskId = taskId;
        this.status = status;
        this.audioUrl = audioUrl;
    }

    public String getTaskId() {
        return taskId;
    }

    public GenerationResult setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public GenerationResult setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public GenerationResult setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
        return this;
    }
}
