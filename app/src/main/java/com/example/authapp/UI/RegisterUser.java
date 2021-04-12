package com.example.authapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.Model.User;
import com.example.authapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference adminDb;
    private TextView login;
    private EditText fn, ln, email, pwd1, pwd2;
    private ProgressBar progressB;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();
        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterUser.this, MainActivity.class));
            }
        });
        fn = findViewById(R.id.firstName);
        ln = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        pwd1 = findViewById(R.id.password1);
        pwd2 = findViewById(R.id.password2);

        progressB = findViewById(R.id.progressBar);

        register = findViewById(R.id.signIn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();

            }
        });

    }

    private void Register(){
        String f = fn.getText().toString().trim();
        String l = ln.getText().toString().trim();
        String e = email.getText().toString().trim();
        String p1 = pwd1.getText().toString().trim();
        String p2 = pwd2.getText().toString().trim();

        if(f.isEmpty()){
            fn.setError("First Name is required!");
            fn.requestFocus();
            return;
        }

        if(l.isEmpty()){
            ln.setError("Last Name is required!");
            ln.requestFocus();
            return;
        }

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

        if(p1.isEmpty()){
            pwd1.setError("Password is required!");
            pwd1.requestFocus();
            return;
        }

        if(p1.length()<6){
            pwd1.setError("Password must be more than 6 characters!");
            pwd1.requestFocus();
            return;
        }

        if(!p2.equals(p1)){
            pwd2.setError("Password does not match!");
            pwd2.requestFocus();
            return;
        }

        progressB.setVisibility(View.VISIBLE);


        mAuth.createUserWithEmailAndPassword(e, p1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(f, l, e);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterUser.this,
                                                        "User has been registered successfully!",
                                                        Toast.LENGTH_LONG).show();
                                                progressB.setVisibility(View.VISIBLE);
                                                startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                            }else{
                                                Toast.makeText(RegisterUser.this,
                                                        "Fail to register! Try Again!",
                                                        Toast.LENGTH_LONG).show();
                                                progressB.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                            );
                        }else{
                            Toast.makeText(RegisterUser.this,
                                    "2Fail to register! Try Again!",
                                    Toast.LENGTH_LONG).show();
                            progressB.setVisibility(View.GONE);

                        }
                    }
                });

    }




}