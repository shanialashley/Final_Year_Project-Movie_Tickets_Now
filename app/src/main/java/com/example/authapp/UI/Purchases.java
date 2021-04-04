package com.example.authapp.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.authapp.Config.Config;
import com.example.authapp.DisplayQRCode;
import com.example.authapp.R;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

public class Purchases extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView p_title, p_date, p_time, p_seniorT, p_adultT, p_childT, p_total;
    private Button paypayB, creditB;
    private String title, date, time;
    private int seniorT, adultT, childT, totalA;

    private int PAYPAL_REQ_CODE = 12;
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
        p_seniorT.setText("Number of Senior Tickets: " + seniorT);

        p_adultT = findViewById(R.id.p_adultT);
        adultT = getIntent().getExtras().getInt("adult_tickets", 0);
        p_adultT.setText("Number of Adult Tickets: " + adultT);

        p_childT = findViewById(R.id.p_childT);
        childT = getIntent().getExtras().getInt("child_tickets", 0);
        p_childT.setText("Number of Children Tickets: " + childT);

        p_total = findViewById(R.id.p_totalA);
        totalA = getIntent().getExtras().getInt("Total", 0);
        p_total.setText("Total Cost: $" + totalA + ".00");

        paypayB = findViewById(R.id.p_paypalB);
        creditB = findViewById(R.id.p_creditB);

        //New Service
        Intent in = new Intent(Purchases.this, PayPalService.class);
        in.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        startService(in);


        paypayB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PayPalPaymentMethod();
            }
        });


    }

    private void PayPalPaymentMethod() {

        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(totalA ), "USD", "Movie Tickets Now", PayPalPayment.PAYMENT_INTENT_SALE);

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
                startActivity(display);

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
}