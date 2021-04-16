package com.example.authapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity {

    private EditText admin_email, admin_id, admin_pwd;
    private Button login;
    private FirebaseAuth mAuth;
    private DatabaseReference adminDB;
    private Query adminQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAuth = FirebaseAuth.getInstance();
        adminDB = FirebaseDatabase.getInstance().getReference("Admin");

        admin_email = findViewById(R.id.admin_email);
        admin_id = findViewById(R.id.admin_id);
        admin_pwd = findViewById(R.id.admin_password);
        login = findViewById(R.id.admin_login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adminLogin();
            }
        });
    }

    //Verify and Login
    private void adminLogin() {
        String e = admin_email.getText().toString().trim();
        String id = admin_id.getText().toString().trim();
        String p = admin_pwd.getText().toString().trim();

        if(id.isEmpty()){
            admin_id.setError("ID is required!");
            admin_id.requestFocus();
            return;
        }



        if(e.isEmpty()){
            admin_email.setError("Email is required!");
            admin_email.requestFocus();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(e).matches()){
            admin_email.setError("Please provide valid email!");
            admin_email.requestFocus();
            return;
        }

        if(p.isEmpty()){
            admin_pwd.setError("Password is required!");
            admin_pwd.requestFocus();
            return;
        }

        loginV(id, e, p);
//        loginAdmin(e,p);

    }

    // check if user is an admin
    private void loginV(String id, String e, String p) {

        adminQ = FirebaseDatabase.getInstance().getReference("Admin")
                .orderByKey().equalTo(id);
        adminQ.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() ){
                        for(DataSnapshot ss: snapshot.getChildren()){
                            String email = ss.child("email").getValue(String.class);
                            if(email != null) {
                                if (email.equalsIgnoreCase(e)) {
                                    loginAdmin(e, p);
                                }
                            }else{
                                Toast.makeText(AdminLogin.this, "Email Empty", Toast.LENGTH_SHORT).show();
                            }
                        }


                }else{

                    Toast.makeText(AdminLogin.this, "Please recheck your credentials! "+ id + e , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    //firebase Login and intent to dashboard
    private void loginAdmin(String e, String p) {

        mAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(AdminLogin.this, AdminDash.class ));
                }else{
                    Toast.makeText(AdminLogin.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}