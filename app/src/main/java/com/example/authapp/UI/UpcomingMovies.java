package com.example.authapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.authapp.R;
import com.google.android.material.navigation.NavigationView;

public class UpcomingMovies extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_movies);

        NavInfo();


    }

    public void NavInfo(){

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Theaters");



        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_login).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        DrawerArrowDrawable arrow = toggle.getDrawerArrowDrawable();
        arrow.setColor(getResources().getColor(R.color.orange));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(UpcomingMovies.this, Home.class));
                        break;

                    case R.id.nav_theaters:
                        startActivity(new Intent(UpcomingMovies.this, Theaters.class));
                        break;

                    case R.id.nav_upcoming_movies:

                        break;

                    case R.id.nav_search:
                        startActivity(new Intent(UpcomingMovies.this, Search.class));
                        break;


                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        navigationView.setCheckedItem(R.id.nav_upcoming_movies);



    }


}