package com.example.spotishare;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaylistSong implements Parcelable {
    private String songName;
    private String fileName;

    public PlaylistSong(String songName, String fileName){
        this.songName = songName;
        this.fileName = fileName;
    }

    protected PlaylistSong(Parcel in) {
        songName = in.readString();
        fileName = in.readString();
    }

    public static final Creator<PlaylistSong> CREATOR = new Creator<PlaylistSong>() {
        @Override
        public PlaylistSong createFromParcel(Parcel in) {
            return new PlaylistSong(in);
        }

        @Override
        public PlaylistSong[] newArray(int size) {
            return new PlaylistSong[size];
        }
    };

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(songName);
        dest.writeString(fileName);
    }
}
