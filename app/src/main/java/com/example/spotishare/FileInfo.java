package com.example.spotishare;

import android.os.Parcel;
import android.os.Parcelable;

public class FileInfo implements Parcelable {
    private String mCurrentUser;
    private String mFileName;
    private String mSongName;
    private String mDownloadLink;

    public FileInfo(){}

    public FileInfo(String currentUser, String fileName, String songName, String downloadLink){
        if(songName.trim().equals("")){
            fileName = "No Name";
        }
        mCurrentUser = currentUser;
        mFileName = fileName;
        mSongName = songName;
        mDownloadLink = downloadLink;
    }

    protected FileInfo(Parcel in) {
        mCurrentUser = in.readString();
        mFileName = in.readString();
        mSongName = in.readString();
        mDownloadLink = in.readString();
    }

    public static final Creator<FileInfo> CREATOR = new Creator<FileInfo>() {
        @Override
        public FileInfo createFromParcel(Parcel in) {
            return new FileInfo(in);
        }

        @Override
        public FileInfo[] newArray(int size) {
            return new FileInfo[size];
        }
    };

    public String getCurrentUser(){
        return mCurrentUser;
    }

    public void setCurrentUser(String currentUser){
        mCurrentUser = currentUser;
    }

    public String getFileName(){
        return mFileName;
    }

    public void setFileName(String fileName){
        mFileName = fileName;
    }

    public String getSongName(){
        return mSongName;
    }

    public void setSongName(String songName){
        mSongName = songName;
    }

    public String getDownloadLink(){ return mDownloadLink;}

    public void setDownloadLink(String downloadLink){ mDownloadLink =  downloadLink;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCurrentUser);
        dest.writeString(mFileName);
        dest.writeString(mSongName);
        dest.writeString(mDownloadLink);
    }
}
