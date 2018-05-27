package com.example.ies.reproductormp3;

/**
 * Created by ies on 5/3/2018.
 */

public class Song {

    private long id;
    private String title;
    private String artist;
    private String path;


    public Song(long songID, String songTitle, String songArtist, String path) {
        id=songID;
        title=songTitle;
        artist=songArtist;
        this.path = path;
    }

    public Song(String path){
        this.path = path;
    }

    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}

    public String getPath(){return path;}

}
