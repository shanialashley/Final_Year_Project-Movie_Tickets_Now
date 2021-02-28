package com.example.authapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.authapp.Model.Movie;
import com.example.authapp.adapters.MovieAdapter;
import com.example.authapp.adapters.MovieItemClickListener;
import com.example.authapp.R;
import com.example.authapp.Model.Slide;
import com.example.authapp.adapters.SliderPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Home extends AppCompatActivity implements MovieItemClickListener {

    private List<Slide> Slides_list;
    private ViewPager slider_pager;
    private SliderPagerAdapter adp;
    private TabLayout indicator;
    private RecyclerView MovieRV, ComingSoonRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        slider_pager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);
        MovieRV = findViewById(R.id.RV_movies);
        ComingSoonRV = findViewById(R.id.RV_Coming_Soon);

        SlidesInfor();

        CurrentMoviesInfor();

       ComingSoonMoviesInfor();



    }

    public void SlidesInfor(){

        Slides_list = new ArrayList<>();
        Slides_list.add(new Slide(R.drawable.tom_and_jerry, "Tom And Jerry"));
        Slides_list.add(new Slide(R.drawable.monster_hunter, "Monster Hunter"));
        Slides_list.add(new Slide(R.drawable.the_marksman, "The Marksman"));
        Slides_list.add(new Slide(R.drawable.the_mauritanian, "The Mauritanian"));
        Slides_list.add(new Slide(R.drawable.the_little_things, "The Little Things"));
        Slides_list.add(new Slide(R.drawable.greenland, "Greenland"));
        Slides_list.add(new Slide(R.drawable.judas, "Judas and the Black Messiah"));
        Slides_list.add(new Slide(R.drawable.wrong_turn, "Wrong Turn"));

        adp = new SliderPagerAdapter(Home.this, Slides_list);

        slider_pager.setAdapter(adp);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new Home.SliderTimer(),4000, 6000 );

        indicator.setupWithViewPager(slider_pager, true);
    }

    public void CurrentMoviesInfor(){

        List<Movie> MovieList = new ArrayList<>();
        MovieList.add(new Movie("Tom And Jerry","MOVIE SYNOPSIS:\nAdaption of the classic Hanna-Barbera property, which reveals how Tom and Jerry first meet and form their rivalry.",
                                R.drawable.tom_and_jerry,R.drawable.tom_and_jerry_cover,
                                "GENRE: Animated / Adventure / Comedy",  "RUNNING TIME: 1h 41m", "RATING: PG",
                                "STARRING: Chloë Grace Moretz, Michael Peña, Rob Delaney",
                                "DIRECTOR: Tim Story", R.raw.tom_and_jerry_trailer , "current"));

        MovieList.add(new Movie("Monster Hunter","MOVIE SYNOPSIS:\nA portal transports Lt. Artemis and an elite unit of soldiers to a strange world where powerful monsters rule with deadly ferocity. Faced with relentless danger, the team encounters a mysterious hunter who may be their only hope to find a way home."
                                 ,R.drawable.monster_hunter, R.drawable.monster_hunter_cover,
                                "GENRE: Action / Adventure / Fantasy", "RUNNING TIME: 1h 43m", "RATING: PG-13",
                                "STARRING: Milla Jovovich, Tony Jaa, T.I.",
                                "DIRECTOR: Paul W.S. Anderson", R.raw.monster_hunter_trailer, "current"));

        MovieList.add(new Movie("The Marksman", "MOVIE SYNOPSIS:\n A rancher on the Arizona border becomes the unlikely defender of a young Mexican boy desperately fleeing the cartel assassins who've pursued him into the U.S.",
                                R.drawable.the_marksman, R.drawable.the_marksman_cover,
                                "GENRE: Action / Thriller", "RUNNING TIME: 1h 47m", "RATING: PG-13",
                                "STARRING: Katheryn Winnick, Liam Neeson, Teresa Ruiz",
                                "DIRECTOR: Robert Lorenz", R.raw.the_marksman_trailer_, "current"));

        MovieList.add(new Movie("The Mauritanian", "MOVIE SYNOPSIS:\n A detainee at the U.S military's Guantanamo Bay detention center is held without charges for over a decade and seeks help from a defense attorney for his release.",
                                R.drawable.the_mauritanian, R.drawable.the_mauritatian_cover,
                                "GENRE: Drama / Thriller", "RUNNING TIME: 2h 09m", "RATING: R",
                                "STARRING: Tahar Rahim, Shailene Woodley, Benedict Cumberbatch",
                                "DIRECTOR: Kevin Macdonald", R.raw.the_mauritatian_trailer, "current" ));

        MovieList.add(new Movie("The Little Things","MOVIE SYNOPSIS:\n Deke, a burnt-out Kern County, CA deputy sheriff teams with Baxter, a crack LASD detective, to nab a serial killer. Deke's nose for the “little things” proves eerily accurate, but his willingness to circumvent the rules embroils Baxter in a soul-shattering dilemma. Meanwhile, Deke must wrestle with a dark secret from his past",
                                R.drawable.the_little_things, R.drawable.the_little_things_cover,
                                "GENRE: Thriller", "RUNNING TIME: 2h 08m", "RATING: 16 years & over",
                                "STARRING: Denzel Washington, Rami Malek, Jared Leto",
                                "DIRECTOR: John Lee Hancock", R.raw.the_little_things_trailer, "current"));

        MovieList.add(new Movie("Greenland","MOVIE SYNOPSIS:\n A detached married couple must get their son and themselves to safety after being randomly selected to enter an underground bunker, as a massive object from space threatens to destroy the world in less than 48 hours.",
                                R.drawable.greenland, R.drawable.greenland_cover,
                                "GENRE: Action / Thriller", "RUNNING TIME: 2h 00m", "RATING: PG-13",
                                "STARRING: Gerard Butler, Morena Baccarin, Scott Glenn",
                                "DIRECTOR: Ric Roman Waugh", R.raw.greenland_trailer, "current"));

        MovieList.add(new Movie("Judas and the Black Messiah","MOVIE SYNOPSIS:\n The story of Fred Hampton, deputy chairman of the national Black Panther Party, who was assassinated in 1969 by a Cook County tactical unit on the orders of the FBI and Chicago Police Department.",
                                    R.drawable.judas, R.drawable.judas_cover ,
                                    "GENRE: Biography / Drama / Historical", "RUNNING TIME: 2h 05m", "RATING: 16 years & over",
                                    "STARRING: Daniel Kaluuya, LaKeith Stanfield, Jesse Plemons",
                                    "DIRECTOR: Shaka King", R.raw.judas_trailer, "current"));

        MovieList.add(new Movie("Wrong Turn","MOVIE SYNOPSIS:\n Jen and a group of friends set out to hike the Appalachian Trail. Despite warnings to stick to the trail, the hikers stray off course—and cross into land inhabited by The Foundation, a hidden community of mountain dwellers who use deadly means to protect their way of life. Suddenly under siege, Jen and her friends seem headed to the point of no return— unless Jen’s father can reach them in time." ,
                                R.drawable.wrong_turn, R.drawable.wrong_turn_cover ,
                                "GENRE: Horror / Thriller", "RUNNING TIME: 1h 49m", "RATING: 16 years & over",
                                "STARRING: Matthew Modine, Emma Dumont, Charlotte Vega",
                                "DIRECTOR: Mike P. Nelson", R.raw.wrong_turn_trailer, "current"));

        MovieAdapter madp = new MovieAdapter(Home.this, MovieList, this);
        MovieRV.setAdapter(madp);
        MovieRV.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false));
    }

    public void ComingSoonMoviesInfor(){
        //horizontal Coming Soon
        List<Movie> ComingSoonLt = new ArrayList<>();
        ComingSoonLt.add(new Movie(R.drawable.raya, "Raya and the Last Dragon", R.drawable.raya_cover));
        ComingSoonLt.add(new Movie(R.drawable.chaos_walking, "Chaos Walking"));
        ComingSoonLt.add(new Movie(R.drawable.i_care_a_lot, "I Care A Lot"));
        ComingSoonLt.add(new Movie(R.drawable.godzilla_vs_kong, "Godzilla Vs Kong"));
        ComingSoonLt.add(new Movie(R.drawable.nobody, "Nobody"));
        ComingSoonLt.add(new Movie(R.drawable.fatale, "Fatale"));
        ComingSoonLt.add(new Movie(R.drawable.mortal_kombat, "Mortal Kombat"));
        ComingSoonLt.add(new Movie(R.drawable.black_widow, "Black Widow"));
        ComingSoonLt.add(new Movie(R.drawable.spiral, "Spiral"));
        ComingSoonLt.add(new Movie(R.drawable.ghostbusters_afterlife, "Ghostbusters: Afterlife"));
        ComingSoonLt.add(new Movie(R.drawable.in_the_heights, "In The Heights"));
        ComingSoonLt.add(new Movie(R.drawable.top_gun_maverick, "Top Gun: Mavarick"));
        ComingSoonLt.add(new Movie(R.drawable.jungle_cruise, "Jungle Cruise"));
        ComingSoonLt.add(new Movie(R.drawable.the_kings_man, "The Kings Man"));
        ComingSoonLt.add(new Movie(R.drawable.no_time_to_die, "No Time To Die"));
        ComingSoonLt.add(new Movie(R.drawable.morbius, "Morbius"));

        MovieAdapter csAdp = new MovieAdapter(this, ComingSoonLt, this);
        ComingSoonRV.setAdapter(csAdp);
        ComingSoonRV.setLayoutManager(new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false));
    }

    //Animation here to do to Details Page
    @Override
    public void onMovieClick(Movie movie, ImageView movieImageView) {

        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("title", movie.getTitle());
        intent.putExtra("img", movie.getThumbnail());
        intent.putExtra("imgCover", movie.getCoverPhoto());
        intent.putExtra("description", movie.getDescription());
        intent.putExtra("genre", movie.getGenre());
        intent.putExtra("length", movie.getLength());
        intent.putExtra("rating", movie.getRating());
        intent.putExtra("starring", movie.getStarring());
        intent.putExtra("director", movie.getDirectors());
        intent.putExtra("trailer", movie.getTrailerLink());
        intent.putExtra("category", movie.getCategory());

        @SuppressLint({"NewApi", "LocalSuppress"})
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Home.this, movieImageView, "sharedTransName");

        startActivity(intent, options.toBundle());

       // Toast.makeText(this, "Item Clicked: " + movie.getTitle(), Toast.LENGTH_LONG).show();


    }


    class SliderTimer extends TimerTask{


        @Override
        public void run() {

            Home.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if  (slider_pager.getCurrentItem() < Slides_list.size() - 1) {
                        slider_pager.setCurrentItem(slider_pager.getCurrentItem() + 1);
                    }
                    else
                        slider_pager.setCurrentItem(0);
                }
            });

        }
    }

}