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

public class AdminMTTS extends AppCompatActivity {

    private LinearLayout pos_r_mf, pos_r_sat, pos_r_sunH, pos_3D_mf, pos_3D_sat, pos_3D_sunH;
    private LinearLayout chag_r_mf, chag_r_sat, chag_r_sunH, chag_3D_mf, chag_3D_sat, chag_3D_sunH;
    private LinearLayout sdo_r_mf, sdo_r_sat, sdo_r_sunH, sdo_3D_mf, sdo_3D_sat, sdo_3D_sunH;
    private LinearLayout tgo_r_mf, tgo_r_sat, tgo_r_sunH;
    private EditText timeS_id;
    private Button mt_saveB;

    private List<String> pos_r_mf_list, pos_r_sat_list, pos_r_sun_list;
    private List<String> pos_3d_mf_list, pos_3d_sat_list, pos_3d_sun_list;

    private List<String> chag_r_mf_list, chag_r_sat_list, chag_r_sun_list;
    private List<String> chag_3d_mf_list, chag_3d_sat_list, chag_3d_sun_list;

    private List<String> sdo_r_mf_list, sdo_r_sat_list, sdo_r_sun_list;
    private List<String> sdo_3d_mf_list, sdo_3d_sat_list, sdo_3d_sun_list;

    private List<String> tgo_r_mf_list, tgo_r_sat_list, tgo_r_sun_list;

    private int pos_r_count = 0,  pos_r_count1 = 0, pos_r_count2 = 0;
    private int pos_3d_count = 0,  pos_3d_count1 = 0, pos_3d_count2 = 0;

    private int chag_r_count = 0,  chag_r_count1 = 0, chag_r_count2 = 0;
    private int chag_3d_count = 0,  chag_3d_count1 = 0, chag_3d_count2 = 0;

    private int sdo_r_count = 0,  sdo_r_count1 = 0, sdo_r_count2 = 0;
    private int sdo_3d_count = 0,  sdo_3d_count1 = 0, sdo_3d_count2 = 0;

    private int tgo_r_count = 0,  tgo_r_count1 = 0, tgo_r_count2 = 0;


