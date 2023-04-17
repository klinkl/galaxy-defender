package com.example.projectapplication;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.media.MediaPlayer;

import java.util.ArrayList;

public class Explosion {
    private ArrayList<Bitmap> frames = new ArrayList<Bitmap>();

    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }

    private Bitmap currentBitmap;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private int x;
private int y;
    public int getCurrentFrame() {
        return currentFrame;
    }

    private int currentFrame;
    public Explosion(Context context, int x, int y) {
        String filename;
        for (int i=0; i<=63; i++){
            if (i<10) {
                filename = "tile00" + i;
            } else
                filename = "tile0" + i;
            int id = context.getResources().getIdentifier(filename,"drawable",context.getPackageName());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),id);
            if (bitmap!=null){
                frames.add(bitmap);
            }
            else {
                System.out.println("error");
            }
        }
        System.out.println(frames.size());
        this.x = x;
        this.y = y;
    }
    public void update(){
        currentBitmap = frames.get(currentFrame);
        currentFrame +=1;
    }
}
