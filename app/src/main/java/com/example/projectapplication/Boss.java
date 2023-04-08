package com.example.projectapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.time.LocalTime;
import java.util.ArrayList;

public class Boss extends Entity{
    public float getMaxhp() {
        return maxhp;
    }

    private float maxhp;
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private boolean isActive = false;
    private long lastTime =0;
    private int bulletfrequency= 750;

    public Boss(Context context, int screenX, int screenY){
        currentBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bossspaceship);
        this.screenX = screenX;
        this.screenY =screenY;
        System.out.println(height + length);
        height = currentBitmap.getHeight();
        length = currentBitmap.getWidth();
        x = (screenX- currentBitmap.getWidth()) /2;
        y = (screenY - currentBitmap.getHeight()) /4;
        rectF = new RectF(x,y,x+currentBitmap.getWidth(),y+currentBitmap.getHeight());
        hp = 500;
        maxhp = hp;
    }
    public void shoot(ArrayList<Bullet> bossBulletList, Context context){
        if (LocalTime.now().toNanoOfDay() / 1000000 - lastTime >= bulletfrequency) {
            bossBulletList.add(new Bullet(context, screenY, screenX, 1));
            bossBulletList.get(bossBulletList.size() - 1).shoot((float) (this.getX() + this.getLength() / 2.6), this.getY() + this.getHeight() / 2);
            lastTime = LocalTime.now().toNanoOfDay() / 1000000;
        }
    }
    public void update(){
        rectF.top= y;
        rectF.left = x;
        rectF.right = x+ currentBitmap.getWidth();
        rectF.bottom = y+ currentBitmap.getHeight();
    }
    public void move(){

    }
    public void spawnEnemies(ArrayList<Enemy> enemyArrayList){

    }
    public void drawHealthBar(Canvas canvas, Paint paint){
        paint.setColor(Color.GRAY);
        float diff = (getMaxhp()-getLength())/2;
        canvas.drawRect(getX()- diff,getY()-50, getX()+ getMaxhp(), getY()-20, paint);
        paint.setColor(Color.RED);
        canvas.drawRect(getX()- diff,getY()-50, getX()+ getHp(), getY()-20, paint);
    }
    public void setHp(float hp){
        this.hp = hp;
    }
    public RectF getRect() {
        return rectF;
    }
}
