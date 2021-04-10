package com.example.authapp.UI;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class Search extends AppCompatActivity  implements MovieItemClickListener {

    ImageView searchB;
    EditText searchT;
    RecyclerView searchRV;
    Query searchQ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchB = findViewById(R.id.search_B);
        searchRV = findViewById(R.id.search_RV);
        searchT = findViewById(R.id.search_text);

        searchB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = searchT.getText().toString().trim();
                SearchDisplay(title);
            }


        });


    }

    private void SearchDisplay(String title) {

        List<Movies> mlist = new ArrayList<>();

        DatabaseReference result = FirebaseDatabase.getInstance().getReference().child("Movies");
        searchQ =  result.orderByChild("title").equalTo(title);
        searchQ.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Movies m = snapshot.getValue(Movies.class);
                    mlist.add(m);
                }

                RecycleViewAdapterUpMovies resultDAdp = new RecycleViewAdapterUpMovies(Search.this, mlist, Search.this);
                searchRV.setLayoutManager(new GridLayoutManager(Search.this, 3));
                searchRV.setAdapter(resultDAdp);
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
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Search.this, movieImageView, "sharedTransName");
        startActivity(intent, options.toBundle());

    }
}