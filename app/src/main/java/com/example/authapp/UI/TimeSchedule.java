package com.example.authapp.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.R;
import com.example.authapp.adapters.RecycleViewAdapterCalendar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TimeSchedule extends AppCompatActivity  {

    private DrawerLayout drawerLayout;
    private Calendar dateP;
    private Toolbar toolbar;
    private RecyclerView rView;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_schedule);


        ToolbarInfo();
        HorizontalDate();

    }

    public void ToolbarInfo(){

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Movie Schedule");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);


    }

    public void HorizontalDate(){



        dateP = Calendar.getInstance();

        List<java.sql.Date> week = new ArrayList<>();


        RecycleViewAdapterCalendar timeScheduleAdp = new RecycleViewAdapterCalendar(TimeSchedule.this, week);
        rView.setLayoutManager(new GridLayoutManager(this, 5));
        rView.setAdapter(timeScheduleAdp);



//        dateP = findViewById(R.id.data_picker);
//        dateP.setListener(new DatePickerListener() {
//            @Override
//            public void onDateSelected(DateTime dateSelected) {
//
//                Toast.makeText(TimeSchedule.this, dateSelected.toString(), Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
//
//        dateP.setDays(5)
//                .setOffset(7)
//                .setDateSelectedTextColor(getResources().getColor(R.color.orange))
//                .setDateSelectedColor(getResources().getColor(R.color.light_orange))
//                .setDayOfWeekTextColor(getResources().getColor(R.color.orange))
//                .setTodayDateTextColor(getResources().getColor(R.color.red))
//                .showTodayButton(true)
//                .init();
//        dateP.setDate(new DateTime());

    }


}