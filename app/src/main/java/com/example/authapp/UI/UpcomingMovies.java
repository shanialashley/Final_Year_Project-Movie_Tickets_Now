package com.example.authapp.UI;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.Movies;
import com.example.authapp.R;
import com.example.authapp.adapters.MovieItemClickListener;
import com.example.authapp.adapters.RecycleViewAdapterUpMovies;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpcomingMovies extends AppCompatActivity  implements MovieItemClickListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private RecyclerView rView;
    Query upcomingRef;
    List<Movies> ComingSoonLt = new ArrayList<>();
    RecycleViewAdapterUpMovies upMoviesAdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_movies);

        rView = findViewById(R.id.upcoming_recycleView);


        NavInfo();
        UpcomingMoviesInfo();
    }



    public void NavInfo(){

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Upcoming Movies");



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

    public void UpcomingMoviesInfo(){
        upcomingRef = FirebaseDatabase.getInstance().getReference("Movies")
                .orderByChild("type").equalTo("upcoming");
        upcomingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ComingSoonLt.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        Movies movie = ss.getValue(Movies.class);
                        ComingSoonLt.add(movie);
                    }
//                            upMoviesAdp.notifyDataSetChanged();
                }

                upMoviesAdp = new RecycleViewAdapterUpMovies(UpcomingMovies.this, ComingSoonLt, UpcomingMovies.this);
                rView.setLayoutManager(new GridLayoutManager(UpcomingMovies.this, 3));
                rView.setAdapter(upMoviesAdp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    @Override
    public void onMovieClick(Movies movie, ImageView movieImageView) {

        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("currentMovie", movie);

        @SuppressLint({"NewApi", "LocalSuppress"})
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UpcomingMovies.this, movieImageView, "sharedTransName");

        startActivity(intent, options.toBundle());

    }


}