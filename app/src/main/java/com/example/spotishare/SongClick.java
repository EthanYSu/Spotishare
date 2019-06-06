package com.example.spotishare;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class SongClick extends AppCompatActivity implements View.OnClickListener {
    private TextView selectedSongName, songUploadedBy, songUploadDate, songUploadInfo;
    private Button downloadSongButton, addToPlaylistButton;

    FileInfo selectedSong;
    private static final String TAG = "SongClick Activity";
    DatabaseReference databaseReference;
    StorageReference storageReference, findSongRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_click);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        selectedSongName = findViewById(R.id.selectedSongName);
        songUploadedBy = findViewById(R.id.songUploadedBy);
        songUploadDate = findViewById(R.id.songUploadDate);
        songUploadInfo = findViewById(R.id.songUploadInfo);

        downloadSongButton = findViewById(R.id.downloadSongButton);
        addToPlaylistButton = findViewById(R.id.addToPlaylistButton);

        downloadSongButton.setOnClickListener(this);
        addToPlaylistButton.setOnClickListener(this);

        Intent intent = getIntent();
        selectedSong = intent.getParcelableExtra("FileInfo");

        createProfile(selectedSong);
    }

    private void createProfile(FileInfo selectedSong){

        String userUploaded = "User who uploaded: " + selectedSong.getCurrentUser() + "\n";
        String uploadDate = "Uploaded on: " + selectedSong.getCurrentDate()+ "\n";
        String songName = "Song Name: " + selectedSong.getSongName()+ "\n";
        String songBio = "Song Bio: " + selectedSong.getSongBio();

        selectedSongName.setText(songName);
        songUploadedBy.setText(userUploaded);
        songUploadDate.setText(uploadDate);
        songUploadInfo.setText(songBio);
    }

    private void downloadSong(Context context, String fileName, String destDirectory, String url){
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destDirectory, fileName);

        downloadManager.enqueue(request);

    }

    private void addToPlaylist(){

    }

    @Override
    public void onClick(View v) {
        if(v == downloadSongButton){
            Intent intent = getIntent();
            FileInfo getCurrentFileName = intent.getParcelableExtra("FileInfo");
            String currentFileName = getCurrentFileName.getFileName();
            String currentDownloadLink = getCurrentFileName.getDownloadLink();

            downloadSong(SongClick.this, currentFileName, DIRECTORY_DOWNLOADS, currentDownloadLink);
        }
        if (v == addToPlaylistButton) {
            addToPlaylist();
        }
    }
}
