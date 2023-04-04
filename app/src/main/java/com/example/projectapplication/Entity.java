package com.example.projectapplication;

import android.graphics.Bitmap;

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


}
