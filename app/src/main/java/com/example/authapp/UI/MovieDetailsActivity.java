package com.example.authapp.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.authapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView movieDThumbnail, movieDCover;
    private TextView movieDTitle, movieDDescription, movieDRating, movieDlength, movieDStarring, movieDDirectors, movieDGenre;
    private FloatingActionButton play_fab;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);




        iniViews();


    }


    void iniViews(){



        String movieTitle = getIntent().getExtras().getString("title");
        int ImageResourceId = getIntent().getExtras().getInt("img");
        int ImageResourceCover = getIntent().getExtras().getInt("imgCover");
        String descrip = getIntent().getExtras().getString("description");
        String genre = getIntent().getExtras().getString("genre");
        String rating = getIntent().getExtras().getString("rating");
        String length = getIntent().getExtras().getString("length");
        String starring = getIntent().getExtras().getString("starring");
        String directors = getIntent().getExtras().getString("director");
        String category = getIntent().getExtras().getString("category");


        movieDThumbnail = findViewById(R.id.details_movie_img);
        movieDThumbnail.setImageResource(ImageResourceId);
        Glide.with(this).load(ImageResourceId).into(movieDThumbnail);

        movieDCover = findViewById(R.id.detail_movie_cover);
        Glide.with(this).load(ImageResourceCover).into(movieDCover);

        movieDTitle = findViewById(R.id.detail_movie_title);
        movieDTitle.setText(movieTitle);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(movieTitle);

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

//        if(category.equals("upcoming")){
//            play_fab.setVisibility(View.GONE);
//        }


        play_fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MovieDetailsActivity.this, MoviePlayerActivity.class);
                        intent.putExtra("trailer", getIntent().getExtras().getInt("trailer"));
                        startActivity(intent);

                    }
                }
        );



    }
}