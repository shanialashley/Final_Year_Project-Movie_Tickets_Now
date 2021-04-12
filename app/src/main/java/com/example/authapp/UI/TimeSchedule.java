package com.example.authapp.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.authapp.Model.Movies;
import com.example.authapp.Model.TimeOfMovie;
import com.example.authapp.R;
import com.example.authapp.adapters.DateItemClickListener;
import com.example.authapp.adapters.RecycleViewAdapterCalendar;
import com.example.authapp.adapters.RecycleViewScheduleAdapter;
import com.example.authapp.adapters.TimeItemClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class TimeSchedule extends AppCompatActivity implements DateItemClickListener, TimeItemClickListener {

    private DrawerLayout drawerLayout;
    private Calendar cal1;
    private Toolbar toolbar;
    private Spinner cities_spinner;
    private DatabaseReference timeReference;

    private ImageView cc8_img, mt_img, cONE_img;
    private TextView trincity, t_regular, t_3D,
                        southpark, s_regular, s_3D, s_4DX, s_CXC;

    private TextView ts_pos, ts_pos_r, ts_pos_3D, ts_chag, ts_chag_r, ts_chag_3D,
                        ts_sdo, ts_sdo_r, ts_sdo_3D, ts_tgo, ts_tgo_r, ts_tgo_3D;

    private TextView ts_cinemaOne, ts_imax, ts_gemStone, ts_4DX;


    private RecyclerView rView, t_r_recycleV, t_3D_recycleV,
                        s_r_recycleV, s_3D_recycleV, s_4DX_recycleV, s_CXC_recycleV;

    private RecyclerView ts_pos_r_recycleV, ts_pos_3D_recycleV, ts_chag_r_recycleV, ts_chag_3D_recycleV
                            , ts_sdo_r_recycleV, ts_sdo_3D_recycleV,  ts_tgo_r_recycleV, ts_tgo_3D_recycleV;

    private RecyclerView ts_imax_recycleR, ts_gemstone_recycleR, ts_4DX_recycleV;

    private FirebaseAuth mAuth;
    private FirebaseUser currentuser;

    private String title, id, movieid, thumbnail;
    private List<TimeOfMovie> mtList, cc8List, cOneList;
    private Date currentdate;
    private String selectedDate;
    private SimpleDateFormat DateFor;
    private SimpleDateFormat dfs;
    private Movies movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_schedule);

        timeReference = FirebaseDatabase.getInstance().getReference().child("TimeSchedule");
        mAuth = FirebaseAuth.getInstance();
        currentuser = mAuth.getCurrentUser();
        currentdate = new Date();

        init();
        ToolbarInfo();


        dfs = new SimpleDateFormat("EEE", Locale.US);
        DateFor = new SimpleDateFormat("E, dd MMM yyyy");

        selectedDate = DateFor.format(currentdate);

        if(dfs.format(currentdate).equals("Mon") || dfs.format(currentdate).equals("Tue") || dfs.format(currentdate).equals("Wed")
            || dfs.format(currentdate).equals("Thu") || dfs.format(currentdate).equals("Fri")){
            onMonFriClick();
        }else if (dfs.format(currentdate).equals("Sat")){
            onSatClick();
        }else if(dfs.format(currentdate).equals("Sun")){
            onSunNHolClick();
        }



        HorizontalDate();

        SpinnerActivity();
    }


    public void ToolbarInfo(){

        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(TimeSchedule.this, MovieDetailsActivity.class) );
                onBackPressed();
            }
        });



    }

    public void HorizontalDate(){

        rView = findViewById(R.id.calendar_recycleView);

        cal1 = Calendar.getInstance();
        cal1.setTime(currentdate);
        int num = cal1.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dfs = new SimpleDateFormat("EEE", Locale.US);
        SimpleDateFormat DateFor = new SimpleDateFormat("E, dd MMM yyyy");

        List<DateOfWeek> week = new ArrayList<>();
        String date_c = DateFor.format(currentdate);
        week.add(new DateOfWeek( String.valueOf(num) , dfs.format(currentdate), date_c));

        cal1.add(Calendar.DATE, 1);
        Date date2 = cal1.getTime();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        String date_s2 = DateFor.format(date2);
        week.add(new DateOfWeek( String.valueOf(cal2.get(Calendar.DAY_OF_MONTH)), dfs.format(date2), date_s2));

        cal2.add(Calendar.DATE, 1);
        Date date3 = cal2.getTime();
        Calendar cal3 = Calendar.getInstance();
        cal3.setTime(date3);
        String date_s3 = DateFor.format(date3);
        week.add(new DateOfWeek( String.valueOf(cal3.get(Calendar.DAY_OF_MONTH)), dfs.format(date3), date_s3));

        cal3.add(Calendar.DATE, 1);
        Date date4 = cal3.getTime();
        Calendar cal4 = Calendar.getInstance();
        cal4.setTime(date4);
        String date_s4 = DateFor.format(date4);
        week.add(new DateOfWeek( String.valueOf(cal4.get(Calendar.DAY_OF_MONTH)) , dfs.format(date4), date_s4));

        cal4.add(Calendar.DATE, 1);
        Date date5 = cal4.getTime();
        Calendar cal5 = Calendar.getInstance();
        cal5.setTime(date5);
        String date_s5 = DateFor.format(date5);
        week.add(new DateOfWeek( String.valueOf(cal5.get(Calendar.DAY_OF_MONTH)) , dfs.format(date5), date_s5));


        RecycleViewAdapterCalendar timeScheduleAdp = new RecycleViewAdapterCalendar(TimeSchedule.this, week, TimeSchedule.this);
        rView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rView.setAdapter(timeScheduleAdp);



    }

    private void init() {

        movie = (Movies) getIntent().getExtras().getSerializable("currentMovie");
        id = getIntent().getExtras().getString("key");

        cities_spinner = findViewById(R.id.cities_spinner);

        title = movie.getTitle();
        thumbnail = movie.getThumbnail_url();

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
        ts_pos = findViewById(R.id.ts_pos);
        ts_pos_r = findViewById(R.id.ts_pos_r);
        ts_pos_r_recycleV = findViewById(R.id.ts_pos_r_recyclerV);
        ts_pos_3D = findViewById(R.id.ts_pos_3D);
        ts_pos_3D_recycleV = findViewById(R.id.ts_pos_3D_recycleV);
        ts_chag = findViewById(R.id.ts_chag);
        ts_chag_r = findViewById(R.id.ts_chag_r);
        ts_chag_r_recycleV = findViewById(R.id.ts_chag_r_recyclerV);
        ts_chag_3D = findViewById(R.id.ts_chag_3D);
        ts_chag_3D_recycleV = findViewById(R.id.ts_chag_3D_recycleV);
        ts_sdo = findViewById(R.id.ts_sdo);
        ts_sdo_r = findViewById(R.id.ts_sdo_r);
        ts_sdo_r_recycleV = findViewById(R.id.ts_sdo_r_recyclerV);
        ts_sdo_3D = findViewById(R.id.ts_sdo_3D);
        ts_sdo_3D_recycleV = findViewById(R.id.ts_sdo_3D_recycleV);
        ts_tgo = findViewById(R.id.ts_tgo);
        ts_tgo_r = findViewById(R.id.ts_tgo_r);
        ts_tgo_r_recycleV = findViewById(R.id.ts_tgo_r_recyclerV);
        ts_tgo_3D = findViewById(R.id.ts_tgo_3D);
        ts_tgo_3D_recycleV = findViewById(R.id.ts_tgo_3D_recycleV);


        cONE_img = findViewById(R.id.ts_imax_img);
        ts_cinemaOne = findViewById(R.id.ts_cinemaOne);
        ts_imax = findViewById(R.id.ts_imax);
        ts_imax_recycleR = findViewById(R.id.ts_imax_recyclerV);
        ts_gemStone = findViewById(R.id.ts_gemStone);
        ts_gemstone_recycleR = findViewById(R.id.ts_gemStone_recycleV);
        ts_4DX = findViewById(R.id.ts_4DX);
        ts_4DX_recycleV = findViewById(R.id.ts_4DX_recycleV);



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
        cities_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){

                    CC8View();
                    MTView();
                    CONEView();

                    currentdate = new Date();
                    if(dfs.format(currentdate).equals("Mon") || dfs.format(currentdate).equals("Tue") || dfs.format(currentdate).equals("Wed")
                            || dfs.format(currentdate).equals("Thu") || dfs.format(currentdate).equals("Fri")){
                        onMonFriClick();
                    }else if (dfs.format(currentdate).equals("Sat")){
                        onSatClick();
                    }else if(dfs.format(currentdate).equals("Sun")){
                        onSunNHolClick();
                    }



                }

                if(position == 1){

                    CC8View();
                    MTGone();
                    CONEGone();

                    DateItemClickListener dateItemClickListener = new DateItemClickListener() {
                        @Override
                        public void onClick(DateOfWeek dOWeek) {
                            if (dOWeek.getDate_week().equals("Mon") || dOWeek.getDate_week().equals("Tue") ||dOWeek.getDate_week().equals("Wed")
                                    || dOWeek.getDate_week().equals("Thu") || dOWeek.getDate_week().equals("Fri")){

                                CC8_mf();

                            }else if(dOWeek.getDate_week().equals("Sat")){

                                CC8_sat();

                            }else if(dOWeek.getDate_week().equals("Sun")){

                                CC8_sunH();

                            }
                        }
                    };



//                    CC8_mf();
//                    CC8_sat();
//                    CC8_sunH();

                }

                if(position == 2){

                    MTView();
                    CC8Gone();
                    CONEGone();

                    DateItemClickListener dateItemClickListener = new DateItemClickListener() {
                        @Override
                        public void onClick(DateOfWeek dOWeek) {
                            if (dOWeek.getDate_week().equals("Mon") || dOWeek.getDate_week().equals("Tue") ||dOWeek.getDate_week().equals("Wed")
                                    || dOWeek.getDate_week().equals("Thu") || dOWeek.getDate_week().equals("Fri")){

                                MT_mf();

                            }else if(dOWeek.getDate_week().equals("Sat")){

                                MT_sat();

                            }else if(dOWeek.getDate_week().equals("Sun")){

                                MT_sun();

                            }
                        }
                    };

//                    MT_mf();
//                    MT_sat();
//                    MT_sun();



                }

                if(position == 3){

                    CONEView();
                    CC8Gone();
                    MTGone();
                    DateItemClickListener dateItemClickListener = new DateItemClickListener() {
                        @Override
                        public void onClick(DateOfWeek dOWeek) {
                            if (dOWeek.getDate_week().equals("Mon") || dOWeek.getDate_week().equals("Tue") ||dOWeek.getDate_week().equals("Wed")
                                    || dOWeek.getDate_week().equals("Thu") || dOWeek.getDate_week().equals("Fri")){

                                CONE_mf();

                            }else if(dOWeek.getDate_week().equals("Sat")){

                                CONE_sat();

                            }else if(dOWeek.getDate_week().equals("Sun")){

                                CONE_sun();

                            }
                        }
                    };

//                    CONE_mf();
//                    CONE_sat();
//                    CONE_sun();


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    @Override
    public void onClick(DateOfWeek dOWeek) {

        selectedDate = dOWeek.getDate();

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
        MT_mf();
        CONE_mf();


    }


    public void onSatClick() {

        CC8_sat();
        MT_sat();
        CONE_sat();
    }


    public void onSunNHolClick() {

        CC8_sunH();
        MT_sun();
        CONE_sun();

    }

    public void CC8_mf(){
        cc8_img.setVisibility(View.VISIBLE);
        DatabaseReference cc8_t_r = timeReference.child(movieid).child("CC8").child("trincity").child("regular").child("mon-fri");
        List<TimeOfMovie> t_r_mf = new ArrayList<>();
        cc8_t_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t_r_mf.clear();

                if (snapshot.exists()) {
                    t_regular.setVisibility(View.VISIBLE);
                    t_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        t_r_mf.add(new TimeOfMovie(n, "cc8_t_r"));
                    }
                }else{
                    t_regular.setVisibility(View.GONE);
                    t_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, t_r_mf, TimeSchedule.this);
                t_r_recycleV.setAdapter(scheduleAdp);
                t_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_t_3d = timeReference.child(movieid).child("CC8").child("trincity").child("3D").child("mon-fri");
        List<TimeOfMovie> t_3D_mf = new ArrayList<>();
        cc8_t_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t_3D_mf.clear();

                if (snapshot.exists()) {
                    t_3D.setVisibility(View.VISIBLE);
                    t_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        t_3D_mf.add(new TimeOfMovie(n, "cc8_t_3D"));
                    }
                }else{
                    t_3D.setVisibility(View.GONE);
                    t_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, t_3D_mf, TimeSchedule.this);
                t_3D_recycleV.setAdapter(scheduleAdp);
                t_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_r = timeReference.child(movieid).child("CC8").child("southpark").child("regular").child("mon-fri");
        List<TimeOfMovie> s_r_mf = new ArrayList<>();
        cc8_s_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s_r_mf.clear();

                if (snapshot.exists()) {
                    s_regular.setVisibility(View.VISIBLE);
                    s_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        s_r_mf.add(new TimeOfMovie(n, "cc8_s_r"));
                    }
                }else{
                    s_regular.setVisibility(View.GONE);
                    s_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, s_r_mf, TimeSchedule.this);
                s_r_recycleV.setAdapter(scheduleAdp);
                s_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_3D = timeReference.child(movieid).child("CC8").child("southpark").child("3D").child("mon-fri");
        List<TimeOfMovie> s_3d_mf = new ArrayList<>();
        cc8_s_3D.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s_3d_mf.clear();

                if (snapshot.exists()) {
                    s_3D.setVisibility(View.VISIBLE);
                    s_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        s_3d_mf.add(new TimeOfMovie(n, "cc8_s_3D"));
                    }
                }else{
                    s_3D.setVisibility(View.GONE);
                    s_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, s_3d_mf , TimeSchedule.this);
                s_3D_recycleV.setAdapter(scheduleAdp);
                s_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_4DX = timeReference.child(movieid).child("CC8").child("southpark").child("4DX").child("mon-fri");
        List<TimeOfMovie> s_4dx_mf = new ArrayList<>();
        cc8_s_4DX.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s_4dx_mf.clear();

                if (snapshot.exists()) {
                    s_4DX.setVisibility(View.VISIBLE);
                    s_4DX_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        s_4dx_mf.add(new TimeOfMovie(n, "cc8_s_4DX"));
                    }
                }else{
                    s_4DX.setVisibility(View.GONE);
                    s_4DX_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, s_4dx_mf, TimeSchedule.this);
                s_4DX_recycleV.setAdapter(scheduleAdp);
                s_4DX_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_CXC = timeReference.child(movieid).child("CC8").child("southpark").child("CXC").child("mon-fri");
        List<TimeOfMovie> s_cxc_mf = new ArrayList<>();
        cc8_s_CXC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s_cxc_mf.clear();

                if (snapshot.exists()) {
                    s_CXC.setVisibility(View.VISIBLE);
                    s_CXC_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        s_cxc_mf.add(new TimeOfMovie(n, "cc8_s_cxc"));
                    }
                }else{
                    s_CXC.setVisibility(View.GONE);
                    s_CXC_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, s_cxc_mf, TimeSchedule.this);
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
        List<TimeOfMovie> t_r_sat = new ArrayList<>();
        cc8_t_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t_r_sat.clear();

                if (snapshot.exists()) {
                    t_regular.setVisibility(View.VISIBLE);
                    t_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        t_r_sat.add(new TimeOfMovie(n, "cc8_t_r"));
                    }
                }else{
                    t_regular.setVisibility(View.GONE);
                    t_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, t_r_sat, TimeSchedule.this);
                t_r_recycleV.setAdapter(scheduleAdp);
                t_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_t_3d = timeReference.child(movieid).child("CC8").child("trincity").child("3D").child("sat");
        List<TimeOfMovie> t_3d_sat = new ArrayList<>();
        cc8_t_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t_3d_sat.clear();

                if (snapshot.exists()) {
                    t_3D.setVisibility(View.VISIBLE);
                    t_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        t_3d_sat.add(new TimeOfMovie(n, "cc8_t_3D"));
                    }
                }else{
                    t_3D.setVisibility(View.GONE);
                    t_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, t_3d_sat, TimeSchedule.this);
                t_3D_recycleV.setAdapter(scheduleAdp);
                t_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_r = timeReference.child(movieid).child("CC8").child("southpark").child("regular").child("sat");
        List<TimeOfMovie> s_r_sat = new ArrayList<>();
        cc8_s_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s_r_sat.clear();

                if (snapshot.exists()) {
                    s_regular.setVisibility(View.VISIBLE);
                    s_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        s_r_sat.add(new TimeOfMovie(n, "cc8_s_r"));
                    }
                }else{
                    s_regular.setVisibility(View.GONE);
                    s_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, s_r_sat, TimeSchedule.this);
                s_r_recycleV.setAdapter(scheduleAdp);
                s_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_3D = timeReference.child(movieid).child("CC8").child("southpark").child("3D").child("sat");
        List<TimeOfMovie> s_3d_sat = new ArrayList<>();
        cc8_s_3D.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s_3d_sat.clear();

                if (snapshot.exists()) {
                    s_3D.setVisibility(View.VISIBLE);
                    s_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        s_3d_sat.add(new TimeOfMovie(n, "cc8_s_3d"));
                    }
                }else{
                    s_3D.setVisibility(View.GONE);
                    s_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, s_3d_sat, TimeSchedule.this);
                s_3D_recycleV.setAdapter(scheduleAdp);
                s_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_4DX = timeReference.child(movieid).child("CC8").child("southpark").child("4DX").child("sat");
        List<TimeOfMovie> s_4dx_sat = new ArrayList<>();
        cc8_s_4DX.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s_4dx_sat.clear();

                if (snapshot.exists()) {
                    s_4DX.setVisibility(View.VISIBLE);
                    s_4DX_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        s_4dx_sat.add(new TimeOfMovie(n, "cc8_s_4dx"));
                    }
                }else{
                    s_4DX.setVisibility(View.GONE);
                    s_4DX_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, s_4dx_sat, TimeSchedule.this);
                s_4DX_recycleV.setAdapter(scheduleAdp);
                s_4DX_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_CXC = timeReference.child(movieid).child("CC8").child("southpark").child("CXC").child("sat");
        List<TimeOfMovie> s_cxc_sat = new ArrayList<>();
        cc8_s_CXC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s_cxc_sat.clear();

                if (snapshot.exists()) {
                    s_CXC.setVisibility(View.VISIBLE);
                    s_CXC_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        s_cxc_sat.add(new TimeOfMovie(n, "cc8_s_cxc"));
                    }
                }else{
                    s_CXC.setVisibility(View.GONE);
                    s_CXC_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, s_cxc_sat, TimeSchedule.this);
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
        List<TimeOfMovie> t_r_sun = new ArrayList<>();
        cc8_t_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t_r_sun.clear();

                if (snapshot.exists()) {
                    t_regular.setVisibility(View.VISIBLE);
                    t_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        t_r_sun.add(new TimeOfMovie(n, "cc8_t_r"));
                    }
                }else{
                    t_regular.setVisibility(View.GONE);
                    t_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, t_r_sun, TimeSchedule.this);
                t_r_recycleV.setAdapter(scheduleAdp);
                t_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_t_3d = timeReference.child(movieid).child("CC8").child("trincity").child("3D").child("sun+hol");
        List<TimeOfMovie> t_3d_sun = new ArrayList<>();
        cc8_t_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t_3d_sun.clear();

                if (snapshot.exists()) {
                    t_3D.setVisibility(View.VISIBLE);
                    t_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        t_3d_sun.add(new TimeOfMovie(n, "cc8_t_3D"));
                    }
                }else{
                    t_3D.setVisibility(View.GONE);
                    t_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, t_3d_sun, TimeSchedule.this);
                t_3D_recycleV.setAdapter(scheduleAdp);
                t_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_r = timeReference.child(movieid).child("CC8").child("southpark").child("regular").child("sun+hol");
        List<TimeOfMovie> s_r_sun = new ArrayList<>();
        cc8_s_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s_r_sun.clear();

                if (snapshot.exists()) {
                    s_regular.setVisibility(View.VISIBLE);
                    s_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        s_r_sun.add(new TimeOfMovie(n, "cc8_s_r"));
                    }
                }else{
                    s_regular.setVisibility(View.GONE);
                    s_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, s_r_sun, TimeSchedule.this);
                s_r_recycleV.setAdapter(scheduleAdp);
                s_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_3D = timeReference.child(movieid).child("CC8").child("southpark").child("3D").child("sun+hol");
        List<TimeOfMovie> s_3d_sun = new ArrayList<>();
        cc8_s_3D.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s_3d_sun.clear();

                if (snapshot.exists()) {
                    s_3D.setVisibility(View.VISIBLE);
                    s_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        s_3d_sun.add(new TimeOfMovie(n, "cc8_s_3d"));
                    }
                }else{
                    s_3D.setVisibility(View.GONE);
                    s_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, s_3d_sun, TimeSchedule.this);
                s_3D_recycleV.setAdapter(scheduleAdp);
                s_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_4DX = timeReference.child(movieid).child("CC8").child("southpark").child("4DX").child("sun+hol");
        List<TimeOfMovie> s_4dx_sun = new ArrayList<>();
        cc8_s_4DX.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s_4dx_sun.clear();

                if (snapshot.exists()) {
                    s_4DX.setVisibility(View.VISIBLE);
                    s_4DX_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        s_4dx_sun.add(new TimeOfMovie(n, "cc8_s_4dx"));
                    }
                }else{
                    s_4DX.setVisibility(View.GONE);
                    s_4DX_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, s_4dx_sun, TimeSchedule.this);
                s_4DX_recycleV.setAdapter(scheduleAdp);
                s_4DX_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference cc8_s_CXC = timeReference.child(movieid).child("CC8").child("southpark").child("CXC").child("sun+hol");
        List<TimeOfMovie> s_cxc_sun = new ArrayList<>();
        cc8_s_CXC.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s_cxc_sun.clear();

                if (snapshot.exists()) {
                    s_CXC.setVisibility(View.VISIBLE);
                    s_CXC_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        s_cxc_sun.add(new TimeOfMovie(n, "cc8_s_cxc"));
                    }
                }else{
                    s_CXC.setVisibility(View.GONE);
                    s_CXC_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, s_cxc_sun, TimeSchedule.this);
                s_CXC_recycleV.setAdapter(scheduleAdp);
                s_CXC_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void MT_mf() {

        DatabaseReference pos_r = timeReference.child(movieid).child("MT").child("pos").child("regular").child("mon-fri");
        List<TimeOfMovie> pos_r_mf = new ArrayList<>();
        pos_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pos_r_mf.clear();

                if (snapshot.exists()) {
                    ts_pos.setVisibility(View.VISIBLE);
                    ts_pos_r.setVisibility(View.VISIBLE);
                    ts_pos_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        pos_r_mf.add(new TimeOfMovie(n, "mt_pos_r"));
                    }
                }else{
                    ts_pos.setVisibility(View.GONE);
                    ts_pos_r.setVisibility(View.GONE);
                    ts_pos_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, pos_r_mf, TimeSchedule.this);
                ts_pos_r_recycleV.setAdapter(scheduleAdp);
                ts_pos_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference pos_3d = timeReference.child(movieid).child("MT").child("pos").child("3D").child("mon-fri");
        List<TimeOfMovie> pos_3d_mf = new ArrayList<>();
        pos_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pos_3d_mf.clear();

                if (snapshot.exists()) {
                    ts_pos.setVisibility(View.VISIBLE);
                    ts_pos_3D.setVisibility(View.VISIBLE);
                    ts_pos_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        pos_3d_mf.add(new TimeOfMovie(n, "mt_pos_3d"));
                    }
                }else{
//                    ts_pos.setVisibility(View.GONE);
                    ts_pos_3D.setVisibility(View.GONE);
                    ts_pos_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, pos_3d_mf, TimeSchedule.this);
                ts_pos_3D_recycleV.setAdapter(scheduleAdp);
                ts_pos_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference chag_r = timeReference.child(movieid).child("MT").child("chag").child("regular").child("mon-fri");
        List<TimeOfMovie> chag_r_mf = new ArrayList<>();
        chag_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chag_r_mf.clear();

                if (snapshot.exists()) {
                    ts_chag.setVisibility(View.VISIBLE);
                    ts_chag_r.setVisibility(View.VISIBLE);
                    ts_chag_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        chag_r_mf.add(new TimeOfMovie(n, "mt_chag_r"));
                    }
                }else{
                    ts_chag.setVisibility(View.GONE);
                    ts_chag_r.setVisibility(View.GONE);
                    ts_chag_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, chag_r_mf, TimeSchedule.this);
                ts_chag_r_recycleV.setAdapter(scheduleAdp);
                ts_chag_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference chag_3d = timeReference.child(movieid).child("MT").child("chag").child("3D").child("mon-fri");
        List<TimeOfMovie> chag_3d_mf = new ArrayList<>();
        chag_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chag_3d_mf.clear();

                if (snapshot.exists()) {
                    ts_chag.setVisibility(View.VISIBLE);
                    ts_chag_3D.setVisibility(View.VISIBLE);
                    ts_chag_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        chag_3d_mf.add(new TimeOfMovie(n, "mt_chag_3d"));
                    }
                }else{
                    ts_chag.setVisibility(View.GONE);
                    ts_chag_3D.setVisibility(View.GONE);
                    ts_chag_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, chag_3d_mf, TimeSchedule.this);
                ts_chag_3D_recycleV.setAdapter(scheduleAdp);
                ts_chag_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference sdo_r = timeReference.child(movieid).child("MT").child("sdo").child("regular").child("mon-fri");
        List<TimeOfMovie> sdo_r_mf = new ArrayList<>();
        sdo_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sdo_r_mf.clear();

                if (snapshot.exists()) {
                    ts_sdo.setVisibility(View.VISIBLE);
                    ts_sdo_r.setVisibility(View.VISIBLE);
                    ts_sdo_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        sdo_r_mf.add(new TimeOfMovie(n, "mt_sdo_r"));
                    }
                }else{
                    ts_sdo.setVisibility(View.GONE);
                    ts_sdo_r.setVisibility(View.GONE);
                    ts_sdo_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, sdo_r_mf, TimeSchedule.this);
                ts_sdo_r_recycleV.setAdapter(scheduleAdp);
                ts_sdo_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference sdo_3d = timeReference.child(movieid).child("MT").child("pos").child("3D").child("mon-fri");
        List<TimeOfMovie> sdo_3d_mf = new ArrayList<>();
        sdo_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sdo_3d_mf.clear();

                if (snapshot.exists()) {
                    ts_sdo_3D.setVisibility(View.VISIBLE);
                    ts_sdo_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        sdo_3d_mf.add(new TimeOfMovie(n, "mt_sdo_3d"));
                    }
                }else{
                    ts_sdo_3D.setVisibility(View.GONE);
                    ts_sdo_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, sdo_3d_mf, TimeSchedule.this);
                ts_sdo_3D_recycleV.setAdapter(scheduleAdp);
                ts_sdo_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference tgo_r = timeReference.child(movieid).child("MT").child("tgo").child("regular").child("mon-fri");
        List<TimeOfMovie> tgo_r_mf = new ArrayList<>();
        tgo_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tgo_r_mf.clear();

                if (snapshot.exists()) {
                    ts_tgo.setVisibility(View.VISIBLE);
                    ts_tgo_r.setVisibility(View.VISIBLE);
                    ts_tgo_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        tgo_r_mf.add(new TimeOfMovie(n, "mt_tgo_r"));
                    }
                }else{
                    ts_tgo.setVisibility(View.GONE);
                    ts_tgo_r.setVisibility(View.GONE);
                    ts_tgo_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, tgo_r_mf, TimeSchedule.this);
                ts_tgo_r_recycleV.setAdapter(scheduleAdp);
                ts_tgo_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference tgo_3d = timeReference.child(movieid).child("MT").child("tgo").child("3D").child("mon-fri");
        List<TimeOfMovie> tgo_3d_mf = new ArrayList<>();
        tgo_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tgo_3d_mf.clear();

                if (snapshot.exists()) {
                    ts_tgo_3D.setVisibility(View.VISIBLE);
                    ts_tgo_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        tgo_3d_mf.add(new TimeOfMovie(n, "mt_tgo_3d"));
                    }
                }else{
                    ts_tgo_3D.setVisibility(View.GONE);
                    ts_tgo_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, tgo_3d_mf, TimeSchedule.this);
                ts_tgo_3D_recycleV.setAdapter(scheduleAdp);
                ts_tgo_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void MT_sat() {

        DatabaseReference pos_r = timeReference.child(movieid).child("MT").child("pos").child("regular").child("sat");
        List<TimeOfMovie> pos_r_sat = new ArrayList<>();
        pos_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pos_r_sat.clear();

                if (snapshot.exists()) {
                    ts_pos.setVisibility(View.VISIBLE);
                    ts_pos_r.setVisibility(View.VISIBLE);
                    ts_pos_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        pos_r_sat.add(new TimeOfMovie(n, "mt_pos_r"));
                    }
                }else{
                    ts_pos.setVisibility(View.GONE);
                    ts_pos_r.setVisibility(View.GONE);
                    ts_pos_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, pos_r_sat, TimeSchedule.this);
                ts_pos_r_recycleV.setAdapter(scheduleAdp);
                ts_pos_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference pos_3d = timeReference.child(movieid).child("MT").child("pos").child("3D").child("sat");
        List<TimeOfMovie> pos_3d_sat = new ArrayList<>();
        pos_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pos_3d_sat.clear();

                if (snapshot.exists()) {
                    ts_pos_3D.setVisibility(View.VISIBLE);
                    ts_pos_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        pos_3d_sat.add(new TimeOfMovie(n, "mt_pos_3d"));
                    }
                }else{
                    ts_pos_3D.setVisibility(View.GONE);
                    ts_pos_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, pos_3d_sat, TimeSchedule.this);
                ts_pos_3D_recycleV.setAdapter(scheduleAdp);
                ts_pos_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference chag_r = timeReference.child(movieid).child("MT").child("chag").child("regular").child("sat");
        List<TimeOfMovie> chag_r_sat = new ArrayList<>();
        chag_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chag_r_sat.clear();

                if (snapshot.exists()) {
                    ts_chag.setVisibility(View.VISIBLE);
                    ts_chag_r.setVisibility(View.VISIBLE);
                    ts_chag_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        chag_r_sat.add(new TimeOfMovie(n, "mt_chag_r"));
                    }
                }else{
                    ts_chag.setVisibility(View.GONE);
                    ts_chag_r.setVisibility(View.GONE);
                    ts_chag_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, chag_r_sat, TimeSchedule.this);
                ts_chag_r_recycleV.setAdapter(scheduleAdp);
                ts_chag_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference chag_3d = timeReference.child(movieid).child("MT").child("chag").child("3D").child("sat");
        List<TimeOfMovie> chag_3d_sat = new ArrayList<>();
        chag_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chag_3d_sat.clear();

                if (snapshot.exists()) {
                    ts_chag.setVisibility(View.VISIBLE);
                    ts_chag_3D.setVisibility(View.VISIBLE);
                    ts_chag_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        chag_3d_sat.add(new TimeOfMovie(n, "mt_chag_3d"));
                    }
                }else{
//                    ts_chag.setVisibility(View.GONE);
                    ts_chag_3D.setVisibility(View.GONE);
                    ts_chag_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, chag_3d_sat, TimeSchedule.this);
                ts_chag_3D_recycleV.setAdapter(scheduleAdp);
                ts_chag_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference sdo_r = timeReference.child(movieid).child("MT").child("sdo").child("regular").child("sat");
        List<TimeOfMovie> sdo_r_sat = new ArrayList<>();
        sdo_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sdo_r_sat.clear();

                if (snapshot.exists()) {
                    ts_sdo.setVisibility(View.VISIBLE);
                    ts_sdo_r.setVisibility(View.VISIBLE);
                    ts_sdo_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        sdo_r_sat.add(new TimeOfMovie(n, "mt_sdo_r"));
                    }
                }else{
                    ts_sdo.setVisibility(View.GONE);
                    ts_sdo_r.setVisibility(View.GONE);
                    ts_sdo_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, sdo_r_sat, TimeSchedule.this);
                ts_sdo_r_recycleV.setAdapter(scheduleAdp);
                ts_sdo_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference sdo_3d = timeReference.child(movieid).child("MT").child("pos").child("3D").child("sat");
        List<TimeOfMovie> sdo_3d_sat = new ArrayList<>();
        sdo_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sdo_3d_sat.clear();

                if (snapshot.exists()) {
                    ts_sdo_3D.setVisibility(View.VISIBLE);
                    ts_sdo_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        sdo_3d_sat.add(new TimeOfMovie(n, "mt_sdo_3d"));
                    }
                }else{
                    ts_sdo_3D.setVisibility(View.GONE);
                    ts_sdo_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, sdo_3d_sat, TimeSchedule.this);
                ts_sdo_3D_recycleV.setAdapter(scheduleAdp);
                ts_sdo_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference tgo_r = timeReference.child(movieid).child("MT").child("tgo").child("regular").child("sat");
        List<TimeOfMovie> tgo_r_sat = new ArrayList<>();
        tgo_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tgo_r_sat.clear();

                if (snapshot.exists()) {
                    ts_tgo.setVisibility(View.VISIBLE);
                    ts_tgo_r.setVisibility(View.VISIBLE);
                    ts_tgo_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        tgo_r_sat.add(new TimeOfMovie(n, "mt_tgo_r"));
                    }
                }else{
                    ts_tgo.setVisibility(View.GONE);
                    ts_tgo_r.setVisibility(View.GONE);
                    ts_tgo_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, tgo_r_sat, TimeSchedule.this);
                ts_tgo_r_recycleV.setAdapter(scheduleAdp);
                ts_tgo_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference tgo_3d = timeReference.child(movieid).child("MT").child("tgo").child("3D").child("sat");
        List<TimeOfMovie> tgo_3d_sat = new ArrayList<>();
        tgo_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tgo_3d_sat.clear();

                if (snapshot.exists()) {
                    ts_tgo_r.setVisibility(View.VISIBLE);
                    ts_tgo_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        tgo_3d_sat.add(new TimeOfMovie(n, "mt_tgo_3d"));
                    }
                }else{
                    ts_tgo_3D.setVisibility(View.GONE);
                    ts_tgo_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, tgo_3d_sat, TimeSchedule.this);
                ts_tgo_3D_recycleV.setAdapter(scheduleAdp);
                ts_tgo_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void MT_sun() {

        DatabaseReference pos_r = timeReference.child(movieid).child("MT").child("pos").child("regular").child("sun+hol");
        List<TimeOfMovie> pos_r_sun = new ArrayList<>();
        pos_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pos_r_sun.clear();

                if (snapshot.exists()) {
                    ts_pos.setVisibility(View.VISIBLE);
                    ts_pos_r.setVisibility(View.VISIBLE);
                    ts_pos_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        pos_r_sun.add(new TimeOfMovie(n, "mt_pos_r"));
                    }
                }else{
                    ts_pos.setVisibility(View.GONE);
                    ts_pos_r.setVisibility(View.GONE);
                    ts_pos_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, pos_r_sun, TimeSchedule.this);
                ts_pos_r_recycleV.setAdapter(scheduleAdp);
                ts_pos_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference pos_3d = timeReference.child(movieid).child("MT").child("pos").child("3D").child("sun+hol");
        List<TimeOfMovie> pos_3d_sun = new ArrayList<>();
        pos_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pos_3d_sun.clear();

                if (snapshot.exists()) {
                    ts_pos.setVisibility(View.VISIBLE);
                    ts_pos_3D.setVisibility(View.VISIBLE);
                    ts_pos_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        pos_3d_sun.add(new TimeOfMovie(n, "mt_pos_3d"));
                    }
                }else{
                    ts_pos_3D.setVisibility(View.GONE);
                    ts_pos_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, pos_3d_sun, TimeSchedule.this);
                ts_pos_3D_recycleV.setAdapter(scheduleAdp);
                ts_pos_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference chag_r = timeReference.child(movieid).child("MT").child("chag").child("regular").child("sun+hol");
        List<TimeOfMovie> chag_r_sun = new ArrayList<>();
        chag_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chag_r_sun.clear();

                if (snapshot.exists()) {
                    ts_chag.setVisibility(View.VISIBLE);
                    ts_chag_r.setVisibility(View.VISIBLE);
                    ts_chag_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        chag_r_sun.add(new TimeOfMovie(n, "mt_chag_r"));
                    }
                }else{
                    ts_chag.setVisibility(View.GONE);
                    ts_chag_r.setVisibility(View.GONE);
                    ts_chag_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, chag_r_sun, TimeSchedule.this);
                ts_chag_r_recycleV.setAdapter(scheduleAdp);
                ts_chag_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference chag_3d = timeReference.child(movieid).child("MT").child("chag").child("3D").child("sun+hol");
        List<TimeOfMovie> chag_3d_sun = new ArrayList<>();
        chag_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chag_3d_sun.clear();

                if (snapshot.exists()) {
                    ts_chag.setVisibility(View.VISIBLE);
                    ts_chag_3D.setVisibility(View.VISIBLE);
                    ts_chag_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        chag_3d_sun.add(new TimeOfMovie(n, "mt_chag_3d"));
                    }
                }else{
                    ts_chag_3D.setVisibility(View.GONE);
                    ts_chag_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, chag_3d_sun, TimeSchedule.this);
                ts_chag_3D_recycleV.setAdapter(scheduleAdp);
                ts_chag_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference sdo_r = timeReference.child(movieid).child("MT").child("sdo").child("regular").child("sun+hol");
        List<TimeOfMovie> sdo_r_sun = new ArrayList<>();
        sdo_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sdo_r_sun.clear();

                if (snapshot.exists()) {
                    ts_sdo.setVisibility(View.VISIBLE);
                    ts_sdo_r.setVisibility(View.VISIBLE);
                    ts_sdo_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        sdo_r_sun.add(new TimeOfMovie(n, "mt_sdo_r"));
                    }
                }else{
                    ts_sdo.setVisibility(View.GONE);
                    ts_sdo_r.setVisibility(View.GONE);
                    ts_sdo_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, sdo_r_sun, TimeSchedule.this);
                ts_sdo_r_recycleV.setAdapter(scheduleAdp);
                ts_sdo_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference sdo_3d = timeReference.child(movieid).child("MT").child("pos").child("3D").child("sun+hol");
        List<TimeOfMovie> sdo_3d_sun = new ArrayList<>();
        sdo_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sdo_3d_sun.clear();

                if (snapshot.exists()) {
                    ts_sdo.setVisibility(View.VISIBLE);
                    ts_sdo_3D.setVisibility(View.VISIBLE);
                    ts_sdo_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        sdo_3d_sun.add(new TimeOfMovie(n, "mt_sdo_3d"));
                    }
                }else{
                    ts_sdo_3D.setVisibility(View.GONE);
                    ts_sdo_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, sdo_3d_sun, TimeSchedule.this);
                ts_sdo_3D_recycleV.setAdapter(scheduleAdp);
                ts_sdo_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference tgo_r = timeReference.child(movieid).child("MT").child("tgo").child("regular").child("sun+hol");
        List<TimeOfMovie> tgo_r_sun = new ArrayList<>();
        tgo_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tgo_r_sun.clear();

                if (snapshot.exists()) {
                    ts_tgo.setVisibility(View.VISIBLE);
                    ts_tgo_r.setVisibility(View.VISIBLE);
                    ts_tgo_r_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        tgo_r_sun.add(new TimeOfMovie(n, "mt_tgo_r"));
                    }
                }else{
                    ts_tgo.setVisibility(View.GONE);
                    ts_tgo_r.setVisibility(View.GONE);
                    ts_tgo_r_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, tgo_r_sun, TimeSchedule.this);
                ts_tgo_r_recycleV.setAdapter(scheduleAdp);
                ts_tgo_r_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference tgo_3d = timeReference.child(movieid).child("MT").child("tgo").child("3D").child("sun+hol");
        List<TimeOfMovie> tgo_3d_sun = new ArrayList<>();
        tgo_3d.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tgo_3d_sun.clear();

                if (snapshot.exists()) {
                    ts_tgo.setVisibility(View.VISIBLE);
                    ts_tgo_3D.setVisibility(View.VISIBLE);
                    ts_tgo_3D_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        tgo_3d_sun.add(new TimeOfMovie(n, "mt_tgo_3d"));
                    }
                }else{
                    ts_tgo_3D.setVisibility(View.GONE);
                    ts_tgo_3D_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, tgo_3d_sun, TimeSchedule.this);
                ts_tgo_3D_recycleV.setAdapter(scheduleAdp);
                ts_tgo_3D_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void CONE_mf(){

        DatabaseReference gemstone = timeReference.child(movieid).child("CONE").child("gemstone").child("mon-fri");
        List<TimeOfMovie> gs_mf = new ArrayList<>();
        gemstone.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gs_mf.clear();

                if (snapshot.exists()) {
                    ts_gemStone.setVisibility(View.VISIBLE);
                    ts_gemstone_recycleR.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        gs_mf.add(new TimeOfMovie(n, "cone_gs"));
                    }
                }else{
                    ts_gemStone.setVisibility(View.GONE);
                    ts_gemstone_recycleR.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, gs_mf, TimeSchedule.this);
                ts_gemstone_recycleR.setAdapter(scheduleAdp);
                ts_gemstone_recycleR.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference CONE_4DX = timeReference.child(movieid).child("CONE").child("4DX").child("mon-fri");
        List<TimeOfMovie> cone_4dx_mf = new ArrayList<>();
        CONE_4DX.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cone_4dx_mf.clear();

                if (snapshot.exists()) {
                    ts_4DX.setVisibility(View.VISIBLE);
                    ts_4DX_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        cone_4dx_mf.add(new TimeOfMovie(n, "cone_4dx"));
                    }
                }else{
                    ts_4DX.setVisibility(View.GONE);
                    ts_4DX_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, cone_4dx_mf, TimeSchedule.this);
                ts_4DX_recycleV.setAdapter(scheduleAdp);
                ts_4DX_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference imax = timeReference.child(movieid).child("CONE").child("imax").child("regular").child("mon-fri");
        List<TimeOfMovie> imax_r_mf = new ArrayList<>();
        imax.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imax_r_mf.clear();

                if (snapshot.exists()) {
                    ts_imax.setVisibility(View.VISIBLE);
                    ts_imax_recycleR.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        imax_r_mf.add(new TimeOfMovie(n, "cone_imax_r"));
                    }
                }else{
                    ts_imax.setVisibility(View.GONE);
                    ts_imax_recycleR.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, imax_r_mf, TimeSchedule.this);
                ts_imax_recycleR.setAdapter(scheduleAdp);
                ts_imax_recycleR.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void CONE_sat(){

        DatabaseReference gemstone = timeReference.child(movieid).child("CONE").child("gemstone").child("sat");
        List<TimeOfMovie> gs_sat = new ArrayList<>();
        gemstone.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gs_sat.clear();

                if (snapshot.exists()) {
                    ts_gemStone.setVisibility(View.VISIBLE);
                    ts_gemstone_recycleR.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        gs_sat.add(new TimeOfMovie(n, "cone_gs"));
                    }
                }else{
                    ts_gemStone.setVisibility(View.GONE);
                    ts_gemstone_recycleR.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, gs_sat, TimeSchedule.this);
                ts_gemstone_recycleR.setAdapter(scheduleAdp);
                ts_gemstone_recycleR.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference CONE_4DX = timeReference.child(movieid).child("CONE").child("4DX").child("sat");
        List<TimeOfMovie> cone_4dx_sat = new ArrayList<>();
        CONE_4DX.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cone_4dx_sat.clear();

                if (snapshot.exists()) {
                    ts_4DX.setVisibility(View.VISIBLE);
                    ts_4DX_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        cone_4dx_sat.add(new TimeOfMovie(n, "cone_4dx"));
                    }
                }else{
                    ts_4DX.setVisibility(View.GONE);
                    ts_4DX_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, cone_4dx_sat, TimeSchedule.this);
                ts_4DX_recycleV.setAdapter(scheduleAdp);
                ts_4DX_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference imax = timeReference.child(movieid).child("CONE").child("imax").child("regular").child("sat");
        List<TimeOfMovie> imax_r_sat = new ArrayList<>();
        imax.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imax_r_sat.clear();

                if (snapshot.exists()) {
                    ts_imax.setVisibility(View.VISIBLE);
                    ts_imax_recycleR.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        imax_r_sat.add(new TimeOfMovie(n, "cone_imax_r"));
                    }
                }else{
                    ts_imax.setVisibility(View.GONE);
                    ts_imax_recycleR.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, imax_r_sat, TimeSchedule.this);
                ts_imax_recycleR.setAdapter(scheduleAdp);
                ts_imax_recycleR.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void CONE_sun(){

        DatabaseReference gemstone = timeReference.child(movieid).child("CONE").child("gemstone").child("sun+hol");
        List<TimeOfMovie> gs_sun = new ArrayList<>();
        gemstone.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gs_sun.clear();

                if (snapshot.exists()) {
                    ts_gemStone.setVisibility(View.VISIBLE);
                    ts_gemstone_recycleR.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        gs_sun.add(new TimeOfMovie(n, "cone_gs"));
                    }
                }else{
                    ts_gemStone.setVisibility(View.GONE);
                    ts_gemstone_recycleR.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, gs_sun, TimeSchedule.this);
                ts_gemstone_recycleR.setAdapter(scheduleAdp);
                ts_gemstone_recycleR.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference CONE_4DX = timeReference.child(movieid).child("CONE").child("4DX").child("sun+hol");
        List<TimeOfMovie> cone_4dx_sun = new ArrayList<>();
        CONE_4DX.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cone_4dx_sun.clear();

                if (snapshot.exists()) {
                    ts_4DX.setVisibility(View.VISIBLE);
                    ts_4DX_recycleV.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        cone_4dx_sun.add(new TimeOfMovie(n, "cone_4dx"));
                    }
                }else{
                    ts_4DX.setVisibility(View.GONE);
                    ts_4DX_recycleV.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, cone_4dx_sun, TimeSchedule.this);
                ts_4DX_recycleV.setAdapter(scheduleAdp);
                ts_4DX_recycleV.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference imax = timeReference.child(movieid).child("CONE").child("imax").child("regular").child("sun+hol");
        List<TimeOfMovie> imax_r_sun = new ArrayList<>();
        imax.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imax_r_sun.clear();

                if (snapshot.exists()) {
                    ts_imax.setVisibility(View.VISIBLE);
                    ts_imax_recycleR.setVisibility(View.VISIBLE);

                    for (DataSnapshot ss : snapshot.getChildren()) {
                        String n = String.valueOf(ss.getValue());
                        imax_r_sun.add(new TimeOfMovie(n, "cone_imax_r"));
                    }
                }else{
                    ts_imax.setVisibility(View.GONE);
                    ts_imax_recycleR.setVisibility(View.GONE);
                }

                RecycleViewScheduleAdapter scheduleAdp = new RecycleViewScheduleAdapter(TimeSchedule.this, imax_r_sun, TimeSchedule.this);
                ts_imax_recycleR.setAdapter(scheduleAdp);
                ts_imax_recycleR.setLayoutManager(new GridLayoutManager(TimeSchedule.this, 3));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onTimeClick(TimeOfMovie timeOfMovie) {

        Intent intent;
        if(currentuser == null) {

            intent = new Intent(TimeSchedule.this, MainActivity.class);
            intent.putExtra("Screen", "Select Tickets");

        }else{

            intent = new Intent(TimeSchedule.this, SelectTickets.class);

        }
        intent.putExtra("currentMovie", movie);
        intent.putExtra("selectDate", selectedDate);
        intent.putExtra("time", timeOfMovie.getT());
        intent.putExtra("theater_type", timeOfMovie.getType());
        startActivity(intent);

    }

    public void CC8Gone(){

        cc8_img.setVisibility(View.GONE);
        trincity.setVisibility(View.GONE);
        t_regular.setVisibility(View.GONE);
        t_r_recycleV.setVisibility(View.GONE);
        t_3D.setVisibility(View.GONE);
        t_3D_recycleV.setVisibility(View.GONE);
        southpark.setVisibility(View.GONE);
        s_regular.setVisibility(View.GONE);
        s_r_recycleV.setVisibility(View.GONE);
        s_3D.setVisibility(View.GONE);
        s_3D_recycleV.setVisibility(View.GONE);
        s_4DX.setVisibility(View.GONE);
        s_4DX_recycleV.setVisibility(View.GONE);
        s_CXC.setVisibility(View.GONE);
        s_CXC_recycleV.setVisibility(View.GONE);
    }

    public void CC8View(){

        cc8_img.setVisibility(View.VISIBLE);
        trincity.setVisibility(View.VISIBLE);
        t_regular.setVisibility(View.VISIBLE);
        t_r_recycleV.setVisibility(View.VISIBLE);
        t_3D.setVisibility(View.VISIBLE);
        t_3D_recycleV.setVisibility(View.VISIBLE);
        southpark.setVisibility(View.VISIBLE);
        s_regular.setVisibility(View.VISIBLE);
        s_r_recycleV.setVisibility(View.VISIBLE);
        s_3D.setVisibility(View.VISIBLE);
        s_3D_recycleV.setVisibility(View.VISIBLE);
        s_4DX.setVisibility(View.VISIBLE);
        s_4DX_recycleV.setVisibility(View.VISIBLE);
        s_CXC.setVisibility(View.VISIBLE);
        s_CXC_recycleV.setVisibility(View.VISIBLE);

    }

    public void MTGone(){

        mt_img.setVisibility(View.GONE);
        ts_pos.setVisibility(View.GONE);
        ts_pos_r.setVisibility(View.GONE);
        ts_pos_r_recycleV.setVisibility(View.GONE);
        ts_pos_3D.setVisibility(View.GONE);
        ts_pos_3D_recycleV.setVisibility(View.GONE);
        ts_chag.setVisibility(View.GONE);
        ts_chag_r.setVisibility(View.GONE);
        ts_chag_r_recycleV.setVisibility(View.GONE);
        ts_chag_3D.setVisibility(View.GONE);
        ts_chag_3D_recycleV.setVisibility(View.GONE);
        ts_sdo.setVisibility(View.GONE);
        ts_sdo_r.setVisibility(View.GONE);
        ts_sdo_r_recycleV.setVisibility(View.GONE);
        ts_sdo_3D.setVisibility(View.GONE);
        ts_sdo_3D_recycleV.setVisibility(View.GONE);
        ts_tgo.setVisibility(View.GONE);
        ts_tgo_r.setVisibility(View.GONE);
        ts_tgo_r_recycleV.setVisibility(View.GONE);
        ts_tgo_3D.setVisibility(View.GONE);
        ts_tgo_3D_recycleV.setVisibility(View.GONE);

    }

    public void MTView(){

        mt_img.setVisibility(View.VISIBLE);
        ts_pos.setVisibility(View.VISIBLE);
        ts_pos_r.setVisibility(View.VISIBLE);
        ts_pos_r_recycleV.setVisibility(View.VISIBLE);
        ts_pos_3D.setVisibility(View.VISIBLE);
        ts_pos_3D_recycleV.setVisibility(View.VISIBLE);
        ts_chag.setVisibility(View.VISIBLE);
        ts_chag_r.setVisibility(View.VISIBLE);
        ts_chag_r_recycleV.setVisibility(View.VISIBLE);
        ts_chag_3D.setVisibility(View.VISIBLE);
        ts_chag_3D_recycleV.setVisibility(View.VISIBLE);
        ts_sdo.setVisibility(View.VISIBLE);
        ts_sdo_r.setVisibility(View.VISIBLE);
        ts_sdo_r_recycleV.setVisibility(View.VISIBLE);
        ts_sdo_3D.setVisibility(View.VISIBLE);
        ts_sdo_3D_recycleV.setVisibility(View.VISIBLE);
        ts_tgo.setVisibility(View.VISIBLE);
        ts_tgo_r.setVisibility(View.VISIBLE);
        ts_tgo_r_recycleV.setVisibility(View.VISIBLE);
        ts_tgo_3D.setVisibility(View.VISIBLE);
        ts_tgo_3D_recycleV.setVisibility(View.VISIBLE);

    }

    public void CONEGone(){

        cONE_img.setVisibility(View.GONE);
        ts_cinemaOne.setVisibility(View.GONE);
        ts_imax.setVisibility(View.GONE);
        ts_imax_recycleR.setVisibility(View.GONE);
        ts_gemStone.setVisibility(View.GONE);
        ts_gemstone_recycleR.setVisibility(View.GONE);
        ts_4DX.setVisibility(View.GONE);
        ts_4DX_recycleV.setVisibility(View.GONE);

    }

    public void CONEView(){

        cONE_img.setVisibility(View.VISIBLE);
        ts_cinemaOne.setVisibility(View.VISIBLE);
        ts_imax.setVisibility(View.VISIBLE);
        ts_imax_recycleR.setVisibility(View.VISIBLE);
        ts_gemStone.setVisibility(View.VISIBLE);
        ts_gemstone_recycleR.setVisibility(View.VISIBLE);
        ts_4DX.setVisibility(View.VISIBLE);
        ts_4DX_recycleV.setVisibility(View.VISIBLE);

    }
}