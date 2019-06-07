package com.example.spotishare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlaylistActivity extends AppCompatActivity implements View.OnClickListener {
    private Button createPlayListButton;
    private ListView playlistListView;
//    private EditText userEnteredPlaylistName;
    private ArrayList<PlaylistList> playlistListArrayList;
    private PlaylistAdapter playlistAdapter;

    DatabaseReference databaseReference, playlistRef;
    FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private static final String TAG = "PlaylistActivity.class";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("users/");

        createPlayListButton = findViewById(R.id.createPlaylistButton);
//        userEnteredPlaylistName = findViewById(R.id.userEnteredPlaylistName);
        playlistListView = findViewById(R.id.playlistList);
        playlistListArrayList = new ArrayList<>();

        createPlayListButton.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading playlist list, please wait ...");
        progressDialog.show();

        Log.d(TAG, "We outside Database reference");
        Log.d(TAG, "current UID" + firebaseAuth.getCurrentUser().getUid());

        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("Playlist Names").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "We in OndataChange");
                Log.d(TAG, "snapshot count: " + dataSnapshot.getChildrenCount());

                progressDialog.dismiss();
                for(DataSnapshot allSnapshots: dataSnapshot.getChildren()){
                    PlaylistList playlistList = allSnapshots.getValue(PlaylistList.class);
                    Log.d(TAG, "abcdefghi" + playlistList.getPlaylistName());
                    playlistListArrayList.add(playlistList);
                }
                playlistAdapter = new PlaylistAdapter(PlaylistActivity.this, R.layout.playlist_list, playlistListArrayList);
                playlistListView.setAdapter(playlistAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == createPlayListButton){
            startActivity(new Intent(this, CreatePlaylistActivity.class));
//
//            String playlistName = userEnteredPlaylistName.getText().toString();
//            String currentUserEmail = firebaseAuth.getCurrentUser().getEmail();
//
//            if(playlistName != null && playlistName.length() > 0){
//                PlaylistList currentPlaylistName = new PlaylistList(playlistName);
//                //databaseReference.child("users/").setValue(currentUserEmail);
////                databaseReference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
////                        .child("Playlist Names").setValue(currentPlaylistName);
//                databaseReference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                        .child("Playlist Names").setValue(playlistName);
//                userEnteredPlaylistName.setText("");
//            }
        }
    }
}
