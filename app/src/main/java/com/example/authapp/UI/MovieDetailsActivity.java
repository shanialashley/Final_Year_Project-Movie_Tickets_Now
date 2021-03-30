package com.example.authapp.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private ImageView movieDThumbnail, movieDCover, img1, img2, img3;
    private TextView movieDTitle, movieDDescription, movieDRating, movieDlength, movieDStarring, movieDDirectors, movieDGenre, showing;

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
        String thumbnail = getIntent().getExtras().getString("img");
        String cover = getIntent().getExtras().getString("imgCover");
        String descrip = getIntent().getExtras().getString("description");
        String genre = getIntent().getExtras().getString("genre");
        String rating = getIntent().getExtras().getString("rating");
        String length = getIntent().getExtras().getString("length");
        String starring = getIntent().getExtras().getString("starring");
        String directors = getIntent().getExtras().getString("director");
        String category = getIntent().getExtras().getString("category");
        String trailer = getIntent().getExtras().getString("trailer");


        movieDThumbnail = findViewById(R.id.details_movie_img);
        Picasso.get().load(thumbnail).into(movieDThumbnail);

        movieDCover = findViewById(R.id.detail_movie_cover);
        Picasso.get().load(cover).into(movieDCover);

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

        showing = findViewById(R.id.detail_movie_now_showing);
        img1 = findViewById(R.id.img_movie_theater__1);
        img2 = findViewById(R.id.img_movie_theater_2);
        img3 = findViewById(R.id.img_movie_theater_3);


        if(category.equals("upcoming")){
            showing.setVisibility(View.GONE);
            img1.setVisibility(View.GONE);
            img2.setVisibility(View.GONE);
            img3.setVisibility(View.GONE);

        }



        play_fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(category.equals("current")) {
                            Intent intent = new Intent(MovieDetailsActivity.this, MoviePlayerActivity.class);
                            intent.putExtra("trailer", getIntent().getExtras().getInt("trailer"));
                            startActivity(intent);
                        }else{
                            Uri uri = Uri.parse(trailer);
                            startActivity(new Intent(Intent.ACTION_VIEW, uri));
                        }
                    }
                }
        );



    }
}