package com.example.authapp.adapters;

import android.widget.ImageView;

import com.example.authapp.Model.Movie;

public interface MovieItemClickListener {

    void onMovieClick(Movie movie, ImageView movieImageView);

}
