package com.example.spotishare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SongClick extends AppCompatActivity {
    private TextView selectedSongName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_click);

        selectedSongName = findViewById(R.id.selectedSongName);

        createProfile();
    }

    private void createProfile(){
        Intent intent = getIntent();
        String songSelected = intent.getStringExtra("DownloadLink");
        selectedSongName.setText(songSelected);
    }
}
