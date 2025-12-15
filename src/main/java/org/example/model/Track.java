package org.example.model;

public class Track {

    private String id;
    private String name;
    private String artist;
    private String album;
    private String previewUrl;

    public Track() {
    }

    public Track(String id, String name, String artist, String album, String previewUrl) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.previewUrl = previewUrl;
    }

    public String getId() {
        return id;
    }

    public Track setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Track setName(String name) {
        this.name = name;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public Track setArtist(String artist) {
        this.artist = artist;
        return this;
    }

    public String getAlbum() {
        return album;
    }

    public Track setAlbum(String album) {
        this.album = album;
        return this;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public Track setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
        return this;
    }

    @Override
    public String toString() {
        return name + " - " + artist;
    }
}
