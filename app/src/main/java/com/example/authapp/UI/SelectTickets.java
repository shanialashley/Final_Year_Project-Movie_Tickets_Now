package com.example.authapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.authapp.Model.Movies;
import com.example.authapp.R;
import com.squareup.picasso.Picasso;

public class SelectTickets extends AppCompatActivity {

    private TextView title_tv, date_tv, senior_tv, adult_tv, child_tv, max_tickets_tv, total_tv ;
    private EditText senior_tickets, adult_tickets, child_tickets;
    private ImageView thumbnail_img;
    private Button continueB;
    private Toolbar toolbar;
    private Movies movie;

    String title;
    String date;
    String time;
    String thumbnail;
    String theater_type, theater_title;
    String rating;
    String theater , location, movie_type;
    int senior_num = 0, child_num = 0 , adult_num = 0;
    int cc8_r_Aprice = 35, cc8_3d_Aprice = 0, cc8_4dx_Aprice = 90, cc8_cxc_Aprice = 50;
    int cc8_r_Cprice = 28, cc8_3d_Cprice = 0, cc8_4dx_Cprice = 75 , cc8_cxc_Cprice = 35;
    int cc8_r_Sprice = 23, cc8_3d_Sprice = 0, cc8_4dx_Sprice = 85, cc8_cxc_Sprice = 45;
    int mt_r_Aprice = 50, mt_r_Cprice = 40,
            mt_3d_Aprice = 60, mt_3d_Cprice = 50,
            mt_tgo_Aprice = 45, mt_tgo_Cprice = 30;

    int imax_r_Aprice = 65, imax_r_Cprice = 50, imax_r_Sprice = 55,
            imax_H3d_Aprice = 75, imax_H3d_Cprice = 60 , imax_H3d_Sprice = 65,
            imax_C3d_Aprice = 50, imax_C3d_Cprice = 40 , imax_C3d_Sprice = 45 ;

