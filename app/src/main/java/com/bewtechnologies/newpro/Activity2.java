package com.bewtechnologies.newpro;

/**
 * Created by Aman  on 9/27/2015.
 */
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;


public class Activity2 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.display_image);



        ImageView image = (ImageView) findViewById(R.id.image_saved);

        ByteArrayInputStream imageStreamClient = new ByteArrayInputStream(
                getIntent().getExtras().getByteArray("draw"));
        image.setImageBitmap(BitmapFactory.decodeStream(imageStreamClient));



    }

}