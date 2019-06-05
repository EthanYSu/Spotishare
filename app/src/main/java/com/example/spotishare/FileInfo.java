package com.example.spotishare;

public class FileInfo {
    private String mCurrentUser;
    private String mFileName;


    public FileInfo(String currentUser, String fileName){
        if(fileName.trim().equals("")){
            fileName = "No Name";
        }
        mCurrentUser = currentUser;
        mFileName = fileName;

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
}
