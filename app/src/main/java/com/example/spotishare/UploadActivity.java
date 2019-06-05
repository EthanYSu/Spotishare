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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int fileRequestCode = 123;

    private Button chooseFileButton, confirmUploadButton;

    private Uri filePath;

    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        storageReference = FirebaseStorage.getInstance().getReference();

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

            String uriString = filePath.toString();
            File currentFile = new File(uriString);
            String currentFileName = currentFile.getName();
            System.out.println("Current File name: " + currentFileName);

            StorageReference riversRef = storageReference.child("audio/AudioFile");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
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
//            String uri = filepath.toString();
//            File myFile = new File(uri);
//            String path = myFile.getAbsolutePath();
//            String displayName = myFile.getName();
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
