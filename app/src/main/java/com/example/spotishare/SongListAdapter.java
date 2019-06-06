package com.example.spotishare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.firebase.database.annotations.NotNull;


import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

public class SongListAdapter extends ArrayAdapter<FileInfo> implements Filterable {
//    private Activity activity;
    private Activity context;
    private int resource;
    private List<FileInfo> songList;

    public SongListAdapter(@NotNull Activity context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource =resource;
        songList = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        View currentSong = convertView;
        LayoutInflater layoutInflater = context.getLayoutInflater();

        currentSong = layoutInflater.inflate(resource, null);

        final FileInfo currentFile = songList.get(position);

        TextView currentSongName = currentSong.findViewById(R.id.listOfSongName);
        TextView currentUserName = currentSong.findViewById(R.id.listOfCurrentUserName);
//        TextView songDownloadLink = v.findViewById(R.id.songDownloadLink);

        currentSongName.setText(songList.get(position).getSongName());
        currentUserName.setText("Uploaded by: " + songList.get(position).getCurrentUser());
//        songDownloadLink.setText(songList.get(position).getDownloadLink());

        currentSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent songClickIntent = new Intent(context, SongClick.class);
                songClickIntent.putExtra("DownloadLink", currentFile.getDownloadLink());
                context.startActivity(songClickIntent);

            }
        });

        return currentSong;
    }
}
