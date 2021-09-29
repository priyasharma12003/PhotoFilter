package com.example.projectc1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.drjacky.imagepicker.ImagePicker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static {
        System.loadLibrary("NativeImageProcessor");
    }


    ImageView image,filter1,filter2,filter3,filter4,filter5,filter6;
    FloatingActionButton fab;
    Bitmap originalbitmap;
    Button save;
    OutputStream mOutputStream;
    Bitmap discard;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            Uri uri = data.getData();
            image.setImageURI(uri);
            filter1.setImageURI(uri);
            filter2.setImageURI(uri);
            filter3.setImageURI(uri);
            filter4.setImageURI(uri);
            filter5.setImageURI(uri);
            filter6.setImageURI(uri);
        try {
            discard =MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image=findViewById(R.id.img);
        filter1=findViewById(R.id.filter1);
        filter2=findViewById(R.id.filter2);
        filter3=findViewById(R.id.filter3);
        filter4=findViewById(R.id.filter4);
        filter5=findViewById(R.id.filter5);
        filter6=findViewById(R.id.filter6);
        fab=findViewById(R.id.floatingActionButton);
        save=findViewById(R.id.save);

        filter1.setOnClickListener(this);
        filter2.setOnClickListener(this);
        filter3.setOnClickListener(this);
        filter4.setOnClickListener(this);
        filter5.setOnClickListener(this);
        filter6.setOnClickListener(this);

        BitmapDrawable drawable=(BitmapDrawable)image.getDrawable();
        originalbitmap=drawable.getBitmap();
        discard =originalbitmap;

fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ImagePicker.Companion.with(MainActivity.this)
//                        .crop()	    			//Crop image
//                        .cropOval()	    		//Allow dimmed layer to have a circle inside
//                        .cropFreeStyle()	    //Let the user to resize crop bounds
//                         .galleryOnly()          //We have to define what image provider we want to use
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080
                .start();
    }
});

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable drawable1 = (BitmapDrawable)image.getDrawable();
                Bitmap bitmap = drawable1.getBitmap();
                originalbitmap=bitmap;
                File filepath= Environment.getExternalStorageDirectory();
                File dir=new File(filepath.getAbsolutePath()+"/Demo/");
                dir.mkdir();
                File file=new File(dir,System.currentTimeMillis()+".jpg");
                try {
                    mOutputStream=new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                originalbitmap.compress(Bitmap.CompressFormat.JPEG,100,mOutputStream);
                Toast.makeText(getApplicationContext(), "Image Saved To Gallery", Toast.LENGTH_SHORT).show();
                try {
                    mOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.discard, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        image.setImageBitmap(discard);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        save.setVisibility(View.VISIBLE);
        switch (view.getId()) {
            case R.id.filter1:
                Filter myFilter = new Filter();
                myFilter.addSubFilter(new SaturationSubFilter(1.3f));
                myFilter.addSubFilter(new ContrastSubFilter(1.2f));
                //BitmapDrawable bitmapDrawable=(BitmapDrawable)image.getDrawable();
                //Bitmap bitmap=bitmapDrawable.getBitmap();
                Bitmap inputImage=discard.copy(Bitmap.Config.ARGB_8888,true);
                Bitmap outputImage = myFilter.processFilter(inputImage);

                image.setImageBitmap(outputImage);
                break;

            case R.id.filter2:
                Filter fooFilter = SampleFilters.getBlueMessFilter();
              //  BitmapDrawable bitmapDrawable1=(BitmapDrawable)image.getDrawable();
                //Bitmap bitmap1=bitmapDrawable1.getBitmap();
                Bitmap inputImage2= discard.copy(Bitmap.Config.ARGB_8888,true);
                Bitmap outputImage2 = fooFilter.processFilter(inputImage2);
                image.setImageBitmap(outputImage2);
                break;

            case R.id.filter3:
                Filter fooFilter3 = SampleFilters.getLimeStutterFilter();
                //BitmapDrawable bitmapDrawable2=(BitmapDrawable)image.getDrawable();
                //Bitmap bitmap2=bitmapDrawable2.getBitmap();
                Bitmap inputImage3= discard.copy(Bitmap.Config.ARGB_8888,true);
                Bitmap outputImage3 = fooFilter3.processFilter(inputImage3);
                image.setImageBitmap(outputImage3);
                break;

            case R.id.filter4:
                Filter fooFilter4 = SampleFilters.getNightWhisperFilter();
                //BitmapDrawable bitmapDrawable3=(BitmapDrawable)image.getDrawable();
               //Bitmap bitmap3=bitmapDrawable3.getBitmap();
                Bitmap inputImage4= discard.copy(Bitmap.Config.ARGB_8888,true);
                Bitmap outputImage4 = fooFilter4.processFilter(inputImage4);
                image.setImageBitmap(outputImage4);
                break;

            case R.id.filter5:
                Filter fooFilter5 = SampleFilters.getStarLitFilter();
                //BitmapDrawable bitmapDrawable3=(BitmapDrawable)image.getDrawable();
                //Bitmap bitmap3=bitmapDrawable3.getBitmap();
                Bitmap inputImage5= discard.copy(Bitmap.Config.ARGB_8888,true);
                Bitmap outputImage5 = fooFilter5.processFilter(inputImage5);
                image.setImageBitmap(outputImage5);
                break;

            case R.id.filter6:
                Filter fooFilter6 = SampleFilters.getAweStruckVibeFilter();
                //BitmapDrawable bitmapDrawable3=(BitmapDrawable)image.getDrawable();
                //Bitmap bitmap3=bitmapDrawable3.getBitmap();
                Bitmap inputImage6= discard.copy(Bitmap.Config.ARGB_8888,true);
                Bitmap outputImage6 = fooFilter6.processFilter(inputImage6);
                image.setImageBitmap(outputImage6);
                break;


        }

    }
}