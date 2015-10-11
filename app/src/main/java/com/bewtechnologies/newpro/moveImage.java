package com.bewtechnologies.newpro;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Aman  on 10/11/2015.
 */
public class moveImage extends AppCompatActivity {


    OurView v;
    Bitmap ghost;
    float x,y;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ghost= BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        x=y=10;
        v = new OurView(this,ghost);


        setContentView(v);
    }


    @Override
    protected void onResume() {
        super.onResume();
        v.resume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        v.pause();
    }
}
