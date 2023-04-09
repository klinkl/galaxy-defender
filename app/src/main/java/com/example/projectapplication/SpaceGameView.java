package com.example.projectapplication;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.util.TimeUtils;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;

public class SpaceGameView extends SurfaceView implements Runnable {

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
    private ArrayList<Bullet> bossBulletList = new ArrayList<Bullet>();
    private Bitmap bitmapback;

    private Boss boss;
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


        initLevel();
    }


    private void initLevel() {

        spaceShip = new Spaceship(context, screenX, screenY);
        boss = new Boss(context, screenX, screenY);
        //bulletList.add(new Bullet(context, screenY, screenX));
    }


    @Override
    public void run() {
        while (playing) {
            score = 10;
            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();
            // Update the frame
            if (!paused) {
                update();
            }

            // Draw the frame
            shoot();
            draw();
            boss();
            if (boss != null) {
                boss.shoot(bossBulletList, context);
                boss.move(spaceShip);
            }
            // Calculate the fps this frame
            // We can then use the result to
            // time animations and more.
            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }

        }

    }


    private void update() {
        spaceShip.update(fps);
        for (int i = 0; i < bossBulletList.size(); i++) {
            if (bossBulletList.get(i).getStatus())
                bossBulletList.get(i).update(fps);
        }
        //spaceShip.update(fps);
        for (int i = 0; i < bulletList.size(); i++) {
            if (bulletList.get(i).getStatus())
                bulletList.get(i).update(fps);
        }
        if (boss !=null) {
            boss.update();
        }
        checkCollisions();

    }


    private void checkCollisions() {
        int i = 0;
        while (i < bossBulletList.size()) {
            if (RectangleCollison(bossBulletList.get(i).getActualRect(),spaceShip.getActualRect())){
                lives--;
                bossBulletList.remove(i);
                continue;
            }
            if (bossBulletList.get(i).getImpactPointY() < 0) {
                bossBulletList.remove(i);
                continue;
            }
            if (bossBulletList.get(i).getImpactPointY() > screenY) {
                bossBulletList.remove(i);
                continue;
            }
            if (bossBulletList.get(i).getImpactPointX() < 0) {
                bossBulletList.remove(i);
                continue;
            }
            if (bossBulletList.get(i).getImpactPointX() > screenX) {
                bossBulletList.remove(i);
                continue;
            }
            i++;
        }
        i = 0;

        while (i < bulletList.size()) {
            if (boss != null) {
                if (RectangleCollison(bulletList.get(i).getActualRect(), boss.getActualRect())) {
                    boss.setHp(boss.getHp() - 20);
                    bulletList.remove(i);
                    continue;
                }
            }
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
private void boss(){
        if (boss != null) {
            boss.setActive(true);
            if (boss.getHp() < 0)
                boss = null;
        }
}
private void drawdebug(){
    paint.setColor(Color.WHITE);
    for (int i = 0; i < bulletList.size(); i++) {
        if (bulletList.get(i).getStatus())
            canvas.drawRect(bulletList.get(i).getActualRect(), paint);
        canvas.drawBitmap(bulletList.get(i).getBitmapBullet(), bulletList.get(i).getRect().left, bulletList.get(i).getRect().top, paint);

    }
    for (int i = 0; i < bossBulletList.size(); i++) {
        if (bossBulletList.get(i).getStatus())
            canvas.drawRect(bossBulletList.get(i).getActualRect(), paint);
        canvas.drawBitmap(bossBulletList.get(i).getBitmapBullet(), bossBulletList.get(i).getRect().left, bossBulletList.get(i).getRect().top, paint);
    }
    if (boss!= null) {
        canvas.drawRect(boss.getActualRect(), paint);
        canvas.drawBitmap(boss.getCurrentBitmap(), boss.getX(), boss.getY(), paint);
    }
    canvas.drawRect(spaceShip.getActualRect(), paint);
    canvas.drawBitmap(spaceShip.getBitmap(), spaceShip.getX(), spaceShip.getY(), paint);

}
    private void draw() {
        // Make sure our drawing surface is valid or we crash
        if (ourHolder.getSurface().isValid()) {
            // Lock the canvas ready to draw
            canvas = ourHolder.lockCanvas();

            // Draw the background color
            canvas.drawColor(Color.argb(255, 26, 128, 182));

            // Choose the brush color for drawing
            paint.setColor(Color.argb(255, 255, 255, 255));

            bitmapback = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
            bitmapback = Bitmap.createScaledBitmap(bitmapback, (int) (screenX), (int) (screenY), false);
            //  canvas.drawBitmap(background.getBitmap(), spaceShip.getX(), spaceShip.getY() , paint);
            //  draw the defender

            canvas.drawBitmap(bitmapback, 0, 0, paint);
            for (int i = 0; i < bulletList.size(); i++) {
                if (bulletList.get(i).getStatus())
                    canvas.drawBitmap(bulletList.get(i).getBitmapBullet(), bulletList.get(i).getRect().left, bulletList.get(i).getRect().top, paint);
            }
            for (int i = 0; i < bossBulletList.size(); i++) {
                if (bossBulletList.get(i).getStatus())
                    canvas.drawBitmap(bossBulletList.get(i).getBitmapBullet(), bossBulletList.get(i).getRect().left, bossBulletList.get(i).getRect().top, paint);
            }
            if (boss != null) {
                canvas.drawBitmap(boss.getCurrentBitmap(), boss.getX(), boss.getY(), paint);
                boss.drawHealthBar(canvas,paint);
            }

            canvas.drawBitmap(spaceShip.getBitmap(), spaceShip.getX(), spaceShip.getY(), paint);
            drawdebug();
            // Draw the score and remaining lives
            // Change the brush color
            paint.setColor(Color.argb(255, 249, 129, 0));
            paint.setTextSize(40);
            canvas.drawText("Score: " + score + "   Lives: " + lives + "    FPS: " + fps, 10, 50, paint);

            // Draw everything to the screen
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void shoot() {
        if (LocalTime.now().toNanoOfDay() / 1000000 - lastTime >= 1000) {
            bulletList.add(new Bullet(context, screenY, screenX,0));
            bulletList.get(bulletList.size() - 1).shoot(spaceShip.getX()+( spaceShip.getLength() /6), spaceShip.getY() + spaceShip.getHeight() / 2);
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
                //Spaceship follows touch input
                spaceShip.setX((int) (motionEvent.getX() - spaceShip.getLength() /2));
                spaceShip.setY((int) (motionEvent.getY() - spaceShip.getHeight()/2));

                //bulletList.get(0).shoot(spaceShip.getX(),spaceShip.getY()+ spaceShip.getHeight()/2,0);


                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:

                //   if(motionEvent.getY() > screenY - screenY / 10) {
                //spaceShip.setMovementState(spaceShip.STOPPED);
                //   }
                break;
        }
        return true;

    }

public boolean RectangleCollison(RectF rect1, RectF rect2){
        if (rect1.left < rect2.right
                && rect1.right > rect2.left &&
        rect1.top < rect2.bottom && rect1.bottom> rect2.top){
            return true;
        }
        else return false;
}
}  // end class
