package com.example.spotishare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView currentUserEmail;
    private Button logoutButton;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        currentUserEmail = findViewById(R.id.currentUserEmail);
        currentUserEmail.setText("Hello " + currentUser.getEmail());

        logoutButton = findViewById(R.id.logoutUserButton);

        logoutButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == logoutButton){
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}
