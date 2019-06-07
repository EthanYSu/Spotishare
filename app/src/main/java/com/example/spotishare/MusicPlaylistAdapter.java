package com.example.spotishare;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MusicPlaylistAdapter extends ArrayAdapter {
    private Activity context;
    private int resource;
    private ArrayList<String> playlistNames;

    public MusicPlaylistAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<String> playlistNames) {
        super(context, resource, playlistNames);
        this.context = context;
        this.resource = resource;
        this.playlistNames = playlistNames;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentPlayListMusic;
        LayoutInflater layoutInflater = context.getLayoutInflater();

        currentPlayListMusic = layoutInflater.from(context).inflate(R.layout.playlist_item, parent, false);

        TextView currentPlayListMusicText = currentPlayListMusic.findViewById(R.id.currentPlaylistMusicItem);

        String currentPlaylistMusic = playlistNames.get(position).toString();

        currentPlayListMusicText.setText(currentPlaylistMusic);

        currentPlayListMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return currentPlayListMusic;
    }
}
