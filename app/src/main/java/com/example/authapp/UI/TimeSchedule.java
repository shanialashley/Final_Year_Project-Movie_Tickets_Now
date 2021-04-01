package com.example.authapp.UI;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.DateOfWeek;
import com.example.authapp.Model.TimeOfMovie;
import com.example.authapp.R;
import com.example.authapp.adapters.DateItemClickListener;
import com.example.authapp.adapters.RecycleViewAdapterCalendar;
import com.example.authapp.adapters.RecycleViewScheduleAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TimeSchedule extends AppCompatActivity implements DateItemClickListener {

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

    private String title, id, movieid;
    private List<TimeOfMovie> mtList;
    private Date currentdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_schedule);

        timeReference = FirebaseDatabase.getInstance().getReference().child("TimeSchedule");
        mtList = new ArrayList<>();
        currentdate = new Date();

        ToolbarInfo();
        init();
        HorizontalDate();
        SpinnerActivity();
        SimpleDateFormat dfs = new SimpleDateFormat("EEE", Locale.US);

        if(dfs.format(currentdate).equals("Mon") || dfs.format(currentdate).equals("Tue") || dfs.format(currentdate).equals("Wed")
            || dfs.format(currentdate).equals("Thu") || dfs.format(currentdate).equals("Fri")){

            onMonFriClick();
        }else if (dfs.format(currentdate).equals("Sat")){
            onSatClick();
        }else if(dfs.format(currentdate).equals("Sun")){
            onSunNHolClick();
        }



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




        cal1 = Calendar.getInstance();
        cal1.setTime(currentdate);
        int num = cal1.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dfs = new SimpleDateFormat("EEE", Locale.US);

        List<DateOfWeek> week = new ArrayList<>();

        week.add(new DateOfWeek( String.valueOf(num) , dfs.format(currentdate), currentdate));

        cal1.add(Calendar.DATE, 1);
        Date date2 = cal1.getTime();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        week.add(new DateOfWeek( String.valueOf(cal2.get(Calendar.DAY_OF_MONTH)), dfs.format(date2), date2));

        cal2.add(Calendar.DATE, 1);
        Date date3 = cal2.getTime();
        Calendar cal3 = Calendar.getInstance();
        cal3.setTime(date3);
        week.add(new DateOfWeek( String.valueOf(cal3.get(Calendar.DAY_OF_MONTH)), dfs.format(date3), date3));

        cal3.add(Calendar.DATE, 1);
        Date date4 = cal3.getTime();
        Calendar cal4 = Calendar.getInstance();
        cal4.setTime(date4);
        week.add(new DateOfWeek( String.valueOf(cal4.get(Calendar.DAY_OF_MONTH)) , dfs.format(date4), date4));

        cal4.add(Calendar.DATE, 1);
        Date date5 = cal4.getTime();
        Calendar cal5 = Calendar.getInstance();
        cal5.setTime(date5);
        week.add(new DateOfWeek( String.valueOf(cal5.get(Calendar.DAY_OF_MONTH)) , dfs.format(date5), date5));


        RecycleViewAdapterCalendar timeScheduleAdp = new RecycleViewAdapterCalendar(TimeSchedule.this, week, TimeSchedule.this);
        rView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rView.setAdapter(timeScheduleAdp);



    }

    private void init() {

        cities_spinner = findViewById(R.id.cities_spinner);

        title = getIntent().getExtras().getString("title");
        id = getIntent().getExtras().getString("key");
        movieid =  title.replaceAll("\\s", "_") +"_"+ id;

        cc8_img = findViewById(R.id.ts_cc8_img);
        trincity = findViewById(R.id.ts_trincity);
        t_regular =findViewById(R.id.ts_trincity_r);
        t_r_recycleV = findViewById(R.id.ts_t_r_recyclerV);
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


    @Override
    public void onClick(DateOfWeek dOWeek) {

        if (dOWeek.getDate_week().equals("Mon") || dOWeek.getDate_week().equals("Tue") ||dOWeek.getDate_week().equals("Wed")
            || dOWeek.getDate_week().equals("Thu") || dOWeek.getDate_week().equals("Fri")){

            onMonFriClick();

        }else if(dOWeek.getDate_week().equals("Sat")){

            onSatClick();

        }else if(dOWeek.getDate_week().equals("Sun")){

            onSunNHolClick();

        }

    }

    public void onMonFriClick() {

        CC8_mf();


    }

    public void onSatClick() {

        CC8_sat();
    }


    public void onSunNHolClick() {

        CC8_sunH();

    }

    public void CC8_mf(){

        DatabaseReference cc8_t_r = timeReference.child(movieid).child("CC8").child("trincity").child("regular").child("mon-fri");
        cc8_t_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();

                if (snapshot.exists()) {
                    t_regular.setVisibility(View.VISIBLE);
                    t_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    t_regular.setVisibility(View.GONE);
                    t_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                t_r_recycleV.setAdapter(scheduleAdp);
                t_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_t_3d = timeReference.child(movieid).child("CC8").child("trincity").child("3D").child("mon-fri");
        cc8_t_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();

                if (snapshot.exists()) {
                    t_3D.setVisibility(View.VISIBLE);
                    t_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    t_3D.setVisibility(View.GONE);
                    t_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                t_3D_recycleV.setAdapter(scheduleAdp);
                t_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_r = timeReference.child(movieid).child("CC8").child("southpark").child("regular").child("mon-fri");
        cc8_s_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();

                if (snapshot.exists()) {
                    s_regular.setVisibility(View.VISIBLE);
                    s_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    s_regular.setVisibility(View.GONE);
                    s_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                s_r_recycleV.setAdapter(scheduleAdp);
                s_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_3D = timeReference.child(movieid).child("CC8").child("southpark").child("3D").child("mon-fri");
        cc8_s_3D.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();

                if (snapshot.exists()) {
                    s_3D.setVisibility(View.VISIBLE);
                    s_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    s_3D.setVisibility(View.GONE);
                    s_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                s_3D_recycleV.setAdapter(scheduleAdp);
                s_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_4DX = timeReference.child(movieid).child("CC8").child("southpark").child("4DX").child("mon-fri");

        cc8_s_4DX.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();

                if (snapshot.exists()) {
                    s_4DX.setVisibility(View.VISIBLE);
                    s_4DX_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    s_4DX.setVisibility(View.GONE);
                    s_4DX_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                s_4DX_recycleV.setAdapter(scheduleAdp);
                s_4DX_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_CXC = timeReference.child(movieid).child("CC8").child("southpark").child("CXC").child("mon-fri");

        cc8_s_CXC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();

                if (snapshot.exists()) {
                    s_CXC.setVisibility(View.VISIBLE);
                    s_CXC_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    s_CXC.setVisibility(View.GONE);
                    s_CXC_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                s_CXC_recycleV.setAdapter(scheduleAdp);
                s_CXC_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void CC8_sat(){

        DatabaseReference cc8_t_r = timeReference.child(movieid).child("CC8").child("trincity").child("regular").child("sat");
        cc8_t_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();
                if (snapshot.exists()) {
                    t_regular.setVisibility(View.VISIBLE);
                    t_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    t_regular.setVisibility(View.GONE);
                    t_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                t_r_recycleV.setAdapter(scheduleAdp);
                t_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_t_3d = timeReference.child(movieid).child("CC8").child("trincity").child("3D").child("sat");
        cc8_t_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();

                if (snapshot.exists()) {
                    t_3D.setVisibility(View.VISIBLE);
                    t_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    t_3D.setVisibility(View.GONE);
                    t_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                t_3D_recycleV.setAdapter(scheduleAdp);
                t_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_r = timeReference.child(movieid).child("CC8").child("southpark").child("regular").child("sat");
        cc8_s_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();

                if (snapshot.exists()) {
                    s_regular.setVisibility(View.VISIBLE);
                    s_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    s_regular.setVisibility(View.GONE);
                    s_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                s_r_recycleV.setAdapter(scheduleAdp);
                s_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_3D = timeReference.child(movieid).child("CC8").child("southpark").child("3D").child("sat");
        cc8_s_3D.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();

                if (snapshot.exists()) {
                    s_3D.setVisibility(View.VISIBLE);
                    s_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    s_3D.setVisibility(View.GONE);
                    s_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                s_3D_recycleV.setAdapter(scheduleAdp);
                s_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_4DX = timeReference.child(movieid).child("CC8").child("southpark").child("4DX").child("sat");

        cc8_s_4DX.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();
                if (snapshot.exists()) {
                    s_4DX.setVisibility(View.VISIBLE);
                    s_4DX_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    s_4DX.setVisibility(View.GONE);
                    s_4DX_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                s_4DX_recycleV.setAdapter(scheduleAdp);
                s_4DX_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_CXC = timeReference.child(movieid).child("CC8").child("southpark").child("CXC").child("sat");
        cc8_s_CXC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();

                if (snapshot.exists()) {
                    s_CXC.setVisibility(View.VISIBLE);
                    s_CXC_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    s_CXC.setVisibility(View.GONE);
                    s_CXC_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                s_CXC_recycleV.setAdapter(scheduleAdp);
                s_CXC_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void CC8_sunH(){

        DatabaseReference cc8_t_r = timeReference.child(movieid).child("CC8").child("trincity").child("regular").child("sun+hol");
        cc8_t_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();
                if (snapshot.exists()) {
                    t_regular.setVisibility(View.VISIBLE);
                    t_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    t_regular.setVisibility(View.GONE);
                    t_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                t_r_recycleV.setAdapter(scheduleAdp);
                t_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_t_3d = timeReference.child(movieid).child("CC8").child("trincity").child("3D").child("sun+hol");
        cc8_t_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();

                if (snapshot.exists()) {
                    t_3D.setVisibility(View.VISIBLE);
                    t_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    t_3D.setVisibility(View.GONE);
                    t_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                t_3D_recycleV.setAdapter(scheduleAdp);
                t_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_r = timeReference.child(movieid).child("CC8").child("southpark").child("regular").child("sun+hol");
        cc8_s_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();

                if (snapshot.exists()) {
                    s_regular.setVisibility(View.VISIBLE);
                    s_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    s_regular.setVisibility(View.GONE);
                    s_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                s_r_recycleV.setAdapter(scheduleAdp);
                s_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_3D = timeReference.child(movieid).child("CC8").child("southpark").child("3D").child("sun+hol");
        cc8_s_3D.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();

                if (snapshot.exists()) {
                    s_3D.setVisibility(View.VISIBLE);
                    s_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    s_3D.setVisibility(View.GONE);
                    s_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                s_3D_recycleV.setAdapter(scheduleAdp);
                s_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_4DX = timeReference.child(movieid).child("CC8").child("southpark").child("4DX").child("sun+hol");
        cc8_s_4DX.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();
                if (snapshot.exists()) {
                    s_4DX.setVisibility(View.VISIBLE);
                    s_4DX_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    s_4DX.setVisibility(View.GONE);
                    s_4DX_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                s_4DX_recycleV.setAdapter(scheduleAdp);
                s_4DX_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_CXC = timeReference.child(movieid).child("CC8").child("southpark").child("CXC").child("sun+hol");
        cc8_s_CXC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mtList.clear();

                if (snapshot.exists()) {
                    s_CXC.setVisibility(View.VISIBLE);
                    s_CXC_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        mtList.add(new TimeOfMovie(n));
                    }
                }else{
                    s_CXC.setVisibility(View.GONE);
                    s_CXC_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, mtList);
                s_CXC_recycleV.setAdapter(scheduleAdp);
                s_CXC_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




}