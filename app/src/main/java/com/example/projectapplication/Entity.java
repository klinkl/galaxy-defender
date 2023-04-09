package com.example.projectapplication;

import android.graphics.Bitmap;

import java.time.LocalTime;

public abstract class Entity {
    protected float hp;
    public Bitmap currentBitmap;
    protected float height;
    protected float length;
    protected float x;
    protected float y;
    protected int screenX;
    protected int screenY;
    protected float speed;
    protected MovingState movingState;

    public float getHp() {
        return hp;
    }

    public void setHp(float hp) {
        this.hp = hp;
    }

    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

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

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public MovingState getMovingState() {
        return movingState;
    }

    public void setMovingState(MovingState movingState) {
        this.movingState = movingState;
    }

    /* public void shoot() {
        if (LocalTime.now().toNanoOfDay() / 1000000 - lastTime >= 1000) {
            bulletList.add(new Bullet(context, screenY, screenX));
            bulletList.get(bulletList.size() - 1).shoot(spaceShip.getX(), spaceShip.getY() + spaceShip.getHeight() / 2, 0);
            lastTime = LocalTime.now().toNanoOfDay() / 1000000;
        }
    } */

   // public abstract void move();




}
