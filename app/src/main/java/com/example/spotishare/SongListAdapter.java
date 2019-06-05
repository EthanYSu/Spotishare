package com.example.spotishare;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.annotations.NotNull;


import java.util.List;

public class SongListAdapter extends ArrayAdapter {
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
        LayoutInflater layoutInflater = context.getLayoutInflater();

        View v = layoutInflater.inflate(resource, null);
        TextView currentSongName = v.findViewById(R.id.listOfSongName);

        currentSongName.setText(songList.get(position).getSongName());


        return v;
    }
}
