package com.example.spotishare;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView signInText;
    private EditText userEmail, userPassword;
    private Button signUpButton;


    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        signInText = findViewById(R.id.signInText);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        signUpButton = findViewById(R.id.registerUser);

        signUpButton.setOnClickListener(this);
        signInText.setOnClickListener(this);

    }

    public void registerUser(){
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

        Toast.makeText(this, "Registering User...", Toast.LENGTH_SHORT).show();

        firebaseAuth.createUserWithEmailAndPassword(currentUserEmail,currentUserPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this, "User Registered Successful!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Registration Failed.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == signUpButton){
            registerUser();
        }

        if(v == signInText){

        }
    }
}
