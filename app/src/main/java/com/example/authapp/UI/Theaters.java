package com.example.authapp.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authapp.R;

import static android.widget.Toast.LENGTH_SHORT;

public class Theaters extends AppCompatActivity {

    private TextView cabcin_title, cabcin_num, cabcin_address;
    private ImageView img1, img2,img3,img4,img5, map, phone_call;
    private View divider3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theaters);

        divider3 = findViewById(R.id.divider3);


        cabcin_title = findViewById(R.id.theater_cabcin_title);
        cabcin_address = findViewById(R.id.theater_cabcin_loc_addr);
        cabcin_num = findViewById(R.id.theater_cabcin_loc_num);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        map = findViewById(R.id.theater_cabcin_map);
        phone_call = findViewById(R.id.theater_cabcin_call);


        ViewGone();


//        cabcin_locations.setOnClickListener(new View.OnClickListener() {
//            boolean selected = false;
//            @Override
//            public void onClick(View view) {
//
//                if(selected == false){
//                    cabb_cin_1.setVisibility(View.VISIBLE);
//                    divider3.setVisibility(View.VISIBLE);
//                    selected = true;
//                }else{
//                    cabb_cin_1.setVisibility(View.GONE);
//                    divider3.setVisibility(View.GONE);
//                    selected = false;
//                }
//            }
//        });


    }

    public void ViewGone(){
        cabcin_title.setVisibility(View.GONE);
        cabcin_address.setVisibility(View.GONE);
        cabcin_num.setVisibility(View.GONE);
        img1.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        img3.setVisibility(View.GONE);
        img4.setVisibility(View.GONE);
        img5.setVisibility(View.GONE);
        map.setVisibility(View.GONE);
        phone_call.setVisibility(View.GONE);
        divider3.setVisibility(View.GONE);

    }


    public void ShowPopUp(View view){
        PopupMenu popUp = new PopupMenu(this, view);
        popUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:
                        ViewGone();
                        return true;

                    case R.id.item2:

                        cabcin_title.setText("Trincity Mall");
                        cabcin_address.setText("Level 2 Mall, Trincity, Trincity, Trinidad & Tobago");
                        cabcin_num.setText("+18686408788");
//                        img3.setOnHoverListener(new View.OnHoverListener() {
//                            @Override
//                            public boolean onHover(View v, MotionEvent event) {
//                                Toast.makeText(Theaters.this, "Accessible", LENGTH_SHORT).show();
//                                return true;
//                            }
//                        });


                        cabcin_title.setVisibility(View.VISIBLE);
                        cabcin_address.setVisibility(View.VISIBLE);
                        cabcin_num.setVisibility(View.VISIBLE);
                        img3.setVisibility(View.VISIBLE);
                        map.setVisibility(View.VISIBLE);
                        phone_call.setVisibility(View.VISIBLE);
                        divider3.setVisibility(View.VISIBLE);

                        return true;
                    case R.id.item3:
                        return true;
                    default:
                        return false;
                }

            }
        });

        popUp.inflate(R.menu.popup_menu);
        popUp.show();

    }
}