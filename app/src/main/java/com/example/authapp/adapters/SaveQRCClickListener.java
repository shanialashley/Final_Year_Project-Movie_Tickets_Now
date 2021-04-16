package com.example.authapp.adapters;

import android.graphics.Bitmap;
import android.view.View;

import com.example.authapp.Model.QRCode;

public interface SaveQRCClickListener {

    void onSave(String ticket_name, View v, QRCode qrCode);
    Bitmap getBitmapOfTickets(View v);
}
