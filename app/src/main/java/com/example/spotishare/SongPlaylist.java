package com.example.spotishare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SongPlaylist extends AppCompatActivity {
    DatabaseReference databaseReference;
    private ArrayList<PlaylistSong> currentPlaylistSongs;
    SongPlayListAdapter songPlayListAdapter;
    private ListView songListView;
    private static final String TAG = "songplaylist.java";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_playlist);


        currentPlaylistSongs = new ArrayList<>();
        Intent intent = getIntent();
        String currentPlaylistName = intent.getStringExtra("playlistName");

        songListView = findViewById(R.id.songPlaylistListView);

        databaseReference = FirebaseDatabase.getInstance().getReference("users/")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Playlist Names")
                .child(currentPlaylistName).child("playlistSongs");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "snapshot count: " + dataSnapshot.getChildrenCount());

                for(DataSnapshot allSnapshots: dataSnapshot.getChildren()){
                    PlaylistSong currentPlaylistSong = allSnapshots.getValue(PlaylistSong.class);
                    PlaylistSong newPlaylistSong = new PlaylistSong(currentPlaylistSong.getSongName(), currentPlaylistSong.getFileName());
                    currentPlaylistSongs.add(newPlaylistSong);
                }
                songPlayListAdapter = new SongPlayListAdapter(SongPlaylist.this, R.layout.playlist_song, currentPlaylistSongs);
                songListView.setAdapter(songPlayListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
