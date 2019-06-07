package com.example.spotishare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreatePlaylistActivity extends AppCompatActivity implements View.OnClickListener {
    private Button createPlaylist;
    private EditText playlistName;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;
    private ArrayList<PlaylistSong> playlistSongArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_playlist);
        playlistSongArrayList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users/");
        createPlaylist = findViewById(R.id.createPlayList);
        playlistName = findViewById(R.id.userEnteredPlaylistName);

        createPlaylist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent getCurrentSongIntent = getIntent();
        final PlaylistSong selectedSong = getCurrentSongIntent.getParcelableExtra("SongInfo");
        playlistSongArrayList.add(selectedSong);
        if(v == createPlaylist){
            PlaylistList playlistList = new PlaylistList(playlistName.getText().toString().trim(), playlistSongArrayList);
            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("Playlist Names")
                    .child(playlistName.getText().toString().trim()).setValue(playlistList);
            Toast.makeText(CreatePlaylistActivity.this, "Playlist Created!", Toast.LENGTH_SHORT).show();

            playlistName.setText("");
        }
    }
}
