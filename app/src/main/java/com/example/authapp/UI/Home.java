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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.authapp.Admin.AdminDash;
import com.example.authapp.Model.Movies;
import com.example.authapp.Model.Slide;
import com.example.authapp.R;
import com.example.authapp.adapters.MovieAdapter;
import com.example.authapp.adapters.MovieItemClickListener;
import com.example.authapp.adapters.SliderPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends AppCompatActivity implements MovieItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    private List<Slide> Slides_list;
    private ViewPager slider_pager;
    private SliderPagerAdapter adp;
    private TabLayout indicator;
    private RecyclerView MovieRV;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private FirebaseUser currentuser;
    private Query currentMoviesQ;
    private List<Movies> MovieList;
    private List<String> Moviekey;
    private Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        slider_pager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);
        MovieRV = findViewById(R.id.RV_movies);


        NavInfo();

        SlidesInfor();

        CurrentMoviesInfor();


    }

    private void hideActionBar() {
        getSupportActionBar().hide();

    }

    public void NavInfo(){

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Movie Tickets Now");

        menu = navigationView.getMenu();

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        DrawerArrowDrawable arrow = toggle.getDrawerArrowDrawable();
        arrow.setColor(getResources().getColor(R.color.orange));

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);


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
                .orderByChild("email").startAt(e).limitToFirst(1);
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
                                Toast.makeText(Home.this, "User has admin privileges!", Toast.LENGTH_SHORT).show();
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
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.nav_home:
                break;

            case R.id.nav_theaters:
                startActivity(new Intent(Home.this, Theaters.class));
                break;

            case R.id.nav_upcoming_movies:
                startActivity(new Intent(Home.this, UpcomingMovies.class));
                break;

            case R.id.nav_search:
                startActivity(new Intent(Home.this, Search.class));
                break;

            case R.id.nav_logout:
                mAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(Home.this, Home.class));
                break;

            case R.id.nav_login:
                startActivity(new Intent(Home.this, MainActivity.class));
                break;

            case R.id.nav_admin:
                startActivity(new Intent(Home.this, AdminDash.class));
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    //Slide with Most Anticipated Movies
    public void SlidesInfor(){

        Slides_list = new ArrayList<>();
        Slides_list.add(new Slide(R.drawable.mortal, "Mortal Kombat (This Friday)"));
        Slides_list.add(new Slide(R.drawable.godzilla_vs_kong, "Kong VS Godzilla"));
        Slides_list.add(new Slide(R.drawable.raya, "Raya And The Last Dragon"));
        Slides_list.add(new Slide(R.drawable.fatale, "Fatale"));
        Slides_list.add(new Slide(R.drawable.the_little_things, "The Little Things"));
        Slides_list.add(new Slide(R.drawable.greenland, "Greenland"));

        adp = new SliderPagerAdapter(Home.this, Slides_list);

        slider_pager.setAdapter(adp);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Home.SliderTimer(),4000, 6000 );

        indicator.setupWithViewPager(slider_pager, true);
    }

    //Horizontal RecycleView for Current Movies
    public void CurrentMoviesInfor(){

        MovieList = new ArrayList<>();
        Moviekey = new ArrayList<>();

        currentMoviesQ = FirebaseDatabase.getInstance().getReference("Movies")
                .orderByChild("type").equalTo("current");
        currentMoviesQ.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MovieList.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        Movies m = ss.getValue(Movies.class);
                        String temp = ss.getKey();
                        Moviekey.add(temp);
                        MovieList.add(m);
                    }
                }
                MovieAdapter madp = new MovieAdapter(Home.this, MovieList, Home.this);
                MovieRV.setAdapter(madp);
                MovieRV.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }



    //Animation here to go to Details Page
    @Override
    public void onMovieClick(Movies movie, ImageView movieImageView) {

        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("currentMovie", movie);
        int k = MovieList.indexOf(movie);
        intent.putExtra("key", Moviekey.get(k));

        @SuppressLint({"NewApi", "LocalSuppress"})
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this, movieImageView, "sharedTransName");

        startActivity(intent, options.toBundle());

    }



    //Timer for Slides
    class SliderTimer extends TimerTask{


        @Override
        public void run() {

            Home.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if  (slider_pager.getCurrentItem() < Slides_list.size() - 1) {
                        slider_pager.setCurrentItem(slider_pager.getCurrentItem() + 1);
                    }
                    else
                        slider_pager.setCurrentItem(0);
                }
            });

        }
    }

}