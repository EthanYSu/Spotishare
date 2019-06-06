package com.example.spotishare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.google.firebase.database.annotations.NotNull;


import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SongListAdapter extends ArrayAdapter<FileInfo> implements Filterable {
    //    private Activity activity;
    private Activity context;
    private int resource;
    private ArrayList<FileInfo> songList, filteredList;
//    private SongFilter songFilter;

    public SongListAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<FileInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.songList = objects;
        this.filteredList = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        View currentSong;
        LayoutInflater layoutInflater = context.getLayoutInflater();

        currentSong = layoutInflater.from(context).inflate(R.layout.file_item,parent,false);

        final FileInfo currentFile = songList.get(position);

        TextView currentSongName = currentSong.findViewById(R.id.listOfSongName);
        TextView currentUserName = currentSong.findViewById(R.id.listOfCurrentUserName);
//        TextView songDownloadLink = v.findViewById(R.id.songDownloadLink);

//        currentSongName.setText(songList.get(position).getSongName());
//        currentUserName.setText("Uploaded by: " + songList.get(position).getCurrentUser());
        currentSongName.setText(currentFile.getSongName());
        currentUserName.setText("Uploaded by: " + currentFile.getCurrentUser());
//        songDownloadLink.setText(songList.get(position).getDownloadLink());

        currentSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent songClickIntent = new Intent(context, SongClick.class);
                songClickIntent.putExtra("FileInfo", currentFile);
                context.startActivity(songClickIntent);

            }
        });

        return currentSong;
    }

//    private class SongFilter extends Filter {
//        ArrayList<FileInfo> filteredList;
//        SongListAdapter songListAdapter;
//
//        public SongFilter(ArrayList<FileInfo> filteredList, SongListAdapter songListAdapter) {
//            this.filteredList = filteredList;
//            this.songListAdapter = songListAdapter;
//        }
//
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            FilterResults filterResults = new FilterResults();
//            if (constraint != null || constraint.length() > 0) {
//                ArrayList<FileInfo> fileData = new ArrayList<>();
//
//                for(int i = 0; i < filteredList.size(); i++){
//                    constraint = constraint.toString().toUpperCase();
//                    if(filteredList.get(i).getSongName().toUpperCase().contains(constraint)){
//                        fileData.add(filteredList.get(i));
//                    }
//                }
//                filterResults.count = fileData.size();
//                filterResults.values = fileData;
//            }
//            else{
//                filterResults.count = filteredList.size();
//                filterResults.values = filteredList;
//            }
//            return filterResults;
//        }
//
//        @Override
//        protected void publishResults (CharSequence constraint, FilterResults results){
//            songListAdapter.songList = (ArrayList<FileInfo>) results.values;
//            songListAdapter.notifyDataSetChanged();
//
//        }
//
//    }
//
//    @Override
//    public Filter getFilter(){
//        if(songFilter == null){
//            songFilter = new SongFilter(filteredList, this);
//        }
//        return songFilter;
//    }
}

