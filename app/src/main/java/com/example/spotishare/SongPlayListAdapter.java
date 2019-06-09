package com.example.spotishare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SongPlayListAdapter extends ArrayAdapter {
    private Activity context;
    private int resource;
    ArrayList<PlaylistSong> playlistSongArrayList;

    public SongPlayListAdapter(@NonNull Activity context, int resource, ArrayList<PlaylistSong> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        playlistSongArrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentPlaylistSongView;
        LayoutInflater layoutInflater = context.getLayoutInflater();

        currentPlaylistSongView = layoutInflater.from(context).inflate(R.layout.playlist_song, parent, false);
        TextView songPlaylistName = currentPlaylistSongView.findViewById(R.id.songPlaylistName);

        final PlaylistSong currentPlayListSong = playlistSongArrayList.get(position);

        songPlaylistName.setText(currentPlayListSong.getSongName());

        currentPlaylistSongView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playerIntent = new Intent(context,PlayerActivity.class);
                playerIntent.putExtra("SongName", currentPlayListSong.getSongName());
                playerIntent.putExtra("FileName", currentPlayListSong.getFileName());
                context.startActivity(playerIntent);
            }
        });

        return currentPlaylistSongView;
    }
}
