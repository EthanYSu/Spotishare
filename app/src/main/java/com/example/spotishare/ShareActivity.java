package com.example.spotishare;

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
import java.util.List;

public class ShareActivity extends AppCompatActivity {
    private ListView sharePlaylistListView;
    DatabaseReference databaseReference;
    private ArrayList<String> playlistNames;
    private ArrayList<PlaylistList> sharePlaylistList;
    private static final String TAG = "ShareActivity.class";
    ShareAdapter shareAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        playlistNames = new ArrayList<>();
        sharePlaylistList = new ArrayList<>();

        sharePlaylistListView = findViewById(R.id.sharePlaylistListView);
        databaseReference = FirebaseDatabase.getInstance().getReference("users/");
        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Playlist Names").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "snapshot count: " + dataSnapshot.getChildrenCount());

                for(DataSnapshot allSnapshots: dataSnapshot.getChildren()){
                    PlaylistList playlistList = allSnapshots.getValue(PlaylistList.class);
                    String currentPlaylistName = playlistList.getPlaylistName();
                    Log.d(TAG, "Playlist Name: " + currentPlaylistName);
                    sharePlaylistList.add(playlistList);
                    playlistNames.add(currentPlaylistName);
                }
                shareAdapter = new ShareAdapter(ShareActivity.this, R.layout.share_song, playlistNames
                );
                sharePlaylistListView.setAdapter(shareAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