    private DatabaseReference referenceTime;
    private DatabaseReference scheduleTimes;
    private String timeSid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_mt_ts);

        referenceTime = FirebaseDatabase.getInstance().getReference().child("TimeSchedule");

        init();
        initList();

        String key = getIntent().getExtras().getString("TimeS_Key");

        if(key != null){
            timeS_id.setText(key);
        }
    }

    private void init() {

        timeS_id = findViewById(R.id.adTime_id);
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


        pos_r_mf = findViewById(R.id.mt_pos_r_mf_LL);
        pos_r_sat = findViewById(R.id.mt_pos_r_sat_LL);
        pos_r_sunH = findViewById(R.id.mt_pos_r_sunH_LL);

        pos_3D_mf = findViewById(R.id.mt_pos_3d_mf_LL);
        pos_3D_sat = findViewById(R.id.mt_pos_3d_sat_LL);
        pos_3D_sunH = findViewById(R.id.mt_pos_3d_sunH_LL);

        chag_r_mf = findViewById(R.id.mt_chag_r_mf_LL);
        chag_r_sat = findViewById(R.id.mt_chag_r_sat_LL);
        chag_r_sunH = findViewById(R.id.mt_chag_r_sunH_LL);

        chag_3D_mf = findViewById(R.id.mt_chag_3d_mf_LL);
        chag_3D_sat = findViewById(R.id.mt_chag_3d_sat_LL);
        chag_3D_sunH = findViewById(R.id.mt_chag_3d_sunH_LL);

        sdo_r_mf = findViewById(R.id.mt_sdo_r_mf_LL);
        sdo_r_sat = findViewById(R.id.mt_sdo_r_sat_LL);
        sdo_r_sunH = findViewById(R.id.mt_sdo_r_sunH_LL);

        sdo_3D_mf = findViewById(R.id.mt_sdo_3d_mf_LL);
        sdo_3D_sat = findViewById(R.id.mt_sdo_3d_sat_LL);
        sdo_3D_sunH = findViewById(R.id.mt_sdo_3d_sunH_LL);

        tgo_r_mf = findViewById(R.id.mt_tgo_r_mf_LL);
        tgo_r_sat = findViewById(R.id.mt_tgo_r_sat_LL);
        tgo_r_sunH = findViewById(R.id.mt_tgo_r_sunH_LL);

        mt_saveB = findViewById(R.id.adTime_saveBCONE);

        mt_saveB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveTimes();
                ClearAllViews();

            }
        });


    }

    private void initList() {

        pos_r_mf_list = new ArrayList<>();
        pos_r_sat_list = new ArrayList<>();
        pos_r_sun_list = new ArrayList<>();

        pos_3d_mf_list = new ArrayList<>();
        pos_3d_sat_list = new ArrayList<>();
        pos_3d_sun_list = new ArrayList<>();

        chag_r_mf_list = new ArrayList<>();
        chag_r_sat_list = new ArrayList<>();
        chag_r_sun_list = new ArrayList<>();

        chag_3d_mf_list = new ArrayList<>();
        chag_3d_sat_list = new ArrayList<>();
        chag_3d_sun_list = new ArrayList<>();

        sdo_r_mf_list = new ArrayList<>();
        sdo_r_sat_list = new ArrayList<>();
        sdo_r_sun_list = new ArrayList<>();

        sdo_3d_mf_list = new ArrayList<>();
        sdo_3d_sat_list = new ArrayList<>();
        sdo_3d_sun_list = new ArrayList<>();

        tgo_r_mf_list = new ArrayList<>();
        tgo_r_sat_list = new ArrayList<>();
        tgo_r_sun_list = new ArrayList<>();


    }

    private void AddTimes(){

        POS_R_mf_AT();
        POS_R_sat_AT();
        POS_R_sun_AT();

        POS_3D_mf_AT();
        POS_3D_sat_AT();
        POS_3D_sun_AT();

        CHAG_R_mf_AT();
        CHAG_R_sat_AT();
        CHAG_R_sun_AT();

        CHAG_3D_mf_AT();
        CHAG_3D_sat_AT();
        CHAG_3D_sun_AT();

        SDO_R_mf_AT();
        SDO_R_sat_AT();
        SDO_R_sun_AT();

        SDO_3D_mf_AT();
        SDO_3D_sat_AT();
        SDO_3D_sun_AT();

        TGO_R_mf_AT();
        TGO_R_sat_AT();
        TGO_R_sun_AT();

    }

    public void POS_R_mf_AT(){

        final View mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = mf_View.findViewById(R.id.st_time_et);
        Button add_img = mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POS_R_mf_AT();

            }
        });
        Button remove_img = mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pos_r_count>1) {
                    pos_r_mf.removeView(mf_View);
                }

            }
        });

        pos_r_mf.addView(mf_View);
        pos_r_count++;

    }

    public void POS_R_sat_AT(){

        final View sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sat_View.findViewById(R.id.st_time_et);
        Button add_img = sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POS_R_sat_AT();

            }
        });
        Button remove_img = sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pos_r_count1>1) {
                    pos_r_sat.removeView(sat_View);
                }

            }
        });

        pos_r_sat.addView(sat_View);
        pos_r_count1++;

    }

    public void POS_R_sun_AT(){

        final View sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sun_View.findViewById(R.id.st_time_et);
        Button add_img = sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POS_R_sun_AT();

            }
        });
        Button remove_img = sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pos_r_count2>1) {
                    pos_r_sunH.removeView(sun_View);
                }

            }
        });

        pos_r_sunH.addView(sun_View);
        pos_r_count2++;

    }

    public void POS_3D_mf_AT(){

        final View mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = mf_View.findViewById(R.id.st_time_et);
        Button add_img = mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POS_3D_mf_AT();

            }
        });
        Button remove_img = mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pos_3d_count>1) {
                    pos_3D_mf.removeView(mf_View);
                }

            }
        });

        pos_3D_mf.addView(mf_View);
        pos_3d_count++;

    }

    public void POS_3D_sat_AT(){

        final View sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sat_View.findViewById(R.id.st_time_et);
        Button add_img = sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POS_3D_sat_AT();

            }
        });
        Button remove_img = sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pos_3d_count1>1) {
                    pos_3D_sat.removeView(sat_View);
                }

            }
        });

        pos_3D_sat.addView(sat_View);
        pos_3d_count1++;

    }

    public void POS_3D_sun_AT(){

        final View sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sun_View.findViewById(R.id.st_time_et);
        Button add_img = sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                POS_3D_sun_AT();

            }
        });
        Button remove_img = sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pos_3d_count2>1) {
                    pos_3D_sunH.removeView(sun_View);
                }

            }
        });

        pos_3D_sunH.addView(sun_View);
        pos_3d_count2++;

    }

    public void CHAG_R_mf_AT(){

        final View r_mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = r_mf_View.findViewById(R.id.st_time_et);
        Button add_img = r_mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHAG_R_mf_AT();

            }
        });
        Button remove_img = r_mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chag_r_count>1) {
                    chag_r_mf.removeView(r_mf_View);
                }

            }
        });

        chag_r_mf.addView(r_mf_View);
        chag_r_count++;

    }

    public void CHAG_R_sat_AT(){

        final View r_sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = r_sat_View.findViewById(R.id.st_time_et);
        Button add_img = r_sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHAG_R_sat_AT();

            }
        });
        Button remove_img = r_sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chag_r_count1>1) {
                    chag_r_sat.removeView(r_sat_View);
                }

            }
        });

        chag_r_sat.addView(r_sat_View);
        chag_r_count1++;

    }

    public void CHAG_R_sun_AT(){

        final View r_sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = r_sun_View.findViewById(R.id.st_time_et);
        Button add_img = r_sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHAG_R_sun_AT();

            }
        });
        Button remove_img = r_sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chag_r_count2>1) {
                    chag_r_sunH.removeView(r_sun_View);
                }

            }
        });

        chag_r_sunH.addView(r_sun_View);
        chag_r_count2++;

    }

    public void CHAG_3D_mf_AT(){

        final View mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = mf_View.findViewById(R.id.st_time_et);
        Button add_img = mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHAG_3D_mf_AT();

            }
        });
        Button remove_img = mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chag_3d_count>1) {
                    chag_3D_mf.removeView(mf_View);
                }

            }
        });

        chag_3D_mf.addView(mf_View);
        chag_3d_count++;

    }

    public void CHAG_3D_sat_AT(){

        final View sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sat_View.findViewById(R.id.st_time_et);
        Button add_img = sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHAG_3D_sat_AT();

            }
        });
        Button remove_img = sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chag_3d_count1>1) {
                    chag_3D_sat.removeView(sat_View);
                }

            }
        });

        chag_3D_sat.addView(sat_View);
        chag_3d_count1++;

    }

    public void CHAG_3D_sun_AT(){

        final View sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sun_View.findViewById(R.id.st_time_et);
        Button add_img = sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CHAG_3D_sun_AT();

            }
        });
        Button remove_img = sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(chag_3d_count2>1) {
                    chag_3D_sunH.removeView(sun_View);
                }

            }
        });

        chag_3D_sunH.addView(sun_View);
        chag_3d_count2++;

    }

    public void SDO_R_mf_AT(){

        final View r_mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = r_mf_View.findViewById(R.id.st_time_et);
        Button add_img = r_mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDO_R_mf_AT();

            }
        });
        Button remove_img = r_mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sdo_r_count>1) {
                    sdo_r_mf.removeView(r_mf_View);
                }

            }
        });

        sdo_r_mf.addView(r_mf_View);
        sdo_r_count++;

    }

    public void SDO_R_sat_AT(){

        final View r_sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = r_sat_View.findViewById(R.id.st_time_et);
        Button add_img = r_sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDO_R_sat_AT();

            }
        });
        Button remove_img = r_sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sdo_r_count1>1) {
                    sdo_r_sat.removeView(r_sat_View);
                }

            }
        });

        sdo_r_sat.addView(r_sat_View);
        sdo_r_count1++;

    }

    public void SDO_R_sun_AT(){

        final View r_sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = r_sun_View.findViewById(R.id.st_time_et);
        Button add_img = r_sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDO_R_sun_AT();

            }
        });
        Button remove_img = r_sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sdo_r_count2>1) {
                    sdo_r_sunH.removeView(r_sun_View);
                }

            }
        });

        sdo_r_sunH.addView(r_sun_View);
        sdo_r_count2++;

    }

    public void SDO_3D_mf_AT(){

        final View mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = mf_View.findViewById(R.id.st_time_et);
        Button add_img = mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDO_3D_mf_AT();

            }
        });
        Button remove_img = mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sdo_3d_count>1) {
                    sdo_3D_mf.removeView(mf_View);
                }

            }
        });

        sdo_3D_mf.addView(mf_View);
        sdo_3d_count++;

    }

    public void SDO_3D_sat_AT(){

        final View sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sat_View.findViewById(R.id.st_time_et);
        Button add_img = sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDO_3D_sat_AT();

            }
        });
        Button remove_img = sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sdo_3d_count1>1) {
                    sdo_3D_sat.removeView(sat_View);
                }

            }
        });

        sdo_3D_sat.addView(sat_View);
        sdo_3d_count1++;

    }

    public void SDO_3D_sun_AT(){

        final View sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = sun_View.findViewById(R.id.st_time_et);
        Button add_img = sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDO_3D_sun_AT();

            }
        });
        Button remove_img = sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sdo_3d_count2>1) {
                    sdo_3D_sunH.removeView(sun_View);
                }

            }
        });

        sdo_3D_sunH.addView(sun_View);
        sdo_3d_count2++;

    }

    public void TGO_R_mf_AT(){

        final View r_mf_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = r_mf_View.findViewById(R.id.st_time_et);
        Button add_img = r_mf_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TGO_R_mf_AT();

            }
        });
        Button remove_img = r_mf_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tgo_r_count>1) {
                    tgo_r_mf.removeView(r_mf_View);
                }

            }
        });

        tgo_r_mf.addView(r_mf_View);
        tgo_r_count++;

    }

    public void TGO_R_sat_AT(){

        final View r_sat_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = r_sat_View.findViewById(R.id.st_time_et);
        Button add_img = r_sat_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TGO_R_sat_AT();

            }
        });
        Button remove_img = r_sat_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tgo_r_count1>1) {
                    tgo_r_sat.removeView(r_sat_View);
                }

            }
        });

        tgo_r_sat.addView(r_sat_View);
        tgo_r_count1++;

    }

    public void TGO_R_sun_AT(){

        final View r_sun_View = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
        EditText time_ET = r_sun_View.findViewById(R.id.st_time_et);
        Button add_img = r_sun_View.findViewById(R.id.st_addB);
        add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TGO_R_sun_AT();

            }
        });
        Button remove_img = r_sun_View.findViewById(R.id.st_removeB);
        remove_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tgo_r_count2>1) {
                    tgo_r_sunH.removeView(r_sun_View);
                }

            }
        });

        tgo_r_sunH.addView(r_sun_View);
        tgo_r_count2++;

    }


    private void SaveTimes() {

        timeSid = timeS_id.getText().toString().trim();
        POS_R_saveTimes(timeSid);
        POS_3D_saveTimes(timeSid);

        CHAG_R_saveTimes(timeSid);
        CHAG_3D_saveTimes(timeSid);

        SDO_R_saveTimes(timeSid);
        SDO_3D_saveTimes(timeSid);

        TGO_R_saveTimes(timeSid);


        ClearList();
        ClearAllViews();

    }

    public void POS_R_saveTimes(String timeSid) {

        pos_r_mf_list.clear();
        pos_r_sat_list.clear();
        pos_r_sun_list.clear();

        for(int i=0; i<pos_r_mf.getChildCount(); i++){
            View mf_V = pos_r_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                pos_r_mf_list.add(t);
            }
        }

        if(pos_r_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("MT").child("pos").child("regular").child("mon-fri");
            for(int i= 0; i<pos_r_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(pos_r_mf_list.get(i));
            }

        }

        pos_r_mf.removeAllViewsInLayout();


        for(int i=0; i<pos_r_sat.getChildCount(); i++){
            View sat_V = pos_r_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                pos_r_sat_list.add(t);
            }

        }

        if(pos_r_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("MT").child("pos").child("regular").child("sat");
            for(int i= 0; i<pos_r_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(pos_r_sat_list.get(i));
            }

        }

        pos_r_sat.removeAllViewsInLayout();

        for(int i=0; i<pos_r_sunH.getChildCount(); i++){
            View sat_V = pos_r_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                pos_r_sun_list.add(t);
            }

        }

        if(pos_r_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("MT").child("pos").child("regular").child("sun+hol");
            for(int i= 0; i<pos_r_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(pos_r_sun_list.get(i));
            }

        }
        pos_r_sunH.removeAllViewsInLayout();

    }

    public void POS_3D_saveTimes(String timeSid) {

        pos_3d_mf_list.clear();
        pos_3d_sat_list.clear();
        pos_3d_sun_list.clear();

        for(int i=0; i<pos_3D_mf.getChildCount(); i++){
            View mf_V = pos_3D_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                pos_3d_mf_list.add(t);
            }
        }

        if(pos_3D_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("MT").child("pos").child("3D").child("mon-fri");
            for(int i= 0; i<pos_3d_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(pos_3d_mf_list.get(i));
            }
        }

        pos_3D_mf.removeAllViewsInLayout();


        for(int i=0; i<pos_3D_sat.getChildCount(); i++){
            View sat_V = pos_3D_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                pos_3d_sat_list.add(t);
            }
        }

        if(pos_3D_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("MT").child("pos").child("3D").child("sat");
            for(int i= 0; i<pos_3d_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(pos_3d_sat_list.get(i));
            }
        }

        pos_3D_sat.removeAllViewsInLayout();

        for(int i=0; i<pos_3D_sunH.getChildCount(); i++){
            View sat_V = pos_3D_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                pos_3d_sun_list.add(t);
            }
        }

        if(pos_3D_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("MT").child("pos").child("3D").child("sun+hol");
            for(int i= 0; i<pos_3d_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(pos_3d_sun_list.get(i));
            }
        }
        pos_3D_sunH.removeAllViewsInLayout();

    }

    public void CHAG_R_saveTimes(String timeSid) {

        chag_r_mf_list.clear();
        chag_r_sat_list.clear();
        chag_r_sun_list.clear();

        for(int i=0; i<chag_r_mf.getChildCount(); i++){
            View mf_V = chag_r_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                chag_r_mf_list.add(t);
            }
        }

        if(chag_r_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("MT").child("chag").child("regular").child("mon-fri");
            for(int i= 0; i<chag_r_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(chag_r_mf_list.get(i));
            }

        }

        chag_r_mf.removeAllViewsInLayout();


        for(int i=0; i<chag_r_sat.getChildCount(); i++){
            View sat_V = chag_r_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                chag_r_sat_list.add(t);
            }

        }

        if(chag_r_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("MT").child("chag").child("regular").child("sat");
            for(int i= 0; i<chag_r_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(chag_r_sat_list.get(i));
            }

        }

        chag_r_sat.removeAllViewsInLayout();

        for(int i=0; i<chag_r_sunH.getChildCount(); i++){
            View sat_V = chag_r_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                chag_r_sun_list.add(t);
            }

        }

        if(chag_r_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("MT").child("chag").child("regular").child("sun+hol");
            for(int i= 0; i<chag_r_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(chag_r_sun_list.get(i));
            }

        }
        chag_r_sunH.removeAllViewsInLayout();

    }

    public void CHAG_3D_saveTimes(String timeSid) {

        chag_3d_mf_list.clear();
        chag_3d_sat_list.clear();
        chag_3d_sun_list.clear();

        for(int i=0; i<chag_3D_mf.getChildCount(); i++){
            View mf_V = chag_3D_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                chag_3d_mf_list.add(t);
            }
        }

        if(chag_3D_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("MT").child("chag").child("3D").child("mon-fri");
            for(int i= 0; i<chag_3d_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(chag_3d_mf_list.get(i));
            }
        }

        chag_3D_mf.removeAllViewsInLayout();


        for(int i=0; i<chag_3D_sat.getChildCount(); i++){
            View sat_V = chag_3D_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                chag_3d_sat_list.add(t);
            }
        }

        if(chag_3D_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("MT").child("chag").child("3D").child("sat");
            for(int i= 0; i<chag_3d_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(chag_3d_sat_list.get(i));
            }
        }

        chag_3D_sat.removeAllViewsInLayout();

        for(int i=0; i<chag_3D_sunH.getChildCount(); i++){
            View sat_V = chag_3D_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                chag_3d_sun_list.add(t);
            }
        }

        if(chag_3D_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("MT").child("chag").child("3D").child("sun+hol");
            for(int i= 0; i<chag_3d_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(chag_3d_sun_list.get(i));
            }
        }
        chag_3D_sunH.removeAllViewsInLayout();

    }

    public void SDO_R_saveTimes(String timeSid) {

        sdo_r_mf_list.clear();
        sdo_r_sat_list.clear();
        sdo_r_sun_list.clear();

        for(int i=0; i<sdo_r_mf.getChildCount(); i++){
            View mf_V = sdo_r_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sdo_r_mf_list.add(t);
            }
        }

        if(sdo_r_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("MT").child("sdo").child("regular").child("mon-fri");
            for(int i= 0; i<sdo_r_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(sdo_r_mf_list.get(i));
            }

        }

        sdo_r_mf.removeAllViewsInLayout();


        for(int i=0; i<sdo_r_sat.getChildCount(); i++){
            View sat_V = sdo_r_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sdo_r_sat_list.add(t);
            }

        }

        if(sdo_r_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("MT").child("sdo").child("regular").child("sat");
            for(int i= 0; i<sdo_r_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(pos_r_sat_list.get(i));
            }

        }

        pos_r_sat.removeAllViewsInLayout();

        for(int i=0; i<pos_r_sunH.getChildCount(); i++){
            View sat_V = pos_r_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sdo_r_sun_list.add(t);
            }

        }

        if(sdo_r_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("MT").child("sdo").child("regular").child("sun+hol");
            for(int i= 0; i<sdo_r_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(pos_r_sun_list.get(i));
            }

        }
        sdo_r_sunH.removeAllViewsInLayout();

    }

    public void SDO_3D_saveTimes(String timeSid) {

        sdo_3d_mf_list.clear();
        sdo_3d_sat_list.clear();
        sdo_3d_sun_list.clear();

        for(int i=0; i<sdo_3D_mf.getChildCount(); i++){
            View mf_V = sdo_3D_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sdo_3d_mf_list.add(t);
            }
        }

        if(sdo_3D_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("MT").child("sdo").child("3D").child("mon-fri");
            for(int i= 0; i<sdo_3d_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(sdo_3d_mf_list.get(i));
            }
        }

        sdo_3D_mf.removeAllViewsInLayout();


        for(int i=0; i<sdo_3D_sat.getChildCount(); i++){
            View sat_V = sdo_3D_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sdo_3d_sat_list.add(t);
            }
        }

        if(sdo_3D_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("MT").child("sdo").child("3D").child("sat");
            for(int i= 0; i<sdo_3d_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(sdo_3d_sat_list.get(i));
            }
        }

        sdo_3D_sat.removeAllViewsInLayout();

        for(int i=0; i<sdo_3D_sunH.getChildCount(); i++){
            View sat_V = sdo_3D_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                sdo_3d_sun_list.add(t);
            }
        }

        if(sdo_3D_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("MT").child("sdo").child("3D").child("sun+hol");
            for(int i= 0; i<sdo_3d_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(sdo_3d_sun_list.get(i));
            }
        }
        sdo_3D_sunH.removeAllViewsInLayout();

    }

    public void TGO_R_saveTimes(String timeSid) {

        tgo_r_mf_list.clear();
        tgo_r_sat_list.clear();
        tgo_r_sun_list.clear();

        for(int i=0; i<tgo_r_mf.getChildCount(); i++){
            View mf_V = tgo_r_mf.getChildAt(i);
            EditText time_ET = (EditText) mf_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                tgo_r_mf_list.add(t);
            }
        }

        if(tgo_r_mf.getChildCount()!=0){
            DatabaseReference mfpost =  referenceTime.child(timeSid).child("MT").child("tgo").child("regular").child("mon-fri");
            for(int i= 0; i<tgo_r_mf_list.size(); i++) {
                mfpost.child(String.valueOf(i)).setValue(tgo_r_mf_list.get(i));
            }

        }

        tgo_r_mf.removeAllViewsInLayout();


        for(int i=0; i<tgo_r_sat.getChildCount(); i++){
            View sat_V = tgo_r_sat.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                tgo_r_sat_list.add(t);
            }

        }

        if(tgo_r_sat.getChildCount()!=0){
            DatabaseReference satpost =  referenceTime.child(timeSid).child("MT").child("tgo").child("regular").child("sat");
            for(int i= 0; i<tgo_r_sat_list.size(); i++) {
                satpost.child(String.valueOf(i)).setValue(tgo_r_sat_list.get(i));
            }

        }

        tgo_r_sat.removeAllViewsInLayout();

        for(int i=0; i<tgo_r_sunH.getChildCount(); i++){
            View sat_V = tgo_r_sunH.getChildAt(i);
            EditText time_ET = (EditText) sat_V.findViewById(R.id.st_time_et);
            String t = time_ET.getText().toString().trim();

            if(!t.isEmpty()){
                tgo_r_sun_list.add(t);
            }

        }

        if(tgo_r_sunH.getChildCount() !=0){
            DatabaseReference sunpost =  referenceTime.child(timeSid).child("MT").child("tgo").child("regular").child("sun+hol");
            for(int i= 0; i<tgo_r_sun_list.size(); i++) {
                sunpost.child(String.valueOf(i)).setValue(tgo_r_sun_list.get(i));
            }

        }
        tgo_r_sunH.removeAllViewsInLayout();

    }

    private void SearchDisplay(String text) {

        if(!text.isEmpty()){
            scheduleTimes =  FirebaseDatabase.getInstance().getReference("TimeSchedule")
                    .child(text);
            scheduleTimes.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        ClearAllViews();
                        Display_POS_R(scheduleTimes, text);
                        Display_POS_3D(scheduleTimes, text);

                        Display_CHAG_R(scheduleTimes, text);
                        Display_CHAG_3D(scheduleTimes, text);

                        Display_SDO_R(scheduleTimes, text);
                        Display_SDO_3D(scheduleTimes, text);

                        Display_TGO_R(scheduleTimes, text);



                    }else{

                        Toast.makeText(AdminMTTS.this, "Key does not exist!", Toast.LENGTH_SHORT).show();
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

    private void ClearAllViews() {

        pos_r_mf.removeAllViews();
        pos_r_sat.removeAllViews();
        pos_r_sunH.removeAllViews();

        pos_3D_mf.removeAllViews();
        pos_3D_sat.removeAllViews();
        pos_3D_sunH.removeAllViews();

        chag_r_mf.removeAllViews();
        chag_r_sat.removeAllViews();
        chag_r_sunH.removeAllViews();

        chag_3D_mf.removeAllViews();
        chag_3D_sat.removeAllViews();
        chag_3D_sunH.removeAllViews();


        sdo_r_mf.removeAllViews();
        sdo_r_sat.removeAllViews();
        sdo_r_sunH.removeAllViews();

        sdo_3D_mf.removeAllViews();
        sdo_3D_sat.removeAllViews();
        sdo_3D_sunH.removeAllViews();

        tgo_r_mf.removeAllViews();
        tgo_r_sat.removeAllViews();
        tgo_r_sunH.removeAllViews();

    }

    public void ClearList(){

        pos_r_mf_list.clear();
        pos_r_sat_list.clear();
        pos_r_sun_list.clear();

        pos_3d_mf_list.clear();
        pos_3d_sat_list.clear();
        pos_3d_sun_list.clear();

        chag_r_mf_list.clear();
        chag_r_sat_list.clear();
        chag_r_sun_list.clear();

        chag_3d_mf_list.clear();
        chag_3d_sat_list.clear();
        chag_3d_sun_list.clear();

        sdo_r_mf_list.clear();
        sdo_r_sat_list.clear();
        sdo_r_sun_list.clear();

        sdo_3d_mf_list.clear();
        sdo_3d_sat_list.clear();
        sdo_3d_sun_list.clear();

        tgo_r_mf_list.clear();
        tgo_r_sat_list.clear();
        tgo_r_sun_list.clear();

    }

    public void Display_POS_R(DatabaseReference scheduleTimes, String timeSid){

        pos_r_mf_list.clear();
        Query r_mf = scheduleTimes.child("MT").child("pos").child("regular").child("mon-fri").orderByKey();
        r_mf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pos_r_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        pos_r_mf_list.add(time);
                    }
                }else{
                    POS_R_mf_AT();
                }

                int size = pos_r_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(pos_r_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(mf_DV, pos_r_mf, pos_r_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("MT").child("pos").child("regular").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            time_ET.setText("");
                            pos_r_mf.removeView(mf_DV);

                        }
                    });

                    pos_r_mf.addView(mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        pos_r_sat_list.clear();
        Query r_sat = scheduleTimes.child("MT").child("pos").child("regular").child("sat").orderByKey();
        r_sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pos_r_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        pos_r_sat_list.add(time);
                    }
                }else{
                    POS_R_sat_AT();
                }

                int size =  pos_r_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(pos_r_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sat_DV, pos_r_sat, pos_r_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("MT").child("pos").child("regular").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            pos_r_sat.removeView(sat_DV);

                        }
                    });

                    pos_r_sat.addView( sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        pos_r_sun_list.clear();
        Query r_sun = scheduleTimes.child("MT").child(" pos").child("regular").child("sun+hol").orderByKey();
        r_sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pos_r_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        pos_r_sun_list.add(time);
                    }
                }else{
                    POS_R_sun_AT();
                }

                int size =  pos_r_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText( pos_r_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sun_DV,  pos_r_sunH,  pos_r_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("MT").child("pos").child("regular").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            pos_r_sunH.removeView(sun_DV);

                        }
                    });

                    pos_r_sunH.addView(sun_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void Display_POS_3D(DatabaseReference scheduleTimes, String timeSid){

        pos_3d_mf_list.clear();
        Query mf = scheduleTimes.child("MT").child("pos").child("3D").child("mon-fri").orderByKey();
        mf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pos_3d_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        pos_3d_mf_list.add(time);
                    }
                }else{
                    POS_3D_mf_AT();
                }

                int size = pos_3d_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(pos_3d_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(mf_DV, pos_3D_mf, pos_3d_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("MT").child("pos").child("3D").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            pos_3D_mf.removeView(mf_DV);

                        }
                    });

                    pos_3D_mf.addView(mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        pos_3d_sat_list.clear();
        Query display_cc8_t_3d_sat = scheduleTimes.child("MT").child("pos").child("3D").child("sat").orderByKey();
        display_cc8_t_3d_sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pos_3d_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        pos_3d_sat_list.add(time);
                    }
                }else{
                    POS_3D_sat_AT();
                }

                int size = pos_3d_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(pos_3d_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sat_DV, pos_3D_sat, pos_3d_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("MT").child("pos").child("3D").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            pos_3D_sat.removeView(sat_DV);

                        }
                    });

                    pos_3D_sat.addView(sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        pos_3d_sun_list.clear();
        Query sun = scheduleTimes.child("MT").child("pos").child("3D").child("sun+hol").orderByKey();
        sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pos_3d_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        pos_3d_sun_list.add(time);
                    }
                }else{
                    POS_3D_sun_AT();
                }

                int size = pos_3d_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(pos_3d_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sun_DV, pos_3D_sunH, pos_3d_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("MT").child("pos").child("3D").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            pos_3D_sunH.removeView(sun_DV);

                        }
                    });

                    pos_3D_sunH.addView(sun_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void Display_CHAG_R(DatabaseReference scheduleTimes, String timeSid){

        chag_r_mf_list.clear();
        Query r_mf = scheduleTimes.child("MT").child("chag").child("regular").child("mon-fri").orderByKey();
        r_mf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chag_r_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        chag_r_mf_list.add(time);
                    }
                }else{
                    CHAG_R_mf_AT();
                }

                int size = chag_r_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(chag_r_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(mf_DV, chag_r_mf, chag_r_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("MT").child("chag").child("regular").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            time_ET.setText("");
                            chag_r_mf.removeView(mf_DV);

                        }
                    });

                    chag_r_mf.addView(mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        chag_r_sat_list.clear();
        Query r_sat = scheduleTimes.child("MT").child("chag").child("regular").child("sat").orderByKey();
        r_sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chag_r_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        chag_r_sat_list.add(time);
                    }
                }else{
                    CHAG_R_sat_AT();
                }

                int size =  chag_r_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(chag_r_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sat_DV, chag_r_sat, chag_r_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("MT").child("chag").child("regular").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            chag_r_sat.removeView(sat_DV);

                        }
                    });

                    chag_r_sat.addView( sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        chag_r_sun_list.clear();
        Query r_sun = scheduleTimes.child("MT").child("chag").child("regular").child("sun+hol").orderByKey();
        r_sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chag_r_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        chag_r_sun_list.add(time);
                    }
                }else{
                    CHAG_R_sun_AT();
                }

                int size =  chag_r_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText( chag_r_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sun_DV,  chag_r_sunH,  chag_r_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("MT").child("chag").child("regular").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            chag_r_sunH.removeView(sun_DV);

                        }
                    });

                    chag_r_sunH.addView(sun_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void Display_CHAG_3D(DatabaseReference scheduleTimes, String timeSid){

        chag_3d_mf_list.clear();
        Query mf = scheduleTimes.child("MT").child("chag").child("3D").child("mon-fri").orderByKey();
        mf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chag_3d_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        chag_3d_mf_list.add(time);
                    }
                }else{
                    CHAG_3D_mf_AT();
                }

                int size = chag_3d_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(chag_3d_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(mf_DV, chag_3D_mf, chag_3d_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("MT").child("chag").child("3D").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            chag_3D_mf.removeView(mf_DV);

                        }
                    });

                    chag_3D_mf.addView(mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        chag_3d_sat_list.clear();
        Query sat = scheduleTimes.child("MT").child("chag").child("3D").child("sat").orderByKey();
        sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chag_3d_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        chag_3d_sat_list.add(time);
                    }
                }else{
                    CHAG_3D_sat_AT();
                }

                int size = chag_3d_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(chag_3d_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sat_DV, chag_3D_sat, chag_3d_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("MT").child("chag").child("3D").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            chag_3D_sat.removeView(sat_DV);

                        }
                    });

                    chag_3D_sat.addView(sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        chag_3d_sun_list.clear();
        Query sun = scheduleTimes.child("MT").child("chag").child("3D").child("sun+hol").orderByKey();
        sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chag_3d_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        chag_3d_sun_list.add(time);
                    }
                }else{
                    CHAG_3D_sun_AT();
                }

                int size = chag_3d_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(chag_3d_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sun_DV, chag_3D_sunH, chag_3d_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("MT").child("chag").child("3D").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            chag_3D_sunH.removeView(sun_DV);

                        }
                    });

                    chag_3D_sunH.addView(sun_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void Display_SDO_R(DatabaseReference scheduleTimes, String timeSid){

        sdo_r_mf_list.clear();
        Query r_mf = scheduleTimes.child("MT").child("sdo").child("regular").child("mon-fri").orderByKey();
        r_mf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sdo_r_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sdo_r_mf_list.add(time);
                    }
                }else{
                    SDO_R_mf_AT();
                }

                int size = sdo_r_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sdo_r_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(mf_DV, sdo_r_mf, sdo_r_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("MT").child("sdo").child("regular").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            time_ET.setText("");
                            sdo_r_mf.removeView(mf_DV);

                        }
                    });

                    sdo_r_mf.addView(mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sdo_r_sat_list.clear();
        Query r_sat = scheduleTimes.child("MT").child("sdo").child("regular").child("sat").orderByKey();
        r_sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sdo_r_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sdo_r_sat_list.add(time);
                    }
                }else{
                    SDO_R_sat_AT();
                }

                int size =  sdo_r_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sdo_r_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sat_DV, sdo_r_sat, sdo_r_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("MT").child("sdo").child("regular").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sdo_r_sat.removeView(sat_DV);

                        }
                    });

                    sdo_r_sat.addView( sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sdo_r_sun_list.clear();
        Query r_sun = scheduleTimes.child("MT").child("sdo").child("regular").child("sun+hol").orderByKey();
        r_sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sdo_r_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sdo_r_sun_list.add(time);
                    }
                }else{
                    SDO_R_sun_AT();
                }

                int size =  sdo_r_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText( sdo_r_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sun_DV,  sdo_r_sunH,  sdo_r_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("MT").child("sdo").child("regular").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sdo_r_sunH.removeView(sun_DV);

                        }
                    });

                    sdo_r_sunH.addView(sun_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void Display_SDO_3D(DatabaseReference scheduleTimes, String timeSid){

        sdo_3d_mf_list.clear();
        Query mf = scheduleTimes.child("MT").child("sdo").child("3D").child("mon-fri").orderByKey();
        mf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sdo_3d_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sdo_3d_mf_list.add(time);
                    }
                }else{
                    SDO_3D_mf_AT();
                }

                int size = sdo_3d_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sdo_3d_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(mf_DV, sdo_3D_mf, sdo_3d_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("MT").child("sdo").child("3D").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sdo_3D_mf.removeView(mf_DV);

                        }
                    });

                    sdo_3D_mf.addView(mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sdo_3d_sat_list.clear();
        Query sat = scheduleTimes.child("MT").child("sdo").child("3D").child("sat").orderByKey();
        sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sdo_3d_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sdo_3d_sat_list.add(time);
                    }
                }else{
                    CHAG_3D_sat_AT();
                }

                int size = sdo_3d_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sdo_3d_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sat_DV, sdo_3D_sat, sdo_3d_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("MT").child("sdo").child("3D").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sdo_3D_sat.removeView(sat_DV);

                        }
                    });

                    sdo_3D_sat.addView(sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        sdo_3d_sun_list.clear();
        Query sun = scheduleTimes.child("MT").child("sdo").child("3D").child("sun+hol").orderByKey();
        sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sdo_3d_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        sdo_3d_sun_list.add(time);
                    }
                }else{
                    CHAG_3D_sun_AT();
                }

                int size = sdo_3d_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(sdo_3d_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sun_DV, sdo_3D_sunH, sdo_3d_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("MT").child("sdo").child("3D").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            sdo_3D_sunH.removeView(sun_DV);

                        }
                    });

                    sdo_3D_sunH.addView(sun_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void Display_TGO_R(DatabaseReference scheduleTimes, String timeSid){

        tgo_r_mf_list.clear();
        Query r_mf = scheduleTimes.child("MT").child("tgo").child("regular").child("mon-fri").orderByKey();
        r_mf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tgo_r_mf_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        tgo_r_mf_list.add(time);
                    }
                }else{
                    TGO_R_mf_AT();
                }

                int size = tgo_r_mf_list.size();
                for(int i= 0 ; i<size; i++) {

                    View mf_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = mf_DV.findViewById(R.id.st_time_et);
                    Button add_img = mf_DV.findViewById(R.id.st_addB);
                    Button remove_img = mf_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(tgo_r_mf_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(mf_DV, tgo_r_mf, tgo_r_mf_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference mfpost = referenceTime.child(timeSid).child("MT").child("tgo").child("regular").child("mon-fri");
                            mfpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            time_ET.setText("");
                            tgo_r_mf.removeView(mf_DV);

                        }
                    });

                    tgo_r_mf.addView(mf_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tgo_r_sat_list.clear();
        Query r_sat = scheduleTimes.child("MT").child("tgo").child("regular").child("sat").orderByKey();
        r_sat.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tgo_r_sat_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        tgo_r_sat_list.add(time);
                    }
                }else{
                    TGO_R_sat_AT();
                }

                int size =  tgo_r_sat_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sat_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sat_DV.findViewById(R.id.st_time_et);
                    Button add_img = sat_DV.findViewById(R.id.st_addB);
                    Button remove_img = sat_DV.findViewById(R.id.st_removeB);

                    time_ET.setText(tgo_r_sat_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sat_DV, tgo_r_sat, tgo_r_sat_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference satpost = referenceTime.child(timeSid).child("MT").child("tgo").child("regular").child("sat");
                            satpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            tgo_r_sat.removeView(sat_DV);

                        }
                    });

                    tgo_r_sat.addView( sat_DV);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        tgo_r_sun_list.clear();
        Query r_sun = scheduleTimes.child("MT").child("tgo").child("regular").child("sun+hol").orderByKey();
        r_sun.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tgo_r_sun_list.clear();
                if(snapshot.exists()){
                    for(DataSnapshot ss: snapshot.getChildren()){
                        String time = ss.getValue().toString();
                        tgo_r_sun_list.add(time);
                    }
                }else{
                    TGO_R_sun_AT();
                }

                int size =  tgo_r_sun_list.size();
                for(int i= 0 ; i<size; i++) {

                    View sun_DV = getLayoutInflater().inflate(R.layout.single_time_item,null,false);
                    EditText time_ET = sun_DV.findViewById(R.id.st_time_et);
                    Button add_img = sun_DV.findViewById(R.id.st_addB);
                    Button remove_img = sun_DV.findViewById(R.id.st_removeB);

                    time_ET.setText( tgo_r_sun_list.get(i));

                    add_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            addNewTime(sun_DV,  tgo_r_sunH,  tgo_r_sun_list);

                        }
                    });

                    int finalI = i;
                    remove_img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            DatabaseReference sunpost = referenceTime.child(timeSid).child("MT").child("tgo").child("regular").child("sun+hol");
                            sunpost.child(String.valueOf(finalI)).removeValue();
                            Toast.makeText(AdminMTTS.this,   finalI + " is removed", Toast.LENGTH_SHORT).show();
                            tgo_r_sunH.removeView(sun_DV);

                        }
                    });

                    tgo_r_sunH.addView(sun_DV);

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