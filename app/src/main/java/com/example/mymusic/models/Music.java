package com.example.mymusic.models;

import java.io.Serializable;

public class Music implements Serializable {

    //attributs de la classe Music
    private String album="";
    private String artist="";
    private String title="";
    private String image="";
    private String preview="";
    private String link="";

    //Constructeur de la classe
    public Music() {

    }

    //Getter and Setter des attributs
    public Music(String artist, String title) {
        this.artist = artist;
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
