package com.example.api_week_tasks_2;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Song {
    private String songName;
    private int plays;
    private double duration;
    private String artist;
    private String label;
    @JsonIgnore
    private byte[] audioData;

    public Song(){
    }

    public void setAudioData(byte[] audioData) {
        this.audioData = audioData;
    }

    public byte[] getAudioData() {
        return audioData;
    }

    public void setName(String name){
        this.songName = name;
    }

    public String getName(){
        return songName;
    }

    public void setArtist(String artist){
        this.artist = artist;
    }

    public String getArtist(){
        return artist;
    }

    public void setDuration(double duration){
        this.duration = duration;
    }

    public double getDuration(){
        return duration;
    }

    public void setLable(String label){
        this.label = label;
    }

    public String getLable(){
        return label;
    }

    public void setPlays(int plays){
        this.plays = plays;
    }

    public int getPlays(){
        return plays;
    }
}
