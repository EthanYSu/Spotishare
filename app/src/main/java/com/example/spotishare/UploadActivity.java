package com.example.spotishare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int fileRequestCode = 123;

    private Button chooseFileButton, confirmUploadButton;
    private TextView fileNameText;

    private Uri filePath;

    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        fileNameText = findViewById(R.id.fileNameText);
        chooseFileButton = findViewById(R.id.chooseFileButton);
        confirmUploadButton = findViewById(R.id.confirmUploadButton);

        chooseFileButton.setOnClickListener(this);
        confirmUploadButton.setOnClickListener(this);

    }

    private void uploadFile( ){
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            final File file = new File(filePath.getLastPathSegment());
            FirebaseUser user = firebaseAuth.getCurrentUser();
            String email = user.getEmail();
            String currentFileName = file.getName();

            final FileInfo fileInfo = new FileInfo(email, currentFileName);

            StorageReference audioRef = storageReference.child("audio/" + currentFileName);
            audioRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                            String uploadID = databaseReference.push().getKey();
                            databaseReference.child(uploadID).setValue(fileInfo);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }

    private void chooseFile(){
        Intent chooseFileIntent = new Intent();
        chooseFileIntent.setType("audio/*");
        chooseFileIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(chooseFileIntent, "Upload an Audio file."),fileRequestCode);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == fileRequestCode && resultCode == RESULT_OK && data != null && data.getData() != null ){
            filePath = data.getData();
            File file = new File(filePath.getLastPathSegment());
            String currentFileName = file.getName();
            String fileNameDisplay = "Current File Name: "+ currentFileName;
            fileNameText.setText(fileNameDisplay);
        }
    }

    @Override
    public void onClick(View v) {

        if(v == chooseFileButton){
            chooseFile();

        }

        if(v == confirmUploadButton){
            uploadFile();
        }

    }
}