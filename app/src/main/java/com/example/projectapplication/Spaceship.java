package com.example.projectapplication;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.view.MotionEvent;

import java.time.LocalTime;
import java.util.ArrayList;

public class Spaceship extends Entity{

private long lastTime = 0;
    public Spaceship(Context context, int screenX, int screenY){

        rectF = new RectF();


        x = screenX / 2;
        y =  screenY * 9/10;

        speed = 350;
        currentBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.spaceship1);
        length = screenX/6;
        height = screenX/6;

        // stretch the bitmap to a size appropriate for the screen resolution
        currentBitmap = Bitmap.createScaledBitmap(currentBitmap,
                (int) (length),
                (int) (height),
                false);

        this.screenX = screenX;
        this.screenY = screenY;
    }



    public void update(long fps){
        rectF.top = y;
        rectF.bottom = y + height;
        rectF.left = x;
        rectF.right = x + length;
    }


    public RectF getActualRect(){
        //0.43 is the difference between actual size and png size
        int diffx= (int)(0.43 * getLength());
        int diffy =(int)(0.31 * getHeight());
        return new RectF(getX()+ diffx/2, getY()+ diffy/2,
                getX()+getLength()-diffx/2, getY()+getHeight()-diffy/2);
    }
    public void move(MotionEvent motionEvent) {
        setX((int)
                (motionEvent.getX() - getLength() /2));
        setY((int)
                (motionEvent.getY() - getHeight()/2));
    }

    public boolean shoot(ArrayList<Bullet> bulletList, Context context, Spaceship spaceShip) {
        if (LocalTime.now().toNanoOfDay() / 1000000 - lastTime >= 1000) {
            bulletList.add(new Bullet(context, screenY, screenX,MovingState.UP,spaceShip.getX()+( spaceShip.getLength() /6)
                    , spaceShip.getY() + spaceShip.getHeight() / 2));
            bulletList.get(bulletList.size() - 1).shoot();

            lastTime = LocalTime.now().toNanoOfDay() / 1000000;
            return true;
        }
        else return false;
    }


}
