package com.example.authapp.UI;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Admin.AdminDash;
import com.example.authapp.Model.Movies;
import com.example.authapp.R;
import com.example.authapp.adapters.MovieItemClickListener;
import com.example.authapp.adapters.RecycleViewAdapterUpMovies;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpcomingMovies extends AppCompatActivity  implements MovieItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private RecyclerView rView;
    private Query upcomingRef;
    private List<Movies> ComingSoonLt = new ArrayList<>();
    private RecycleViewAdapterUpMovies upMoviesAdp;
    private FirebaseAuth mAuth;
    private FirebaseUser currentuser;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_movies);

        mAuth = FirebaseAuth.getInstance();

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

        menu = navigationView.getMenu();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        DrawerArrowDrawable arrow = toggle.getDrawerArrowDrawable();
        arrow.setColor(getResources().getColor(R.color.orange));

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_upcoming_movies);

    }

    @Override
    protected void onStart() {
        super.onStart();

        currentuser = mAuth.getCurrentUser();
        if(currentuser == null) {
            menu.findItem(R.id.nav_logout).setVisible(false);
            menu.findItem(R.id.nav_admin).setVisible(false);

        }else{
            menu.findItem(R.id.nav_login).setVisible(false);
            String email = currentuser.getEmail();
            AdminView(email);
        }

    }

    //Hide Admin View if the user does not have admin privilege
    private void AdminView(String e) {

        Query q = FirebaseDatabase.getInstance().getReference("Admin")
                .orderByChild("email").startAt(e);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String email = ss.child("email").getValue(String.class);
                        if (email != null) {
                            if (!email.equalsIgnoreCase(e)) {
                                menu.findItem(R.id.nav_admin).setVisible(false);

                            }else{
                                menu.findItem(R.id.nav_admin).setVisible(true);
                                Toast.makeText(UpcomingMovies.this, "User has admin privileges!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

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

            case R.id.nav_logout:
                mAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(UpcomingMovies.this, Home.class));
                break;

            case R.id.nav_login:
                startActivity(new Intent(UpcomingMovies.this, MainActivity.class));
                break;

            case R.id.nav_admin:
                startActivity(new Intent(UpcomingMovies.this, AdminDash.class));
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    //Get Upcoming Movies From Firebase
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



    // Send information through intent to details screen
    @Override
    public void onMovieClick(Movies movie, ImageView movieImageView) {

        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("currentMovie", movie);

        @SuppressLint({"NewApi", "LocalSuppress"})
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(UpcomingMovies.this, movieImageView, "sharedTransName");

        startActivity(intent, options.toBundle());

    }


}