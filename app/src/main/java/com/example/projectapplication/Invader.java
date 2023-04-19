package com.example.projectapplication;

import android.content.Context;

import java.util.ArrayList;

public abstract class Invader extends Entity {

    public static int getBulletfrequency() {
        return bulletfrequency;
    }

    public static void setBulletfrequency(int bulletfrequency) {
        Invader.bulletfrequency = bulletfrequency;
    }

    public static long getLastBulletTime() {
        return lastBulletTime;
    }

    public static void setLastBulletTime(long lastBulletTime) {
        Invader.lastBulletTime = lastBulletTime;
    }

    protected static int bulletfrequency;
    protected static long lastBulletTime;


    public abstract void shoot(ArrayList<Bullet> bulletlist, Context context, Spaceship player);

}
