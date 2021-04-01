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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.authapp.Model.Movies;
import com.example.authapp.Model.Slide;
import com.example.authapp.R;
import com.example.authapp.adapters.MovieAdapter;
import com.example.authapp.adapters.MovieItemClickListener;
import com.example.authapp.adapters.SliderPagerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
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
    Query currentMoviesQ;
    List<Movies> MovieList;
    List<String> Moviekey;


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



//        hideActionBar();

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

        //Hide or show item in Menu
        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_login).setVisible(false);


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
                startActivity(new Intent(Home.this, MainActivity.class));
                break;


        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void SlidesInfor(){

        Slides_list = new ArrayList<>();
        Slides_list.add(new Slide(R.drawable.tom_and_jerry, "Tom And Jerry"));
        Slides_list.add(new Slide(R.drawable.monster_hunter, "Monster Hunter"));
        Slides_list.add(new Slide(R.drawable.the_marksman, "The Marksman"));
        Slides_list.add(new Slide(R.drawable.the_mauritanian, "The Mauritanian"));
        Slides_list.add(new Slide(R.drawable.the_little_things, "The Little Things"));
        Slides_list.add(new Slide(R.drawable.greenland, "Greenland"));
        Slides_list.add(new Slide(R.drawable.judas, "Judas and the Black Messiah"));
        Slides_list.add(new Slide(R.drawable.wrong_turn, "Wrong Turn"));

        adp = new SliderPagerAdapter(Home.this, Slides_list);

        slider_pager.setAdapter(adp);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Home.SliderTimer(),4000, 6000 );

        indicator.setupWithViewPager(slider_pager, true);
    }

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



    //Animation here to do to Details Page
    @Override
    public void onMovieClick(Movies movie, ImageView movieImageView) {

        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("img", movie.getThumbnail_url());
        intent.putExtra("imgCover", movie.getCover_url());
        intent.putExtra("description", movie.getDescription());
        intent.putExtra("genre", movie.getGenre());
        intent.putExtra("length", movie.getLength());
        intent.putExtra("rating", movie.getRating());
        intent.putExtra("starring", movie.getStarring());
        intent.putExtra("director", movie.getDirectors());
        intent.putExtra("trailer", movie.getTrailer_link());
        intent.putExtra("category", movie.getType());
        int k = MovieList.indexOf(movie);
        intent.putExtra("key", Moviekey.get(k));

        @SuppressLint({"NewApi", "LocalSuppress"})
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this, movieImageView, "sharedTransName");

        startActivity(intent, options.toBundle());

       // Toast.makeText(this, "Item Clicked: " + movie.getTitle(), Toast.LENGTH_LONG).show();


    }




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