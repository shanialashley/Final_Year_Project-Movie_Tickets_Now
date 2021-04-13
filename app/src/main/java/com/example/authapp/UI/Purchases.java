package com.example.authapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.authapp.Config.Config;
import com.example.authapp.Model.ReportInfo;
import com.example.authapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Purchases extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView p_theater, p_title, p_date, p_time, p_seniorT, p_adultT, p_childT, p_total;
    private Button paypayB, creditB;
    private DatabaseReference reports;
    private String title = "", date = "", time = "", theater_title = "", key = "", theater = " ", location = " ", movie_type= " ";
    int total_tickets = 0;
     int newTotal = 0;
    private int seniorT, adultT, childT, totalA;

    private int PAYPAL_REQ_CODE = 123;
    private PayPalPayment payPalPayment;
    private PayPalConfiguration payPalConfig = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(Config.PAYPAL_CLIENTS_ID);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases);

        ToolbarInfo();
        init();



    }



    public void ToolbarInfo(){


        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Payment Information");

        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Purchases.this, SelectTickets.class) );
            }
        });

    }

    private void init() {

        p_theater = findViewById(R.id.p_theater);
        theater_title = getIntent().getExtras().getString("theater_title");
        p_theater.setText("Theater: " + theater_title);

        theater = getIntent().getExtras().getString("theater");
        location = getIntent().getExtras().getString("location");
        movie_type = getIntent().getExtras().getString("movie_type");

        p_title = findViewById(R.id.p_title);
        title = getIntent().getExtras().getString("title");
        p_title.setText("Movie Title: " + title);

        p_date = findViewById(R.id.p_date);
        date = getIntent().getExtras().getString("date");
        p_date.setText("Date of Movie: " + date);

        p_time = findViewById(R.id.p_time);
        time = getIntent().getExtras().getString("time");
        p_time.setText("Movie Time: " + time);

        p_seniorT = findViewById(R.id.p_seniorT);
        seniorT = getIntent().getExtras().getInt("senior_tickets", 0);
        p_seniorT.setText("Number of Senior Tickets: " + seniorT );

        p_adultT = findViewById(R.id.p_adultT);
        adultT = getIntent().getExtras().getInt("adult_tickets", 0);
        p_adultT.setText("Number of Adult Tickets: " + adultT);

        p_childT = findViewById(R.id.p_childT);
        childT = getIntent().getExtras().getInt("child_tickets", 0);
        p_childT.setText("Number of Children Tickets: " + childT);

        p_total = findViewById(R.id.p_totalA);
        totalA = getIntent().getExtras().getInt("Total", 0);
        p_total.setText("Total Cost: $" + totalA + ".00");

        total_tickets = getIntent().getExtras().getInt("max_tickets" , 0);


        paypayB = findViewById(R.id.p_paypalB);

        creditB = findViewById(R.id.p_creditB);
        creditB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateReport();
                Intent display = new Intent(Purchases.this, DisplayQRCode.class);
                display.putExtra("theater_title", theater_title);
                display.putExtra("title", title);
                display.putExtra("date", date);
                display.putExtra("time", time);
                display.putExtra("seniorT", seniorT);
                display.putExtra("adultT", adultT);
                display.putExtra("childT", childT);
                startActivity(display);


            }
        });




        paypayB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalPaymentMethod();
            }
        });



    }

    private void PayPalPaymentMethod() {

        //New Service
        Intent in = new Intent(Purchases.this, PayPalService.class);
        in.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        startService(in);

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(totalA ), "USD", "Movie Tickets Now",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(Purchases.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PAYPAL_REQ_CODE){

            if(resultCode == Activity.RESULT_OK){

                Intent display = new Intent(Purchases.this, DisplayQRCode.class);
                display.putExtra("theater_title", theater_title);
                display.putExtra("theater", theater);
                display.putExtra("location", location);
                display.putExtra("movie_type", movie_type);
                display.putExtra("title", title);
                display.putExtra("date", date);
                display.putExtra("time", time);
                display.putExtra("seniorT", seniorT);
                display.putExtra("adultT", adultT);
                display.putExtra("childT", childT);
                startActivity(display);
                UpdateReport();
                Toast.makeText(this, "Payment was successful!", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "Payment was unsuccessful!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(Purchases.this, PayPalService.class));
        super.onDestroy();
    }

    public void UpdateReport(){

        List<ReportInfo> rlist = new ArrayList<>();
        List<String> keylist = new ArrayList<>();

        reports = FirebaseDatabase.getInstance().getReference().child("Reports");
        reports.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    for(DataSnapshot ss: snapshot.getChildren()){
                        ReportInfo rp = ss.getValue(ReportInfo.class);
                                rlist.add(rp);
                                keylist.add(ss.getKey());

                    }


            }else{
                    DatabaseReference addReport = reports.push();
                    addReport.setValue(new ReportInfo(title, date, time, total_tickets,
                            theater, location, movie_type));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

//        if(rlist.isEmpty() && keylist.isEmpty()){
//            DatabaseReference addReport = reports.push();
//            addReport.setValue(new ReportInfo(title, date, time, total_tickets,
//                    theater, location, movie_type));
//        }else{
//            for(int i = 0; i<rlist.size(); i++) {
//                ReportInfo rp = rlist.get(i);
//
//                if (rp.getTitle().equals(title) && rp.getTime().equals(time) &&
//                        rp.getDate().equals(date) && rp.getTheater().equals(theater) &&
//                        rp.getLocation().equals(location) && rp.getMovie_type().equals(movie_type)) {
//                    newTotal = total_tickets + rp.getNum_of_tickets();
//                    Map<String, Object> update = new HashMap<>();
//                    update.put("num_of_tickets", newTotal);
//                    DatabaseReference newupdate = reports.child(keylist.get(i));
//                    newupdate.updateChildren(update);
//                }
//            }
//        }



    }


}