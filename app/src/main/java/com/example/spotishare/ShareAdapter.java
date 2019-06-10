package com.example.spotishare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ShareAdapter extends ArrayAdapter {
    private Activity context;
    private int resource;
    private ArrayList<String> playlistNames;
//    private ArrayList<PlaylistList> playlistLists;

    public ShareAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<String> playlistNames) {
        super(context, resource, playlistNames);
        this.context = context;
        this.resource = resource;
        this.playlistNames = playlistNames;
//        this.playlistLists = playlistLists;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currentSharePlaylist;
        LayoutInflater layoutInflater = context.getLayoutInflater();

        currentSharePlaylist = layoutInflater.from(context).inflate(R.layout.share_song, parent, false);

        final TextView shareSongList = currentSharePlaylist.findViewById(R.id.shareSongList);

        final String currentPlaylistName = playlistNames.get(position);

        shareSongList.setText(currentPlaylistName);

        currentSharePlaylist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent shareUserIntent = new Intent(context, ShareToUser.class);
                shareUserIntent.putExtra("SharePlaylistName", currentPlaylistName);
                context.startActivity(shareUserIntent);

            }
        });
        return currentSharePlaylist;
    }
}
