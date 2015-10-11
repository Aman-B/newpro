package com.bewtechnologies.newpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookSdk;

/**
 * Created by Aman  on 10/3/2015.
 */
public class Start extends AppCompatActivity implements View.OnClickListener {

    Button das,fb,newone;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        FacebookSdk.sdkInitialize(this.getApplicationContext());

        das= (Button) findViewById(R.id.das);
        fb=(Button) findViewById(R.id.fblogin);
        newone=(Button) findViewById(R.id.button2);


        das.setOnClickListener(this);
        fb.setOnClickListener(this);
        newone.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {

        int id; //id of buttons


        id=v.getId();

        switch (id)
        {
            case R.id.das:
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                break;

            case R.id.fblogin:
                 Intent i1 = new Intent(this,fblogin.class);
                 startActivity(i1);
                 break;

            //TODO: put newone's case here:

            case R.id.button2:
                 Intent i2 = new Intent(this,moveImage.class);
                 startActivity(i2);
                 break;

            default:
                Toast.makeText(getApplicationContext(),"Does not compute.",Toast.LENGTH_SHORT).show();
                break;
        }



    }
}
