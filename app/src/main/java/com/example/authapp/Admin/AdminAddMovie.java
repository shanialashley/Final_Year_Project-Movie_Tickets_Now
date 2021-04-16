package com.example.authapp.Admin;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.authapp.Model.Movies;
import com.example.authapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminAddMovie extends AppCompatActivity {

    private String title;
    private String description;
    private String genre;
    private String thumbnail_url;
    private String length;
    private String rating;
    private String starring;
    private String directors;
    private String trailer_link;
    private String cover_url;
    private String type;
    String last_key;
    int movieid;

    private Boolean thumbnailSelected = false;
    private Boolean coverphotoSelected = false;

    int Image_Request_Code = 7;

    StorageReference mstorageRef;
    StorageTask mUplpoadTask;
    DatabaseReference referencemovie;

    private EditText am_title, am_description, am_genre, am_length, am_rating, am_starring, am_directors, am_thumbnail, am_coverphoto, am_trailer_link;
    private ImageView am_thumbnail_img, am_coverphoto_img;
    private Spinner am_typeS;
    private Button am_add, am_uploadThumbnail, am_uploadCover;

    private Uri imageUri, thumbnailUri, coverUri;
    private String thumbnailUid;
    private String coverUid;
    AlertDialog.Builder builder;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_movie);

        referencemovie = FirebaseDatabase.getInstance().getReference().child("Movies");
        mstorageRef = FirebaseStorage.getInstance().getReference().child("Movies");

        ToolbarInfo();
        init();

    }

    public void ToolbarInfo(){

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String s = "Add Movie";
        getSupportActionBar().setTitle(s);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    public void init() {

        am_title = findViewById(R.id.movie_title);
        am_description = findViewById(R.id.movie_description);
        am_genre = findViewById(R.id.movie_genre);
        am_length = findViewById(R.id.movie_length);
        am_rating = findViewById(R.id.movie_rating);
        am_starring = findViewById(R.id.movie_starring);
        am_directors = findViewById(R.id.movie_directors);
        am_typeS = findViewById(R.id.movie_type_S);
        am_trailer_link = findViewById(R.id.movie_trailer_link);
        am_thumbnail = findViewById(R.id.movie_thumbnail_title);
        am_coverphoto = findViewById(R.id.movie_cover_title);
        am_coverphoto_img = findViewById(R.id.movie_cover);
        am_thumbnail_img = findViewById(R.id.movie_thumbnail);
        am_uploadCover = findViewById(R.id.movie_upload_cover);
        am_uploadThumbnail = findViewById(R.id.movie_upload_thumbnail);
        am_add = findViewById(R.id.movie_submit);

        List<String> tlist = new ArrayList<>();
        tlist.add("current");
        tlist.add("upcoming");


        ArrayAdapter<String> dataAdp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tlist);
        dataAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        am_typeS.setAdapter(dataAdp);

        am_typeS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position == 0){
                    type = "current";
                }

                if(position == 1){
                    type = "upcoming";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Toast.makeText(AdminAddMovie.this, "Please Select a Value!", Toast.LENGTH_SHORT).show();

            }
        });

        am_uploadThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser();
                thumbnailSelected = true;
            }
        });

        am_uploadCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser();
                coverphotoSelected = true;
            }
        });

        am_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUplpoadTask != null && mUplpoadTask.isInProgress()){
                    Toast.makeText(AdminAddMovie.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }else{
                    Fileuploader();
                }
            }
        });

        builder = new AlertDialog.Builder(this);

    }

    public void GetInfo(){

        title = am_title.getText().toString().trim();
        description = am_description.getText().toString().trim();
        genre = am_genre.getText().toString().trim();
        length = am_length.getText().toString().trim();
        rating = am_rating.getText().toString().trim();
        starring = am_starring.getText().toString().trim();
        directors = am_directors.getText().toString().trim();
        trailer_link = am_trailer_link.getText().toString().trim();


    }

    public String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }

    private void Fileuploader() {


        if (thumbnailUri != null || coverUri != null) {

            String imageN =  am_title.getText().toString().replaceAll("\\s", "_");
            thumbnailUid = System.currentTimeMillis()+"."+getExtension(thumbnailUri);
            coverUid = System.currentTimeMillis()+"."+getExtension(coverUri);

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            Query lastQuery =  referencemovie.orderByKey().limitToLast(1);

            lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot ss: snapshot.getChildren())
                    {
                        last_key = ss.getKey();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });





            if (!TextUtils.isEmpty(last_key) && TextUtils.isDigitsOnly(last_key)) {
                movieid = Integer.parseInt(last_key) + 1;
            } else {
                movieid = 0;
            }

            if(movieid != 0) {

                DatabaseReference newpost = referencemovie.child(String.valueOf(movieid));
                am_thumbnail.setText(thumbnailUid);
                am_coverphoto.setText(coverUid);
                GetInfo();
                Movies movie = new Movies();

                StorageReference Ref = mstorageRef.child("Thumbnail/" + imageN + "_thumbnail");
                mUplpoadTask = Ref.putFile(thumbnailUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                thumbnail_url = task.getResult().toString();

                                movie.setThumbnail_url(thumbnail_url);
                                newpost.child("thumbnail_url").setValue(thumbnail_url);

                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AdminAddMovie.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });


                StorageReference Ref2 = mstorageRef.child("CoverPhoto/" + imageN + "_cover");
                mUplpoadTask = Ref2.putFile(coverUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();

                        Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                cover_url = task.getResult().toString();
                                movie.setCover_url(cover_url);
                                newpost.child("cover_url").setValue(cover_url);
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(AdminAddMovie.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                                        .getTotalByteCount());
                                progressDialog.setMessage("Uploaded " + (int) progress + "%");
                            }
                        });

                movie.setTitle(title);
                movie.setTitle(description);
                movie.setGenre(genre);
                movie.setLength(length);
                movie.setRating(rating);
                movie.setStarring(starring);
                movie.setDirectors(directors);
                movie.setType(type);
                movie.setTrailer_link(trailer_link);

                newpost.child("title").setValue(title);
                newpost.child("description").setValue(description);
                newpost.child("genre").setValue(genre);
                newpost.child("length").setValue(length);
                newpost.child("rating").setValue(rating);
                newpost.child("starring").setValue(starring);
                newpost.child("directors").setValue(directors);
                newpost.child("type").setValue(type);
                newpost.child("trailer_link").setValue(trailer_link);

                if(type.equals("current")) {
                    String key = title.replaceAll("\\s", "_") + "_" + movieid;
                    builder.setMessage("Do you want to add the Time Schedule Now?")
                            .setTitle("Add Times")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent in = new Intent(AdminAddMovie.this, AdminTimeSchedule.class);
                                    Bundle data = new Bundle();
                                    data.putString("TimeS_Key", key);
                                    in.putExtras(data);
                                    startActivity(in);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    Toast.makeText(AdminAddMovie.this, "Movie Added",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.show();
                }else{
                    Toast.makeText(AdminAddMovie.this, "Movie Added",
                            Toast.LENGTH_SHORT).show();
                }


            }

        }
    }

    private void FileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(thumbnailSelected == true){
            if(requestCode == Image_Request_Code && resultCode == RESULT_OK
                    && data != null && data.getData() != null )
            {
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    am_thumbnail_img.setImageBitmap(bitmap);
                    Bitmap thumbnailBitmap = bitmap;
                    thumbnailUri = imageUri;
                    thumbnailSelected = false;

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        if(coverphotoSelected == true){
            if(requestCode == Image_Request_Code && resultCode == RESULT_OK
                    && data != null && data.getData() != null )
            {
                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    am_coverphoto_img.setImageBitmap(bitmap);
                    Bitmap coverBitmap = bitmap;
                    coverUri = imageUri;
                    coverphotoSelected = false;


                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

}