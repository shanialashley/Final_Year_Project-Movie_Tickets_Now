package com.example.authapp.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authapp.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;

import java.net.Inet4Address;

public class Theaters extends AppCompatActivity {

    private TextView cabcin_title, cabcin_num, cabcin_address;
    private ImageView  img1,img2, img3,img4,img5, cabcin_map, cabcin_phone_call;

    private TextView mt_title, mt_num, mt_address;
    private ImageView mt_map, mt_call;

    private TextView imax_title, imax_num, imax_address;
    private ImageView imax_map, imax_call, imax_icon;


    private View divider3, divider5, divider7;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private static final int REQUEST_CALL = 1;


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
        cabcin_map = findViewById(R.id.theater_cabcin_map);
        cabcin_phone_call = findViewById(R.id.theater_cabcin_call);

        mt_title = findViewById(R.id.theater_mt_title);
        mt_address = findViewById(R.id.theaters_mt_loc_addr);
        mt_num = findViewById(R.id.theater_mt_loc_num);
        mt_map = findViewById(R.id.theater_mt_map);
        mt_call = findViewById(R.id.theater_mt_call);
        divider5 = findViewById(R.id.divider5);

        imax_title = findViewById(R.id.theaters_imax_title);
        imax_address = findViewById(R.id.theaters_imax_loc_addr);
        imax_num = findViewById(R.id.theaters_imax_loc_num);
        imax_map = findViewById(R.id.theater_imax_map);
        imax_call = findViewById(R.id.theater_imax_call);
        imax_icon = findViewById(R.id.theaters_imax_locations_icon);
        divider7 = findViewById(R.id.divider7);


        CaribCin_ViewGone();
        Mt_ViewGone();
        Imax_ViewGone();


        NavInfo();

