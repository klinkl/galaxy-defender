package com.example.projectapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Invader {

    private int column = 0;
    private int row = 0;
    private boolean rageMode = false;


    public Enemy(Context context, int row, int column, int screenX, int screenY) {

        //blank RectF for collisionTesting
        rectF = new RectF();

        // Set dimensions of the sprite ( square)
        length = screenX / 10;
        height = length;

        //Positioning based on column, sprite dimensions and padding
        x = column * (length + length/2 );
        y = row * (height);


        //Initialize enemy bitmap
        currentBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);


        // stretch the bitmap to a size appropriate for the screen resolution
        currentBitmap = Bitmap.createScaledBitmap(currentBitmap,
                (int) (length),
                (int) (height),
                false);

        // set other variables
        setActive(true);
        setBulletfrequency(1500);
        this.screenX = screenX;
        this.screenY = screenY;
        this.column = column;
        this.row = row;
        this.speed = 100;
        this.movingState = MovingState.RIGHT;
    }

    public void moveLeft(float distanceX){

        this.x = getX() - distanceX;
    }

    public void moveRight (float distanceX){

        this.x =  getX() + distanceX;
    }

    public void moveDown (){
        this.y = getY() + (getHeight() / 4);
        if (getSpeed() < 200){
            setSpeed(getSpeed()* 1.1f);
        }
    }

    public float predictNextX (long fps){
        if (getMovingState() == MovingState.LEFT){
            return getX() - (getSpeed() / fps);
        }
        else{
            return getX() + (getSpeed() / fps);
        }
    }

    public void changeDirection(){
        if (getMovingState() == MovingState.LEFT){
            setMovingState(MovingState.RIGHT);
        }
        else setMovingState(MovingState.LEFT);
    }

  public boolean hitsBorder(long fps) {
        float prediction = predictNextX(fps);
        if (prediction + getLength() > getScreenX() || prediction < 0) {
            return true;
        } else return false;
    }


    public void move(long fps){
        float distanceX = getSpeed() / fps;
        if (getMovingState() == MovingState.RIGHT) {
            moveRight(distanceX);
        }
       else {
           moveLeft(distanceX);
       }
        //For hit-detection
        rectF.top = y;
        rectF.bottom = y + height;
        rectF.left = x;
        rectF.right = x + length;
    }
    public static void increaseBulletFrequency() {
        if (getBulletfrequency()>= 750){
            setBulletfrequency(getBulletfrequency() - 50);
        }
    }

    public void enterRageMode(){
        setBulletfrequency(500);
        setSpeed(400);
        setRageMode(true);
    }

    @Override
    public void shoot(ArrayList<Bullet> bulletlist, Context context, Spaceship player) {
        Random random = new Random();
        int probability = 10; // 10% chance of dropping a bullet
        long currentTime = System.currentTimeMillis();

        if (Math.abs( getX() - player.getX()) < player.getLength() /2 ){
                probability = 50;
            }
        if (isRageMode()){
            probability = 100;
        }
        if (currentTime - getLastBulletTime() >= getBulletfrequency()) {
             int randomNumber = random.nextInt(100);

             if (randomNumber <= probability) {
                 // Create a new bullet object at the enemy's position
                 float bulletX = getX();
                 float bulletY = getY();
                 bulletlist.add(new Bullet(context,screenX, screenY, MovingState.DOWN,bulletX, bulletY));
                 bulletlist.get( bulletlist.size()-1).shoot();
                 setLastBulletTime(currentTime);
             }
         }
    }

    public RectF getActualRect(){
        int diffx= (int)(0.2 * getLength());
        int diffy =(int)(0.32 * getHeight());
        return new RectF(getX()+ diffx/2, getY()+ diffy/2,
                getX()+getLength()-diffx/2, getY()+getHeight()-diffy/2);
    }


    public boolean isRageMode() {
        return rageMode;
    }

    public void setRageMode(boolean rageMode) {
        this.rageMode = rageMode;
    }


    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }






}
