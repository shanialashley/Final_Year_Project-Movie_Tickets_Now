package com.example.authapp.UI;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.R;
import com.squareup.picasso.Picasso;

public class SelectTickets extends AppCompatActivity {

    private TextView title_tv, date_tv;
    private ImageView thumbnail_img;
    String title, date, time, thumbnail, theater_type;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tickets);

        init();
    }

    private void init() {

        title_tv = findViewById(R.id.st_title);
        title = getIntent().getExtras().getString("title");
        title_tv.setText(title);

        thumbnail_img = findViewById(R.id.st_movie_thumbnail);
        Picasso.get().load(thumbnail).into(thumbnail_img);

        date = getIntent().getExtras().getString("date");
        time = getIntent().getExtras().getString("time");
        date_tv = findViewById(R.id.st_movie_dateNtime);
        date_tv.setText(date + " "+ time);



    }
}