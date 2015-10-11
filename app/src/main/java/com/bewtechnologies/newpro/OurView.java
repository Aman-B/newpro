package com.bewtechnologies.newpro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Aman  on 10/11/2015.
 */
public class OurView extends SurfaceView implements Runnable {

    Canvas c;
    Drawable d;
    Thread t;
    SurfaceHolder holder;
    boolean allgood=false;
    Bitmap caughtghost;
    float x,y;


    public OurView(Context context, Bitmap ghost) {
        super(context);
        caughtghost=ghost;
        holder = getHolder();
        x=y=10;
    }

    @Override
    public void run() {

        while(allgood==true)
        {
            if(!holder.getSurface().isValid())
            {
                continue;
            }

            c = holder.lockCanvas();

            //Setting background image for canvas
            d = getResources().getDrawable(R.drawable.w_bg);
            d.setBounds(getLeft(),getTop(),getRight(),getBottom());
            d.draw(c);


            //putting ghost on canvas
            c.drawBitmap(caughtghost, x, y, null);

            holder.unlockCanvasAndPost(c);


            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            c= holder.lockCanvas();
            c.drawColor(Color.WHITE);
            d.draw(c);
            holder.unlockCanvasAndPost(c);

            break;
        }

    }

    public void pause(){
        allgood=false;
        while(true){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
      t= null;
    }

    public void resume(){
        allgood=true;

        t= new Thread(this);
        t.start();


    }

}
