package com.example.authapp.Admin;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class AdminUpdateMovie extends AppCompatActivity {

    private EditText upd_id, upd_title, upd_description, upd_genre,
            upd_length, upd_rating, upd_starring, upd_directors,
            upd_trailer_link, upd_thumbnail_title, upd_cover_title, upd_type;
    private ImageView upd_thumbnail_img, upd_cover_img;

    private Button upd_displayB, upd_updateB, upd_deleteB;
    DatabaseReference referencemovie, update;
    StorageReference mstorageRef;
    StorageTask mUplpoadTask = null;

    private Uri imageUri, thumbnailUri, coverUri;
    private String thumbnailUid;
    private String coverUid;

    private Boolean thumbnailSelected = false;
    private Boolean coverphotoSelected = false;

    int Image_Request_Code = 7;
    Movies m ;
    private String id;
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
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_movie);

        mstorageRef = FirebaseStorage.getInstance().getReference().child("Movies");
        referencemovie = FirebaseDatabase.getInstance().getReference().child("Movies");
        ToolbarInfo();
        init();

    }

    public void ToolbarInfo(){

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String s = "Update Movies";
        getSupportActionBar().setTitle(s);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void init() {

        upd_id = findViewById(R.id.upd_movie_id);
        upd_displayB = findViewById(R.id.upd_movie_display);
        upd_displayB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayByID();
            }
        });

        upd_title = findViewById(R.id.upd_movie_title);
        upd_description = findViewById(R.id.upd_movie_description);
        upd_genre = findViewById(R.id.upd_movie_genre);
        upd_length = findViewById(R.id.upd_movie_length);
        upd_rating = findViewById(R.id.upd_movie_rating);
        upd_starring = findViewById(R.id.udp_movie_starring);
        upd_directors = findViewById(R.id.udp_movie_directors);
        upd_trailer_link = findViewById(R.id.upd_movie_trailer_link);
        upd_type = findViewById(R.id.upd_movie_type);
        upd_thumbnail_title = findViewById(R.id.upd_movie_thumbnail_title);
        upd_cover_title = findViewById(R.id.upd_movie_cover_title);



        upd_cover_img = findViewById(R.id.upd_movie_cover);
        upd_cover_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser();
                coverphotoSelected = true;
            }
        });

        upd_thumbnail_img = findViewById(R.id.upd_movie_thumbnail);
        upd_thumbnail_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChooser();
                thumbnailSelected = true;
            }
        });

        upd_updateB = findViewById(R.id.upd_movie_update);
        upd_updateB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mUplpoadTask != null && mUplpoadTask.isInProgress()){
                    Toast.makeText(AdminUpdateMovie.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                }else{
                    UpdateInfo();
                }
            }
        });

        upd_deleteB = findViewById(R.id.upd_movie_delete);
        upd_deleteB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteInfo();
            }
        });

    }

    public void DisplayInfo(Movies m){

        upd_title.setText(m.getTitle());
        upd_description.setText(m.getDescription());
        upd_genre.setText(m.getGenre());
        upd_length.setText(m.getLength());
        upd_rating.setText(m.getRating());
        upd_starring.setText(m.getStarring());
        upd_directors.setText(m.getDirectors());
        upd_trailer_link.setText(m.getTrailer_link());
        upd_type.setText(m.getType());

        if(m.getThumbnail_url() != null){
            upd_thumbnail_title.setText(m.getThumbnail_url());
        }

        if(m.getCover_url()!= null) {
            upd_cover_title.setText(m.getCover_url());
        }
        Picasso.get().load(m.getThumbnail_url()).into(upd_thumbnail_img);
        Picasso.get().load(m.getCover_url()).into(upd_cover_img);

    }

    public void GetInfo(){

        id = upd_id.getText().toString().trim();
        title = upd_title.getText().toString().trim();
        description = upd_description.getText().toString().trim();
        genre = upd_genre.getText().toString().trim();
        length = upd_length.getText().toString().trim();
        rating = upd_rating.getText().toString().trim();
        starring = upd_starring.getText().toString().trim();
        directors = upd_directors.getText().toString().trim();
        trailer_link = upd_trailer_link.getText().toString().trim();
        type = upd_type.getText().toString().trim();


    }

    public void DisplayByID(){

        id = upd_id.getText().toString().trim();
        if(!id.equals(null) || !id.equals("") || !id.equals(" ") ) {
            DatabaseReference currentmovie = referencemovie.child(id);
            currentmovie.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {

                            m = snapshot.getValue(Movies.class);
                            DisplayInfo(m);

                    } else {
                        Toast.makeText(AdminUpdateMovie.this, "ID does not exist", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            Toast.makeText(this, "Enter Movie Key!", Toast.LENGTH_SHORT).show();
        }

    }

    public void UpdateInfo(){




        final ProgressDialog progressDialog = new ProgressDialog(this);
//            progressDialog.setTitle("Uploading...");
//            progressDialog.show();

        GetInfo();
        Toast.makeText(AdminUpdateMovie.this, "Update Successfully", Toast.LENGTH_SHORT).show();


        update = referencemovie.child(id);

        if(thumbnailSelected == true) {

            String imageN =  title.replaceAll("\\s", "_");
            thumbnailUid = System.currentTimeMillis()+"."+getExtension(thumbnailUri);

            StorageReference Ref = mstorageRef.child("Thumbnails/" + imageN + "_thumbnail");
            mUplpoadTask = Ref.putFile(thumbnailUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(AdminUpdateMovie.this, "Image Upload Successfully", Toast.LENGTH_SHORT).show();
                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {

                            thumbnail_url = task.getResult().toString();
                            update.child("thumbnail_url").setValue(thumbnail_url);

                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AdminUpdateMovie.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot
                            .getTotalByteCount());
                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                }
            });

        }

        if(coverphotoSelected == true) {

            String imageN =  title.replaceAll("\\s", "_");
            coverUid = System.currentTimeMillis()+"."+getExtension(coverUri);

            StorageReference Ref2 = mstorageRef.child("CoverPhoto/" + imageN + "_cover");
            mUplpoadTask = Ref2.putFile(coverUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();

                    Toast.makeText(AdminUpdateMovie.this, "Image Upload Successfully", Toast.LENGTH_SHORT).show();
                    Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            cover_url = task.getResult().toString();
                            update.child("cover_url").setValue(cover_url);
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AdminUpdateMovie.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

        }

//            movie.setTitle(title);
//            movie.setTitle(description);
//            movie.setGenre(genre);
//            movie.setLength(length);
//            movie.setRating(rating);
//            movie.setStarring(starring);
//            movie.setDirectors(directors);
//            movie.setType(type);
//            movie.setTrailer_link(trailer_link);

        if(!title.isEmpty()){
            update.child("title").setValue(title);
        }

        if(!description.isEmpty()){
            update.child("description").setValue(description);
        }

        if(!genre.isEmpty()){
            update.child("genre").setValue(genre);
        }

        if(!length.isEmpty()){
            update.child("length").setValue(length);
        }

        if(!rating.isEmpty()){
            update.child("rating").setValue(rating);
        }

        if(!starring.isEmpty()){
            update.child("starring").setValue(starring);
        }

        if(!directors.isEmpty()){
            update.child("directors").setValue(directors);
        }

        if(!type.isEmpty()){
            update.child("type").setValue(type);
        }else{

        }


        if(!trailer_link.isEmpty()){
            update.child("trailer_link").setValue(trailer_link);
        }

        Toast.makeText(AdminUpdateMovie.this, "Movie Updated", Toast.LENGTH_SHORT).show();





    }

    public String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
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

        if(requestCode == Image_Request_Code && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                upd_thumbnail_img.setImageBitmap(bitmap);
                Bitmap thumbnailBitmap = bitmap;
                thumbnailUri = imageUri;
                thumbnailSelected = false;

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }



        if(requestCode == Image_Request_Code && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                upd_cover_img.setImageBitmap(bitmap);
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

    private void DeleteInfo() {

        referencemovie = FirebaseDatabase.getInstance().getReference().child("Movies");
        id = upd_id.getText().toString().trim();
        DatabaseReference currentmovie = referencemovie.child(id);
        currentmovie.removeValue();
        Toast.makeText(this, "Deleted Movie", Toast.LENGTH_SHORT).show();
    }
}