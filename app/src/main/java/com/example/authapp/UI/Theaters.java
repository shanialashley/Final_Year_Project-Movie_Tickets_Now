package com.example.authapp.UI;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.authapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class Theaters extends AppCompatActivity {

    private TextView cabcin_title, cabcin_num, cabcin_address;
    private ImageView  img1,img2, img3,img4,img5, cabcin_map, cabcin_phone_call;

    private TextView mt_title, mt_num, mt_address;
    private ImageView mt_map, mt_call;

    private TextView imax_title, imax_num, imax_address;
    private ImageView imax_map, imax_call, imax_icon;

    private View d2, d4, d6;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private static final int REQUEST_CALL = 1;
    private Menu menu;
    private FirebaseAuth mAuth;
    private FirebaseUser currentuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theaters);

        mAuth = FirebaseAuth.getInstance();

        init();

        CaribCin_ViewGone();

        Mt_ViewGone();

        Imax_ViewGone();

        Imax_ShowPopUp();

        NavInfo();





    }

    private void init() {

        d2 = findViewById(R.id.divider2);
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

        d4 = findViewById(R.id.divider4);
        mt_title = findViewById(R.id.theater_mt_title);
        mt_address = findViewById(R.id.theaters_mt_loc_addr);
        mt_num = findViewById(R.id.theater_mt_loc_num);
        mt_map = findViewById(R.id.theater_mt_map);
        mt_call = findViewById(R.id.theater_mt_call);

        d6 = findViewById(R.id.divider6);
        imax_title = findViewById(R.id.theaters_imax_title);
        imax_address = findViewById(R.id.theaters_imax_loc_addr);
        imax_num = findViewById(R.id.theaters_imax_loc_num);
        imax_map = findViewById(R.id.theater_imax_map);
        imax_call = findViewById(R.id.theater_imax_call);
        imax_icon = findViewById(R.id.theaters_imax_locations_icon);

    }


    public void CC8_Theaters(View v){
        Intent in = new Intent(Theaters.this, SingleTheaterMovies.class);
        in.putExtra("screen", "CC8");
        startActivity(in);
    }

    public void MT_Theaters(View v){
        Intent in = new Intent(Theaters.this, SingleTheaterMovies.class);
        in.putExtra("screen", "MT");
        startActivity(in);
    }

    public void CONE_Theaters(View v){
        Intent in = new Intent(Theaters.this, SingleTheaterMovies.class);
        in.putExtra("screen", "CONE");
        startActivity(in);
    }

    public void NavInfo(){

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Theaters");



        menu = navigationView.getMenu();
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

                    case R.id.nav_logout:
                        mAuth.getInstance().signOut();
                        finish();
                        startActivity(new Intent(Theaters.this, Home.class));
                        break;

                    case R.id.nav_login:
                        startActivity(new Intent(Theaters.this, MainActivity.class).putExtra("Screen", "home"));
                        break;


                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        navigationView.setCheckedItem(R.id.nav_theaters);



    }

    @Override
    protected void onStart() {
        super.onStart();

        currentuser = mAuth.getCurrentUser();
        if(currentuser == null) {
            menu.findItem(R.id.nav_logout).setVisible(false);

        }else{
            menu.findItem(R.id.nav_login).setVisible(false);

        }

    }

    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    public void CaribCin_ViewGone(){
        d2.setVisibility(View.GONE);
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


    }

    public void Mt_ViewGone() {

        d4.setVisibility(View.GONE);
        mt_title.setVisibility(View.GONE);
        mt_address.setVisibility(View.GONE);
        mt_num.setVisibility(View.GONE);
        mt_map.setVisibility(View.GONE);
        mt_call.setVisibility(View.GONE);

    }

    public void Mt_View() {
        d4.setVisibility(View.VISIBLE);
        mt_title.setVisibility(View.VISIBLE);
        mt_address.setVisibility(View.VISIBLE);
        mt_num.setVisibility(View.VISIBLE);
        mt_map.setVisibility(View.VISIBLE);
        mt_call.setVisibility(View.VISIBLE);


    }

    public void Imax_ViewGone() {
        d6.setVisibility(View.GONE);
        imax_title.setVisibility(View.GONE);
        imax_address.setVisibility(View.GONE);
        imax_num.setVisibility(View.GONE);
        imax_map.setVisibility(View.GONE);
        imax_call.setVisibility(View.GONE);


    }

    public void showToast(View v, String s){
        StyleableToast.makeText(this,s, R.style.customToast).show();

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
                                    showToast(v, "Accessible");

                            }
                        });


                        cabcin_map.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                double v1 = 10.627516433365702;
                                double v2 = -61.35230154504384;

                                Intent intent = new Intent(Theaters.this, PopupMap.class);
                                intent.putExtra("v1", v1);
                                intent.putExtra("v2", v2);
                                intent.putExtra("location", "Trincity Mall");
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
                        d2.setVisibility(View.VISIBLE);


                        return true;

                    case R.id.item3:

                        CaribCin_ViewGone();

                        cabcin_title.setText("SouthPark");
                        cabcin_address.setText("Tarouba Link Road, San Fernando, Trinidad & Tobago");
                        cabcin_num.setText("18682818907");
                        img1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast(v, "Caribbean Cinema Extreme");
                            }
                        });

                        img2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast(v, "Game Room");
                            }
                        });

                        img3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast(v, "Accessible");
                            }
                        });

                        img4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast(v, "Party Room");
                            }
                        });

                        img5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showToast(v, "Sala Tipo Stadium");
                            }
                        });

                        cabcin_map.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                double v1 = 10.289918814363192;
                                double v2 = -61.44491608465729;

                                Intent intent = new Intent(Theaters.this, PopupMap.class);
                                intent.putExtra("v1", v1);
                                intent.putExtra("v2", v2);
                                intent.putExtra("location", "SouthPark Mall");
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
                        img1.setVisibility(View.VISIBLE);
                        img2.setVisibility(View.VISIBLE);
                        img3.setVisibility(View.VISIBLE);
                        img4.setVisibility(View.VISIBLE);
                        img5.setVisibility(View.VISIBLE);
                        cabcin_map.setVisibility(View.VISIBLE);
                        cabcin_phone_call.setVisibility(View.VISIBLE);
                        d2.setVisibility(View.VISIBLE);


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

                                double v1 = 10.665178570504835;
                                double v2 = -61.53032269639846;

                                Intent intent = new Intent(Theaters.this, PopupMap.class);
                                intent.putExtra("v1", v1);
                                intent.putExtra("v2", v2);
                                intent.putExtra("location", "Trincity Mall");
                                startActivity(intent);
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

                                double v1 = 10.531096615688952;
                                double v2 = -61.40767756686947;

                                Intent intent = new Intent(Theaters.this, PopupMap.class);
                                intent.putExtra("v1", v1);
                                intent.putExtra("v2", v2);
                                intent.putExtra("location", "Trincity Mall");
                                startActivity(intent);
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

                                double v1 = 10.282421975516769;
                                double v2 = -61.43710923730376;

                                Intent intent = new Intent(Theaters.this, PopupMap.class);
                                intent.putExtra("v1", v1);
                                intent.putExtra("v2", v2);
                                intent.putExtra("location", "Trincity Mall");
                                startActivity(intent);
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

                                double v1 = 11.168306256399358;
                                double v2 = -60.77883910548481;

                                Intent intent = new Intent(Theaters.this, PopupMap.class);
                                intent.putExtra("v1", v1);
                                intent.putExtra("v2", v2);
                                intent.putExtra("location", "Trincity Mall");
                                startActivity(intent);
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

    public void Imax_ShowPopUp(){
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

    public void Imax_Show(){
        imax_title.setText("Port of Spain");
        imax_address.setText("One Woodbrook Place, Port of Spain.");
        imax_num.setText("1 868 299 4629");
        imax_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double v1 = 10.668737823359455;
                double v2 = -61.5275396742442;

                Intent intent = new Intent(Theaters.this, PopupMap.class);
                intent.putExtra("v1", v1);
                intent.putExtra("v2", v2);
                intent.putExtra("location", "Trincity Mall");
                startActivity(intent);
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


// Permissions for Making a Call
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