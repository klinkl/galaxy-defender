package com.example.projectapplication;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.Pair;

public class Bullet extends Entity {


    private Pair<Float, Float> vector;
    private boolean pathFinding;
    private Matrix rotMatrix = new Matrix();
    public Bullet(Context context, int screenY, int screenX, MovingState direction, float startX, float startY) {
        setSpeed(650);

        //  height = screenY / 20;
        movingState = direction;
        currentBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet1);
        if (movingState == MovingState.DOWN){
            //flip bitmap
            Matrix matrix = new Matrix();
            matrix.preRotate(180);
            currentBitmap =Bitmap.createBitmap(currentBitmap, 0,0,currentBitmap.getWidth(),currentBitmap.getHeight(),matrix,true);
        }
        isActive = false;
        this.screenX =screenX;
        this.screenY = screenY;
        x = startX;
        y = startY;
        this.rectF = new RectF(x,y,x+currentBitmap.getWidth(),y+currentBitmap.getHeight());

    }



    public boolean shoot() {
        if (!isActive) {


            isActive = true;

            length = getCurrentBitmap().getWidth();
            height = getCurrentBitmap().getHeight();

            return true;
        }

        // Bullet already active
        return false;
    }
    public boolean shoot(Spaceship spaceship) {
        if (!isActive) {


            isActive = true;

            length = getCurrentBitmap().getWidth();
            height = getCurrentBitmap().getHeight();
            float diffx = spaceship.getX()+spaceship.getLength()/2-(this.getX()+this.getLength()/2);
            float diffy = spaceship.getY()+ spaceship.getHeight()/2-(this.getY()+this.getHeight()/2);
            float magnitude = (float)sqrt(pow(diffx,2) + pow(diffy,2));
            vector = new Pair<>((diffx *(1/magnitude)), (diffy*(1/magnitude)));
            pathFinding = true;
            //Matrix rotation needed
            double angle = Math.toDegrees(Math.atan2(vector.second, vector.first));
            rotMatrix.preRotate((float)angle-90);
            currentBitmap =Bitmap.createBitmap(currentBitmap, 0,0,currentBitmap.getWidth(),currentBitmap.getHeight(),rotMatrix,true);
            return true;
        }

        // Bullet already active
        return false;
    }

    public void update(long fps) {
        if (pathFinding == true){

            x+= vector.first * speed /fps;
            y+= vector.second * speed/fps;
        }
        // Just move up or down
            else if(movingState == MovingState.UP){
            y = y - speed / fps;
        }else if (movingState == MovingState.DOWN){
            y = y + speed / fps;
        }

        // Update rect
        rectF.left = x;
        rectF.right = x + length;
        rectF.top = y;
        rectF.bottom = y + height;
    }

    public RectF getActualRect(){
        int diffx= (int)(0.66 * getLength());
        int diffy =(int)(0.5 * getHeight());
        return new RectF(getX()+ diffx/2, getY()+ diffy/2,
                getX()+getLength()-diffx/2, getY()+getHeight()-diffy/2);
    }


    public float getImpactPointY() {
        if (movingState == MovingState.DOWN) {
            return y + height;
        }

        return y;

    }

    public float getImpactPointX() {
        if (movingState == MovingState.RIGHT){
            return  x + length; }

        return x;}
    //



}