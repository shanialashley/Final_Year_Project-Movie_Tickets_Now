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
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.authapp.Purchases;
import com.example.authapp.R;
import com.squareup.picasso.Picasso;

public class SelectTickets extends AppCompatActivity {

    private TextView title_tv, date_tv, senior_tv, adult_tv, child_tv, max_tickets_tv, total_tv ;
    private EditText senior_tickets, adult_tickets, child_tickets;
    private ImageView thumbnail_img;
    private Button continueB;
    private Toolbar toolbar;

    String title;
    String date;
    String time;
    String thumbnail;
    String theater_type;
    int senior_num = 0, child_num = 0 , adult_num = 0;
    int total_tickets = 0;
    private View view;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_tickets);

        init();
        ToolbarInfo();

    }

    public void ToolbarInfo(){


        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if(theater_type.equals("cc8_t_r") || theater_type.equals("cc8_t_3D") ||
            theater_type.equals("cc8_s_r") || theater_type.equals("cc8_s_3d") ||
                    theater_type.equals("cc8_s_4dx") || theater_type.equals("cc8_s_cxc")) {
            getSupportActionBar().setTitle("Caribbean Cinema 8");
        }
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectTickets.this, TimeSchedule.class) );
            }
        });



    }

    private void init() {

        title_tv = findViewById(R.id.st_title);
        title = getIntent().getExtras().getString("title");
        title_tv.setText(title);

        thumbnail_img = findViewById(R.id.st_movie_thumbnail);
        thumbnail = getIntent().getExtras().getString("thumbnail");
        Picasso.get().load(thumbnail).into(thumbnail_img);

        date = getIntent().getExtras().getString("selectDate");
        time = getIntent().getExtras().getString("time");
        date_tv = findViewById(R.id.st_movie_dateNtime);
        date_tv.setText(date + "\nShow Time: "+ time);

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

        Checktickets(view);

        continueB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidEntry(v);
                Intent intent = new Intent(SelectTickets.this, Purchases.class);
                startActivity(intent);
            }
        });



        senior_tickets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp1 = s.toString();
                if(!temp1.equals("") ) {
                    senior_num = Integer.parseInt(temp1);
                }
                if(total_tickets <= 10) {
                    total_tickets = senior_num + child_num + adult_num;
                    max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
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
                String temp1 = s.toString();
                if(!temp1.equals("") ) {
                    adult_num = Integer.parseInt(temp1);
                }
                if(total_tickets <= 10) {
                    total_tickets = senior_num + child_num + adult_num;
                    max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
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
                String temp1 = s.toString();
                if(!temp1.equals("") ) {
                    child_num = Integer.parseInt(temp1);
                }
                if(total_tickets <= 10) {
                    total_tickets = senior_num + child_num + adult_num;
                    max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
                }
            }
        });


    }

    public void Checktickets(View v){
//        String temp1 = senior_tickets.getText().toString();
//        String temp2 = adult_tickets.getText().toString();
//        String temp3 = child_tickets.getText().toString();
//
//        if(!temp1.equals("") || !temp2.equals("") || !temp3.equals("") ) {
//            senior_num = Integer.parseInt(temp1);
//
//            adult_num = Integer.parseInt(temp2);
//
//            child_num = Integer.parseInt(temp3);
//        }
//        int total_tickets = 0;
//        total_tickets = senior_num + child_num + adult_num;
//
//        max_tickets_tv.setText(String.valueOf(total_tickets) + "/10");
    }

    public void ValidEntry( View view){


        if( !(senior_num >= 0 && senior_num <= 10) ){
            senior_tickets.setError("Please Enter Amount between 1 and 10!");
            senior_tickets.requestFocus();

        }


        if( !(adult_num >= 0 && adult_num <= 10)){
            adult_tickets.setError("Please Enter Amount between 1 and 10!");
            adult_tickets.requestFocus();

        }


        if( !(child_num >= 0 && child_num <= 10)){
            child_tickets.setError("Please Enter Amount between 1 and 10!");
            child_tickets.requestFocus();
        }

    }


}