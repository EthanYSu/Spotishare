package com.example.spotishare;

public class FileInfo {
    private String mCurrentUser;
    private String mFileName;
    private String mSongName;

    public FileInfo(){}

    public FileInfo(String currentUser, String fileName, String songName){
        if(songName.trim().equals("")){
            fileName = "No Name";
        }
        mCurrentUser = currentUser;
        mFileName = fileName;
        mSongName = songName;

    }

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
}
