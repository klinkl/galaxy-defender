package com.example.projectapplication;

import android.graphics.Bitmap;
import android.graphics.RectF;

public abstract class Entity {
    public void setHp(float hp) {
        this.hp = hp;
    }

    public void setCurrentBitmap(Bitmap currentBitmap) {
        this.currentBitmap = currentBitmap;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setMovingState(MovingState movingState) {
        this.movingState = movingState;
    }

    public RectF getRectF() {
        return rectF;
    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }

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

    protected float x = 0;
    protected float y = 0;
    protected int screenX;
    protected int screenY;
    protected float speed;
    protected MovingState movingState;

    protected RectF rectF;



    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isActive() {
        return isActive;
    }

    protected boolean isActive = false;



}