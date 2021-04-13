package com.example.authapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.authapp.R;

public class AdminDash extends AppCompatActivity {

    private CardView add_movie, update_movie, display_movie;
    private CardView timeSchedule, report, qrc_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);


        init();

    }

    public void init(){

        add_movie = findViewById(R.id.add_movie_cardV);
        add_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDash.this, AdminAddMovie.class);
                startActivity(intent);
            }
        });

        update_movie = findViewById(R.id.update_movie_cardV);
        update_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDash.this, AdminUpdateMovie.class);
                startActivity(intent);
            }
        });

        display_movie = findViewById(R.id.display_movie_cardV);
        display_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDash.this, AdminDisplayMovies.class);
                startActivity(intent);
            }
        });

        timeSchedule = findViewById(R.id.add_timeS_cardV);
        timeSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDash.this, AdminTimeSchedule.class);
                startActivity(intent);
            }
        });

        qrc_scan = findViewById(R.id.qrc_scan_cardV);
        qrc_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminDash.this, AdminQRCScanner.class);
                startActivity(intent);
            }
        });

        report = findViewById(R.id.report_cardV);
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}