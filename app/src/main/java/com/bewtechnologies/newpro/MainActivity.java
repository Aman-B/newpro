package com.bewtechnologies.newpro;

import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;


public class MainActivity extends AppCompatActivity {

    private Button button_save,button_clear;
    private GestureOverlayView gesture;
     static int n=100;
    Bitmap gestureImg;
    Uri imguri;


    @Override
    protected void onResume() {
        super.onResume();

        gesture.buildDrawingCache(true);
        gesture.setDrawingCacheEnabled(true);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Here? :","yes");


        gesture = (GestureOverlayView) findViewById(R.id.gestures);
        button_save = (Button) findViewById(R.id.save_button);
        button_clear =(Button) findViewById(R.id.button);

        gesture.buildDrawingCache(true);
        gesture.setDrawingCacheEnabled(true);



        button_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    n++;
                 /*   Bitmap gestureImg = gesture.getGesture().toBitmap(100, 100,
                            8, Color.WHITE);*/

                    gestureImg = gesture.getDrawingCache();
                  /*  if(gestureImg==null)
                    {
                        gesture.setBackgroundColor(Color.WHITE);
                        gestureImg = gesture.getGesture().toBitmap(100, 100,
                                8, Color.BLACK);

                    }*/
                    Log.i("Tag:", "gestureimg "+gestureImg);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    gestureImg.compress(Bitmap.CompressFormat.PNG, 100, bos);
                    byte[] bArray = bos.toByteArray();
                    imguri=saveimg(gesture,gestureImg);
                  //  gesture.setDrawingCacheEnabled(false);
                    Intent intent = new Intent(MainActivity.this, Activity2.class);

                    intent.putExtra("draw", bArray);
                    startActivity(intent);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "No draw on the string",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        //clear

        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gesture.cancelClearAnimation();
                gesture.clear(true);
            }
        });


    }

    private void convertingImg() {

        try {
            n++;
                 /*   Bitmap gestureImg = gesture.getGesture().toBitmap(100, 100,
                            8, Color.WHITE);*/

            gestureImg = gesture.getDrawingCache();
                  /*  if(gestureImg==null)
                    {
                        gesture.setBackgroundColor(Color.WHITE);
                        gestureImg = gesture.getGesture().toBitmap(100, 100,
                                8, Color.BLACK);

                    }*/
            Log.i("Tag:", "gestureimg "+gestureImg);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            gestureImg.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            imguri=saveimg(gesture,gestureImg);
            //  gesture.setDrawingCacheEnabled(false);
            Intent intent = new Intent(MainActivity.this, Activity2.class);

            intent.putExtra("draw", bArray);
            startActivity(intent);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "No draw on the string",
                    Toast.LENGTH_SHORT).show();
        }


    }

    private Uri saveimg(GestureOverlayView gesture, Bitmap b2) {
        String name = "image"+n;
        String s="here : ";
        Bitmap bitmap = gesture.getDrawingCache();


        s=s+bitmap;


        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(path + "/check1");
        if(!myDir.exists()) {
            myDir.mkdirs();
        }
        //File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), name);

        File file=new File(myDir,name);
        s=s+path+file;
        try
        {
            if(!file.exists())
            {
              file.createNewFile();

            }
            if(file.exists())
            {
                file.delete();
            }
            s= s+path+file+file.createNewFile();
            if(!file.createNewFile())
            {
                Toast.makeText(getApplicationContext(),"file cant be saved : " +s,Toast.LENGTH_SHORT).show();
            }
            FileOutputStream ostream = new FileOutputStream(file);
             b2.compress(Bitmap.CompressFormat.PNG, 100, ostream);
            ostream.flush();
            ostream.close();
          /*  gesture.invalidate();*/
            System.out.println("Our string : "+s);

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"file cant be saved :"+ s,Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }finally
        {
        /* gesture.clearAnimation();
            gesture.clear(true);*/
            gesture.invalidate();
            gesture.setDrawingCacheEnabled(false);




        }
        return Uri.fromFile(file);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id== R.id.share)
        {


            convertingImg();




            Toast.makeText(getApplicationContext(),"Saved image!",Toast.LENGTH_SHORT).show();

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM,imguri);
            shareIntent.setType("image/png");
            startActivity(Intent.createChooser(shareIntent,"Share it on :"));

        }


        return super.onOptionsItemSelected(item);
    }



}
