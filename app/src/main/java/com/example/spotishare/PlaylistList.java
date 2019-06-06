package com.example.spotishare;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaylistList implements Parcelable {
    private String playlistName;

    public PlaylistList(String playlistName){
        this.playlistName = playlistName;
    }

    protected PlaylistList(Parcel in) {
        playlistName = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(playlistName);
    }
}
