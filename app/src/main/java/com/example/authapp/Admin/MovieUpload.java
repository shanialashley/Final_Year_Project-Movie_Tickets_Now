package com.example.authapp.Admin;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp.Model.Movie;
import com.example.authapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.io.Files;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class MovieUpload extends AppCompatActivity {

    private EditText title, description, genre, thumbnail,
            length, starring, director, trailer_link, cover_photo;
    private Button upload_thumbnail, upload_cover, update, submit;
    private Spinner rating_spinner, category_spinner;
    private String rating, category, imageTitle, coverTitle, thumbnailTitle, thumbnailUrl, coverUrl;
    private final int IMG_REQUEST_ID = 7;
    private Uri thumbnailUri, coverUri, imageUri;

    private String t, d, g, l, s, dir, link;

    FirebaseStorage moviesStorage;
    StorageReference moviesSReference;
    DatabaseReference moviesDb;
    StorageTask uploadMoviesSTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_upload);

        init();
        inToStrings();
        spinnersActivities();
        ThumbnailActivities();
        CoverPhotoActivity();
    }

    public void init(){

        title = findViewById(R.id.movieUpload_title);
        description = findViewById(R.id.movieUpload_description);
        genre = findViewById(R.id.movieUpload_genre);
        thumbnail = findViewById(R.id.movieUpload_thumbnail);
        upload_thumbnail = findViewById(R.id.movie_upload_thumbnail);
        length = findViewById(R.id.movieUpload_length);
        rating_spinner = findViewById(R.id.movieUpload_rating_spinner);
        starring = findViewById(R.id.movieUpload_starring);
        director = findViewById(R.id.movieUpload_directors);
        trailer_link = findViewById(R.id.movieUpload_trailer_link);
        cover_photo = findViewById(R.id.movieUpload_cover);
        upload_cover = findViewById(R.id.movie_upload_cover);
        category_spinner = findViewById(R.id.movieUpload_category_spinner);
        update = findViewById(R.id.movieUpload_update);
        submit = findViewById(R.id.movieUpload_submit);

        moviesDb = FirebaseDatabase.getInstance().getReference().child("Movies");
        moviesSReference = FirebaseStorage.getInstance().getReference().child("Movies");






    }

    public void inToStrings(){

        t = title.getText().toString().trim();
        d = description.getText().toString().trim();
        g = genre.getText().toString().trim();
        l = length.getText().toString().trim();
        s = starring.getText().toString().trim();
        dir = director.getText().toString().trim();
        link = trailer_link.getText().toString().trim();
    }

    public void spinnersActivities(){

        List<String> ratingList = new ArrayList<>();
        ratingList.add("No Rating");
        ratingList.add("G");
        ratingList.add("PG");
        ratingList.add("PG-13");
        ratingList.add("16 Years & Over");
        ratingList.add("R");

        List<String> categoryList = new ArrayList<>();
        categoryList.add("Current");
        categoryList.add("Upcoming");

        ArrayAdapter<String> ratingAdapter = new ArrayAdapter<>(this, android.R.layout. simple_spinner_item, ratingList);
        ratingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rating_spinner.setAdapter(ratingAdapter);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout. simple_spinner_item, categoryList);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(categoryAdapter);

        rating_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rating = parent.getItemAtPosition(position).toString();
                Toast.makeText(MovieUpload.this, "Rating: " + rating, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
                Toast.makeText(MovieUpload.this, "Category: " + category, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void ThumbnailActivities(){

        upload_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestImage();
            }
        });

        thumbnailUri = imageUri;
        thumbnailTitle = imageTitle;
        thumbnail.setText(imageTitle);

    }

    public void CoverPhotoActivity(){

        upload_cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestImage();
            }
        });

        coverUri = imageUri;
        coverTitle = imageTitle;
        cover_photo.setText(imageTitle);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST_ID &&
                resultCode == RESULT_OK && data != null && data.getData() != null){

            imageUri = data.getData();

            String path = "";
            Cursor cursor;
            int column_index_data;
            String[] projection = {MediaStore.MediaColumns.DATA,MediaStore.Images.Media.BUCKET_DISPLAY_NAME
                    ,MediaStore.Images.Media._ID, MediaStore.Images.Thumbnails.DATA};
            final String orderby = MediaStore.Images.Media.DEFAULT_SORT_ORDER;
            cursor = MovieUpload.this.getContentResolver().query(imageUri, projection, null,
                     null, orderby);
            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            while(cursor.moveToNext()){
                path = cursor.getString(column_index_data);
                imageTitle = Files.getNameWithoutExtension(path);
            }



        }
    }

    private void requestImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), IMG_REQUEST_ID);

    }

    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    public void uploadFileToFirebase(View v){

        if(thumbnail.equals("")){
            Toast.makeText(this, "Please Select a Image!", Toast.LENGTH_SHORT).show();
        }else{
            if(uploadMoviesSTask != null && uploadMoviesSTask.isInProgress()){
                Toast.makeText(this, "Image upload is already in progress!", Toast.LENGTH_SHORT).show();
            }else{
                uploadFile();
            }
        }


    }



    private void uploadFile() {



        if(thumbnailUri != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Image Uploading......");
            progressDialog.show();
            final StorageReference storageReference = moviesSReference.child("Thumbnail/" + thumbnailTitle);
            uploadMoviesSTask = storageReference.putFile(thumbnailUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            thumbnailUrl = uri.toString();
                            progressDialog.dismiss();
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            Double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + progress + "%.....");
                        }
                    });
        }else{
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }

        if(coverUri != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Image Uploading......");
            progressDialog.show();
            final StorageReference storageReference = moviesSReference.child("CoverPhoto/" + coverTitle);
            uploadMoviesSTask = storageReference.putFile(coverUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    coverUrl = uri.toString();
                                    progressDialog.dismiss();
                                }
                            });
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            Double progress = (100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + progress + "%.....");
                        }
                    });
        }else{
            Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
        }

        Movie movies = new Movie(t, d, g, thumbnailUrl, coverUrl, link, l, rating, s, dir, category);
        String uploadid = moviesDb.push().getKey();
        moviesDb.child(uploadid).setValue(movies);



    }


}