package com.example.authapp.Admin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.authapp.R;
import com.google.zxing.Result;

public class AdminQRCScanner extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView scannerView;
    TextView qrc_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_qrc_scanner);

        init();
    }

    private void init() {

        scannerView = findViewById(R.id.scannerV);
        qrc_result = findViewById(R.id.qrc_result);

        codeScanner = new CodeScanner(AdminQRCScanner.this, scannerView);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        qrc_result.setText(result.getText());
                    }
                });

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        codeScanner.startPreview();
    }
}