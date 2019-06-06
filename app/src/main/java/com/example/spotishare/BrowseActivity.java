package com.example.spotishare;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BrowseActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ArrayList<FileInfo> fileInfo;
    private ListView songListView;
    private SongListAdapter songListAdapter;
    private ProgressDialog progressDialog;
    private SearchView songSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        databaseReference = FirebaseDatabase.getInstance().getReference("songInfo/");

        fileInfo = new ArrayList<>();
        songListView = findViewById(R.id.listViewSongs);
        songSearch = findViewById(R.id.songSearch);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading song list, please wait ...");
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                for(DataSnapshot allSnapshots: dataSnapshot.getChildren()){
                    FileInfo currentFileInfo = allSnapshots.getValue(FileInfo.class);
                    fileInfo.add(currentFileInfo);
                }

                songListAdapter = new SongListAdapter(BrowseActivity.this, R.layout.file_item, fileInfo);
                songListView.setAdapter(songListAdapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(BrowseActivity.this, "Could not read from Database :( ", Toast.LENGTH_LONG).show();

            }
        });

    }

}
