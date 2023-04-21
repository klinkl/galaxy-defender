package com.example.projectapplication;

import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Meteor extends Entity{
    public int getMeteorOffset() {
        return meteorOffset;
    }

    public void setMeteorOffset(int meteorOffset) {
        this.meteorOffset = meteorOffset;
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    private int meteorOffset;
    private Random random;

    public Meteor (Context context) {

        this.currentBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.meteor);
        this.random = new Random();
        resetPosition();
    }

    public void resetPosition () {
        x = random.nextInt(600);

        y = random.nextInt(600) * -1;
        speed = 15 + random.nextInt(5);
        meteorOffset = random.nextInt(40) - 20;
        if (x <= 150) {
            x = -100;
            y = random.nextInt(150) + 50;
            meteorOffset = random.nextInt(20) + 10;
        }
        if (x >= 450) {
            x = 1000;
            y = random.nextInt(150) + 50;
            meteorOffset = (random.nextInt(20) +10) *-1;
        }
    }


    public double meteorSpaceshipDistance (Meteor meteor, Spaceship spaceship) {
        return Math.sqrt(
                Math.pow(x - spaceship.getX(), 2)
                        + Math.pow(y - spaceship.getY(), 2)
        );
    }

    public void move() {
        setY(getY() + getSpeed());
        setX(getX() + getMeteorOffset());
    }
}
