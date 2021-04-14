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

import com.example.authapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminCC8TS extends AppCompatActivity {

    private LinearLayout t_r_mf, t_r_sat, t_r_sunH, t_3D_mf, t_3D_sat, t_3D_sunH;
    private LinearLayout sp_r_mf, sp_r_sat, sp_r_sunH, sp_3D_mf, sp_3D_sat, sp_3D_sunH;
    private LinearLayout sp_4DX_mf, sp_4DX_sat, sp_4DX_sunH, sp_CXC_mf, sp_CXC_sat, sp_CXC_sunH;
    private EditText timeS_id;
    private Button saveB;

    private List<String> t_r_mf_list, t_r_sat_list, t_r_sun_list;
    private List<String> t_3d_mf_list, t_3d_sat_list, t_3d_sun_list;
    private List<String> sp_r_mf_list, sp_r_sat_list, sp_r_sun_list;
    private List<String> sp_3d_mf_list, sp_3d_sat_list, sp_3d_sun_list;
    private List<String> sp_4dx_mf_list, sp_4dx_sat_list, sp_4dx_sun_list;
    private List<String> sp_cxc_mf_list, sp_cxc_sat_list, sp_cxc_sun_list;

    private int t_r_count = 0,  t_r_count1 = 0, t_r_count2 = 0;
    private int t_3d_count = 0,  t_3d_count1 = 0, t_3d_count2 = 0;
    private int sp_r_count = 0,  sp_r_count1 = 0, sp_r_count2 = 0;
    private int sp_3d_count = 0,  sp_3d_count1 = 0, sp_3d_count2 = 0;
    private int sp_4dx_count = 0,  sp_4dx_count1 = 0, sp_4dx_count2 = 0;
    private int sp_cxc_count = 0,  sp_cxc_count1 = 0, sp_cxc_count2 = 0;
    private DatabaseReference referenceTime;
    private DatabaseReference scheduleTimes;
    private String timeSid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_cc8_ts);

        referenceTime = FirebaseDatabase.getInstance().getReference().child("TimeSchedule");

        init();
        initList();

        String key = getIntent().getExtras().getString("TimeS_Key");

        if(key != null){
            timeS_id.setText(key);
        }
    }

    private void init() {

        timeS_id = findViewById(R.id.adTime_idCONE);
        timeS_id.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    SearchDisplay(timeS_id.getText().toString().trim());

                    return true;
                }

                return false;
            }
        });

        t_r_mf = findViewById(R.id.cc8_t_r_mf_LL);
        t_r_sat = findViewById(R.id.cc8_t_r_sat_LL);
        t_r_sunH = findViewById(R.id.cc8_t_r_sunH_LL);

        t_3D_mf = findViewById(R.id.cc8_t_3d_mf_LL);
        t_3D_sat = findViewById(R.id.cc8_t_3d_sat_LL);
        t_3D_sunH = findViewById(R.id.cc8_t_3d_sunH_LL);

        sp_r_mf = findViewById(R.id.cc8_sp_r_mf_LL);
        sp_r_sat = findViewById(R.id.cc8_sp_r_sat_LL);
        sp_r_sunH = findViewById(R.id.cc8_sp_r_sunH_LL);

        sp_3D_mf  = findViewById(R.id.cc8_sp_3d_mf_LL);
        sp_3D_sat  = findViewById(R.id.cc8_sp_3d_sat_LL);
        sp_3D_sunH  = findViewById(R.id.cc8_sp_3d_sunH_LL);

        sp_4DX_mf = findViewById(R.id.cc8_sp_4dx_mf_LL);
        sp_4DX_sat = findViewById(R.id.cc8_sp_4dx_sat_LL);
        sp_4DX_sunH = findViewById(R.id.cc8_sp_4dx_sunH_LL);

        sp_CXC_mf  = findViewById(R.id.cc8_sp_cxc_mf_LL);
        sp_CXC_sat  = findViewById(R.id.cc8_sp_cxc_sat_LL);
        sp_CXC_sunH  = findViewById(R.id.cc8_sp_cxc_sunH_LL);

        saveB = findViewById(R.id.adTime_saveB);

        saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveTimes();
                ClearAllViews();

            }
        });




    }

    private void initList() {

        t_r_mf_list = new ArrayList<>();
        t_r_sat_list = new ArrayList<>();
        t_r_sun_list = new ArrayList<>();

        t_3d_mf_list = new ArrayList<>();
        t_3d_sat_list = new ArrayList<>();
        t_3d_sun_list = new ArrayList<>();

        sp_r_mf_list = new ArrayList<>();
        sp_r_sat_list = new ArrayList<>();
        sp_r_sun_list = new ArrayList<>();

        sp_3d_mf_list = new ArrayList<>();
        sp_3d_sat_list = new ArrayList<>();
        sp_3d_sun_list = new ArrayList<>();

        sp_4dx_mf_list = new ArrayList<>();
        sp_4dx_sat_list = new ArrayList<>();
        sp_4dx_sun_list = new ArrayList<>();

        sp_cxc_mf_list = new ArrayList<>();
        sp_cxc_sat_list = new ArrayList<>();
        sp_cxc_sun_list = new ArrayList<>();

    }

    private void AddTimes() {
        CC8_T_R_mf_addTimes();
        CC8_T_R_sat_addTimes();
        CC8_T_R_sun_addTimes();

        CC8_T_3D_mf_addTimes();
        CC8_T_3D_sat_addTimes();
        CC8_T_3D_sun_addTimes();

        CC8_SP_R_mf_addTimes();
        CC8_SP_R_sat_addTimes();
        CC8_SP_R_sun_addTimes();

        CC8_SP_3D_mf_addTimes();
        CC8_SP_3D_sat_addTimes();
        CC8_SP_3D_sun_addTimes();

        CC8_SP_4DX_mf_addTimes();
        CC8_SP_4DX_sat_addTimes();
        CC8_SP_4DX_sun_addTimes();

        CC8_SP_CXC_mf_addTimes();
        CC8_SP_CXC_sat_addTimes();
        CC8_SP_CXC_sun_addTimes();
    }

    public void CC8_T_R_mf_addTimes(){

        final View t_r_mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = t_r_mf_View.findViewById(R.id.st_time_et);
        Button add_img = t_r_mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_T_R_mf_addTimes();

            }
        });
        Button remove_img = t_r_mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(t_r_count>1) {
                    t_r_mf.removeView(t_r_mf_View);
                }

            }
        });

        t_r_mf.addView(t_r_mf_View);
        t_r_count++;

    }

    public void CC8_T_R_sat_addTimes(){

        final View t_r_sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = t_r_sat_View.findViewById(R.id.st_time_et);
        Button add_img = t_r_sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_T_R_sat_addTimes();

            }
        });
        Button remove_img = t_r_sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(t_r_count1>1) {
                    t_r_sat.removeView(t_r_sat_View);
                }

            }
        });

        t_r_sat.addView(t_r_sat_View);
        t_r_count1++;

    }

    public void CC8_T_R_sun_addTimes(){

        final View t_r_sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = t_r_sun_View.findViewById(R.id.st_time_et);
        Button add_img = t_r_sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_T_R_sun_addTimes();

            }
        });
        Button remove_img = t_r_sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(t_r_count2>1) {
                    t_r_sunH.removeView(t_r_sun_View);
                }

            }
        });

        t_r_sunH.addView(t_r_sun_View);
        t_r_count2++;

    }

    public void CC8_T_3D_mf_addTimes(){

        final View t_3d_mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = t_3d_mf_View.findViewById(R.id.st_time_et);
        Button add_img = t_3d_mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_T_3D_mf_addTimes();

            }
        });
        Button remove_img = t_3d_mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(t_3d_count>1) {
                    t_3D_mf.removeView(t_3d_mf_View);
                }

            }
        });

        t_3D_mf.addView(t_3d_mf_View);
        t_3d_count++;

    }

    public void CC8_T_3D_sat_addTimes(){

        final View t_3D_sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = t_3D_sat_View.findViewById(R.id.st_time_et);
        Button add_img = t_3D_sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_T_3D_sat_addTimes();

            }
        });
        Button remove_img = t_3D_sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(t_3d_count1>1) {
                    t_3D_sat.removeView(t_3D_sat_View);
                }

            }
        });

        t_3D_sat.addView(t_3D_sat_View);
        t_3d_count1++;

    }

    public void CC8_T_3D_sun_addTimes(){

        final View t_3D_sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = t_3D_sun_View.findViewById(R.id.st_time_et);
        Button add_img = t_3D_sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_T_3D_sun_addTimes();

            }
        });
        Button remove_img = t_3D_sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(t_3d_count2>1) {
                    t_3D_sunH.removeView(t_3D_sun_View);
                }

            }
        });

        t_3D_sunH.addView(t_3D_sun_View);
        t_3d_count2++;

    }

    public void CC8_SP_R_mf_addTimes(){

        final View sp_r_mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sp_r_mf_View.findViewById(R.id.st_time_et);
        Button add_img = sp_r_mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_SP_R_mf_addTimes();

            }
        });
        Button remove_img = sp_r_mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sp_r_count>1) {
                    sp_r_mf.removeView(sp_r_mf_View);
                }

            }
        });

        sp_r_mf.addView(sp_r_mf_View);
        Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
        sp_r_count++;

    }

    public void CC8_SP_R_sat_addTimes(){

        final View sp_r_sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sp_r_sat_View.findViewById(R.id.st_time_et);
        Button add_img = sp_r_sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_SP_R_sat_addTimes();

            }
        });
        Button remove_img = sp_r_sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sp_r_count1>1) {
                    sp_r_sat.removeView(sp_r_sat_View);
                }

            }
        });

        sp_r_sat.addView(sp_r_sat_View);
        sp_r_count1++;

    }

    public void CC8_SP_R_sun_addTimes(){

        final View sp_r_sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sp_r_sun_View.findViewById(R.id.st_time_et);
        Button add_img = sp_r_sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_SP_R_sun_addTimes();

            }
        });
        Button remove_img = sp_r_sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sp_r_count2>1) {
                    sp_r_sunH.removeView(sp_r_sun_View);
                }

            }
        });

        sp_r_sunH.addView(sp_r_sun_View);
        sp_r_count2++;

    }

    public void CC8_SP_3D_mf_addTimes(){

        final View sp_3D_mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sp_3D_mf_View.findViewById(R.id.st_time_et);
        Button add_img = sp_3D_mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_SP_3D_mf_addTimes();

            }
        });
        Button remove_img = sp_3D_mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sp_3d_count>1) {
                    sp_3D_mf.removeView(sp_3D_mf_View);
                }

            }
        });

        sp_3D_mf.addView(sp_3D_mf_View);
        sp_3d_count++;

    }

    public void CC8_SP_3D_sat_addTimes(){

        final View sp_3D_sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sp_3D_sat_View.findViewById(R.id.st_time_et);
        Button add_img = sp_3D_sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_SP_3D_sat_addTimes();

            }
        });
        Button remove_img = sp_3D_sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sp_3d_count1>1) {
                    sp_3D_sat.removeView(sp_3D_sat_View);
                }

            }
        });

        sp_3D_sat.addView(sp_3D_sat_View);
        sp_3d_count1++;

    }

    public void CC8_SP_3D_sun_addTimes(){

        final View sp_3D_sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sp_3D_sun_View.findViewById(R.id.st_time_et);
        Button add_img = sp_3D_sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_SP_3D_sun_addTimes();

            }
        });
        Button remove_img = sp_3D_sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sp_3d_count2>1) {
                    sp_3D_sunH.removeView(sp_3D_sun_View);
                }

            }
        });

        sp_3D_sunH.addView(sp_3D_sun_View);
        sp_3d_count2++;

    }

    public void CC8_SP_4DX_mf_addTimes(){

        final View sp_4dx_mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sp_4dx_mf_View.findViewById(R.id.st_time_et);
        Button add_img = sp_4dx_mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_SP_4DX_mf_addTimes();

            }
        });
        Button remove_img = sp_4dx_mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sp_4dx_count>1) {
                    sp_4DX_mf.removeView(sp_4dx_mf_View);
                }

            }
        });

        sp_4DX_mf.addView(sp_4dx_mf_View);
        sp_4dx_count++;

    }

    public void CC8_SP_4DX_sat_addTimes(){

        final View sp_4dx_sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sp_4dx_sat_View.findViewById(R.id.st_time_et);
        Button add_img = sp_4dx_sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_SP_4DX_sat_addTimes();

            }
        });
        Button remove_img = sp_4dx_sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sp_4dx_count1>1) {
                    sp_4DX_sat.removeView(sp_4dx_sat_View);
                }

            }
        });

        sp_4DX_sat.addView(sp_4dx_sat_View);
        sp_4dx_count1++;

    }

    public void CC8_SP_4DX_sun_addTimes(){

        final View sp_4dx_sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sp_4dx_sun_View.findViewById(R.id.st_time_et);
        Button add_img = sp_4dx_sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_SP_4DX_sun_addTimes();

            }
        });
        Button remove_img = sp_4dx_sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sp_4dx_count2>1) {
                    sp_4DX_sunH.removeView(sp_4dx_sun_View);
                }

            }
        });

        sp_4DX_sunH.addView(sp_4dx_sun_View);
        sp_4dx_count2++;

    }

    public void CC8_SP_CXC_mf_addTimes(){

        final View sp_cxc_mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sp_cxc_mf_View.findViewById(R.id.st_time_et);
        Button add_img = sp_cxc_mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_SP_CXC_mf_addTimes();

            }
        });
        Button remove_img = sp_cxc_mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sp_cxc_count>1) {
                    sp_CXC_mf.removeView(sp_cxc_mf_View);
                }

            }
        });

        sp_CXC_mf.addView(sp_cxc_mf_View);
        sp_cxc_count++;

    }

    public void CC8_SP_CXC_sat_addTimes(){

        final View sp_cxc_sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sp_cxc_sat_View.findViewById(R.id.st_time_et);
        Button add_img = sp_cxc_sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_SP_CXC_sat_addTimes();

            }
        });
        Button remove_img = sp_cxc_sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sp_cxc_count1>1) {
                    sp_CXC_sat.removeView(sp_cxc_sat_View);
                }

            }
        });

        sp_CXC_sat.addView(sp_cxc_sat_View);
        sp_cxc_count1++;

    }

    public void CC8_SP_CXC_sun_addTimes(){

        final View sp_cxc_sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sp_cxc_sun_View.findViewById(R.id.st_time_et);
        Button add_img = sp_cxc_sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CC8_SP_CXC_sun_addTimes();

            }
        });
        Button remove_img = sp_cxc_sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sp_cxc_count2>1) {
                    sp_CXC_sunH.removeView(sp_cxc_sun_View);
                }

            }
        });

        sp_CXC_sunH.addView(sp_cxc_sun_View);
        sp_cxc_count2++;

    }

    public void SaveTimes(){


        timeSid = timeS_id.getText().toString().trim();

        CC8_T_R_saveTimes(timeSid);
        CC8_T_3D_saveTimes(timeSid);
        CC8_SP_R_saveTimes(timeSid);
        CC8_SP_3D_saveTimes(timeSid);
        CC8_SP_4DX_saveTimes(timeSid);
        CC8_SP_CXC_saveTimes(timeSid);

        ClearList();
        ClearAllViews();

        Toast.makeText(this, "Information was saved!", Toast.LENGTH_SHORT).show();


    }

    public void CC8_T_R_saveTimes(String timeSid) {

        t_r_mf_list.clear();
        t_r_sat_list.clear();
        t_r_sun_list.clear();

        for(int i=0; i<t_r_mf.getChildCount(); i++){
            View mf_V = t_r_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                t_r_mf_list.add(t);
            }
        }

        if(t_r_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("CC8").child("trincity").child("regular").child("mon-fri");
            for(int i= 0; i<t_r_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(t_r_mf_list.get(i));
            }

        }

        t_r_mf.removeAllViewsInLayout();


        for(int i=0; i<t_r_sat.getChildCount(); i++){
            View sat_V = t_r_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                t_r_sat_list.add(t);
            }

        }

        if(t_r_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("CC8").child("trincity").child("regular").child("sat");
            for(int i= 0; i<t_r_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(t_r_sat_list.get(i));
            }

        }

        t_r_sat.removeAllViewsInLayout();

        for(int i=0; i<t_r_sunH.getChildCount(); i++){
            View sat_V = t_r_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                t_r_sun_list.add(t);
            }

        }

        if(t_r_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("CC8").child("trincity").child("regular").child("sun+hol");
            for(int i= 0; i<t_r_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(t_r_sun_list.get(i));
            }

        }
        t_r_sunH.removeAllViewsInLayout();
    }

    public void CC8_T_3D_saveTimes(String timeSid) {

        t_3d_mf_list.clear();
        t_3d_sat_list.clear();
        t_3d_sun_list.clear();

        for(int i=0; i<t_3D_mf.getChildCount(); i++){
            View mf_V = t_3D_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                t_3d_mf_list.add(t);
            }
        }

        if(t_3D_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("CC8").child("trincity").child("3D").child("mon-fri");
            for(int i= 0; i<t_3d_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(t_3d_mf_list.get(i));
            }
        }

        t_3D_mf.removeAllViewsInLayout();


        for(int i=0; i<t_3D_sat.getChildCount(); i++){
            View sat_V = t_3D_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                t_3d_sat_list.add(t);
            }
        }

        if(t_3D_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("CC8").child("trincity").child("3D").child("sat");
            for(int i= 0; i<t_3d_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(t_3d_sat_list.get(i));
            }
        }

        t_3D_sat.removeAllViewsInLayout();

        for(int i=0; i<t_3D_sunH.getChildCount(); i++){
            View sat_V = t_3D_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                t_3d_sun_list.add(t);
            }
        }

        if(t_3D_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("CC8").child("trincity").child("3D").child("sun+hol");
            for(int i= 0; i<t_3d_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(t_3d_sun_list.get(i));
            }
        }
        t_3D_sunH.removeAllViewsInLayout();

    }

    public void CC8_SP_R_saveTimes(String timeSid) {

        sp_r_mf_list.clear();
        sp_r_sat_list.clear();
        sp_r_sun_list.clear();

        for(int i=0; i<sp_r_mf.getChildCount(); i++){
            View mf_V = sp_r_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sp_r_mf_list.add(t);
            }
        }

        if(sp_r_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("CC8").child("southpark").child("regular").child("mon-fri");
            for(int i= 0; i<sp_r_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(sp_r_mf_list.get(i));
            }
        }

        sp_r_mf.removeAllViewsInLayout();

        for(int i=0; i<sp_r_sat.getChildCount(); i++){
            View sat_V = sp_r_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sp_r_sat_list.add(t);
            }
        }

        if(sp_r_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("CC8").child("southpark").child("regular").child("sat");
            for(int i= 0; i<sp_r_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(sp_r_sat_list.get(i));
            }
        }

        sp_r_sat.removeAllViewsInLayout();

        for(int i=0; i<sp_r_sunH.getChildCount(); i++){
            View sat_V = sp_r_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sp_r_sun_list.add(t);
            }
        }

        if(sp_r_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("CC8").child("southpark").child("regular").child("sun+hol");
            for(int i= 0; i<sp_r_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(sp_r_sun_list.get(i));
            }
        }

        sp_r_sunH.removeAllViewsInLayout();
    }

    public void CC8_SP_3D_saveTimes(String timeSid) {

        sp_3d_mf_list.clear();
        sp_3d_sat_list.clear();
        sp_3d_sun_list.clear();

        for(int i=0; i<sp_3D_mf.getChildCount(); i++){
            View mf_V = sp_3D_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sp_3d_mf_list.add(t);
            }
        }

        if(sp_3D_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("CC8").child("southpark").child("3D").child("mon-fri");
            for(int i= 0; i<sp_3d_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(sp_3d_mf_list.get(i));
            }
        }
        sp_3D_mf.removeAllViews();


        for(int i=0; i<sp_3D_sat.getChildCount(); i++){
            View sat_V = sp_3D_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sp_3d_sat_list.add(t);
            }
        }

        if(sp_3D_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("CC8").child("southpark").child("3D").child("sat");
            for(int i= 0; i<sp_3d_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(sp_3d_sat_list.get(i));
            }
        }

        sp_3D_sat.removeAllViews();

        for(int i=0; i<sp_3D_sunH.getChildCount(); i++){
            View sat_V = sp_3D_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sp_3d_sun_list.add(t);
            }
        }

        if(sp_3D_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("CC8").child("southpark").child("3D").child("sun+hol");
            for(int i= 0; i<sp_3d_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(sp_3d_sun_list.get(i));
            }
        }
        sp_3D_sunH.removeAllViews();
    }

    public void CC8_SP_4DX_saveTimes(String timeSid) {

        sp_4dx_mf_list.clear();
        sp_4dx_sat_list.clear();
        sp_4dx_sun_list.clear();

        for(int i=0; i<sp_4DX_mf.getChildCount(); i++){
            View mf_V = sp_4DX_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sp_4dx_mf_list.add(t);
            }
        }

        if(sp_4DX_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("CC8").child("southpark").child("4DX").child("mon-fri");
            for(int i= 0; i<sp_4dx_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(sp_4dx_mf_list.get(i));
            }
        }
        sp_4DX_mf.removeAllViews();

        for(int i=0; i<sp_4DX_sat.getChildCount(); i++){
            View sat_V = sp_4DX_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sp_4dx_sat_list.add(t);
            }

        }

        if(sp_4DX_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("CC8").child("southpark").child("4DX").child("sat");
            for(int i= 0; i<sp_4dx_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(sp_4dx_sat_list.get(i));
            }
        }

        sp_4DX_sat.removeAllViews();

        for(int i=0; i<sp_4DX_sunH.getChildCount(); i++){
            View sat_V = sp_4DX_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sp_4dx_sun_list.add(t);
            }

        }

        if(sp_4DX_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("CC8").child("southpark").child("4DX").child("sun+hol");
            for(int i= 0; i<sp_4dx_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(sp_4dx_sun_list.get(i));
            }
        }
        sp_4DX_sunH.removeAllViews();

    }

    public void CC8_SP_CXC_saveTimes(String timeSid) {

        sp_cxc_mf_list.clear();
        sp_cxc_sat_list.clear();
        sp_cxc_sun_list.clear();

        for(int i=0; i<sp_CXC_mf.getChildCount(); i++){
            View mf_V = sp_CXC_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sp_cxc_mf_list.add(t);
            }

        }

        if(sp_CXC_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("CC8").child("southpark").child("CXC").child("mon-fri");
            for(int i= 0; i<sp_cxc_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(sp_cxc_mf_list.get(i));
            }
        }
        sp_CXC_mf.removeAllViews();

        for(int i=0; i<sp_CXC_sat.getChildCount(); i++){
            View sat_V = sp_CXC_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sp_cxc_sat_list.add(t);
            }
        }

        if(sp_CXC_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("CC8").child("southpark").child("CXC").child("sat");
            for(int i= 0; i<sp_cxc_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(sp_cxc_sat_list.get(i));
            }
        }

        sp_CXC_sat.removeAllViews();

        for(int i=0; i<sp_CXC_sunH.getChildCount(); i++){
            View sat_V = sp_CXC_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sp_cxc_sun_list.add(t);
            }
        }

        if(sp_CXC_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("CC8").child("southpark").child("CXC").child("sun+hol");
            for(int i= 0; i<sp_cxc_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(sp_cxc_sun_list.get(i));
            }
        }
        sp_CXC_sunH.removeAllViews();

    }

    public void SearchDisplay(String text){

        if(!text.isEmpty()){
            scheduleTimes =  FirebaseDatabase.getInstance().getReference("TimeSchedule")
                    .child(text);
            scheduleTimes.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        ClearAllViews();
                        Display_T_R(scheduleTimes, text);
                        Display_T_3D(scheduleTimes, text);
                        Display_SP_R(scheduleTimes, text);
                        Display_SP_3D(scheduleTimes, text);
                        Display_SP_CXC(scheduleTimes, text);
                        Display_SP_4DX(scheduleTimes, text);

                    }else{

                        Toast.makeText(AdminCC8TS.this, "Key does not exist!", Toast.LENGTH_SHORT).show();
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

    public void Display_T_R(DatabaseReference scheduleTimes, String timeSid){

        t_r_mf_list.clear();
        Query display_cc8_t_r_mf = scheduleTimes.child("CC8").child("trincity").child("regular").child("mon-fri").orderByKey();
        display_cc8_t_r_mf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t_r_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        t_r_mf_list.add(time);
                    }
                }else{
                    CC8_T_R_mf_addTimes();
                }

                int size = t_r_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View t_r_mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = t_r_mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = t_r_mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = t_r_mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(t_r_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(t_r_mf_DV, t_r_mf, t_r_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("CC8").child("trincity").child("regular").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            time_ET.setText("");
                            t_r_mf.removeView(t_r_mf_DV);

                        }
                    });

                    t_r_mf.addView(t_r_mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        t_r_sat_list.clear();
        Query display_cc8_t_r_sat = scheduleTimes.child("CC8").child("trincity").child("regular").child("sat").orderByKey();
        display_cc8_t_r_sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t_r_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        t_r_sat_list.add(time);
                    }
                }else{
                    CC8_T_R_sat_addTimes();
                }

                int size = t_r_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View t_r_sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = t_r_sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = t_r_sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = t_r_sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(t_r_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(t_r_sat_DV, t_r_sat, t_r_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("CC8").child("trincity").child("regular").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            t_r_sat.removeView(t_r_sat_DV);

                        }
                    });

                    t_r_sat.addView(t_r_sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        t_r_sun_list.clear();
        Query display_cc8_t_r_sun = scheduleTimes.child("CC8").child("trincity").child("regular").child("sun+hol").orderByKey();
        display_cc8_t_r_sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t_r_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        t_r_sun_list.add(time);
                    }
                }else{
                    CC8_T_R_sun_addTimes();
                }

                int size = t_r_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View t_r_sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = t_r_sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = t_r_sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = t_r_sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(t_r_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(t_r_sun_DV, t_r_sunH, t_r_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("CC8").child("trincity").child("regular").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            t_r_sunH.removeView(t_r_sun_DV);

                        }
                    });

                    t_r_sunH.addView(t_r_sun_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void Display_T_3D(DatabaseReference scheduleTimes, String timeSid){

        t_3d_mf_list.clear();
        Query display_cc8_t_3d_mf = scheduleTimes.child("CC8").child("trincity").child("3D").child("mon-fri").orderByKey();
        display_cc8_t_3d_mf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t_3d_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        t_3d_mf_list.add(time);
                    }
                }else{
                    CC8_T_3D_mf_addTimes();
                }

                int size = t_3d_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View t_3d_mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = t_3d_mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = t_3d_mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = t_3d_mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(t_3d_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(t_3d_mf_DV, t_3D_mf, t_3d_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("CC8").child("trincity").child("3D").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            t_3D_mf.removeView(t_3d_mf_DV);

                        }
                    });

                    t_3D_mf.addView(t_3d_mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        t_3d_sat_list.clear();
        Query display_cc8_t_3d_sat = scheduleTimes.child("CC8").child("trincity").child("3D").child("sat").orderByKey();
        display_cc8_t_3d_sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t_3d_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        t_3d_sat_list.add(time);
                    }
                }else{
                    CC8_T_3D_sat_addTimes();
                }

                int size = t_3d_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View t_3d_sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = t_3d_sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = t_3d_sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = t_3d_sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(t_3d_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(t_3d_sat_DV, t_3D_sat, t_3d_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("CC8").child("trincity").child("3D").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            t_3D_sat.removeView(t_3d_sat_DV);

                        }
                    });

                    t_3D_sat.addView(t_3d_sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        t_3d_sun_list.clear();
        Query display_cc8_t_3d_sun = scheduleTimes.child("CC8").child("trincity").child("3D").child("sun+hol").orderByKey();
        display_cc8_t_3d_sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                t_3d_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        t_3d_sun_list.add(time);
                    }
                }else{
                    CC8_T_3D_sun_addTimes();
                }

                int size = t_3d_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View t_3d_sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = t_3d_sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = t_3d_sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = t_3d_sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(t_3d_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(t_3d_sun_DV, t_3D_sunH, t_3d_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("CC8").child("trincity").child("3D").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            t_3D_sunH.removeView(t_3d_sun_DV);

                        }
                    });

                    t_3D_sunH.addView(t_3d_sun_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void Display_SP_R(DatabaseReference scheduleTimes, String timeSid){

        sp_r_mf_list.clear();
        Query display_cc8_sp_r = scheduleTimes.child("CC8").child("southpark").child("regular").child("mon-fri").orderByKey();
        display_cc8_sp_r.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sp_r_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sp_r_mf_list.add(time);
                    }
                }else{
                    CC8_SP_R_mf_addTimes();
                }

                int size = sp_r_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sp_r_mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sp_r_mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = sp_r_mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = sp_r_mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sp_r_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sp_r_mf_DV, sp_r_mf, sp_r_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("CC8").child("southpark").child("regular").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sp_r_mf.removeView(sp_r_mf_DV);

                        }
                    });

                    sp_r_mf.addView(sp_r_mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sp_r_sat_list.clear();
        Query display_cc8_sp_r_sat = scheduleTimes.child("CC8").child("southpark").child("regular").child("sat").orderByKey();
        display_cc8_sp_r_sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sp_r_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sp_r_sat_list.add(time);
                    }
                }else{
                    CC8_SP_R_sat_addTimes();
                }

                int size = sp_r_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sp_r_sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sp_r_sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = sp_r_sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = sp_r_sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sp_r_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sp_r_sat_DV, sp_r_sat, sp_r_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("CC8").child("southpark").child("regular").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sp_r_sat.removeView(sp_r_sat_DV);

                        }
                    });

                    sp_r_sat.addView(sp_r_sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sp_r_sun_list.clear();
        Query display_cc8_sp_r_sun = scheduleTimes.child("CC8").child("southpark").child("regular").child("sun+hol").orderByKey();
        display_cc8_sp_r_sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sp_r_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sp_r_sun_list.add(time);
                    }
                }else{
                    CC8_SP_R_sun_addTimes();
                }

                int size = sp_r_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sp_r_sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sp_r_sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = sp_r_sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = sp_r_sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sp_r_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sp_r_sun_DV, sp_r_sunH, sp_r_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("CC8").child("southpark").child("regular").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sp_r_sunH.removeView(sp_r_sun_DV);

                        }
                    });

                    sp_r_sunH.addView(sp_r_sun_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Display_SP_3D(DatabaseReference scheduleTimes, String timeSid){

        sp_3d_mf_list.clear();
        Query display_cc8_sp_3d_mf = scheduleTimes.child("CC8").child("southpark").child("3D").child("mon-fri").orderByKey();
        display_cc8_sp_3d_mf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sp_3d_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sp_3d_mf_list.add(time);
                    }
                }else{
                    CC8_SP_3D_mf_addTimes();
                }

                int size = sp_3d_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sp_3d_mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sp_3d_mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = sp_3d_mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = sp_3d_mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sp_3d_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sp_3d_mf_DV, sp_3D_mf, sp_3d_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("CC8").child("southpark").child("3D").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sp_3D_mf.removeView(sp_3d_mf_DV);

                        }
                    });

                    sp_3D_mf.addView(sp_3d_mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sp_3d_sat_list.clear();
        Query display_cc8_sp_3d_sat = scheduleTimes.child("CC8").child("southpark").child("3D").child("sat").orderByKey();
        display_cc8_sp_3d_sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sp_3d_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sp_3d_sat_list.add(time);
                    }
                }else{
                    CC8_SP_3D_sat_addTimes();
                }

                int size = sp_3d_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sp_3d_sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sp_3d_sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = sp_3d_sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = sp_3d_sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sp_3d_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sp_3d_sat_DV, sp_3D_sat, sp_3d_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("CC8").child("southpark").child("3D").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sp_3D_sat.removeView(sp_3d_sat_DV);

                        }
                    });

                    sp_3D_sat.addView(sp_3d_sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sp_3d_sun_list.clear();
        Query display_cc8_sp_3d_sun = scheduleTimes.child("CC8").child("southpark").child("3D").child("sun+hol").orderByKey();
        display_cc8_sp_3d_sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sp_3d_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sp_3d_sun_list.add(time);
                    }
                }else{
                    CC8_SP_3D_sun_addTimes();
                }

                int size = sp_3d_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sp_3d_sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sp_3d_sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = sp_3d_sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = sp_3d_sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sp_3d_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sp_3d_sun_DV, sp_3D_sunH, sp_3d_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("CC8").child("southpark").child("3D").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sp_3D_sunH.removeView(sp_3d_sun_DV);

                        }
                    });

                    sp_3D_sunH.addView(sp_3d_sun_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void Display_SP_CXC(DatabaseReference scheduleTimes, String timeSid){

        sp_cxc_mf_list.clear();
        Query display_cc8_sp_cxc_mf = scheduleTimes.child("CC8").child("southpark").child("CXC").child("mon-fri").orderByKey();
        display_cc8_sp_cxc_mf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sp_cxc_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sp_cxc_mf_list.add(time);
                    }
                }else{
                    CC8_SP_CXC_mf_addTimes();
                }

                int size = sp_cxc_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sp_cxc_mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sp_cxc_mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = sp_cxc_mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = sp_cxc_mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sp_cxc_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sp_cxc_mf_DV, sp_CXC_mf, sp_cxc_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("CC8").child("southpark").child("CXC").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sp_CXC_mf.removeView(sp_cxc_mf_DV);

                        }
                    });

                    sp_CXC_mf.addView(sp_cxc_mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sp_cxc_sat_list.clear();
        Query display_cc8_sp_cxc_sat = scheduleTimes.child("CC8").child("southpark").child("CXC").child("sat").orderByKey();
        display_cc8_sp_cxc_sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sp_cxc_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sp_cxc_sat_list.add(time);
                    }
                }else{
                    CC8_SP_CXC_sat_addTimes();
                }

                int size = sp_cxc_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sp_cxc_sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sp_cxc_sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = sp_cxc_sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = sp_cxc_sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sp_cxc_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sp_cxc_sat_DV, sp_CXC_sat, sp_cxc_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("CC8").child("southpark").child("CXC").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sp_CXC_sat.removeView(sp_cxc_sat_DV);

                        }
                    });

                    sp_CXC_sat.addView(sp_cxc_sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sp_cxc_sun_list.clear();
        Query display_cc8_sp_cxc_sun = scheduleTimes.child("CC8").child("southpark").child("CXC").child("sun+hol").orderByKey();
        display_cc8_sp_cxc_sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sp_cxc_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sp_cxc_sun_list.add(time);
                    }
                }else{
                    CC8_SP_CXC_sun_addTimes();
                }

                int size = sp_cxc_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sp_cxc_sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sp_cxc_sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = sp_cxc_sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = sp_cxc_sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sp_cxc_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sp_cxc_sun_DV, sp_CXC_sunH, sp_cxc_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("CC8").child("southpark").child("CXC").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sp_CXC_sunH.removeView(sp_cxc_sun_DV);

                        }
                    });

                    sp_CXC_sunH.addView(sp_cxc_sun_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void Display_SP_4DX(DatabaseReference scheduleTimes, String timeSid){

        sp_4dx_mf_list.clear();
        Query display_cc8_sp_4dx_mf = scheduleTimes.child("CC8").child("southpark").child("4DX").child("mon-fri").orderByKey();
        display_cc8_sp_4dx_mf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sp_4dx_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sp_4dx_mf_list.add(time);
                    }
                }else{
                    CC8_SP_4DX_mf_addTimes();
                }

                int size = sp_4dx_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sp_4dx_mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sp_4dx_mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = sp_4dx_mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = sp_4dx_mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sp_4dx_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sp_4dx_mf_DV, sp_4DX_mf, sp_4dx_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("CC8").child("southpark").child("4DX").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sp_4DX_mf.removeView(sp_4dx_mf_DV);

                        }
                    });

                    sp_4DX_mf.addView(sp_4dx_mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sp_4dx_sat_list.clear();
        Query display_cc8_sp_4dx_sat = scheduleTimes.child("CC8").child("southpark").child("4DX").child("sat").orderByKey();
        display_cc8_sp_4dx_sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sp_4dx_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sp_4dx_sat_list.add(time);
                    }
                }else{
                    CC8_SP_4DX_sat_addTimes();
                }

                int size = sp_4dx_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sp_4dx_sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sp_4dx_sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = sp_4dx_sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = sp_4dx_sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sp_4dx_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sp_4dx_sat_DV, sp_4DX_sat, sp_4dx_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("CC8").child("southpark").child("4DX").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sp_4DX_sat.removeView(sp_4dx_sat_DV);

                        }
                    });

                    sp_4DX_sat.addView(sp_4dx_sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sp_4dx_sun_list.clear();
        Query display_cc8_sp_4dx_sun = scheduleTimes.child("CC8").child("southpark").child("4DX").child("sun+hol").orderByKey();
        display_cc8_sp_4dx_sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sp_4dx_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sp_4dx_sun_list.add(time);
                    }
                }else{
                    CC8_SP_4DX_sun_addTimes();
                }

                int size = sp_4dx_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sp_4dx_sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sp_4dx_sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = sp_4dx_sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = sp_4dx_sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sp_4dx_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sp_4dx_sun_DV, sp_4DX_sunH, sp_4dx_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("CC8").child("southpark").child("4DX").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminCC8TS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sp_4DX_sunH.removeView(sp_4dx_sun_DV);

                        }
                    });

                    sp_4DX_sunH.addView(sp_4dx_sun_DV);

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

    public void ClearAllViews(){

        t_r_mf.removeAllViews();
        t_r_sat.removeAllViews();
        t_r_sunH.removeAllViews();

        t_3D_mf.removeAllViews();
        t_3D_sat.removeAllViews();
        t_3D_sunH.removeAllViews();

        sp_r_mf.removeAllViews();
        sp_r_sat.removeAllViews();
        sp_r_sunH.removeAllViews();

        sp_3D_mf.removeAllViews();
        sp_3D_sat.removeAllViews();
        sp_3D_sunH.removeAllViews();

        sp_4DX_mf.removeAllViews();
        sp_4DX_sat.removeAllViews();
        sp_4DX_sunH.removeAllViews();

        sp_CXC_mf.removeAllViews();
        sp_CXC_sat.removeAllViews();
        sp_CXC_sunH.removeAllViews();

    }

    public void ClearList(){
        t_r_mf_list.clear();
        t_r_sat_list.clear();
        t_r_sun_list.clear();

        t_3d_mf_list.clear();
        t_3d_sat_list.clear();
        t_3d_sun_list.clear();

        sp_r_mf_list.clear();
        sp_r_sat_list.clear();
        sp_r_sun_list.clear();

        sp_3d_mf_list.clear();
        sp_3d_sat_list.clear();
        sp_3d_sun_list.clear();

        sp_4dx_mf_list.clear();
        sp_4dx_sat_list.clear();
        sp_4dx_sun_list.clear();

        sp_cxc_mf_list.clear();
        sp_cxc_sat_list.clear();
        sp_cxc_sun_list.clear();
    }

}