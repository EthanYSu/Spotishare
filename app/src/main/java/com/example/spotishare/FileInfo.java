package com.example.spotishare;

public class FileInfo {
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
}
