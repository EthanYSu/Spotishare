package com.example.spotishare;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends ArrayAdapter {
    private Activity context;
    private int resource;
    private ArrayList<PlaylistList> playlistListArrayList;
    private static final String TAG = "IN PLAYLIST ADAPTER";

    public PlaylistAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<PlaylistList> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        playlistListArrayList = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "We in adapter");
        View currentPlayList;

        LayoutInflater layoutInflater = context.getLayoutInflater();

        currentPlayList = layoutInflater.from(context).inflate(R.layout.playlist_list, parent, false);

        final PlaylistList playlistList = playlistListArrayList.get(position);

        TextView playlistName = currentPlayList.findViewById(R.id.currentPlaylist);

        playlistName.setText(playlistList.getPlaylistName());

        return currentPlayList;
    }
}
