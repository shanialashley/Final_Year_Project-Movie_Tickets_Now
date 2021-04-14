package com.example.authapp.adapters;

import android.graphics.Bitmap;
import android.view.View;

public interface SaveQRCClickListener {

    void onSave(String ticket_name, View v);
    Bitmap getBitmapOfTickets(View v);
}
