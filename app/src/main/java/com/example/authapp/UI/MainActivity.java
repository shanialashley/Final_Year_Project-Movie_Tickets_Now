package com.example.authapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.Admin.AdminLogin;
import com.example.authapp.Model.Movies;
import com.example.authapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private TextView signup, forgetPwd, adminlogin;
    private EditText username, pwd;
    private Button login;
    private ImageButton showp;

    private FirebaseAuth mAuth;
    private Movies movie;
    private String selectedDate, time, theater_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        adminlogin = findViewById(R.id.adminlogin);
        adminlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdminLogin.class));
            }
        });


        forgetPwd = findViewById(R.id.forgetPwd);
        forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
            }
        });

        signup = findViewById(R.id.signUp);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(MainActivity.this, RegisterUser.class));
            }
        });

        username = findViewById(R.id.username);
        pwd = findViewById(R.id.password);

        login = findViewById(R.id.button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        showp = findViewById(R.id.showPassword);
        showp.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
        showp.setOnClickListener(new View.OnClickListener() {
            boolean show = false;
            @Override
            public void onClick(View view) {

                if(show == false){
                    showp.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    show = true;
                }else{
                    showp.setImageResource(R.drawable.ic_baseline_remove_red_eye_24);
                    pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    show = false;
                }

            }
        });

    }

    private void userLogin() {
        String e = username.getText().toString().trim();
        String p = pwd.getText().toString().trim();

        if(e.isEmpty()){
            username.setError("Email is required!");
            username.requestFocus();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(e).matches()){
            username.setError("Please provide valid email!");
            username.requestFocus();
            return;
        }

        if(p.isEmpty()){
            pwd.setError("Password is required!");
            pwd.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

//                    Bundle data = ;
                    String s =getIntent().getExtras().getString("Screen");

                    if(s.equals("Select_Tickets")) {

                        movie = (Movies) getIntent().getExtras().getSerializable("currentMovie");
                        selectedDate = getIntent().getExtras().getString("selectedDate");
                        time = getIntent().getExtras().getString("time");
                        theater_type = getIntent().getExtras().getString("theater_type");

                        Intent in = new Intent(MainActivity.this, SelectTickets.class);
                        in.putExtra("currentMovie", movie);
                        in.putExtra("selectDate", selectedDate);
                        in.putExtra("time", time);
                        in.putExtra("theater_type", theater_type);
                        startActivity(in);
                    }else{
                        startActivity(new Intent(MainActivity.this, Home.class));



                    }
                }else{
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }




}