        imax_icon.setOnClickListener(new View.OnClickListener() {

            Boolean select = true;

            @Override
            public void onClick(View v) {

                if(select==true){

                    Imax_Show();
                    select=false;
                }else{
                    Imax_ViewGone();
                    select=true;
                }

            }
        });



    }

    public void NavInfo(){

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Theaters");



        Menu menu = navigationView.getMenu();
//        menu.findItem(R.id.nav_login).setVisible(false);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        DrawerArrowDrawable arrow = toggle.getDrawerArrowDrawable();
        arrow.setColor(getResources().getColor(R.color.orange));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        startActivity(new Intent(Theaters.this, Home.class));
                        break;

                    case R.id.nav_theaters:

                        break;

                    case R.id.nav_upcoming_movies:
                        startActivity(new Intent(Theaters.this, UpcomingMovies.class));
                        break;

                    case R.id.nav_search:
                        startActivity(new Intent(Theaters.this, Search.class));
                        break;


                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        navigationView.setCheckedItem(R.id.nav_theaters);



    }




    public void CaribCin_ViewGone(){
        cabcin_title.setVisibility(View.GONE);
        cabcin_address.setVisibility(View.GONE);
        cabcin_num.setVisibility(View.GONE);
        img1.setVisibility(View.GONE);
        img2.setVisibility(View.GONE);
        img3.setVisibility(View.GONE);
        img4.setVisibility(View.GONE);
        img5.setVisibility(View.GONE);
        cabcin_map.setVisibility(View.GONE);
        cabcin_phone_call.setVisibility(View.GONE);
        divider3.setVisibility(View.GONE);

    }

    private void Mt_ViewGone() {

        mt_title.setVisibility(View.GONE);
        mt_address.setVisibility(View.GONE);
        mt_num.setVisibility(View.GONE);
        mt_map.setVisibility(View.GONE);
        mt_call.setVisibility(View.GONE);
        divider5.setVisibility(View.GONE);

    }

    private void Mt_View() {

        mt_title.setVisibility(View.VISIBLE);
        mt_address.setVisibility(View.VISIBLE);
        mt_num.setVisibility(View.VISIBLE);
        mt_map.setVisibility(View.VISIBLE);
        mt_call.setVisibility(View.VISIBLE);
        divider5.setVisibility(View.VISIBLE);

    }

    private void Imax_ViewGone() {

        imax_title.setVisibility(View.GONE);
        imax_address.setVisibility(View.GONE);
        imax_num.setVisibility(View.GONE);
        imax_map.setVisibility(View.GONE);
        imax_call.setVisibility(View.GONE);
        divider7.setVisibility(View.GONE);

    }

    public void Carib_Cin_ShowPopUp(View view){
        PopupMenu popUp = new PopupMenu(this, view);
        popUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:
                        CaribCin_ViewGone();
                        return true;

                    case R.id.item2:

                        CaribCin_ViewGone();

                        cabcin_title.setText("Trincity Mall");
                        cabcin_address.setText("Level 2 Mall, Trincity, Trincity, Trinidad & Tobago");
                        cabcin_num.setText("18686408788");
                        img3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                            }
                        });


                        cabcin_map.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                double v1 = 10.62783278487164;
                                double v2 = -61.352172796533786;

                                Intent intent = new Intent(Theaters.this, PopupMap.class);
                                intent.putExtra("v1", v1);
                                intent.putExtra("v2", v2);
                                startActivity(intent);

                            }
                        });

                        cabcin_phone_call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                makePhoneCall(cabcin_num.getText().toString());
                            }
                        });

                        cabcin_title.setVisibility(View.VISIBLE);
                        cabcin_address.setVisibility(View.VISIBLE);
                        cabcin_num.setVisibility(View.VISIBLE);
                        img3.setVisibility(View.VISIBLE);
                        cabcin_map.setVisibility(View.VISIBLE);
                        cabcin_phone_call.setVisibility(View.VISIBLE);
                        divider3.setVisibility(View.VISIBLE);

                        return true;

                    case R.id.item3:

                        CaribCin_ViewGone();

                        cabcin_title.setText("SouthPark");
                        cabcin_address.setText("Tarouba Link Road, San Fernando, Trinidad & Tobago");
                        cabcin_num.setText("18682818907");
                        cabcin_phone_call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                makePhoneCall(cabcin_num.getText().toString());
                            }
                        });


                        cabcin_title.setVisibility(View.VISIBLE);
                        cabcin_address.setVisibility(View.VISIBLE);
                        cabcin_num.setVisibility(View.VISIBLE);
                        img1.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.VISIBLE);
                        img3.setVisibility(View.VISIBLE);
                        img4.setVisibility(View.VISIBLE);
                        img5.setVisibility(View.VISIBLE);
                        cabcin_map.setVisibility(View.VISIBLE);
                        cabcin_phone_call.setVisibility(View.VISIBLE);
                        divider3.setVisibility(View.VISIBLE);

                        return true;
                    default:
                        return false;
                }

            }
        });

        popUp.inflate(R.menu.popup_menu);
        popUp.show();

    }

    public void MT_ShowPopUp(View view){
        PopupMenu popUp = new PopupMenu(this, view);
        popUp.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:
                        Mt_ViewGone();
                        return true;

                    case R.id.item2:
                        Mt_ViewGone();

                        mt_title.setText("PORT OF SPAIN");
                        mt_address.setText("Lot D, MovieTowne Boulevard, Audrey Jeffers Highway, Port of Spain Trinidad .");
                        mt_num.setText(" 1 (868) 62-STARS (78277)\n" +
                                "1 (868) 627-2002 ");
                        mt_map.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        mt_call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                makePhoneCall(mt_num.getText().toString());
                            }
                        });

                        Mt_View();
                        return true;

                    case R.id.item3:
                        Mt_ViewGone();

                        mt_title.setText("CHAGUANAS");
                        mt_address.setText("Price Plaza, Endeavour Road, Chaguanas, Trinidad ");
                        mt_num.setText("1 (868) 62-STARS (78277)");
                        mt_map.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        mt_call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                makePhoneCall(mt_num.getText().toString());
                            }
                        });

                        Mt_View();
                        return true;

                    case R.id.item4:
                        Mt_ViewGone();

                        mt_title.setText("SAN FERNANDO");
                        mt_address.setText("Corinth Street, San Fernando ");
                        mt_num.setText("1-868-627-8277 (62-STARS)");
                        mt_map.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        mt_call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                makePhoneCall(mt_num.getText().toString());
                            }
                        });

                        Mt_View();

                        return true;

                    case R.id.item5:
                        Mt_ViewGone();

                        mt_title.setText("TOBAGO");
                        mt_address.setText("Gulf View Mall, Lowlands, Tobago ");
                        mt_num.setText("1 (868) 62-STARS (78277)");
                        mt_map.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        mt_call.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                makePhoneCall(mt_num.getText().toString());
                            }
                        });

                        Mt_View();

                        return true;

                    default:
                        return false;
            }
        }});

        popUp.inflate(R.menu.mt_popup_menu);
        popUp.show();

    }

    public void Imax_Show(){
        imax_title.setText("Port of Spain");
        imax_address.setText("One Woodbrook Place, Port of Spain.");
        imax_num.setText("1 868 299 4629");
        imax_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        imax_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall(imax_num.getText().toString());
            }
        });

        imax_title.setVisibility(View.VISIBLE);
        imax_address.setVisibility(View.VISIBLE);
        imax_num.setVisibility(View.VISIBLE);
        imax_map.setVisibility(View.VISIBLE);
        imax_call.setVisibility(View.VISIBLE);
        divider7.setVisibility(View.VISIBLE);

    }

    public void makePhoneCall(String cell_num){

        if(ContextCompat.checkSelfPermission(Theaters.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(Theaters.this,
                    new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CALL);

        }else{
                String dail = "tel:" + cell_num;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dail)));
        }

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode == REQUEST_CALL){
//            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                String s ="";
//                makePhoneCall(s);
//            }else{
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
//
//            }
//
//        }
//
//    }
}