package com.example.spotishare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlaylistActivity extends AppCompatActivity implements View.OnClickListener {
    private Button createPlayListButton;
    private ListView playlistList;
    private EditText userEnteredPlaylistName;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        firebaseAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        createPlayListButton = findViewById(R.id.createPlaylistButton);
        userEnteredPlaylistName = findViewById(R.id.userEnteredPlaylistName);
        playlistList = findViewById(R.id.playlistList);

        createPlayListButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent songIntent = getIntent();
        PlaylistSong currentSong = songIntent.getParcelableExtra("SongInfo");
        if(v == createPlayListButton){
            String playlistName = userEnteredPlaylistName.getText().toString();
            String currentUserEmail = firebaseAuth.getCurrentUser().getEmail();

            if(playlistName != null && playlistName.length() > 0){
                PlaylistList currentPlaylistName = new PlaylistList(playlistName);
                //databaseReference.child("users/").setValue(currentUserEmail);
                databaseReference.child("userscc").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(playlistName).setValue(currentSong);

                userEnteredPlaylistName.setText("");
            }
        }
    }
}
