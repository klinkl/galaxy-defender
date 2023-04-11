package com.example.projectapplication;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class Spaceship extends Entity{


    RectF rect;
    private Bitmap bitmapup;
    public final int STOPPED = 0;

    ///maybe more movement than this
    private int SpaceShipMoving = STOPPED;
    private int speed;

    public Spaceship(Context context, int screenX, int screenY){

        rect = new RectF();


        x = screenX / 2;
        y =  screenY * 9/10;

        speed = 350;
        bitmapup = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship1);
        length = screenX/6;
        height = screenX/6;

        // stretch the bitmap to a size appropriate for the screen resolution
        bitmapup = Bitmap.createScaledBitmap(bitmapup,
                (int) (length),
                (int) (height),
                false);

        //  bitmapup = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceshipup);
        //  bitmapup = Bitmap.createScaledBitmap(bitmapup, (int) (length), (int) (height),false);


        currentBitmap = bitmapup;
        this.screenX = screenX;
        this.screenY = screenY;
    }



    public void update(long fps){
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;
    }


    public RectF getRect(){
        return rect;
    }

    public RectF getActualRect(){
        //0.43 is the difference between actual size and png size
        int diffx= (int)(0.43 * getLength());
        int diffy =(int)(0.31 * getHeight());
        return new RectF(getX()+ diffx/2, getY()+ diffy/2,
                getX()+getLength()-diffx/2, getY()+getHeight()-diffy/2);
    }
    public Bitmap getBitmap(){

        return currentBitmap;
    }

    public float getX(){
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public float getY(){
        return y;
    }
    public void setY(int y){
        this.y = y;
    }
    public float getLength(){
        return length;
    }
    public float getHeight(){
        return height;
    }

}
