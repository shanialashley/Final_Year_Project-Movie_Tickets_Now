package com.example.authapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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
import com.example.authapp.adapters.RecycleViewAdapterUpMovies;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpcomingMovies extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private RecyclerView rView;
    Query upcomingRef;
    List<Movies> ComingSoonLt = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_movies);

        rView = findViewById(R.id.upcoming_recycleView);
        RecycleViewAdapterUpMovies upMoviesAdp = new RecycleViewAdapterUpMovies(UpcomingMovies.this, ComingSoonLt);
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
                            upMoviesAdp.notifyDataSetChanged();
                    }


                rView.setLayoutManager(new GridLayoutManager(UpcomingMovies.this, 3));
                rView.setAdapter(upMoviesAdp);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        NavInfo();
        ComingSoonMoviesInfor();
        UpcomingData();
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


    public void UpcomingData() {



    }
    public void ComingSoonMoviesInfor(){
        //horizontal Coming Soon

//        ComingSoonLt.add(new Movie(R.drawable.raya, "Raya and the Last Dragon"));
//        ComingSoonLt.add(new Movie(R.drawable.chaos_walking, "Chaos Walking"));
//        ComingSoonLt.add(new Movie(R.drawable.i_care_a_lot, "I Care A Lot"));
//        ComingSoonLt.add(new Movie(R.drawable.godzilla_vs_kong, "Godzilla Vs Kong"));
//        ComingSoonLt.add(new Movie(R.drawable.nobody, "Nobody"));
//        ComingSoonLt.add(new Movie(R.drawable.fatale, "Fatale"));
//        ComingSoonLt.add(new Movie(R.drawable.mortal_kombat, "Mortal Kombat"));
//        ComingSoonLt.add(new Movie(R.drawable.black_widow, "Black Widow"));
//        ComingSoonLt.add(new Movie(R.drawable.spiral, "Spiral"));
//        ComingSoonLt.add(new Movie(R.drawable.ghostbusters_afterlife, "Ghostbusters: Afterlife"));
//        ComingSoonLt.add(new Movie(R.drawable.in_the_heights, "In The Heights"));
//        ComingSoonLt.add(new Movie(R.drawable.top_gun_maverick, "Top Gun: Mavarick"));
//        ComingSoonLt.add(new Movie(R.drawable.jungle_cruise, "Jungle Cruise"));
//        ComingSoonLt.add(new Movie(R.drawable.the_kings_man, "The Kings Man"));
//        ComingSoonLt.add(new Movie(R.drawable.no_time_to_die, "No Time To Die"));
//        ComingSoonLt.add(new Movie(R.drawable.morbius, "Morbius"));


    }


}