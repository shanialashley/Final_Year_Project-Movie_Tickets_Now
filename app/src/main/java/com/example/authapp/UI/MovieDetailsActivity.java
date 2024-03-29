package com.example.authapp.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.authapp.Model.Movies;
import com.example.authapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView movieDThumbnail, movieDCover, img1, img2, img3;
    private TextView movieDTitle, movieDDescription, movieDRating, movieDlength, movieDStarring, movieDDirectors, movieDGenre, showing;
    private Button continueB;
    private FloatingActionButton play_fab;
    private Toolbar toolbar;
    Movies movie;
    String movieTitle, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);


        iniViews();
        ToolbarInfo();




    }

    public void ToolbarInfo(){


        toolbar = findViewById(R.id.toolbar_md);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(movieTitle);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }


    void iniViews(){

        movie = (Movies) getIntent().getExtras().getSerializable("currentMovie");

        movieTitle = movie.getTitle();
        String thumbnail = movie.getThumbnail_url();
        String cover = movie.getCover_url();
        String descrip = movie.getDescription();
        String genre = movie.getGenre();
        String rating = movie.getRating();
        String length = movie.getLength();
        String starring = movie.getStarring();
        String directors = movie.getDirectors();
        String category = movie.getType();
        String trailer = movie.getTrailer_link();
        key = getIntent().getExtras().getString("key");


        movieDThumbnail = findViewById(R.id.details_movie_img);
        Picasso.get().load(thumbnail).into(movieDThumbnail);

        movieDCover = findViewById(R.id.detail_movie_cover);
        Picasso.get().load(cover).into(movieDCover);

        movieDTitle = findViewById(R.id.detail_movie_title);
        movieDTitle.setText(movieTitle);


        movieDDescription = findViewById(R.id.detail_movie_description);
        movieDDescription.setText(descrip);

        movieDGenre = findViewById(R.id.details_movie_genre);
        movieDGenre.setText(genre);

        movieDRating = findViewById(R.id.detail_movie_rating);
        movieDRating.setText(rating);

        movieDlength = findViewById(R.id.detail_movie_length);
        movieDlength.setText(length);

        movieDStarring = findViewById(R.id.detail_movie_starring);
        movieDStarring.setText(starring);

        movieDDirectors = findViewById(R.id.detail_movie_director);
        movieDDirectors.setText(directors);

        movieDCover.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));

        play_fab = findViewById(R.id.play_fab);
        play_fab.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));

        showing = findViewById(R.id.detail_movie_now_showing);
        img1 = findViewById(R.id.img_movie_theater__1);
        img2 = findViewById(R.id.img_movie_theater_2);
        img3 = findViewById(R.id.img_movie_theater_3);


        if(category.equals("upcoming")){
            showing.setVisibility(View.GONE);
            img1.setVisibility(View.GONE);
            img2.setVisibility(View.GONE);
            img3.setVisibility(View.GONE);
            continueB.setVisibility(View.GONE);

        }


        play_fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            Uri uri = Uri.parse(trailer);
                            startActivity(new Intent(Intent.ACTION_VIEW, uri));

                    }
                }
        );

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MovieDetailsActivity.this, SingleTheaterMovies.class).putExtra("screen", "CONE"));

            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MovieDetailsActivity.this, SingleTheaterMovies.class).putExtra("screen", "CC8"));

            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MovieDetailsActivity.this, SingleTheaterMovies.class).putExtra("screen", "MT"));

            }
        });

        continueB = findViewById(R.id.continueButton);
        continueB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieDetailsActivity.this, TimeSchedule.class);
                intent.putExtra("currentMovie", movie);
                intent.putExtra("key", key);
                startActivity(intent);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        ShowInTheseTheaters(key);
    }

    //Display Logo images of Theaters showing this movie
    public void ShowInTheseTheaters(String key){

        Query CC8_ref = FirebaseDatabase.getInstance().getReference("Movies").orderByKey().startAt(key).limitToFirst(1);
        CC8_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String t = ss.child("theaters").child("T_CC8").getValue(String.class);
                        String cKey = ss.getKey();
                        if(t != null && cKey.equals(key) ){
                            if(t.equals("true")) {
                                img2.setVisibility(View.VISIBLE);
                            }else{
                                img2.setVisibility(View.GONE);
                            }
                        }
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query MT_ref = FirebaseDatabase.getInstance().getReference("Movies").orderByKey().startAt(key).limitToFirst(1);
        MT_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String t = ss.child("theaters").child("T_MT").getValue(String.class);
                        String cKey = ss.getKey();
                        if(t != null && cKey.equals(key)){
                            if(t.equals("true")) {
                                img3.setVisibility(View.VISIBLE);
                            }else{
                                img3.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Query CONE_ref = FirebaseDatabase.getInstance().getReference("Movies").orderByKey().startAt(key).limitToFirst(1);
        CONE_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String t = ss.child("theaters").child("T_CONE").getValue(String.class);
                        String cKey = ss.getKey();
                        if(t != null && cKey.equals(key)){
                            if(t.equals("true")) {
                               img1.setVisibility(View.VISIBLE);
                            }else{
                                img1.setVisibility(View.GONE);
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


}