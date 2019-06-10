package com.example.spotishare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView currentUserEmail;
    private Button logoutButton, uploadButton, browseButton, musicPlayerButton, sharePlaylistButton;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        currentUserEmail = findViewById(R.id.currentUserEmail);
        currentUserEmail.setText("Hello " + currentUser.getEmail());


        browseButton = findViewById(R.id.browseButton);
        logoutButton = findViewById(R.id.logoutUserButton);
        uploadButton = findViewById(R.id.uploadButton);
        musicPlayerButton = findViewById(R.id.musicPlayerButton);
        sharePlaylistButton = findViewById(R.id.sharePlaylistButton);

        logoutButton.setOnClickListener(this);
        uploadButton.setOnClickListener(this);
        browseButton.setOnClickListener(this);
        musicPlayerButton.setOnClickListener(this);
        sharePlaylistButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == logoutButton){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        if(v == uploadButton){
            startActivity(new Intent(this, UploadActivity.class));
        }
        if(v == browseButton){
            startActivity(new Intent(this, BrowseActivity.class));
        }

        if(v == musicPlayerButton){
            startActivity(new Intent(this, MusicPlayerActivity.class));
        }

        if(v == sharePlaylistButton){
            startActivity(new Intent(this, ShareActivity.class));
        }
    }
}
