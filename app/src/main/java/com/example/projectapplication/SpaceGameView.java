package com.example.projectapplication;


import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.util.TimeUtils;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class SpaceGameView extends SurfaceView implements Runnable{

    private Context context;

    // This is our thread
    private Thread gameThread = null;

    // Our SurfaceHolder to lock the surface before we draw our graphics
    private SurfaceHolder ourHolder;

    // A boolean which we will set and unset
    // when the game is running- or not.
    private volatile boolean playing;

    // Game is paused at the start
    private boolean paused = true;

    // A Canvas and a Paint object
    private Canvas canvas;
    private Paint paint;

    // This variable tracks the game frame rate
    private long fps;

    // This is used to help calculate the fps
    private long timeThisFrame;

    // The size of the screen in pixels
    private int screenX;
    private int screenY;

    // The score
    public int score = 0;

    // Lives
    private int lives = 4;

    long lastTime = 0;
    private Spaceship spaceShip;
    private ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
    private Bitmap bitmapback;

    //meins
    private ArrayList<Meteor> meteors;

    static int dWidth, dHeight;


    // This special constructor method runs
    public SpaceGameView(Context context, int x, int y) {

        // The next line of code asks the
        // SurfaceView class to set up our object.
        // How kind.
        super(context);

        // Make a globally available copy of the context so we can use it in another method
        this.context = context;

        // Initialize ourHolder and paint objects
        ourHolder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;

        //meins

        meteors = new ArrayList<>();

        for (int i=0;i<4;i++) {
            Meteor meteor = new Meteor(context);
            meteors.add(meteor);
        }


        Display display =  ((Activity) getContext()).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        dWidth = point.x;
        dHeight = point.y;


        initLevel();
    }



    private void initLevel(){

        spaceShip = new Spaceship(context, screenX, screenY);
        //bulletList.add(new Bullet(context, screenY, screenX));
    }


    @Override
    public void run() {
        while (playing) {
            score = 10;
            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();
            // Update the frame
            if(!paused){
                update();
            }

            // Draw the frame
            shoot();
            draw();

            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }

        }

    }



    private void update(){

        //spaceShip.update(fps);
        for (int i=0; i<bulletList.size(); i++) {
            if (bulletList.get(i).getStatus())
                bulletList.get(i).update(fps);
        }
        checkCollisions();

    }


    private void checkCollisions(){
        //  if (spaceShip.getX() > screenX - spaceShip.getLength())
        //     spaceShip.setX(0);
        //  if (spaceShip.getX() < 0 + spaceShip.getLength())
        //      spaceShip.setX(screenX);

        //   if (spaceShip.getY() > screenY - spaceShip.getLength())
        //       spaceShip.setY(0);
        //   if (spaceShip.getY() < 0 + spaceShip.getLength())
        //       spaceShip.setY(screenY);
        int i=0;
    while(i< bulletList.size()) {
        if (bulletList.get(i).getImpactPointY() < 0) {
            bulletList.remove(i);
            continue;
        }
        if (bulletList.get(i).getImpactPointY() > screenY) {
            bulletList.remove(i);
            continue;
        }
        if (bulletList.get(i).getImpactPointX() < 0) {
            bulletList.remove(i);
            continue;
        }
        if (bulletList.get(i).getImpactPointX() > screenX) {
            bulletList.remove(i);
            continue;
        }
        i++;
    }
    }




    private void draw(){
        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            // Draw the background color
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255,  255, 255, 255));

            bitmapback = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
            bitmapback = Bitmap.createScaledBitmap(bitmapback, (int) (screenX), (int) (screenY),false);
            //  canvas.drawBitmap(background.getBitmap(), spaceShip.getX(), spaceShip.getY() , paint);
            //  draw the defender
            canvas.drawBitmap(bitmapback, 0, 0, paint);
            for(int i=0; i< bulletList.size(); i++) {
                if (bulletList.get(i).getStatus())
                    canvas.drawBitmap(bulletList.get(i).getBitmapBullet(), bulletList.get(i).getRect().left, bulletList.get(i).getRect().top, paint);
            }
            //meins

            for (int i=0;i<meteors.size();i++) {
                canvas.drawBitmap(meteors.get(i).getMeteor(), meteors.get(i).meteorX, meteors.get(i).meteorY, null);
                meteors.get(i).meteorY += meteors.get(i).meteorSpeed;
                meteors.get(i).meteorX += meteors.get(i).meteorOffset;
                if (meteors.get(i).meteorY >= dHeight ||
                meteors.get(i).meteorX > 1000 ||
                meteors.get(i).meteorX < -200) {
                    meteors.get(i).resetPosition();
                }
            }

            for (int i=0;i< meteors.size();i++) {
                if (meteors.get(i).meteorSpaceshipDistance(meteors.get(i), spaceShip) <= meteors.get(i).getMeteorWidth() / 2) {
                    lives--;
                    meteors.get(i).resetPosition();
                }
            }

            if (lives == 0) {
                playing = false;
            }
            canvas.drawBitmap(spaceShip.getBitmap(), spaceShip.getX(), spaceShip.getY() , paint);





            // Draw the score and remaining lives
            // Change the brush color
            paint.setColor(Color.argb(255,  249, 129, 0));
            paint.setTextSize(40);
            canvas.drawText("Score: " + score + "   Lives: " + lives + "    FPS: " + fps, 10,50, paint);

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

public void shoot(){
    if (LocalTime.now().toNanoOfDay() / 1000000 - lastTime >= 1000) {
        bulletList.add(new Bullet(context, screenY, screenX));
        bulletList.get(bulletList.size() - 1).shoot(spaceShip.getX(), spaceShip.getY() + spaceShip.getHeight() / 2, 0);
        lastTime = LocalTime.now().toNanoOfDay() / 1000000;
    }
}


    // If SpaceGameActivity is paused/stopped
    // shutdown our thread.
    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }



    // If SpaceInvadersActivity is started then
    // start our thread.
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }


    // The SurfaceView class implements onTouchListener
    // So we can override this method and detect screen touches.
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            // Player has touched the screen

            case MotionEvent.ACTION_MOVE:


                paused = false;
                spaceShip.setX((int) motionEvent.getX());
                spaceShip.setY((int) motionEvent.getY());

                //bulletList.get(0).shoot(spaceShip.getX(),spaceShip.getY()+ spaceShip.getHeight()/2,0);


                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:

                //   if(motionEvent.getY() > screenY - screenY / 10) {
                spaceShip.setMovementState(spaceShip.STOPPED);
                //   }
                break;
        }
        return true;

    }





}  // end class
