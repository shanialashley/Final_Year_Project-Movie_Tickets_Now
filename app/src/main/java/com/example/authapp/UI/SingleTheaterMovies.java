package com.example.authapp.UI;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.Movies;
import com.example.authapp.R;
import com.example.authapp.adapters.MovieItemClickListener;
import com.example.authapp.adapters.RecycleViewAdapterUpMovies;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SingleTheaterMovies extends AppCompatActivity implements MovieItemClickListener {

    private RecyclerView stm_RV;
    private List<Movies> mLt;
    private List<String> cc8Lt = new ArrayList<>();
    private DatabaseReference movies_Ref;
    private RecycleViewAdapterUpMovies stmAdp;
    private Toolbar toolbar;
    List<Movies> MovieList;
    List<String> Moviekey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_theater_movies);

        stm_RV = findViewById(R.id.stm_recycleView);

        movies_Ref = FirebaseDatabase.getInstance().getReference("Movies");
        mLt = new ArrayList<>();
        ToolbarInfo();
        String screen = getIntent().getExtras().getString("screen");

        if(screen.equals("CC8")) {
            CC8_Movies();
            getSupportActionBar().setTitle("Caribbean Cinema 8");
        }else if(screen.equals("MT")){
            MT_Movies();
            getSupportActionBar().setTitle("Movie Towne");
        }else if(screen.equals("CONE")){
            CONE_Movies();
            getSupportActionBar().setTitle("Cinema ONE");
        }

    }

    public void ToolbarInfo(){


        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }
    //Show only Cimema 8
    private void CC8_Movies() {

        MovieList = new ArrayList<>();
        Moviekey = new ArrayList<>();

       Query ref = FirebaseDatabase.getInstance().getReference("Movies").orderByChild("type").equalTo("current");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mLt.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                            Movies m = ss.getValue(Movies.class);
                            String t = ss.child("theaters").child("T_CC8").getValue(String.class);
                            if(t != null){
                                if(t.equals("true")) {
                                    String temp = ss.getKey();
                                    Moviekey.add(temp);
                                    MovieList.add(m);
                                }
                            }
                    }
                }

                stmAdp = new RecycleViewAdapterUpMovies(SingleTheaterMovies.this, MovieList, SingleTheaterMovies.this);
                stm_RV.setLayoutManager(new GridLayoutManager(SingleTheaterMovies.this, 3));
                stm_RV.setAdapter(stmAdp);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    //Show only Movie Towne
    private void MT_Movies() {

        MovieList = new ArrayList<>();
        Moviekey = new ArrayList<>();

        Query ref = FirebaseDatabase.getInstance().getReference("Movies").orderByChild("type").equalTo("current");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mLt.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        Movies m = ss.getValue(Movies.class);
                        String t = ss.child("theaters").child("T_MT").getValue(String.class);
                        if(t != null){
                            if(t.equals("true")) {
                                String temp = ss.getKey();
                                Moviekey.add(temp);
                                MovieList.add(m);
                            }
                        }
                    }
                }

                stmAdp = new RecycleViewAdapterUpMovies(SingleTheaterMovies.this, MovieList, SingleTheaterMovies.this);
                stm_RV.setLayoutManager(new GridLayoutManager(SingleTheaterMovies.this, 3));
                stm_RV.setAdapter(stmAdp);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    //Intent for each movie to go to the Details Screen
    @Override
    public void onMovieClick(Movies movie, ImageView movieImageView) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("currentMovie", movie);
        int k = MovieList.indexOf(movie);
        intent.putExtra("key", Moviekey.get(k));

        @SuppressLint({"NewApi", "LocalSuppress"})
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SingleTheaterMovies.this, movieImageView, "sharedTransName");

        startActivity(intent, options.toBundle());
    }

    //Show only Cimema One
    private void CONE_Movies() {

        MovieList = new ArrayList<>();
        Moviekey = new ArrayList<>();

        Query ref = FirebaseDatabase.getInstance().getReference("Movies").orderByChild("type").equalTo("current");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mLt.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        Movies m = ss.getValue(Movies.class);
                        String t = ss.child("theaters").child("T_CONE").getValue(String.class);
                        if(t != null){
                            if(t.equals("true")) {
                                String temp = ss.getKey();
                                Moviekey.add(temp);
                                MovieList.add(m);
                            }
                        }
                    }
                }

                stmAdp = new RecycleViewAdapterUpMovies(SingleTheaterMovies.this, MovieList, SingleTheaterMovies.this);
                stm_RV.setLayoutManager(new GridLayoutManager(SingleTheaterMovies.this, 3));
                stm_RV.setAdapter(stmAdp);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}