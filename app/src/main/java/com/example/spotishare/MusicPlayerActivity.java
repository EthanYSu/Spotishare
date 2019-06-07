package com.example.spotishare;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MusicPlayerActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    private ArrayList<String> playlistNames;
    String[] currentPlaylists;
    ListView playlistListView;
    private static final String TAG = "MPActivity.class";
    MusicPlaylistAdapter musicPlaylistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        playlistListView = findViewById(R.id.songListView);
        playlistNames = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("users/");
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Playlist Names").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "snapshot count: " + dataSnapshot.getChildrenCount());

                for(DataSnapshot allSnapshots: dataSnapshot.getChildren()){
                    PlaylistList playlistList = allSnapshots.getValue(PlaylistList.class);
                    String currentPlaylistName = playlistList.getPlaylistName();
                    Log.d(TAG, "Playlist Name: " + currentPlaylistName);
                    playlistNames.add(currentPlaylistName);
                }
                musicPlaylistAdapter = new MusicPlaylistAdapter(MusicPlayerActivity.this, R.layout.playlist_item, playlistNames);
                playlistListView.setAdapter(musicPlaylistAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
