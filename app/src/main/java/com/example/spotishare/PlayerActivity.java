package com.example.spotishare;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener{
    MediaPlayer mediaPlayer;
    private static final String TAG = "PlayerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        Intent intent = getIntent();
        String selectedFileName = intent.getStringExtra("FileName");

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        streamAudioFromFirebase(selectedFileName);
    }

    private void streamAudioFromFirebase(String selectedFileName){
        final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        Log.d(TAG, "Current FileName:" + selectedFileName);

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("audio/" + selectedFileName);

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try{
                   final String url = uri.toString();
                   mediaPlayer.setDataSource(url);

                   mediaPlayer.setOnPreparedListener(PlayerActivity.this);
                   mediaPlayer.prepareAsync();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }
}
