package com.example.authapp.Admin;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.Movies;
import com.example.authapp.R;
import com.example.authapp.adapters.RecycleViewDisplayAdp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminDisplayMovies extends AppCompatActivity {

    private RecyclerView ds_recycleV;
    private EditText ds_search;
    private DatabaseReference referencemovie;
    private List<Movies> moviesList;
    private List<String> movieKey;
    private Query currentMoviesQ;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_display_movies);

        referencemovie = FirebaseDatabase.getInstance().getReference().child("Movies");
        movieKey = new ArrayList<>();
        moviesList = new ArrayList<>();

        ToolbarInfo();
        init();

    }

    public void ToolbarInfo(){


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String s = "Display Movies";
        getSupportActionBar().setTitle(s);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    private void init() {

        ds_recycleV = findViewById(R.id.ds_movies_recycleV);

        DisplayAll();

        ds_search = findViewById(R.id.ds_search);
        ds_search.addTextChangedListener(new TextWatcher() {
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
                    Toast.makeText(AdminDisplayMovies.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });


        ds_search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    String text = ds_search.getText().toString().trim();
                    if(text != null) {
                        SearchDisplay(text);
                    }else{
                        Toast.makeText(AdminDisplayMovies.this, "Enter Movie Key ", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }

                return false;
            }
        });
    }

    private void DisplayAll() {


        referencemovie.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                moviesList.clear();
                movieKey.clear();

                if(snapshot.exists()){

                    for(DataSnapshot ss: snapshot.getChildren()){
                        Movies m = ss.getValue(Movies.class);
                        String temp = ss.getKey();
                        movieKey.add(temp);
                        moviesList.add(m);
                    }
                }
                RecycleViewDisplayAdp mAdp = new RecycleViewDisplayAdp(AdminDisplayMovies.this, moviesList, movieKey);
                ds_recycleV.setAdapter(mAdp);
                ds_recycleV.setLayoutManager(new LinearLayoutManager(AdminDisplayMovies.this, LinearLayoutManager.VERTICAL, false));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void SearchDisplay(String text) {

        if(!text.isEmpty()){
            currentMoviesQ = FirebaseDatabase.getInstance().getReference("Movies")
                    .orderByChild("title").startAt(text).endAt(text + "\uf8ff");
            currentMoviesQ.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    moviesList.clear();
                    movieKey.clear();
                    if(snapshot.exists()){
                        for(DataSnapshot ss: snapshot.getChildren()){
                            Movies m = ss.getValue(Movies.class);
                            if(m.getTitle() != null ) {
                                String temp = ss.getKey();
                                movieKey.add(temp);
                                moviesList.add(m);
                            }
                        }

                        RecycleViewDisplayAdp mAdp = new RecycleViewDisplayAdp(AdminDisplayMovies.this, moviesList, movieKey);
                        ds_recycleV.setAdapter(mAdp);
                        ds_recycleV.setLayoutManager(new LinearLayoutManager(AdminDisplayMovies.this, LinearLayoutManager.VERTICAL, false));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            if(moviesList.isEmpty()){

                currentMoviesQ = FirebaseDatabase.getInstance().getReference("Movies")
                        .orderByKey().startAt(text);
                currentMoviesQ.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        moviesList.clear();
                        movieKey.clear();
                        if(snapshot.exists()){
                            for(DataSnapshot ss: snapshot.getChildren()){
                                Movies m = ss.getValue(Movies.class);
                                String temp = ss.getKey();
                                movieKey.add(temp);
                                moviesList.add(m);
                            }

                            if(!moviesList.isEmpty()){
                                RecycleViewDisplayAdp mAdp = new RecycleViewDisplayAdp(AdminDisplayMovies.this, moviesList, movieKey);
                                ds_recycleV.setAdapter(mAdp);
                                ds_recycleV.setLayoutManager(new LinearLayoutManager(AdminDisplayMovies.this, LinearLayoutManager.VERTICAL, false));
                                moviesList.clear();
                                mAdp.notifyDataSetChanged();

                            }


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }else{
            DisplayAll();
        }
    }

}