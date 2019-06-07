package com.example.spotishare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreatePlaylistActivity extends AppCompatActivity implements View.OnClickListener {
    private Button createPlaylist;
    private EditText playlistName;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_playlist);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users/");
        createPlaylist = findViewById(R.id.createPlayList);
        playlistName = findViewById(R.id.userEnteredPlaylistName);

        createPlaylist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == createPlaylist){
            PlaylistList playlistList = new PlaylistList(playlistName.getText().toString().trim());
            databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("Playlist Names")
                    .child(playlistName.getText().toString().trim()).setValue(playlistList);
            Toast.makeText(CreatePlaylistActivity.this, "Playlist Created!", Toast.LENGTH_SHORT).show();

            playlistName.setText("");
        }
    }
}
