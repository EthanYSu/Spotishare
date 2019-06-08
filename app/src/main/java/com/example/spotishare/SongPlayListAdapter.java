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

        PlaylistSong currentPlayListSong = playlistSongArrayList.get(position);

        songPlaylistName.setText(currentPlayListSong.getSongName());

        return songPlaylistName;
    }
}
