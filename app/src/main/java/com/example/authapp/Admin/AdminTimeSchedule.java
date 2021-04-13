package com.example.authapp.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.R;

public class AdminTimeSchedule extends AppCompatActivity {

    private Button cc8_B, mt_B, cone_B;
    private EditText id;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_time_schedule);

        cc8_B = findViewById(R.id.ct_cc8);
        id = findViewById(R.id.at_id);

        Bundle TimeS_key = getIntent().getExtras();

        if(TimeS_key != null) {
            key = TimeS_key.getString("TimeS_Key");
        }else{
            key = "";
        }
        id.setText(key);



        cc8_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AdminTimeSchedule.this, AdminCC8TS.class);
                intent.putExtra("TimeS_Key", id.getText().toString().trim());
                startActivity(intent);
            }
        });

        mt_B = findViewById(R.id.ct_mt);
        mt_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AdminTimeSchedule.this, AdminMTTS.class);
                intent.putExtra("TimeS_Key", id.getText().toString().trim());
                startActivity(intent);
            }
        });
        cone_B = findViewById(R.id.ct_cone);
        cone_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(AdminTimeSchedule.this, AdminConeTS.class);
                intent.putExtra("TimeS_Key", id.getText().toString().trim());
                startActivity(intent);
            }
        });

    }
}