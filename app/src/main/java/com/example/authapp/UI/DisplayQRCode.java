package com.example.authapp.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.BuildConfig;
import com.example.authapp.Model.QRCode;
import com.example.authapp.R;
import com.example.authapp.adapters.RecycleViewQRCode;
import com.example.authapp.adapters.SaveQRCClickListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DisplayQRCode extends AppCompatActivity implements SaveQRCClickListener {
    String title, theater_title, date, time, ticket_type;
    Button d_qr_return;
    int sT, aT, cT;
    RecyclerView qrc_recycleV;
    List<QRCode> qr_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_q_r_code);

        init();

    }

    private void init() {
        qrc_recycleV = findViewById(R.id.d_qrc_recycleV);
        title = getIntent().getExtras().getString("title");
        theater_title = getIntent().getExtras().getString("theater_title");
        date = getIntent().getExtras().getString("date");
        time = getIntent().getExtras().getString("time");
        sT = getIntent().getExtras().getInt("seniorT");
        aT = getIntent().getExtras().getInt("adultT");
        cT = getIntent().getExtras().getInt("childT");
        d_qr_return = findViewById(R.id.d_qrc_return);
        d_qr_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DisplayQRCode.this, Home.class));

            }
        });

        qr_list = new ArrayList<>();

        if(sT != 0){
            for(int i=1; i<=sT; i++){
                ticket_type = "Senior";
                qr_list.add( new QRCode(theater_title, title, date, time, ticket_type));
            }
        }

        if(aT != 0){
            for(int i=1; i<=aT; i++){
                ticket_type = "Adult";
                qr_list.add( new QRCode(theater_title, title, date, time, ticket_type));
            }
        }

        if(cT != 0){
            for(int i=1; i<=cT; i++){
                ticket_type = "Child";
                qr_list.add( new QRCode(theater_title, title, date, time, ticket_type));
            }
        }

        RecycleViewQRCode QRCodeAdp = new RecycleViewQRCode(this, qr_list, DisplayQRCode.this);
        qrc_recycleV.setAdapter(QRCodeAdp);
        qrc_recycleV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    @SuppressLint("ResourceAsColor")
    public Bitmap getBitmapOfTickets(View v){

        Bitmap ticket = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ticket);
        Drawable ticketDrawable = v.getBackground();
        if (ticketDrawable != null) {
            v.draw(canvas);
        }else{
            canvas.drawColor(R.color.green);
        }

        return ticket;
    }

    @Override
    public void onSave(String ticket_name, View v){
        Bitmap b = getBitmapOfTickets(v);
        try{
            File file = new File(this.getExternalCacheDir(), ticket_name);
            FileOutputStream fout = new FileOutputStream(file);
            Uri uri = FileProvider.getUriForFile(
                    this,
                    BuildConfig.APPLICATION_ID + "." + getLocalClassName() + ".provider",
                    file);
            b.compress(Bitmap.CompressFormat.PNG, 100, fout);
            fout.flush();
            fout.close();
            file.setReadable(true, false);

            Intent in = new Intent(Intent.ACTION_SEND);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            in.putExtra(Intent.EXTRA_STREAM, uri);
            in.setType("image/png");
            startActivity(Intent.createChooser(in, "Save by"));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void saveToDB(Bitmap bm, QRCode qrCode) {

        DatabaseReference addR = FirebaseDatabase.getInstance().getReference("QRC_Purchased").child(qrCode.getTitle()).child(qrCode.getId_code());
        StorageReference storeR = FirebaseStorage.getInstance().getReference("QRC").child("QRC_Purchased/" + qrCode.getTheater_title() + "/" + qrCode.getId_code());

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, bao); // bmp is bitmap from user image file
            bm.recycle();



        byte[] byteArray = bao.toByteArray();

        UploadTask uploadTask = storeR.putBytes(byteArray);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String url = task.getResult().toString();

                        qrCode.setQrc_img(url);
                        addR.child("qrc_img").setValue(url);

                    }
                });
            }
        });


        addR.child("theater_title").setValue(qrCode.getTheater_title());
        addR.child("title").setValue(qrCode.getTitle());
        addR.child("date").setValue(qrCode.getDate());
        addR.child("time").setValue(qrCode.getTime());
        addR.child("ticket_type").setValue(qrCode.getTicket_type());
        addR.child("id_code").setValue(qrCode.getId_code());

    }


}

