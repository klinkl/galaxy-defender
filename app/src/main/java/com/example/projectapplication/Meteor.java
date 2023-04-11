package com.example.projectapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Meteor {
    Bitmap meteor;
    int meteorX, meteorY, meteorSpeed, meteorOffset;
    Random random;

    public Meteor (Context context) {
        meteor = BitmapFactory.decodeResource(context.getResources(), R.drawable.meteor);
        random = new Random();
        resetPosition();
    }

    public void resetPosition () {
        meteorX = random.nextInt(600);

        meteorY = random.nextInt(600) * -1;
        meteorSpeed = 20 + random.nextInt(16);
        meteorOffset = random.nextInt(40) - 20;
        if (meteorX <= 150) {
            meteorX = -100;
            meteorY = random.nextInt(150) + 50;
            meteorOffset = random.nextInt(40) + 20;
        }
        if (meteorX >= 450) {
            meteorX = 1000;
            meteorY = random.nextInt(150) + 50;
            meteorOffset = (random.nextInt(40) +20) *-1;
        }
    }

    public Bitmap getMeteor () {
        return meteor;
    }

    public int getMeteorWidth () {
        return meteor.getWidth();
    }

    public int getMeteorHeight () {
        return meteor.getHeight();
    }

    public double meteorSpaceshipDistance (Meteor meteor, Spaceship spaceship) {
        return Math.sqrt(
                Math.pow(meteorX - spaceship.getX(), 2)
                        + Math.pow(meteorY - spaceship.getY(), 2)
        );
    }
}
