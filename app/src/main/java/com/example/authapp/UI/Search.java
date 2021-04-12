package com.example.authapp.UI;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

import com.example.authapp.Model.Movies;
import com.example.authapp.R;
import com.example.authapp.adapters.MovieItemClickListener;
import com.example.authapp.adapters.RecycleViewAdapterUpMovies;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity  implements MovieItemClickListener, NavigationView.OnNavigationItemSelectedListener {

    private ImageButton searchB;
    private EditText searchT;
    private RecyclerView searchRV;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Query searchQ;
    private FirebaseAuth mAuth;
    private FirebaseUser currentuser;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mAuth = FirebaseAuth.getInstance();

        searchRV = findViewById(R.id.search_RV);
        searchT = findViewById(R.id.search_text);
        NavInfo();

        searchT.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString() != null || s.toString() != "" ){
                    SearchDisplay(s.toString());
                }else{
                    SearchDisplay("");
                    Toast.makeText(Search.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        currentuser = mAuth.getCurrentUser();
        if(currentuser == null) {
            menu.findItem(R.id.nav_logout).setVisible(false);
        }else{
            menu.findItem(R.id.nav_login).setVisible(false);
        }

    }

    public void NavInfo(){

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search");


        //Hide or show item in Menu
        menu = navigationView.getMenu();



        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        DrawerArrowDrawable arrow = toggle.getDrawerArrowDrawable();
        arrow.setColor(getResources().getColor(R.color.orange));

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_search);



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
                startActivity(new Intent(Search.this, Home.class));
                break;

            case R.id.nav_theaters:
                startActivity(new Intent(Search.this, Theaters.class));
                break;

            case R.id.nav_upcoming_movies:
                startActivity(new Intent(Search.this, UpcomingMovies.class));
                break;

            case R.id.nav_search:
                break;

            case R.id.nav_logout:
                mAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(Search.this, Home.class));
                break;

            case R.id.nav_login:
                startActivity(new Intent(Search.this, MainActivity.class));
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void SearchDisplay(String title) {

        List<Movies> mlist = new ArrayList<>();
            if(!title.equals("")) {
                DatabaseReference result = FirebaseDatabase.getInstance().getReference().child("Movies");
                searchQ = result.orderByChild("title").startAt(title).endAt(title + "\uf8ff");
                searchQ.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot ss : snapshot.getChildren()) {
                                Movies m = ss.getValue(Movies.class);
                                mlist.add(m);
                            }
                            searchRV.setVisibility(View.VISIBLE);
                            RecycleViewAdapterUpMovies resultDAdp = new RecycleViewAdapterUpMovies(Search.this, mlist, Search.this);
                            searchRV.setLayoutManager(new GridLayoutManager(Search.this, 3));
                            searchRV.setAdapter(resultDAdp);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }else{
                mlist.clear();
                searchRV.setVisibility(View.INVISIBLE);
            }


    }

    @Override
    public void onMovieClick(Movies movie, ImageView movieImageView) {

        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("currentMovie", movie);
        @SuppressLint({"NewApi", "LocalSuppress"})
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Search.this, movieImageView, "sharedTransName");
        startActivity(intent, options.toBundle());

    }
}