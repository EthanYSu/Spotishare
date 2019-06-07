package com.example.spotishare;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class PlaylistList implements Parcelable {
    private String playlistName;
    private ArrayList<PlaylistSong> playlistSongs;

    public PlaylistList(){}

    public PlaylistList(String playlistName, ArrayList<PlaylistSong> playlistSongs){
        this.playlistName = playlistName;
        this.playlistSongs = playlistSongs;
    }

    protected PlaylistList(Parcel in) {
        playlistName = in.readString();
        playlistSongs = in.readArrayList(null);
    }

    public static final Creator<PlaylistList> CREATOR = new Creator<PlaylistList>() {
        @Override
        public PlaylistList createFromParcel(Parcel in) {
            return new PlaylistList(in);
        }

        @Override
        public PlaylistList[] newArray(int size) {
            return new PlaylistList[size];
        }
    };

    public String getPlaylistName() {
        return this.playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public ArrayList<PlaylistSong> getPlaylistSongs(){ return this.playlistSongs;}

    public void setPlaylistSongs(ArrayList<PlaylistSong> playlistSongs){
        this.playlistSongs = playlistSongs;
    }

//    public List<String> getFileName(){ return this.fileName;}
//
//    public void setFileName(List<String> fileName){
//        this.fileName = fileName;
//    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(playlistName);
        dest.writeList(playlistSongs);

    }
}
