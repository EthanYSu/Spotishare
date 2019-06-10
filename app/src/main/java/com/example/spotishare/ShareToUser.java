package com.example.spotishare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShareToUser extends AppCompatActivity implements View.OnClickListener {
    private EditText userEnteredEmail;
    private Button shareButton;
    DatabaseReference databaseReference;
    private static final String TAG = "ShareToUser.class";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_to_user);

        userEnteredEmail = findViewById(R.id.userEnteredEmail);
        shareButton = findViewById(R.id.shareButton);
        databaseReference = FirebaseDatabase.getInstance().getReference("users/");

        shareButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == shareButton){
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d(TAG, "snapshot count: " + dataSnapshot.getChildrenCount());
                    Log.d(TAG, "datasnapshot " + dataSnapshot.getValue());

                    Intent intent = getIntent();
                    final String selectedPlaylistName = intent.getStringExtra("SharePlaylistName");
                    for(DataSnapshot allSnapshots: dataSnapshot.getChildren()){
                        User currentUser = allSnapshots.getValue(User.class);
                        final String currentUID = allSnapshots.getKey();
                        Log.d(TAG, "current email " +currentUser.getEmail() );
                        if(userEnteredEmail.getText().toString().trim().equals(currentUser.getEmail())){
                            DatabaseReference sharePlaylistToUser = FirebaseDatabase.getInstance().getReference("users/")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Playlist Names");
                            sharePlaylistToUser.addListenerForSingleValueEvent(new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Log.d(TAG, "datasnapshot 2 " + dataSnapshot.getValue());
                                    for(DataSnapshot allSnapshots2: dataSnapshot.getChildren()){
                                        PlaylistList currentPlaylist = allSnapshots2.getValue(PlaylistList.class);
                                        PlaylistList addNewCurrentPlaylist = new PlaylistList(currentPlaylist.getPlaylistName(), currentPlaylist.getPlaylistSongs());
                                        Log.d(TAG, "List of all users's Playlist" + currentPlaylist.getPlaylistSongs());
                                        if(currentPlaylist.getPlaylistName().equals(selectedPlaylistName)){
                                            updateUsersPlaylist(currentUID, addNewCurrentPlaylist, selectedPlaylistName);
                                        }
                                    }


                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void updateUsersPlaylist(String currentUID, final PlaylistList addNewCurrentPlaylist, String selectedPlaylistName){
        final ArrayList<PlaylistList> getAllCurrentPlaylists = new ArrayList<>();
        final DatabaseReference addToUser = FirebaseDatabase.getInstance().getReference("users/")
                .child(currentUID).child("Playlist Names");
        addToUser.child(selectedPlaylistName).setValue(addNewCurrentPlaylist);
//        addToUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for(DataSnapshot allSnapshot3: dataSnapshot.getChildren()){
//                    PlaylistList tempPlaylist = allSnapshot3.getValue(PlaylistList.class);
//                    PlaylistList remakePlaylist = new PlaylistList(tempPlaylist.getPlaylistName(), tempPlaylist.getPlaylistSongs());
//                    getAllCurrentPlaylists.add(remakePlaylist);
//                }
//                getAllCurrentPlaylists.add(addNewCurrentPlaylist);
//                addToUser.setValue(getAllCurrentPlaylists);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }

}
