package com.example.spotishare;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SongClick extends AppCompatActivity {
    private TextView selectedSongName;
    FileInfo selectedSong;
    private static final String TAG = "SongClick Activity";
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_click);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        selectedSongName = findViewById(R.id.selectedSongName);
        Intent intent = getIntent();
        selectedSong = intent.getParcelableExtra("FileInfo");

        createProfile(selectedSong);
    }

    private void createProfile(FileInfo selectedSong){

        String userUploaded = "User who uploaded: " + selectedSong.getCurrentUser() + "\n";
        String fileName = "File Name: " + selectedSong.getFileName()+ "\n";
        String songName = "Song Name: " + selectedSong.getSongName()+ "\n";
        String downloadLink = "Download Link: " + selectedSong.getDownloadLink();
        selectedSongName.setText(downloadLink + userUploaded + fileName + songName);
    }
}
