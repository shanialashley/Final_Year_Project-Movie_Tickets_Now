package com.example.authapp.UI;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.DateOfWeek;
import com.example.authapp.R;
import com.example.authapp.adapters.RecycleViewAdapterCalendar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeSchedule extends AppCompatActivity  {

    private DrawerLayout drawerLayout;
    private Calendar cal1;
    private Toolbar toolbar;
    private RecyclerView rView;
    private Spinner cities_spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_schedule);


        ToolbarInfo();
        init();
        HorizontalDate();
        SpinnerActivity();

    }


    public void ToolbarInfo(){

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);



        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Movie Schedule");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);


    }

    public void HorizontalDate(){

        rView = findViewById(R.id.calendar_recycleView);


        Date currentdate = new Date();

        cal1 = Calendar.getInstance();
        cal1.setTime(currentdate);
        int num = cal1.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dfs = new SimpleDateFormat("EEE", Locale.US);

        List<DateOfWeek> week = new ArrayList<>();

        week.add(new DateOfWeek( String.valueOf(num) , dfs.format(currentdate)));

        cal1.add(Calendar.DATE, 1);
        Date date2 = cal1.getTime();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        week.add(new DateOfWeek( String.valueOf(cal2.get(Calendar.DAY_OF_MONTH)), dfs.format(date2)));

        cal2.add(Calendar.DATE, 1);
        Date date3 = cal2.getTime();
        Calendar cal3 = Calendar.getInstance();
        cal3.setTime(date3);
        week.add(new DateOfWeek( String.valueOf(cal3.get(Calendar.DAY_OF_MONTH)), dfs.format(date3)));

        cal3.add(Calendar.DATE, 1);
        Date date4 = cal3.getTime();
        Calendar cal4 = Calendar.getInstance();
        cal4.setTime(date4);
        week.add(new DateOfWeek( String.valueOf(cal4.get(Calendar.DAY_OF_MONTH)) , dfs.format(date4)));

        cal4.add(Calendar.DATE, 1);
        Date date5 = cal4.getTime();
        Calendar cal5 = Calendar.getInstance();
        cal5.setTime(date5);
        week.add(new DateOfWeek( String.valueOf(cal5.get(Calendar.DAY_OF_MONTH)) , dfs.format(date5)));


        RecycleViewAdapterCalendar timeScheduleAdp = new RecycleViewAdapterCalendar(TimeSchedule.this, week);
        rView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rView.setAdapter(timeScheduleAdp);



    }

    private void init() {

        cities_spinner = findViewById(R.id.cities_spinner);

    }

    private void SpinnerActivity() {

        List<String> theaters = new ArrayList<>();
        theaters.add("All Theaters");
        theaters.add("Caribbean Cinema 8");
        theaters.add("Movie Towne");
        theaters.add("CinemaONE");

        ArrayAdapter<String> theaterAdapter = new ArrayAdapter<>(this, android.R.layout. simple_spinner_item, theaters);
        theaterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cities_spinner.setAdapter(theaterAdapter);

    }



}