package com.example.projectapplication;

import android.graphics.Bitmap;
import android.graphics.RectF;

public abstract class Entity {
    protected float hp;

    public float getHp() {
        return hp;
    }

    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    public float getHeight() {
        return height;
    }

    public float getLength() {
        return length;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public float getSpeed() {
        return speed;
    }

    public MovingState getMovingState() {
        return movingState;
    }

    protected Bitmap currentBitmap;
    protected float height;
    protected float length;

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    protected float x;
    protected float y;
    protected int screenX;
    protected int screenY;
    protected float speed;
    protected MovingState movingState;

    protected RectF rectF;


}
