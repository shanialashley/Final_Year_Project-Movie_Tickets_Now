package com.example.authapp.UI;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.DateOfWeek;
import com.example.authapp.R;
import com.example.authapp.adapters.RecycleViewAdapterCalendar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeSchedule extends AppCompatActivity  {

    private DrawerLayout drawerLayout;
    private ImageView cc8_img, mt_img, cONE_img;
    private TextView trincity, t_regular, t_3D,
                        southpark, s_regular, s_3D, s_4DX, s_CXC;
    private Calendar cal1;
    private Toolbar toolbar;
    private RecyclerView rView, t_r_recycleV, t_3D_recycleV,
                        s_r_recycleV, s_3D_recycleV, s_4DX_recycleV, s_CXC_recycleV;
    private Spinner cities_spinner;
    private DatabaseReference timeReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_schedule);

        timeReference = FirebaseDatabase.getInstance().getReference().child("TimeSchedule");


        ToolbarInfo();
        init();
        HorizontalDate();
        SpinnerActivity();
        CC8Activities();

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

        cc8_img = findViewById(R.id.ts_cc8_img);
        trincity = findViewById(R.id.ts_trincity);
        t_regular =findViewById(R.id.ts_trincity_r);
        t_r_recycleV =findViewById(R.id.ts_t_r_recyclerV);
        t_3D = findViewById(R.id.ts_t_3D);
        t_3D_recycleV = findViewById(R.id.ts_t_3D_recycleV);
        southpark = findViewById(R.id.ts_southPark);
        s_regular = findViewById(R.id.ts_sp_r);
        s_r_recycleV = findViewById(R.id.ts_sp_r_recyclerV);
        s_3D = findViewById(R.id.ts_sp_3D);
        s_3D_recycleV = findViewById(R.id.ts_sp_3D_recycleV);
        s_4DX = findViewById(R.id.ts_sp_4Dx);
        s_4DX_recycleV = findViewById(R.id.ts_sp_4Dx_recyclerV);
        s_CXC = findViewById(R.id.ts_sp_CXC);
        s_CXC_recycleV = findViewById(R.id.ts_sp_CXC_recycleV);




        mt_img = findViewById(R.id.ts_mt_img);
        cONE_img = findViewById(R.id.ts_imax_img);


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

    private void CC8Activities() {

//        String imageN =  title.getText().toString().replaceAll("\\s", "_");
//            DatabaseReference cc8_t_r  = timeReference.child(movieid).child("CC8").child("trincity").child("regular").referenceTime.child(movieid).child("CC8").child("trincity").child("regular").child("mon-fri");;
    }



}