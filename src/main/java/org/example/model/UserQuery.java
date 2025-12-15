package org.example.model;

public class UserQuery {

    private String text;      // general search text or prompt
    private String mood;
    private String genre;
    private String artist;

    public String getText() {
        return text;
    }

    public UserQuery setText(String text) {
        this.text = text;
        return this;
    }

    public String getMood() {
        return mood;
    }

    public UserQuery setMood(String mood) {
        this.mood = mood;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public UserQuery setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public String getArtist() {
        return artist;
    }

    public UserQuery setArtist(String artist) {
        this.artist = artist;
        return this;
    }
}

