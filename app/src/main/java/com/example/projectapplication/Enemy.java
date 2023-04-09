package com.example.projectapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Entity {
    /*
  protected float hp;
  public Bitmap sprite;
  protected float height;
  protected float length;
protected float x;
  protected float y;
  protected int screenX;
  protected int screenY;
  protected float speed;

 */
    private boolean isMovingRight = true;
    RectF rect;
    boolean isVisible;


    public RectF getRect() {
        return rect;
    }

    public boolean isMovingRight() {
        return isMovingRight;
    }

    public void setMovingRight(boolean movingRight) {
        isMovingRight = movingRight;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
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

    int column = 0;
    int row = 0;
    private static long lastBulletTime;

    public static long getLastBulletTime() {
        return lastBulletTime;
    }

    public static void setLastBulletTime(long lastBulletTime) {
        Enemy.lastBulletTime = lastBulletTime;
    }

    public static long getBulletFrequency() {
        return bulletFrequency;
    }
    public static void setBulletFrequency(long bulletFrequency) {
        Enemy.bulletFrequency = bulletFrequency;
    }




    private static long bulletFrequency = 5000;

    public Enemy(Context context, int row, int column, int screenX, int screenY) {

        //blank RectF for collisionTesting
        rect = new RectF();

        length = screenX / 10;
        height = screenX / 10;

        x = column * (length + length/2);
        y = row * (height);


        //Initialize enemy bitmap
        currentBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);


        // stretch the bitmap to a size appropriate for the screen resolution
        currentBitmap = Bitmap.createScaledBitmap(currentBitmap,
                (int) (length),
                (int) (height),
                false);

        // set other variables
        isVisible = true;
        this.screenX = screenX; // nötig?
        this.screenY = screenY; // nötig?
        this.column = column;
        this.row = row;
        speed = 100;
    }

    public void moveLeft(float distanceX){
        x -= distanceX;
    }

    public void moveRight (float distanceX){
        x += distanceX;
    }

    public void moveDown (float distanceY){
        y += distanceY;
    }

    public void changeDirection(){
        if (isMovingRight){
            isMovingRight = false;
        }
        else isMovingRight = true;

        moveDown(50);
        setSpeed(getSpeed() );
    }

    public boolean canMoveHorizontally() {
        if (x + length >= screenX || x < 0 ) {
            return false;
        } else return true;

    }

    public void move(long fps){
        float distanceX = speed / fps;
        if (isMovingRight) {
            moveRight(distanceX);
        }
       else {
           moveLeft(distanceX);
       }
        //For hit-detection
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;
    }
    public static void reduceBulletFrequency() {
        if (Enemy.bulletFrequency >= 1000){
            Enemy.bulletFrequency -= 50;
        }
    }
    public void dropBullet(ArrayList<Bullet> bulletlist, Context context, Spaceship player) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastBulletTime >= bulletFrequency) {
            int probability = 10; // 10% chance of dropping a bullet

            Random random = new Random();
            if (Math.abs(this.x - player.getX()) < player.getLength() / 2){
                probability = 50;
            }
            int randomNumber = random.nextInt(1000);

            if (randomNumber <= probability) {
                // Create a new bullet object at the enemy's position
                float bulletX = x;
                float bulletY = y;
                bulletlist.add(new Bullet(context,screenX, screenY));
                bulletlist.get( bulletlist.size()-1).shoot(bulletX, bulletY,1);
                lastBulletTime = currentTime;
            }
        }

    }

    public boolean isVisible() {
        return isVisible;
    }
}
