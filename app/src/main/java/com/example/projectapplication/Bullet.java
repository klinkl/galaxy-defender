package com.example.projectapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;

public class Bullet {

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    private float x;
    private float y;

    private RectF rect;

    public Bitmap getBitmapBullet() {
        return bitmapBullet;
    }

    private Bitmap bitmapBullet;
    // Which way is it shooting
    public int UP = 0;
    public int DOWN = 1;
    public int RIGHT = 2;
    public int LEFT = 3;
    // Going nowhere
    int heading = -1;
    float speed = 650;
    private int screenY;
    private int screenX;

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    private int width;
    private int height;

    private boolean isActive;

    public Bullet(Context context, int screenY, int screenX, int direction) {

        //  height = screenY / 20;
        heading = direction;
        bitmapBullet = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet1);
        if (heading ==1){
            //flip bitmap
            Matrix matrix = new Matrix();
            matrix.preRotate(180);
            bitmapBullet =Bitmap.createBitmap(bitmapBullet, 0,0,bitmapBullet.getWidth(),bitmapBullet.getHeight(),matrix,true);
        }
        isActive = false;
        this.screenX =screenX;
        this.screenY = screenY;
        this.rect = new RectF();
    }

    public boolean shoot(float startX, float startY) {
        if (!isActive) {

            x = startX;
            y = startY;


            width = getBitmapBullet().getWidth();
            height = getBitmapBullet().getHeight();
            isActive = true;
            return true;
        }

        // Bullet already active
        return false;
    }

    public void update(long fps) {

        // Just move up or down
        if(heading == UP){
            y = y - speed / fps;
        }else if (heading == DOWN){
            y = y + speed / fps;
        }
        else if (heading == RIGHT){
            x = x + speed / fps;
        }

        else
        { x = x - speed / fps;}

        // Update rect
        rect.left = x;
        rect.right = x + width;
        rect.top = y;
        rect.bottom = y + height;
    }

    public RectF getActualRect(){
        int diffx= (int)(0.66 * getWidth());
        int diffy =(int)(0.5 * getHeight());
        return new RectF(getX()+ diffx/2, getY()+ diffy/2,
                getX()+getWidth()-diffx/2, getY()+getHeight()-diffy/2);
    }
    public RectF getRect(){
        return  rect;
    }

    public boolean getStatus(){
        return isActive;
    }

    public void setInactive(){
        isActive = false;
    }
    public RectF getCenter(){
        return new RectF(x+getBitmapBullet().getWidth()/2,y+getBitmapBullet().getHeight()/2,0,0);
    }
    public float getImpactPointY() {
        if (heading == DOWN) {
            return y + height;
        }

        return y;

    }

    public float getImpactPointX() {
        if (heading == RIGHT){
            return  x + width; }

        return x;}
    //



}