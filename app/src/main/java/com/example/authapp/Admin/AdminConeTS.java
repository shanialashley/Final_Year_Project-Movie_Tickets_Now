package com.example.authapp.Admin;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.authapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminConeTS extends AppCompatActivity {

    private LinearLayout imax_r_mf, imax_r_sat, imax_r_sunH;
    private LinearLayout gs_r_mf, gs_r_sat, gs_r_sunH;
    private LinearLayout c_4dx_mf, c_4dx_sat, c_4dx_sunH;
    private EditText c_timeS_id;
    private Button c_saveB;

    private List<String> imax_r_mf_list, imax_r_sat_list, imax_r_sun_list;
    private List<String> gs_r_mf_list, gs_r_sat_list, gs_r_sun_list;
    private List<String> c_4dx_mf_list, c_4dx_sat_list, c_4dx_sun_list;

    private int imax_r_count = 0,  imax_r_count1 = 0, imax_r_count2 = 0;
    private int gs_r_count = 0,  gs_r_count1 = 0, gs_r_count2 = 0;
    private int c_4dx_count = 0,  c_4dx_count1 = 0, c_4dx_count2 = 0;

    private DatabaseReference referenceTime;
    private DatabaseReference scheduleTimes;
    private String timeSid;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cone_t_s);

        referenceTime = FirebaseDatabase.getInstance().getReference().child("TimeSchedule");

        ToolbarInfo();
        init();
        initList();

        String key = getIntent().getExtras().getString("TimeS_Key");

        if(key != null){
            c_timeS_id.setText(key);
        }

    }

    public void ToolbarInfo(){


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String s = "Cinema ONE";
        getSupportActionBar().setTitle(s);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    private void init() {
        imax_r_mf = findViewById(R.id.cone_imax_r_mf_LL);
        imax_r_sat = findViewById(R.id.cone_imaxx_r_sat_LL);
        imax_r_sunH = findViewById(R.id.cone_imax_r_sunH_LL);

        gs_r_mf = findViewById(R.id.cone_gs_r_mf_LL);
        gs_r_sat = findViewById(R.id.cone_gs_r_sat_LL);
        gs_r_sunH = findViewById(R.id.cone_gs_r_sunH_LL);

        c_4dx_mf = findViewById(R.id.cone_4dx_r_mf_LL);
        c_4dx_sat = findViewById(R.id.cone_4dx_r_sat_LL);
        c_4dx_sunH = findViewById(R.id.cone_4dx_r_sunH_LL);

        c_timeS_id = findViewById(R.id.adTime_idCONE);
        c_timeS_id.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    SearchDisplay(c_timeS_id.getText().toString().trim());

                    return true;
                }

                return false;
            }
        });
        c_saveB = findViewById(R.id.adTime_saveBCONE);

        c_saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveTimes();
                ClearAllViews();

            }
        });
    }

    private void initList() {

        imax_r_mf_list = new ArrayList<>();
        imax_r_sat_list = new ArrayList<>();
        imax_r_sun_list = new ArrayList<>();

        gs_r_mf_list = new ArrayList<>();
        gs_r_sat_list = new ArrayList<>();
        gs_r_sun_list = new ArrayList<>();

        c_4dx_mf_list = new ArrayList<>();
        c_4dx_sat_list = new ArrayList<>();
        c_4dx_sun_list = new ArrayList<>();

    }

    private void ClearList() {

        imax_r_mf_list.clear();
        imax_r_sat_list.clear();
        imax_r_sun_list.clear();

        gs_r_mf_list.clear();
        gs_r_sat_list.clear();
        gs_r_sun_list.clear();

        c_4dx_mf_list.clear();
        c_4dx_sat_list.clear();
        c_4dx_sun_list.clear();

    }

    private void ClearAllViews(){

        imax_r_mf.removeAllViews();
        imax_r_sat.removeAllViews();
        imax_r_sunH.removeAllViews();

        gs_r_mf.removeAllViews();
        gs_r_sat.removeAllViews();
        gs_r_sunH.removeAllViews();

        c_4dx_mf.removeAllViews();
        c_4dx_sat.removeAllViews();
        c_4dx_sunH.removeAllViews();
    }

    public void AddTimes(){

        IMAX_R_mf_AT();
        IMAX_R_sat_AT();
        IMAX_R_sun_AT();

        GS_mf_AT();
        GS_sat_AT();
        GS_sun_AT();

        C_4DX_mf_AT();
        C_4DX_sat_AT();
        C_4DX_sun_AT();

    }

    public void IMAX_R_mf_AT(){

        final View mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = mf_View.findViewById(R.id.st_time_et);
        Button add_img = mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMAX_R_mf_AT();

            }
        });
        Button remove_img = mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imax_r_count>1) {
                    imax_r_mf.removeView(mf_View);
                }

            }
        });

        imax_r_mf.addView(mf_View);
        imax_r_count++;

    }

    public void IMAX_R_sat_AT(){

        final View sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sat_View.findViewById(R.id.st_time_et);
        Button add_img = sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMAX_R_sat_AT();

            }
        });
        Button remove_img = sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imax_r_count1>1) {
                    imax_r_sat.removeView(sat_View);
                }

            }
        });

        imax_r_sat.addView(sat_View);
        imax_r_count1++;

    }

    public void IMAX_R_sun_AT(){

        final View sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sun_View.findViewById(R.id.st_time_et);
        Button add_img = sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMAX_R_sun_AT();

            }
        });
        Button remove_img = sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imax_r_count2>1) {
                    imax_r_sunH.removeView(sun_View);
                }

            }
        });

        imax_r_sunH.addView(sun_View);
        imax_r_count2++;

    }

    public void GS_mf_AT(){

        final View mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = mf_View.findViewById(R.id.st_time_et);
        Button add_img = mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GS_mf_AT();

            }
        });
        Button remove_img = mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gs_r_count>1) {
                    gs_r_mf.removeView(mf_View);
                }

            }
        });

        gs_r_mf.addView(mf_View);
        gs_r_count++;

    }

    public void GS_sat_AT(){

        final View sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sat_View.findViewById(R.id.st_time_et);
        Button add_img = sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GS_sat_AT();

            }
        });
        Button remove_img = sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gs_r_count1>1) {
                    gs_r_sat.removeView(sat_View);
                }

            }
        });

        gs_r_sat.addView(sat_View);
        gs_r_count1++;

    }

    public void GS_sun_AT(){

        final View sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sun_View.findViewById(R.id.st_time_et);
        Button add_img = sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GS_sun_AT();

            }
        });
        Button remove_img = sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(gs_r_count2>1) {
                    gs_r_sunH.removeView(sun_View);
                }

            }
        });

        gs_r_sunH.addView(sun_View);
        gs_r_count2++;

    }

    public void C_4DX_mf_AT(){

        final View mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = mf_View.findViewById(R.id.st_time_et);
        Button add_img = mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_4DX_mf_AT();

            }
        });
        Button remove_img = mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(c_4dx_count>1) {
                    c_4dx_mf.removeView(mf_View);
                }

            }
        });

        c_4dx_mf.addView(mf_View);
        c_4dx_count++;

    }

    public void C_4DX_sat_AT(){

        final View sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sat_View.findViewById(R.id.st_time_et);
        Button add_img = sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_4DX_sat_AT();

            }
        });
        Button remove_img = sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(c_4dx_count1>1) {
                    c_4dx_sat.removeView(sat_View);
                }

            }
        });

        c_4dx_sat.addView(sat_View);
        c_4dx_count1++;

    }

    public void C_4DX_sun_AT(){

        final View sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sun_View.findViewById(R.id.st_time_et);
        Button add_img = sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C_4DX_sun_AT();

            }
        });
        Button remove_img = sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(c_4dx_count2>1) {
                    c_4dx_sunH.removeView(sun_View);
                }

            }
        });

        c_4dx_sunH.addView(sun_View);
        c_4dx_count2++;

    }

    private void SaveTimes() {

        timeSid = c_timeS_id.getText().toString().trim();
        IMAX_R_saveTimes(timeSid);
        GS_saveTimes(timeSid);
        C_4DX_saveTimes(timeSid);

    }

    public void IMAX_R_saveTimes(String timeSid) {

        imax_r_mf_list.clear();
        imax_r_sat_list.clear();
        imax_r_sun_list.clear();

        for(int i=0; i<imax_r_mf.getChildCount(); i++){
            View mf_V = imax_r_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                imax_r_mf_list.add(t);
            }
        }

        if(imax_r_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("CONE").child("imax").child("regular").child("mon-fri");
            for(int i= 0; i<imax_r_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(imax_r_mf_list.get(i));
            }

        }

        imax_r_mf.removeAllViewsInLayout();


        for(int i=0; i<imax_r_sat.getChildCount(); i++){
            View sat_V = imax_r_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                imax_r_sat_list.add(t);
            }

        }

        if(imax_r_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("CONE").child("imax").child("regular").child("sat");
            for(int i= 0; i<imax_r_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(imax_r_sat_list.get(i));
            }

        }

        imax_r_sat.removeAllViewsInLayout();

        for(int i=0; i<imax_r_sunH.getChildCount(); i++){
            View sat_V = imax_r_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                imax_r_sun_list.add(t);
            }

        }

        if(imax_r_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("CONE").child("imax").child("regular").child("sun+hol");
            for(int i= 0; i<imax_r_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(imax_r_sun_list.get(i));
            }

        }
        imax_r_sunH.removeAllViewsInLayout();

    }

    public void GS_saveTimes(String timeSid) {

        gs_r_mf_list.clear();
        gs_r_sat_list.clear();
        gs_r_sun_list.clear();

        for(int i=0; i<gs_r_mf.getChildCount(); i++){
            View mf_V = gs_r_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                gs_r_mf_list.add(t);
            }
        }

        if(gs_r_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("CONE").child("gemstone").child("mon-fri");
            for(int i= 0; i<gs_r_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(gs_r_mf_list.get(i));
            }

        }

        gs_r_mf.removeAllViewsInLayout();


        for(int i=0; i<gs_r_sat.getChildCount(); i++){
            View sat_V = gs_r_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                gs_r_sat_list.add(t);
            }

        }

        if(gs_r_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("CONE").child("gemstone").child("sat");
            for(int i= 0; i<gs_r_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(gs_r_sat_list.get(i));
            }

        }

        gs_r_sat.removeAllViewsInLayout();

        for(int i=0; i<gs_r_sunH.getChildCount(); i++){
            View sat_V = gs_r_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                gs_r_sun_list.add(t);
            }

        }

        if(gs_r_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("CONE").child("gemstone").child("sun+hol");
            for(int i= 0; i<gs_r_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(gs_r_sun_list.get(i));
            }

        }
        gs_r_sunH.removeAllViewsInLayout();

    }

    public void C_4DX_saveTimes(String timeSid) {

        c_4dx_mf_list.clear();
        c_4dx_sat_list.clear();
        c_4dx_sun_list.clear();

        for(int i=0; i<c_4dx_mf.getChildCount(); i++){
            View mf_V = c_4dx_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                c_4dx_mf_list.add(t);
            }
        }

        if(c_4dx_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("CONE").child("4DX").child("mon-fri");
            for(int i= 0; i<c_4dx_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(c_4dx_mf_list.get(i));
            }

        }

        c_4dx_mf.removeAllViewsInLayout();


        for(int i=0; i<c_4dx_sat.getChildCount(); i++){
            View sat_V = c_4dx_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                c_4dx_sat_list.add(t);
            }

        }

        if(c_4dx_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("CONE").child("4DX").child("sat");
            for(int i= 0; i<c_4dx_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(c_4dx_sat_list.get(i));
            }

        }

        c_4dx_sat.removeAllViewsInLayout();

        for(int i=0; i<c_4dx_sunH.getChildCount(); i++){
            View sat_V = c_4dx_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                c_4dx_sun_list.add(t);
            }

        }

        if(c_4dx_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("CONE").child("4DX").child("sun+hol");
            for(int i= 0; i<c_4dx_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(c_4dx_sun_list.get(i));
            }

        }
        c_4dx_sunH.removeAllViewsInLayout();

    }

    private void SearchDisplay(String text){

    if(!text.isEmpty()){
        scheduleTimes =  FirebaseDatabase.getInstance().getReference("TimeSchedule")
                .child(text);
        scheduleTimes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    ClearAllViews();
                    Display_IMAX_R(scheduleTimes, text);
                    Display_GS(scheduleTimes, text);
                    Display_4DX(scheduleTimes, text);

                }else{

                    Toast.makeText(AdminConeTS.this, "Key does not exist!", Toast.LENGTH_SHORT).show();
                    ClearAllViews();
                    AddTimes();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }else{
        Toast.makeText(this, "Please enter a key", Toast.LENGTH_SHORT).show();
    }

}

    private void Display_IMAX_R(DatabaseReference scheduleTimes, String text) {

            imax_r_mf_list.clear();
            Query r_mf = scheduleTimes.child("CONE").child("imax").child("regular").child("mon-fri").orderByKey();
            r_mf.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    imax_r_mf_list.clear();
                    if(snapshot.exists()){
                        for(DataSnapshot ss: snapshot.getChildren()){
                            String time = ss.getValue().toString();
                            imax_r_mf_list.add(time);
                        }
                    }else{
                        IMAX_R_mf_AT();
                    }

                    int size = imax_r_mf_list.size();
                    for(int i= 0 ; i<size; i++) {

                        View mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                        EditText time_ET = mf_DV.findViewById(R.id.st_time_et);
                        Button add_img = mf_DV.findViewById(R.id.st_addB);
                        Button remove_img = mf_DV.findViewById(R.id.st_removeB);

                        time_ET.setText(imax_r_mf_list.get(i));

                        add_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                addNewTime(mf_DV, imax_r_mf, imax_r_mf_list);

                            }
                        });

                        int finalI = i;
                        remove_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                DatabaseReference mfpost = referenceTime.child(timeSid).child("CONE").child("imax").child("regular").child("mon-fri");
                                mfpost.child(String.valueOf(finalI)).removeValue();
                                Toast.makeText(AdminConeTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                                time_ET.setText("");
                                imax_r_mf.removeView(mf_DV);

                            }
                        });

                        imax_r_mf.addView(mf_DV);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        imax_r_sat_list.clear();
            Query r_sat = scheduleTimes.child("CONE").child("imax").child("regular").child("sat").orderByKey();
            r_sat.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    imax_r_sat_list.clear();
                    if(snapshot.exists()){
                        for(DataSnapshot ss: snapshot.getChildren()){
                            String time = ss.getValue().toString();
                            imax_r_sat_list.add(time);
                        }
                    }else{
                        IMAX_R_sat_AT();
                    }

                    int size =  imax_r_sat_list.size();
                    for(int i= 0 ; i<size; i++) {

                        View sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                        EditText time_ET = sat_DV.findViewById(R.id.st_time_et);
                        Button add_img = sat_DV.findViewById(R.id.st_addB);
                        Button remove_img = sat_DV.findViewById(R.id.st_removeB);

                        time_ET.setText(imax_r_sat_list.get(i));

                        add_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                addNewTime(sat_DV, imax_r_sat, imax_r_sat_list);

                            }
                        });

                        int finalI = i;
                        remove_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                DatabaseReference satpost = referenceTime.child(timeSid).child("CONE").child("imax").child("regular").child("sat");
                                satpost.child(String.valueOf(finalI)).removeValue();
                                Toast.makeText(AdminConeTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                                imax_r_sat.removeView(sat_DV);

                            }
                        });

                        imax_r_sat.addView(sat_DV);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        imax_r_sun_list.clear();
            Query r_sun = scheduleTimes.child("CONE").child("imax").child("regular").child("sun+hol").orderByKey();
            r_sun.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    imax_r_sun_list.clear();
                    if(snapshot.exists()){
                        for(DataSnapshot ss: snapshot.getChildren()){
                            String time = ss.getValue().toString();
                            imax_r_sun_list.add(time);
                        }
                    }else{
                        IMAX_R_sun_AT();
                    }

                    int size =  imax_r_sun_list.size();
                    for(int i= 0 ; i<size; i++) {

                        View sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                        EditText time_ET = sun_DV.findViewById(R.id.st_time_et);
                        Button add_img = sun_DV.findViewById(R.id.st_addB);
                        Button remove_img = sun_DV.findViewById(R.id.st_removeB);

                        time_ET.setText( imax_r_sun_list.get(i));

                        add_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                addNewTime(sun_DV,  imax_r_sunH,  imax_r_sun_list);

                            }
                        });

                        int finalI = i;
                        remove_img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                DatabaseReference sunpost = referenceTime.child(timeSid).child("CONE").child("imax").child("regular").child("sun+hol");
                                sunpost.child(String.valueOf(finalI)).removeValue();
                                Toast.makeText(AdminConeTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                                imax_r_sunH.removeView(sun_DV);

                            }
                        });

                        imax_r_sunH.addView(sun_DV);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



    }

    private void Display_GS(DatabaseReference scheduleTimes, String text) {

        gs_r_mf_list.clear();
        Query r_mf = scheduleTimes.child("CONE").child("gemstone").child("mon-fri").orderByKey();
        r_mf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gs_r_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        gs_r_mf_list.add(time);
                    }
                }else{
                    GS_mf_AT();
                }

                int size = gs_r_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(gs_r_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(mf_DV, gs_r_mf, gs_r_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("CONE").child("gemstone").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminConeTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            time_ET.setText("");
                            gs_r_mf.removeView(mf_DV);

                        }
                    });

                    gs_r_mf.addView(mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        gs_r_sat_list.clear();
        Query r_sat = scheduleTimes.child("CONE").child("gemstone").child("sat").orderByKey();
        r_sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gs_r_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        gs_r_sat_list.add(time);
                    }
                }else{
                    GS_sat_AT();
                }

                int size =  gs_r_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(gs_r_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sat_DV, gs_r_sat, gs_r_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("CONE").child("gemstone").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminConeTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            gs_r_sat.removeView(sat_DV);

                        }
                    });

                    gs_r_sat.addView(sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        gs_r_sun_list.clear();
        Query r_sun = scheduleTimes.child("CONE").child("gemstone").child("sun+hol").orderByKey();
        r_sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                gs_r_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        gs_r_sun_list.add(time);
                    }
                }else{
                    GS_sun_AT();
                }

                int size =  gs_r_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText( gs_r_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sun_DV,  gs_r_sunH,  gs_r_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("CONE").child("gemstone").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminConeTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            gs_r_sunH.removeView(sun_DV);

                        }
                    });

                    gs_r_sunH.addView(sun_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void Display_4DX(DatabaseReference scheduleTimes, String text) {

        c_4dx_mf_list.clear();
        Query r_mf = scheduleTimes.child("CONE").child("4DX").child("mon-fri").orderByKey();
        r_mf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                c_4dx_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        c_4dx_mf_list.add(time);
                    }
                }else{
                    C_4DX_mf_AT();
                }

                int size = c_4dx_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(c_4dx_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(mf_DV, c_4dx_mf, c_4dx_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("CONE").child("4DX").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminConeTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            time_ET.setText("");
                            c_4dx_mf.removeView(mf_DV);

                        }
                    });

                    c_4dx_mf.addView(mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        c_4dx_sat_list.clear();
        Query r_sat = scheduleTimes.child("CONE").child("4DX").child("sat").orderByKey();
        r_sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                c_4dx_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        c_4dx_sat_list.add(time);
                    }
                }else{
                    C_4DX_sat_AT();
                }

                int size =  c_4dx_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(c_4dx_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sat_DV, c_4dx_sat, c_4dx_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("CONE").child("4DX").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminConeTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            c_4dx_sat.removeView(sat_DV);

                        }
                    });

                    c_4dx_sat.addView(sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        c_4dx_sun_list.clear();
        Query r_sun = scheduleTimes.child("CONE").child("4DX").child("sun+hol").orderByKey();
        r_sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                c_4dx_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        c_4dx_sun_list.add(time);
                    }
                }else{
                    C_4DX_sun_AT();
                }

                int size =  c_4dx_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText( c_4dx_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sun_DV,  c_4dx_sunH,  c_4dx_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("CONE").child("4DX").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminConeTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            c_4dx_sunH.removeView(sun_DV);

                        }
                    });

                    c_4dx_sunH.addView(sun_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void addNewTime(View displayV,  LinearLayout layout, List<String> list) {

        displayV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = displayV.findViewById(R.id.st_time_et);
        Button add_img = displayV.findViewById(R.id.st_addB);
        Button remove_img = displayV.findViewById(R.id.st_removeB);

        time_ET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    if(!time_ET.getText().toString().trim().isEmpty()) {
                        list.add(time_ET.getText().toString().trim());

                    }
                    return true;
                }
                return false;
            }
        });


        View finalDisplayV = displayV;
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewTime(finalDisplayV, layout, list);
            }
        });


        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.removeView(finalDisplayV);
            }
        });

        layout.addView(displayV);

    }


}