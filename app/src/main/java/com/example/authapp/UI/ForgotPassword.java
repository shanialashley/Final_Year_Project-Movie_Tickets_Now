package com.example.authapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.authapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText email;
    private Button resetB;
    private ProgressBar progB;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.fP_email);
        resetB = findViewById(R.id.resetP);
        progB = findViewById(R.id.progressBar2);

        mAuth = FirebaseAuth.getInstance();

        resetB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();

            }
        });

    }

    private void resetPassword() {

        String e = email.getText().toString().trim();

        if(e.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(e).matches()){
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;
        }

        progB.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(e).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,
                            "Check your email to reset your password!",
                            Toast.LENGTH_LONG).show();
                    progB.setVisibility(View.GONE);
                }else{
                    Toast.makeText(ForgotPassword.this,
                            "Try Again! Something wrong happened!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}