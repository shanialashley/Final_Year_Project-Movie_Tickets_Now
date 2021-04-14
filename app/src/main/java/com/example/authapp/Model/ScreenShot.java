package com.example.authapp.Model;

import android.graphics.Bitmap;
import android.view.View;

public class ScreenShot {

    public static Bitmap takeScreenShot(View v){
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static Bitmap takeScreenShotOfQRC(View view){
        return takeScreenShot(view.getRootView());
    }


}