    int gs_price = 100, cone_4dx_Aprice = 100, cone_4dx_Cprice = 85, cone_4dx_Sprice = 85;
    int total_tickets = 0, total_amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tickets);

        ToolbarInfo();
        init();


    }

    public void ToolbarInfo(){


        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    private void init() {

        movie = (Movies) getIntent().getExtras().getSerializable("currentMovie");

        title_tv = findViewById(R.id.st_title);
        title = movie.getTitle();
        title_tv.setText(title);

        thumbnail_img = findViewById(R.id.st_movie_thumbnail);
        thumbnail = movie.getThumbnail_url();
        Picasso.get().load(thumbnail).into(thumbnail_img);

        date = getIntent().getExtras().getString("selectDate");
        time = getIntent().getExtras().getString("time");
        date_tv = findViewById(R.id.st_movie_dateNtime);
        date_tv.setText(date + "\n\nShow Time: "+ time);

        theater_type = getIntent().getExtras().getString("theater_type");

        senior_tv = findViewById(R.id.st_senior);
        senior_tickets = findViewById(R.id.st_senior_num);


        adult_tv = findViewById(R.id.st_adult);
        adult_tickets = findViewById(R.id.st_adult_num);

        child_tv = findViewById(R.id.st_child);
        child_tickets = findViewById(R.id.st_child_num);

        max_tickets_tv = findViewById(R.id.st_maxi_num);
        total_tv = findViewById(R.id.st_total_amount);

        continueB = findViewById(R.id.st_continueB);

        Checktickets();

        continueB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(total_amount != 0) {
                    Intent intent = new Intent(SelectTickets.this, Purchases.class);
                    intent.putExtra("theater_title", theater_title);
                    intent.putExtra("theater", theater);
                    intent.putExtra("location", location);
                    intent.putExtra("movie_type", movie_type);
                    intent.putExtra("title", title);
                    intent.putExtra("date", date);
                    intent.putExtra("time", time);
                    intent.putExtra("adult_tickets", adult_num);
                    intent.putExtra("child_tickets", child_num);
                    intent.putExtra("senior_tickets", senior_num);
                    intent.putExtra("max_tickets", total_tickets);
                    intent.putExtra("Total", total_amount);
                    startActivity(intent);
                }
            }
        });


        rating = movie.getRating();
        if(rating.equals("Rating: R")){
            child_tv.setVisibility(View.GONE);
            child_tickets.setVisibility(View.GONE);
        }




    }

    public void Checktickets(){

        if(theater_type.equals("cc8_t_r") || theater_type.equals("cc8_t_3D") ||
                theater_type.equals("cc8_s_r") || theater_type.equals("cc8_s_3d") ||
                theater_type.equals("cc8_s_4dx") || theater_type.equals("cc8_s_cxc")) {

                theater_title = "Caribbean Cinema 8";
                 getSupportActionBar().setTitle(theater_title);
                  theater = theater_title;

                 if(theater_type.equals("cc8_t_r") ||theater_type.equals("cc8_s_r")){
                     movie_type = "regular";
                     if(theater_type.equals("cc8_t_r")){
                         theater_title = "Caribbean Cinema 8: Trincity 2D";
                         getSupportActionBar().setTitle(theater_title);
                            location = "Trincity";

                     }else if(theater_type.equals("cc8_s_r")){
                         theater_title = "Caribbean Cinema 8: SouthPark 2D";
                         getSupportActionBar().setTitle(theater_title);
                         location = "SouthPark";
                     }


                     senior_tv.setText("Senior ($" + cc8_r_Sprice + ")");
                     adult_tv.setText("Adult ($" + cc8_r_Aprice + ")");
                     child_tv.setText("Child ($"+ cc8_r_Cprice + ")");

                     senior_tickets.addTextChangedListener(new TextWatcher() {
                         @Override
                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                         }

                         @Override
                         public void onTextChanged(CharSequence s, int start, int before, int count) {

                         }

                         @Override
                         public void afterTextChanged(Editable s) {
                             String temp = s.toString();
                             if(!temp.equals("") ) {
                                 senior_num = Integer.parseInt(temp);
                             }else{
                                 senior_num = 0;
                             }

                             if(senior_num <= 10) {

                                 total_tickets =  adult_num + child_num + senior_num ;

                                 if(total_tickets <= 10) {

                                     max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                     total_amount = (adult_num * cc8_r_Aprice) + (child_num * cc8_r_Cprice) + (senior_num * cc8_r_Sprice);
                                     total_tv.setText("$" + total_amount + ".00");

                                 }else if(total_tickets >= 10){

                                     total_tickets =  adult_num + child_num - senior_num ;
                                     senior_tickets.setError("10 Tickets Maximum ");
                                     senior_tickets.requestFocus();
                                     return;

                                 }

                             }else if(senior_num > 10){

                                 senior_tickets.setError("10 is the Maximum Number of Tickets!");
                                 senior_tickets.requestFocus();
                                 return;

                             }

                         }
                     });

                     adult_tickets.addTextChangedListener(new TextWatcher() {

                         @Override
                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                         }

                         @Override
                         public void onTextChanged(CharSequence s, int start, int before, int count) {

                         }

                         @Override
                         public void afterTextChanged(Editable s) {

                             String temp = s.toString();
                             if(!temp.equals("") ) {
                                 adult_num = Integer.parseInt(temp);
                             }else{
                                 adult_num = 0;
                             }

                             if(adult_num <= 10) {

                                 total_tickets =  senior_num+ child_num + adult_num ;

                                 if(total_tickets <= 10) {

                                     max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                     total_amount = (adult_num * cc8_r_Aprice) + (child_num * cc8_r_Cprice) + (senior_num * cc8_r_Sprice);
                                     total_tv.setText("$" + total_amount + ".00");

                                 }else if(total_tickets >= 10){

                                     total_tickets =  senior_num + child_num - adult_num ;
                                     adult_tickets.setError("10 Tickets Maximum ");
                                     adult_tickets.requestFocus();
                                     return;
                                 }

                             }else if(adult_num > 10){

                                 adult_tickets.setError("10 is the Maximum Number of Tickets!");
                                 adult_tickets.requestFocus();
                                 return;

                             }


                         }
                     });

                     if(movie.getRating().equals("RATING: ")){}

                     child_tickets.addTextChangedListener(new TextWatcher() {

                         @Override
                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                         }

                         @Override
                         public void onTextChanged(CharSequence s, int start, int before, int count) {

                         }

                         @Override
                         public void afterTextChanged(Editable s) {

                             String temp = s.toString();
                             if(!temp.equals("") ) {
                                 child_num = Integer.parseInt(temp);
                             }else{
                                 child_num = 0;
                             }

                             if(child_num <= 10) {

                                 total_tickets =  adult_num + child_num + senior_num;

                                 if(total_tickets <= 10) {

                                     max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                     total_amount = (adult_num * cc8_r_Aprice) + (child_num * cc8_r_Cprice) + (senior_num * cc8_r_Sprice);
                                     total_tv.setText("$" + total_amount + ".00" );

                                 }else if(total_tickets >= 10){

                                     total_tickets =  adult_num + senior_num - child_num;
                                     child_tickets.setError("10 Tickets Maximum ");
                                     child_tickets.requestFocus();
                                     return;
                                 }

                             }else if(child_num > 10){

                                 child_tickets.setError("10 is the Maximum Number of Tickets!");
                                 child_tickets.requestFocus();
                                 return;

                             }

                         }
                     });

                 }

                 if(theater_type.equals("cc8_t_3D") || theater_type.equals("cc8_s_3d") ){

                     movie_type = "3D";

                     if(theater_type.equals("cc8_t_3D")){
                         theater_title = "Caribbean Cinema 8: Trincity 3D";
                         getSupportActionBar().setTitle(theater_title);
                         location = "Trincity";
                     }else if(theater_type.equals("cc8_s_3d")){

                         theater_title = "Caribbean Cinema 8: SouthPark 3D";
                         getSupportActionBar().setTitle(theater_title);
                         location = "southPark";
                     }

                     senior_tv.setText("Senior ($" + cc8_3d_Sprice + ")");
                     adult_tv.setText("Adult ($" + cc8_3d_Aprice + ")");
                     child_tv.setText("Child ($"+ cc8_3d_Cprice + ")");

                     senior_tickets.addTextChangedListener(new TextWatcher() {
                         @Override
                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                         }

                         @Override
                         public void onTextChanged(CharSequence s, int start, int before, int count) {

                         }

                         @Override
                         public void afterTextChanged(Editable s) {
                             String temp = s.toString();
                             if(!temp.equals("") ) {
                                 senior_num = Integer.parseInt(temp);
                             }else{
                                 senior_num = 0;
                             }

                             if(senior_num <= 10) {

                                 total_tickets =  adult_num + child_num + senior_num ;

                                 if(total_tickets <= 10) {

                                     max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                     total_amount = (adult_num * cc8_3d_Aprice) + (child_num * cc8_3d_Cprice) + (senior_num * cc8_3d_Sprice);
                                     total_tv.setText("$" + total_amount + ".00");

                                 }else if(total_tickets >= 10){

                                     total_tickets =  adult_num + child_num - senior_num ;
                                     senior_tickets.setError("10 Tickets Maximum ");
                                     senior_tickets.requestFocus();
                                     return;

                                 }

                             }else if(senior_num > 10){

                                 senior_tickets.setError("10 is the Maximum Number of Tickets!");
                                 senior_tickets.requestFocus();
                                 return;

                             }

                         }
                     });

                     adult_tickets.addTextChangedListener(new TextWatcher() {

                         @Override
                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                         }

                         @Override
                         public void onTextChanged(CharSequence s, int start, int before, int count) {

                         }

                         @Override
                         public void afterTextChanged(Editable s) {

                             String temp = s.toString();
                             if(!temp.equals("") ) {
                                 adult_num = Integer.parseInt(temp);
                             }else{
                                 adult_num = 0;
                             }

                             if(adult_num <= 10) {

                                 total_tickets =  senior_num+ child_num + adult_num ;

                                 if(total_tickets <= 10) {

                                     max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                     total_amount = (adult_num * cc8_3d_Aprice) + (child_num * cc8_3d_Cprice) + (senior_num * cc8_3d_Sprice);
                                     total_tv.setText("$" + total_amount + ".00");

                                 }else if(total_tickets >= 10){

                                     total_tickets =  senior_num + child_num - adult_num ;
                                     adult_tickets.setError("10 Tickets Maximum ");
                                     adult_tickets.requestFocus();
                                     return;
                                 }

                             }else if(adult_num > 10){

                                 adult_tickets.setError("10 is the Maximum Number of Tickets!");
                                 adult_tickets.requestFocus();
                                 return;

                             }


                         }
                     });

                     child_tickets.addTextChangedListener(new TextWatcher() {

                         @Override
                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                         }

                         @Override
                         public void onTextChanged(CharSequence s, int start, int before, int count) {

                         }

                         @Override
                         public void afterTextChanged(Editable s) {

                             String temp = s.toString();
                             if(!temp.equals("") ) {
                                 child_num = Integer.parseInt(temp);
                             }else{
                                 child_num = 0;
                             }

                             if(child_num <= 10) {

                                 total_tickets =  adult_num + child_num + senior_num;

                                 if(total_tickets <= 10) {

                                     max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                     total_amount = (adult_num * cc8_3d_Aprice) + (child_num * cc8_3d_Cprice) + (senior_num * cc8_3d_Sprice);
                                     total_tv.setText("$" + total_amount + ".00" );

                                 }else if(total_tickets >= 10){

                                     total_tickets =  adult_num + senior_num - child_num;
                                     child_tickets.setError("10 Tickets Maximum ");
                                     child_tickets.requestFocus();
                                     return;
                                 }

                             }else if(child_num > 10){

                                 child_tickets.setError("10 is the Maximum Number of Tickets!");
                                 child_tickets.requestFocus();
                                 return;

                             }

                         }
                     });

                 }

                 if( theater_type.equals("cc8_s_4dx")){

                     theater_title = "Caribbean Cinema 8: SouthPark 4DX";
                     getSupportActionBar().setTitle(theater_title);
                     location = "SouthPark";
                     movie_type = "4DX";

                     senior_tv.setText("Senior ($" + cc8_4dx_Sprice + ")");
                     adult_tv.setText("Adult ($" + cc8_4dx_Aprice + ")");
                     child_tv.setText("Child ($"+ cc8_4dx_Cprice + ")");

                     senior_tickets.addTextChangedListener(new TextWatcher() {
                         @Override
                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                         }

                         @Override
                         public void onTextChanged(CharSequence s, int start, int before, int count) {

                         }

                         @Override
                         public void afterTextChanged(Editable s) {
                             String temp = s.toString();
                             if(!temp.equals("") ) {
                                 senior_num = Integer.parseInt(temp);
                             }else{
                                 senior_num = 0;
                             }

                             if(senior_num <= 10) {

                                 total_tickets =  adult_num + child_num + senior_num ;

                                 if(total_tickets <= 10) {

                                     max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                     total_amount = (adult_num * cc8_4dx_Aprice) + (child_num * cc8_4dx_Cprice) + (senior_num * cc8_4dx_Sprice);
                                     total_tv.setText("$" + total_amount + ".00");

                                 }else if(total_tickets >= 10){

                                     total_tickets =  adult_num + child_num - senior_num ;
                                     senior_tickets.setError("10 Tickets Maximum ");
                                     senior_tickets.requestFocus();
                                     return;

                                 }

                             }else if(senior_num > 10){

                                 senior_tickets.setError("10 is the Maximum Number of Tickets!");
                                 senior_tickets.requestFocus();
                                 return;

                             }

                         }
                     });

                     adult_tickets.addTextChangedListener(new TextWatcher() {

                         @Override
                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                         }

                         @Override
                         public void onTextChanged(CharSequence s, int start, int before, int count) {

                         }

                         @Override
                         public void afterTextChanged(Editable s) {

                             String temp = s.toString();
                             if(!temp.equals("") ) {
                                 adult_num = Integer.parseInt(temp);
                             }else{
                                 adult_num = 0;
                             }

                             if(adult_num <= 10) {

                                 total_tickets =  senior_num+ child_num + adult_num ;

                                 if(total_tickets <= 10) {

                                     max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                     total_amount = (adult_num * cc8_4dx_Aprice) + (child_num * cc8_4dx_Cprice) + (senior_num * cc8_4dx_Sprice);
                                     total_tv.setText("$" + total_amount + ".00");

                                 }else if(total_tickets >= 10){

                                     total_tickets =  senior_num + child_num - adult_num ;
                                     adult_tickets.setError("10 Tickets Maximum ");
                                     adult_tickets.requestFocus();
                                     return;
                                 }

                             }else if(adult_num > 10){

                                 adult_tickets.setError("10 is the Maximum Number of Tickets!");
                                 adult_tickets.requestFocus();
                                 return;

                             }


                         }
                     });

                     child_tickets.addTextChangedListener(new TextWatcher() {

                         @Override
                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                         }

                         @Override
                         public void onTextChanged(CharSequence s, int start, int before, int count) {

                         }

                         @Override
                         public void afterTextChanged(Editable s) {

                             String temp = s.toString();
                             if(!temp.equals("") ) {
                                 child_num = Integer.parseInt(temp);
                             }else{
                                 child_num = 0;
                             }

                             if(child_num <= 10) {

                                 total_tickets =  adult_num + child_num + senior_num;

                                 if(total_tickets <= 10) {

                                     max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                     total_amount = (adult_num * cc8_4dx_Aprice) + (child_num * cc8_4dx_Cprice) + (senior_num * cc8_4dx_Sprice);
                                     total_tv.setText("$" + total_amount + ".00" );

                                 }else if(total_tickets >= 10){

                                     total_tickets =  adult_num + senior_num - child_num;
                                     child_tickets.setError("10 Tickets Maximum ");
                                     child_tickets.requestFocus();
                                     return;
                                 }

                             }else if(child_num > 10){

                                 child_tickets.setError("10 is the Maximum Number of Tickets!");
                                 child_tickets.requestFocus();
                                 return;

                             }

                         }
                     });

                 }

                 if( theater_type.equals("cc8_s_cxc") ){

                     theater_title = "Caribbean Cinema 8: SouthPark CXC";
                     getSupportActionBar().setTitle(theater_title);
                        location = "SouthPark";
                        movie_type = "CXC";


                     senior_tv.setText("Senior ($" + cc8_cxc_Sprice + ")");
                     adult_tv.setText("Adult ($" + cc8_cxc_Aprice + ")");
                     child_tv.setText("Child ($"+ cc8_cxc_Cprice + ")");

                     senior_tickets.addTextChangedListener(new TextWatcher() {
                         @Override
                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                         }

                         @Override
                         public void onTextChanged(CharSequence s, int start, int before, int count) {

                         }

                         @Override
                         public void afterTextChanged(Editable s) {
                             String temp = s.toString();
                             if(!temp.equals("") ) {
                                 senior_num = Integer.parseInt(temp);
                             }else{
                                 senior_num = 0;
                             }

                             if(senior_num <= 10) {

                                 total_tickets =  adult_num + child_num + senior_num ;

                                 if(total_tickets <= 10) {

                                     max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                     total_amount = (adult_num * cc8_cxc_Aprice) + (child_num * cc8_cxc_Cprice) + (senior_num * cc8_cxc_Sprice);
                                     total_tv.setText("$" + total_amount + ".00");

                                 }else if(total_tickets >= 10){

                                     total_tickets =  adult_num + child_num - senior_num ;
                                     senior_tickets.setError("10 Tickets Maximum ");
                                     senior_tickets.requestFocus();
                                     return;

                                 }

                             }else if(senior_num > 10){

                                 senior_tickets.setError("10 is the Maximum Number of Tickets!");
                                 senior_tickets.requestFocus();
                                 return;

                             }

                         }
                     });

                     adult_tickets.addTextChangedListener(new TextWatcher() {

                         @Override
                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                         }

                         @Override
                         public void onTextChanged(CharSequence s, int start, int before, int count) {

                         }

                         @Override
                         public void afterTextChanged(Editable s) {

                             String temp = s.toString();
                             if(!temp.equals("") ) {
                                 adult_num = Integer.parseInt(temp);
                             }else{
                                 adult_num = 0;
                             }

                             if(adult_num <= 10) {

                                 total_tickets =  senior_num+ child_num + adult_num ;

                                 if(total_tickets <= 10) {

                                     max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                     total_amount = (adult_num * cc8_cxc_Aprice) + (child_num * cc8_cxc_Cprice) + (senior_num * cc8_cxc_Sprice);
                                     total_tv.setText("$" + total_amount + ".00");

                                 }else if(total_tickets >= 10){

                                     total_tickets =  senior_num + child_num - adult_num ;
                                     adult_tickets.setError("10 Tickets Maximum ");
                                     adult_tickets.requestFocus();
                                     return;
                                 }

                             }else if(adult_num > 10){

                                 adult_tickets.setError("10 is the Maximum Number of Tickets!");
                                 adult_tickets.requestFocus();
                                 return;

                             }


                         }
                     });

                     child_tickets.addTextChangedListener(new TextWatcher() {

                         @Override
                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                         }

                         @Override
                         public void onTextChanged(CharSequence s, int start, int before, int count) {

                         }

                         @Override
                         public void afterTextChanged(Editable s) {

                             String temp = s.toString();
                             if(!temp.equals("") ) {
                                 child_num = Integer.parseInt(temp);
                             }else{
                                 child_num = 0;
                             }

                             if(child_num <= 10) {

                                 total_tickets =  adult_num + child_num + senior_num;

                                 if(total_tickets <= 10) {

                                     max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                     total_amount = (adult_num * cc8_cxc_Aprice) + (child_num * cc8_cxc_Cprice) + (senior_num * cc8_cxc_Sprice);
                                     total_tv.setText("$" + total_amount + ".00" );

                                 }else if(total_tickets >= 10){

                                     total_tickets =  adult_num + senior_num - child_num;
                                     child_tickets.setError("10 Tickets Maximum ");
                                     child_tickets.requestFocus();
                                     return;
                                 }

                             }else if(child_num > 10){

                                 child_tickets.setError("10 is the Maximum Number of Tickets!");
                                 child_tickets.requestFocus();
                                 return;

                             }

                         }
                     });

                 }


        }

        if(theater_type.equals("mt_pos_r") || theater_type.equals("mt_chag_r") || theater_type.equals("mt_sdo_r") ||
                theater_type.equals("mt_tgo_r") || theater_type.equals("mt_pos_3d") ||
                theater_type.equals("mt_chag_3d") || theater_type.equals("mt_sdo_3d") || theater_type.equals("mt_tgo_3d")){

            theater_title = "Movie Towne";
            getSupportActionBar().setTitle(theater_title);
            theater = theater_title;

            if(theater_type.equals("mt_pos_r") || theater_type.equals("mt_chag_r") || theater_type.equals("mt_sdo_r") ){
                    movie_type = "regular";

                if(theater_type.equals("mt_pos_r")){
                    theater_title = "Movie Towne: Port Of Spain 2D";
                    getSupportActionBar().setTitle(theater_title);
                    location = "Port Of Spain";

                }else if(theater_type.equals("mt_chag_r")){

                    theater_title = "Movie Towne: Chaguanas 2D";
                    getSupportActionBar().setTitle(theater_title);
                    location = "Chaguanas";

                }else if(theater_type.equals("mt_sdo_r")){
                    theater_title = "Movie Towne: San Fernando 2D";
                    getSupportActionBar().setTitle(theater_title);
                    location = "San Fernando";
                }


                senior_tv.setText("Senior ($" + mt_r_Aprice + ")");
                adult_tv.setText("Adult ($" + mt_r_Aprice + ")");
                child_tv.setText("Child ($"+ mt_r_Cprice + ")");

                senior_tickets.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            senior_num = Integer.parseInt(temp);
                        }else{
                            senior_num = 0;
                        }

                        if(senior_num <= 10) {

                            total_tickets =  adult_num + child_num + senior_num ;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * mt_r_Aprice) + (child_num * mt_r_Cprice) + (senior_num * mt_r_Aprice);
                                total_tv.setText("$" + total_amount + ".00");

                            }else if(total_tickets >= 10){

                                total_tickets =  adult_num + child_num - senior_num ;
                                senior_tickets.setError("10 Tickets Maximum ");
                                senior_tickets.requestFocus();
                                return;

                            }

                        }else if(senior_num > 10){

                            senior_tickets.setError("10 is the Maximum Number of Tickets!");
                            senior_tickets.requestFocus();
                            return;

                        }

                    }
                });

                adult_tickets.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            adult_num = Integer.parseInt(temp);
                        }else{
                            adult_num = 0;
                        }

                        if(adult_num <= 10) {

                            total_tickets =  senior_num+ child_num + adult_num ;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * mt_r_Aprice) + (child_num * mt_r_Cprice) + (senior_num * mt_r_Aprice);
                                total_tv.setText("$" + total_amount + ".00");

                            }else if(total_tickets >= 10){

                                total_tickets =  senior_num + child_num - adult_num ;
                                adult_tickets.setError("10 Tickets Maximum ");
                                adult_tickets.requestFocus();
                                return;
                            }

                        }else if(adult_num > 10){

                            adult_tickets.setError("10 is the Maximum Number of Tickets!");
                            adult_tickets.requestFocus();
                            return;

                        }


                    }
                });

                child_tickets.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            child_num = Integer.parseInt(temp);
                        }else{
                            child_num = 0;
                        }

                        if(child_num <= 10) {

                            total_tickets =  adult_num + child_num + senior_num;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * mt_r_Aprice) + (child_num * mt_r_Cprice) + (senior_num * mt_r_Aprice);
                                total_tv.setText("$" + total_amount + ".00" );

                            }else if(total_tickets >= 10){

                                total_tickets =  adult_num + senior_num - child_num;
                                child_tickets.setError("10 Tickets Maximum ");
                                child_tickets.requestFocus();
                                return;
                            }

                        }else if(child_num > 10){

                            child_tickets.setError("10 is the Maximum Number of Tickets!");
                            child_tickets.requestFocus();
                            return;

                        }

                    }
                });

            }

            if(theater_type.equals("mt_tgo_r")){

                theater_title = "Movie Towne: Tobago 2D";
                getSupportActionBar().setTitle(theater_title);
                location = "Tobago";
                movie_type = "regular";


                senior_tv.setText("Senior ($" + mt_tgo_Aprice + ")");
                adult_tv.setText("Adult ($" + mt_tgo_Aprice + ")");
                child_tv.setText("Child ($"+ mt_tgo_Cprice + ")");

                senior_tickets.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            senior_num = Integer.parseInt(temp);
                        }else{
                            senior_num = 0;
                        }

                        if(senior_num <= 10) {

                            total_tickets =  adult_num + child_num + senior_num ;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * mt_tgo_Aprice) + (child_num * mt_tgo_Cprice) + (senior_num * mt_tgo_Aprice);
                                total_tv.setText("$" + total_amount + ".00");

                            }else if(total_tickets >= 10){

                                total_tickets =  adult_num + child_num - senior_num ;
                                senior_tickets.setError("10 Tickets Maximum ");
                                senior_tickets.requestFocus();
                                return;

                            }

                        }else if(senior_num > 10){

                            senior_tickets.setError("10 is the Maximum Number of Tickets!");
                            senior_tickets.requestFocus();
                            return;

                        }

                    }
                });

                adult_tickets.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            adult_num = Integer.parseInt(temp);
                        }else{
                            adult_num = 0;
                        }

                        if(adult_num <= 10) {

                            total_tickets =  senior_num+ child_num + adult_num ;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * mt_tgo_Aprice) + (child_num * mt_tgo_Cprice) + (senior_num * mt_tgo_Aprice);
                                total_tv.setText("$" + total_amount + ".00");

                            }else if(total_tickets >= 10){

                                total_tickets =  senior_num + child_num - adult_num ;
                                adult_tickets.setError("10 Tickets Maximum ");
                                adult_tickets.requestFocus();
                                return;
                            }

                        }else if(adult_num > 10){

                            adult_tickets.setError("10 is the Maximum Number of Tickets!");
                            adult_tickets.requestFocus();
                            return;

                        }


                    }
                });

                child_tickets.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            child_num = Integer.parseInt(temp);
                        }else{
                            child_num = 0;
                        }

                        if(child_num <= 10) {

                            total_tickets =  adult_num + child_num + senior_num;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * mt_tgo_Aprice) + (child_num * mt_tgo_Cprice) + (senior_num * mt_tgo_Aprice);
                                total_tv.setText("$" + total_amount + ".00" );

                            }else if(total_tickets >= 10){

                                total_tickets =  adult_num + senior_num - child_num;
                                child_tickets.setError("10 Tickets Maximum ");
                                child_tickets.requestFocus();
                                return;
                            }

                        }else if(child_num > 10){

                            child_tickets.setError("10 is the Maximum Number of Tickets!");
                            child_tickets.requestFocus();
                            return;

                        }

                    }
                });

            }

            if( theater_type.equals("mt_pos_3d") || theater_type.equals("mt_chag_3d") || theater_type.equals("mt_sdo_3d") ){

                movie_type = "3D";

                if(theater_type.equals("mt_pos_3d")){
                    theater_title = "Movie Towne: Port Of Spain 3D";
                    getSupportActionBar().setTitle(theater_title);
                    location = "Port Of Spain";

                }else if(theater_type.equals("mt_chag_3d")){

                    theater_title = "Movie Towne: Chaguanas 3D";
                    getSupportActionBar().setTitle(theater_title);
                    location = "Chaguanas";

                }else if(theater_type.equals("mt_sdo_3d")){
                    theater_title = "Movie Towne: San Fernando 3D";
                    getSupportActionBar().setTitle(theater_title);
                    location = "San Fernando";
                }


                senior_tv.setText("Senior ($" + mt_3d_Aprice + ")");
                adult_tv.setText("Adult ($" + mt_3d_Aprice + ")");
                child_tv.setText("Child ($"+ mt_3d_Cprice + ")");

                senior_tickets.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            senior_num = Integer.parseInt(temp);
                        }else{
                            senior_num = 0;
                        }

                        if(senior_num <= 10) {

                            total_tickets =  adult_num + child_num + senior_num ;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * mt_3d_Aprice) + (child_num * mt_3d_Cprice) + (senior_num * mt_3d_Aprice);
                                total_tv.setText("$" + total_amount + ".00");

                            }else if(total_tickets >= 10){

                                total_tickets =  adult_num + child_num - senior_num ;
                                senior_tickets.setError("10 Tickets Maximum ");
                                senior_tickets.requestFocus();
                                return;

                            }

                        }else if(senior_num > 10){

                            senior_tickets.setError("10 is the Maximum Number of Tickets!");
                            senior_tickets.requestFocus();
                            return;

                        }

                    }
                });

                adult_tickets.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            adult_num = Integer.parseInt(temp);
                        }else{
                            adult_num = 0;
                        }

                        if(adult_num <= 10) {

                            total_tickets =  senior_num+ child_num + adult_num ;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * mt_3d_Aprice) + (child_num * mt_3d_Cprice) + (senior_num * mt_3d_Aprice);
                                total_tv.setText("$" + total_amount + ".00");

                            }else if(total_tickets >= 10){

                                total_tickets =  senior_num + child_num - adult_num ;
                                adult_tickets.setError("10 Tickets Maximum ");
                                adult_tickets.requestFocus();
                                return;
                            }

                        }else if(adult_num > 10){

                            adult_tickets.setError("10 is the Maximum Number of Tickets!");
                            adult_tickets.requestFocus();
                            return;

                        }


                    }
                });

                child_tickets.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            child_num = Integer.parseInt(temp);
                        }else{
                            child_num = 0;
                        }

                        if(child_num <= 10) {

                            total_tickets =  adult_num + child_num + senior_num;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * mt_3d_Aprice) + (child_num * mt_3d_Cprice) + (senior_num * mt_3d_Aprice);
                                total_tv.setText("$" + total_amount + ".00" );

                            }else if(total_tickets >= 10){

                                total_tickets =  adult_num + senior_num - child_num;
                                child_tickets.setError("10 Tickets Maximum ");
                                child_tickets.requestFocus();
                                return;
                            }

                        }else if(child_num > 10){

                            child_tickets.setError("10 is the Maximum Number of Tickets!");
                            child_tickets.requestFocus();
                            return;

                        }

                    }
                });

            }

        }



        if(theater_type.equals("cone_gs") || theater_type.equals("cone_4dx") || theater_type.equals("cone_imax_r")){


            theater_title = "Cinema ONE";
            getSupportActionBar().setTitle(theater_title);
            theater = theater_title;


            if(theater_type.equals("cone_gs")){

                theater_title = "Cinema ONE: GemStone";
                getSupportActionBar().setTitle(theater_title);
                location = "GemStone";
                movie_type = "GemStone";

                senior_tv.setText("Senior ($" + gs_price + ")");
                adult_tv.setText("Adult ($" + gs_price  + ")");
                child_tv.setText("Child ($"+ gs_price  + ")");

                senior_tickets.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            senior_num = Integer.parseInt(temp);
                        }else{
                            senior_num = 0;
                        }

                        if(senior_num <= 10) {

                            total_tickets =  adult_num + child_num + senior_num ;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * gs_price ) + (child_num * gs_price ) + (senior_num * gs_price );
                                total_tv.setText("$" + total_amount + ".00");

                            }else if(total_tickets >= 10){

                                total_tickets =  adult_num + child_num - senior_num ;
                                senior_tickets.setError("10 Tickets Maximum ");
                                senior_tickets.requestFocus();
                                return;

                            }

                        }else if(senior_num > 10){

                            senior_tickets.setError("10 is the Maximum Number of Tickets!");
                            senior_tickets.requestFocus();
                            return;

                        }

                    }
                });

                adult_tickets.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            adult_num = Integer.parseInt(temp);
                        }else{
                            adult_num = 0;
                        }

                        if(adult_num <= 10) {

                            total_tickets =  senior_num+ child_num + adult_num ;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * gs_price ) + (child_num * gs_price ) + (senior_num * gs_price );
                                total_tv.setText("$" + total_amount + ".00");

                            }else if(total_tickets >= 10){

                                total_tickets =  senior_num + child_num - adult_num ;
                                adult_tickets.setError("10 Tickets Maximum ");
                                adult_tickets.requestFocus();
                                return;
                            }

                        }else if(adult_num > 10){

                            adult_tickets.setError("10 is the Maximum Number of Tickets!");
                            adult_tickets.requestFocus();
                            return;

                        }


                    }
                });

                child_tickets.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            child_num = Integer.parseInt(temp);
                        }else{
                            child_num = 0;
                        }

                        if(child_num <= 10) {

                            total_tickets =  adult_num + child_num + senior_num;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * gs_price ) + (child_num * gs_price ) + (senior_num * gs_price );
                                total_tv.setText("$" + total_amount + ".00" );

                            }else if(total_tickets >= 10){

                                total_tickets =  adult_num + senior_num - child_num;
                                child_tickets.setError("10 Tickets Maximum ");
                                child_tickets.requestFocus();
                                return;
                            }

                        }else if(child_num > 10){

                            child_tickets.setError("10 is the Maximum Number of Tickets!");
                            child_tickets.requestFocus();
                            return;

                        }

                    }
                });
            }

            if(theater_type.equals("cone_4dx")){

                theater_title = "Cinema ONE: 4DX";
                getSupportActionBar().setTitle(theater_title);
                location = "4DX";
                movie_type = "4DX";

                senior_tv.setText("Senior ($" + cone_4dx_Sprice + ")");
                adult_tv.setText("Adult ($" + cone_4dx_Aprice + ")");
                child_tv.setText("Child ($"+ cone_4dx_Cprice + ")");

                senior_tickets.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            senior_num = Integer.parseInt(temp);
                        }else{
                            senior_num = 0;
                        }

                        if(senior_num <= 10) {

                            total_tickets =  adult_num + child_num + senior_num ;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * cone_4dx_Aprice) + (child_num * cone_4dx_Cprice) + (senior_num * cone_4dx_Sprice);
                                total_tv.setText("$" + total_amount + ".00");

                            }else if(total_tickets >= 10){

                                total_tickets =  adult_num + child_num - senior_num ;
                                senior_tickets.setError("10 Tickets Maximum ");
                                senior_tickets.requestFocus();
                                return;

                            }

                        }else if(senior_num > 10){

                            senior_tickets.setError("10 is the Maximum Number of Tickets!");
                            senior_tickets.requestFocus();
                            return;

                        }

                    }
                });

                adult_tickets.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            adult_num = Integer.parseInt(temp);
                        }else{
                            adult_num = 0;
                        }

                        if(adult_num <= 10) {

                            total_tickets =  senior_num+ child_num + adult_num ;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * cone_4dx_Aprice) + (child_num * cone_4dx_Cprice) + (senior_num * cone_4dx_Sprice);
                                total_tv.setText("$" + total_amount + ".00");

                            }else if(total_tickets >= 10){

                                total_tickets =  senior_num + child_num - adult_num ;
                                adult_tickets.setError("10 Tickets Maximum ");
                                adult_tickets.requestFocus();
                                return;
                            }

                        }else if(adult_num > 10){

                            adult_tickets.setError("10 is the Maximum Number of Tickets!");
                            adult_tickets.requestFocus();
                            return;

                        }


                    }
                });

                child_tickets.addTextChangedListener(new TextWatcher() {

                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String temp = s.toString();
                        if(!temp.equals("") ) {
                            child_num = Integer.parseInt(temp);
                        }else{
                            child_num = 0;
                        }

                        if(child_num <= 10) {

                            total_tickets =  adult_num + child_num + senior_num;

                            if(total_tickets <= 10) {

                                max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                                total_amount = (adult_num * cone_4dx_Aprice) + (child_num * cone_4dx_Cprice) + (senior_num * cone_4dx_Sprice);
                                total_tv.setText("$" + total_amount + ".00" );

                            }else if(total_tickets >= 10){

                                total_tickets =  adult_num + senior_num - child_num;
                                child_tickets.setError("10 Tickets Maximum ");
                                child_tickets.requestFocus();
                                return;
                            }

                        }else if(child_num > 10){

                            child_tickets.setError("10 is the Maximum Number of Tickets!");
                            child_tickets.requestFocus();
                            return;

                        }

                    }
                });

            }

           if(theater_type.equals("cone_imax_r")){

               theater_title = "Cinema ONE: IMAX 2D";
               getSupportActionBar().setTitle(theater_title);
               location = "IMAX";
               movie_type = "regular";

               senior_tv.setText("Senior ($" + imax_r_Sprice + ")");
               adult_tv.setText("Adult ($" + imax_r_Aprice + ")");
               child_tv.setText("Child ($"+ imax_r_Cprice + ")");

               senior_tickets.addTextChangedListener(new TextWatcher() {
                   @Override
                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                   }

                   @Override
                   public void onTextChanged(CharSequence s, int start, int before, int count) {

                   }

                   @Override
                   public void afterTextChanged(Editable s) {
                       String temp = s.toString();
                       if(!temp.equals("") ) {
                           senior_num = Integer.parseInt(temp);
                       }else{
                           senior_num = 0;
                       }

                       if(senior_num <= 10) {

                           total_tickets =  adult_num + child_num + senior_num ;

                           if(total_tickets <= 10) {

                               max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                               total_amount = (adult_num * imax_r_Aprice) + (child_num * imax_r_Cprice) + (senior_num * imax_r_Sprice);
                               total_tv.setText("$" + total_amount + ".00");

                           }else if(total_tickets >= 10){

                               total_tickets =  adult_num + child_num - senior_num ;
                               senior_tickets.setError("10 Tickets Maximum ");
                               senior_tickets.requestFocus();
                               return;

                           }

                       }else if(senior_num > 10){

                           senior_tickets.setError("10 is the Maximum Number of Tickets!");
                           senior_tickets.requestFocus();
                           return;

                       }

                   }
               });

               adult_tickets.addTextChangedListener(new TextWatcher() {

                   @Override
                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                   }

                   @Override
                   public void onTextChanged(CharSequence s, int start, int before, int count) {

                   }

                   @Override
                   public void afterTextChanged(Editable s) {

                       String temp = s.toString();
                       if(!temp.equals("") ) {
                           adult_num = Integer.parseInt(temp);
                       }else{
                           adult_num = 0;
                       }

                       if(adult_num <= 10) {

                           total_tickets =  senior_num+ child_num + adult_num ;

                           if(total_tickets <= 10) {

                               max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                               total_amount = (adult_num * imax_r_Aprice) + (child_num * imax_r_Cprice) + (senior_num * imax_r_Sprice);
                               total_tv.setText("$" + total_amount + ".00");

                           }else if(total_tickets >= 10){

                               total_tickets =  senior_num + child_num - adult_num ;
                               adult_tickets.setError("10 Tickets Maximum ");
                               adult_tickets.requestFocus();
                               return;
                           }

                       }else if(adult_num > 10){

                           adult_tickets.setError("10 is the Maximum Number of Tickets!");
                           adult_tickets.requestFocus();
                           return;

                       }


                   }
               });

               child_tickets.addTextChangedListener(new TextWatcher() {

                   @Override
                   public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                   }

                   @Override
                   public void onTextChanged(CharSequence s, int start, int before, int count) {

                   }

                   @Override
                   public void afterTextChanged(Editable s) {

                       String temp = s.toString();
                       if(!temp.equals("") ) {
                           child_num = Integer.parseInt(temp);
                       }else{
                           child_num = 0;
                       }

                       if(child_num <= 10) {

                           total_tickets =  adult_num + child_num + senior_num;

                           if(total_tickets <= 10) {

                               max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                               total_amount = (adult_num * imax_r_Aprice) + (child_num * imax_r_Cprice) + (senior_num * imax_r_Sprice);
                               total_tv.setText("$" + total_amount + ".00" );

                           }else if(total_tickets >= 10){

                               total_tickets =  adult_num + senior_num - child_num;
                               child_tickets.setError("10 Tickets Maximum ");
                               child_tickets.requestFocus();
                               return;
                           }

                       }else if(child_num > 10){

                           child_tickets.setError("10 is the Maximum Number of Tickets!");
                           child_tickets.requestFocus();
                           return;

                       }

                   }
               });


           }

        }

    }

    public void ValidEntry( View view){


//        if( !(senior_num >= 0 && senior_num <= 10) ){
//            senior_tickets.setError("Please Enter Amount between 1 and 10!");
//            senior_tickets.requestFocus();
//            return;
//        }
//
//
//        if( !(adult_num >= 0 && adult_num <= 10)){
//            adult_tickets.setError("Please Enter Amount between 1 and 10!");
//            adult_tickets.requestFocus();
//            return;
//        }
//
//
//        if( !(child_num >= 0 && child_num <= 10)){
//            child_tickets.setError("Please Enter Amount between 1 and 10!");
//            child_tickets.requestFocus();
//            return;
//        }

    }


}