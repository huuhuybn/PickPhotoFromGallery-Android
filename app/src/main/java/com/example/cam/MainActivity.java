package com.example.cam;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.BitSet;

public class MainActivity extends AppCompatActivity {

    // mp3 , wav, wma ,ogg ......
    // mp4, avi , mkv (codec - bo giai ma),...

    MediaPlayer mediaPlayer;
    String url = "https://data.chiasenhac.com/down2/2261/2/2260237-44280fbe/128/Phao%20Hong%20-%20Dat%20Long%20Vinh.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.play).setOnClickListener(v -> {
            mediaPlayer = MediaPlayer.create(this, Uri.parse(url));
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!mediaPlayer.isPlaying()) mediaPlayer.start();

            // 1
            int duration = mediaPlayer.getDuration(); // 3 x 60 = 180s
            int current = mediaPlayer.getCurrentPosition();

        });

        // Camera Intent xxxxxx
        // Camera API
        findViewById(R.id.capture).setOnClickListener(v -> {
            try {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, 999);
            } catch (Exception exp) {
                Log.i("Error", exp.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                ImageView imageView = findViewById(R.id.thumb);
                imageView.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }


        }
    }
}