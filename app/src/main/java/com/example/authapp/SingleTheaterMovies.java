package com.example.authapp;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.Movies;
import com.example.authapp.UI.MovieDetailsActivity;
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

    RecyclerView stm_RV;
    Query cc8_Ref;
    Query mt_Ref;
    Query cone_Ref;
    List<Movies> mLt;
    String theater;
    List<String> cc8Lt = new ArrayList<>();
    DatabaseReference movies_Ref;
    RecycleViewAdapterUpMovies stmAdp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_theater_movies);

        stm_RV = findViewById(R.id.stm_recycleView);

        movies_Ref = FirebaseDatabase.getInstance().getReference("Movies");
        mLt = new ArrayList<>();
        CC8_Movies();
    }

    private void CC8_Movies() {

        cc8_Ref = FirebaseDatabase.getInstance().getReference("TimeSchedule").orderByChild("CC8");
        cc8_Ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cc8Lt.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        cc8Lt.add(ss.child("movie_id").getValue().toString());
                    }
                }
                mLt.clear();
                if(cc8Lt.isEmpty()){
                    Toast.makeText(SingleTheaterMovies.this, "Empty", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(SingleTheaterMovies.this, "Size: " + cc8Lt.size(), Toast.LENGTH_SHORT).show();
                    displayQuery();
                }

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
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SingleTheaterMovies.this, movieImageView, "sharedTransName");

        startActivity(intent, options.toBundle());
    }

    public void displayQuery(){
                    Query singleM = movies_Ref.orderByChild("type").equalTo("current");
                    singleM.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for(DataSnapshot ss: snapshot.getChildren()){
                                    Movies m = ss.getValue(Movies.class);
                                    mLt.add(m);
                                }

                            }

                            stmAdp = new RecycleViewAdapterUpMovies(SingleTheaterMovies.this, mLt, SingleTheaterMovies.this);
                            stm_RV.setLayoutManager(new GridLayoutManager(SingleTheaterMovies.this, 3));
                            stm_RV.setAdapter(stmAdp);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

//                    DatabaseReference db = movies_Ref.child("1");
//                    db.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if(snapshot.exists()){
//
//                                   // Movies m = ss.getValue(Movies.class);
//                                    mLt.add(snapshot.getValue(Movies.class));
//
//                            }
//                            if(mLt.isEmpty()){
//                                Toast.makeText(SingleTheaterMovies.this, "Empty", Toast.LENGTH_SHORT).show();
//                            }else{
//                                Toast.makeText(SingleTheaterMovies.this, "Size: " + mLt.size(), Toast.LENGTH_SHORT).show();
//                                stmAdp = new RecycleViewAdapterUpMovies(SingleTheaterMovies.this, mLt, SingleTheaterMovies.this);
//                                stm_RV.setLayoutManager(new GridLayoutManager(SingleTheaterMovies.this, 3));
//                                stm_RV.setAdapter(stmAdp);
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });






    }
}