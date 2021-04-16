package com.example.authapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.authapp.R;
import com.example.authapp.UI.Home;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminDash extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private CardView add_movie, update_movie, display_movie;
    private CardView timeSchedule, report, qrc_scan;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Menu menu;
    private FirebaseAuth mAuth;
    private FirebaseUser currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash);

        mAuth = FirebaseAuth.getInstance();
        NavInfo();
        init();

    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    public void NavInfo(){

        drawerLayout = findViewById(R.id.admindash_drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Movie Tickets Now Admin");

        menu = navigationView.getMenu();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        DrawerArrowDrawable arrow = toggle.getDrawerArrowDrawable();
        arrow.setColor(getResources().getColor(R.color.orange));

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_dash);


    }

    @Override
    protected void onStart() {
        super.onStart();

        currentuser = mAuth.getCurrentUser();
        if(currentuser != null) {
            menu.findItem(R.id.nav_login).setVisible(false);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_dash:
                break;

            case R.id.nav_addMovie:
                startActivity(new Intent(AdminDash.this, AdminAddMovie.class));
                break;

            case R.id.nav_updateMovie:
                startActivity(new Intent(AdminDash.this, AdminUpdateMovie.class));
                break;

            case R.id.nav_display_movies:
                startActivity(new Intent(AdminDash.this, AdminDisplayMovies.class));
                break;

            case R.id.nav_timeSchedules:
                startActivity(new Intent(AdminDash.this, AdminTimeSchedule.class));
                break;

            case R.id.nav_scanQRC:
                startActivity(new Intent(AdminDash.this, AdminQRCScanner.class));
                break;

            case R.id.nav_report:
                Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                mAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(AdminDash.this, AdminLogin.class));
                break;

            case R.id.nav_login:
                startActivity(new Intent(AdminDash.this, AdminLogin.class));
                break;

            case R.id.nav_user:
                startActivity(new Intent(AdminDash.this, Home.class));


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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