package com.example.spotishare;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText userEmail, userPassword;
    private Button loginButton;
    private TextView registerUser;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this, ProfileActivity.class));
        }

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        loginButton = findViewById(R.id.loginUser);
        registerUser = findViewById(R.id.notRegisteredText);

        loginButton.setOnClickListener(this);
        registerUser.setOnClickListener(this);

    }

    public void userLogin(){
        String currentUserEmail = userEmail.getText().toString().trim();
        String currentUserPassword = userPassword.getText().toString().trim();

        if(TextUtils.isEmpty(currentUserEmail)){
            Toast.makeText(this, "Please enter a valid Email!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(currentUserPassword)){
            Toast.makeText(this, "Please enter a valid Password!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(currentUserEmail,currentUserPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == loginButton){
            userLogin();
        }
        if(v == registerUser){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}
