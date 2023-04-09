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

        length = screenX/6;
        height = screenY/10;

        x = screenX / 2;
        y =  screenY * 9/10;

        speed = 350;
        bitmapup = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship1);

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
