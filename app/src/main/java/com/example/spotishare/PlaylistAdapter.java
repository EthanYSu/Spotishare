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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PlaylistAdapter extends ArrayAdapter {
    private Activity context;
    private PlaylistSong playlistSong;
    private int resource;
    private ArrayList<PlaylistList> playlistListArrayList;
    private static final String TAG = "IN PLAYLIST ADAPTER";
    private DatabaseReference databaseReference;
    PlaylistList databasePlaylist;
    private ArrayList<PlaylistSong> currentPlaylistSongs;



    public PlaylistAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<PlaylistList> objects, PlaylistSong playlistSong) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        playlistListArrayList = objects;
        this.playlistSong = playlistSong;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "We in adapter");
        final View currentPlayList;
        LayoutInflater layoutInflater = context.getLayoutInflater();

        currentPlayList = layoutInflater.from(context).inflate(R.layout.playlist_list, parent, false);

        final PlaylistList playlistList = playlistListArrayList.get(position);
        currentPlaylistSongs = new ArrayList<>();

        final TextView playlistName = currentPlayList.findViewById(R.id.currentPlaylist);
        playlistName.setText(playlistList.getPlaylistName());

        currentPlayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Playlist Name outside: "+ playlistList.getPlaylistName());

                databaseReference = FirebaseDatabase.getInstance().getReference("users/")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Playlist Names")
                .child(playlistList.getPlaylistName()).child("playlistSongs");

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d(TAG, "snapshot count: " + dataSnapshot.getChildrenCount());

                        for(DataSnapshot allSnapshots: dataSnapshot.getChildren()){
                            PlaylistSong currentPlaylistSong = allSnapshots.getValue(PlaylistSong.class);
//                            PlaylistList currentPlaylistSong = allSnapshots.getValue(PlaylistList.class);
//                            Log.d(TAG, "Current Playlist Name: " + currentPlaylistSong.getPlaylistName());
//                            Log.d(TAG, "Current Playlist songs: " + currentPlaylistSong.getPlaylistSongs());

                            Log.d(TAG, "Current Song Name: " + currentPlaylistSong.getFileName());
                            Log.d(TAG, "Current File Name: " + currentPlaylistSong.getSongName());
                            if(!currentPlaylistSong.getSongName().equals(playlistSong.getSongName())) {
                                PlaylistSong newPlaylistSong = new PlaylistSong(currentPlaylistSong.getSongName(), currentPlaylistSong.getFileName());
                                currentPlaylistSongs.add(newPlaylistSong);
                            }

                        }
                        Log.d(TAG, "Current Playlist: " + currentPlaylistSongs);
                        Log.d(TAG, "Current Song Added: " + playlistSong);
                        currentPlaylistSongs.add(playlistSong);
//                        Log.d(TAG, "Current Arraylist: " + currentPlaylistSongs);

                        PlaylistList updatedPlaylistList = new PlaylistList(playlistList.getPlaylistName(), currentPlaylistSongs);
                        DatabaseReference newRef = FirebaseDatabase.getInstance().getReference("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Playlist Names")
                                .child(playlistList.getPlaylistName());
                        newRef.setValue(updatedPlaylistList);
                        String toastAddedSongMessage = "Song Added to " + playlistList.getPlaylistName();
                        Toast.makeText(context, toastAddedSongMessage, Toast.LENGTH_SHORT).show();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
//                Log.d(TAG, "Current Playlist: " + currentPlaylistSongs);
//                databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                        .child("Playlist Names").child(playlistList.getPlaylistName()).child("playlistSongs").addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        for(DataSnapshot allSnapshots: dataSnapshot.getChildren()){
////                            databasePlaylist = allSnapshots.getValue(PlaylistList.class);
//                            PlaylistSong tempPlaylistSong = new PlaylistSong();
//
//
//                            PlaylistList currentIndexSong = allSnapshots.getValue(PlaylistList.class);
//                            Log.d(TAG, "Current Song: "+ currentIndexSong.getPlaylistSongs());
//
//
////                            currentPlaylistSongs.add(currentIndexSong);
////                            Log.d(TAG, "Playlist Name: "+ databasePlaylist.getPlaylistName());
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });


//                currentPlaylistSongs.add(playlistSong);
//                Log.d(TAG, "Current Arraylist: " + currentPlaylistSongs);
//
//                PlaylistList updatedPlaylistList = new PlaylistList(databasePlaylist.getPlaylistName(), currentPlaylistSongs);
//                databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Playlist Names")
//                        .child(playlistList.getPlaylistName()).setValue(updatedPlaylistList);
            }
        });

        return currentPlayList;
    }
}
