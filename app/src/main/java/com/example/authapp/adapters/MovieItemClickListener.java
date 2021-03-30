package com.example.authapp.adapters;

import android.widget.ImageView;

import com.example.authapp.Model.Movies;

public interface MovieItemClickListener {

    void onMovieClick(Movies movie, ImageView movieImageView);

}
