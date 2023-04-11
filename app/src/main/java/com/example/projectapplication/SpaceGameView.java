package com.example.projectapplication;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.time.LocalTime;
import java.util.ArrayList;

public class SpaceGameView extends SurfaceView implements Runnable {

    private int numEnemies = 0;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<Bullet> enemyBullets = new ArrayList<>();

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
    private long lastCollision;
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

        enemies = new ArrayList<>();
        // screenX / length of two enemies
        int numColumns = screenX / (2 * screenX / 10);

        // Create the enemies and add them to the list
        for (int row = 0; row < 6; row++) {
            for (int column = 0; column < numColumns; column++) {
                Enemy enemy = new Enemy(getContext(), row, column, screenX, screenY);
                enemies.add(enemy);
            }
        }


    }


    @Override
    public void run() {
        while (playing) {
            // Capture the current time in milliseconds in startFrameTime
            long startFrameTime = System.currentTimeMillis();
            // Update the frame
            if (!paused) {
                shoot();
                update();
              if (enemies.isEmpty())
                boss();
                if (boss != null) {
                    boss.shoot(bossBulletList, context, spaceShip);
                    boss.move(spaceShip);
                }
            }
            // Draw the frame

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


    private void update() {

        spaceShip.update(fps);
        for (int i = 0; i < bossBulletList.size(); i++) {
            if (bossBulletList.get(i).getStatus())
                bossBulletList.get(i).update(fps);
        }

        for (int i = 0; i < bulletList.size(); i++) {
            if (bulletList.get(i).getStatus())
                bulletList.get(i).update(fps);
        }




        //enemybulletList
        for (int i = 0; i < enemyBullets.size(); i++) {
            if (enemyBullets.get(i).getStatus())
                enemyBullets.get(i).update(fps);
        }

        // update enemy movement
        boolean hit = false;

        for (int i = 0; i < enemies.size(); i++){
            Enemy enemy = enemies.get(i);
            if (enemies.size() < 10){
                enemy.enterRageMode();
            }
            enemy.dropBullet(enemyBullets, context, spaceShip);
            enemy.move(fps);
            if (enemy.hitsBorder(fps)){
                hit = true;
            };

        }
        // System.out.print(hit);
        // change direction if screenHit
       if (hit) {
            Enemy.reduceBulletFrequency();
            for (int i = 0; i < enemies.size(); i++){
                Enemy enemy = enemies.get(i);
                enemy.changeDirection();
                enemy.moveDown();
            }
            hit = false;
        }

        if (boss != null) {
            boss.update();
        }

        checkCollisions();

    }


    private void checkCollisions() {

        if (boss != null && boss.isActive()) {
            if (LocalTime.now().toNanoOfDay() / 1000000 - lastCollision >= 1000) {
                if (RectangleCollison(spaceShip.getActualRect(), boss.getActualRect())) {
                    lives--;
                    if (lives == 0) {
                        // Game over
                        gameOver();
                        pause();

                    }
                    lastCollision = LocalTime.now().toNanoOfDay() / 1000000;
                }
            }
        }

        int i = 0;
        while (i < bossBulletList.size()) {
            if (RectangleCollison(bossBulletList.get(i).getActualRect(), spaceShip.getActualRect())) {
                lives--;
                if (lives == 0) {
                    // Game over
                    gameOver();
                    pause();

                }
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

        /*
        while (i < bulletList.size()) {
            if (boss != null) {
                if (RectangleCollison(bulletList.get(i).getActualRect(), boss.getActualRect()) && boss.isActive()) {
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
        }

        i++; */
        i = 0;
        while ( i < bulletList.size()) {
            if (boss != null) {
                if (RectangleCollison(bulletList.get(i).getActualRect(), boss.getActualRect()) && boss.isActive()) {
                    boss.setHp(boss.getHp() - 20);
                    bulletList.remove(i);
                    continue;
                }
            }
               Bullet bullet = bulletList.get(i);
                if (bulletList.get(i).getStatus()) {
                    RectF bulletRect = bulletList.get(i).getActualRect();
                    int j = 0;
                    while (j < enemies.size()) {
                        Enemy enemy = enemies.get(j);
                        if (enemy.getStatus()) {
                            RectF enemyRect = enemy.getActualRect();
                            if (RectF.intersects(bullet.getActualRect(), enemyRect)) {
                                // Bullet and enemy have collided
                                bullet.setInactive();
                                enemies.get(j).setInactive();
                                bulletList.remove(bullet);
                                enemies.remove(enemy);
                                score += 10;
                                continue;
                            }
                        }
                        j++;
                    }
                    // Check if bullet has gone out of the screen
                    if (bullet.getActualRect().bottom < 0) {
                        bullet.setInactive();
                        bulletList.remove(bullet);
                        continue;
                    }
                    if (bullet.getImpactPointY() < 0) {
                        bulletList.remove(bullet);
                        continue;
                    }
                    if (bullet.getImpactPointX() > screenX) {
                        bulletList.remove(bullet);
                        continue;
                    }
                    if (bullet.getImpactPointX() < 0) {
                        bulletList.remove(bullet);
                        continue;
                    }
                }
                i++;
            }

        i =0;
        // Check for enemy bullet collisions with spaceship
        while( i < enemyBullets.size()) {
            Bullet enemyBullet = enemyBullets.get(i);
            if (enemyBullet.getStatus()) {
                RectF enemyBulletRect = enemyBullet.getActualRect();
                if (RectF.intersects(enemyBulletRect, spaceShip.getActualRect())) {
                    // Enemy bullet has hit the spaceship
                    enemyBullet.setInactive();
                    enemyBullets.remove(enemyBullet);
                    System.out.println(lives);
                    lives--;
                    if (lives == 0) {
                        // Game over
                        gameOver();
                        pause();

                    }
                }
                // Check if enemy bullet has gone out of the screen
                if (enemyBulletRect.top > screenY) {
                    enemyBullet.setInactive();
                    enemyBullets.remove(enemyBullet);
                }
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
private void drawdebug() {
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
    if (boss != null) {
        canvas.drawRect(boss.getActualRect(), paint);
        canvas.drawBitmap(boss.getCurrentBitmap(), boss.getX(), boss.getY(), paint);
    }
    canvas.drawRect(spaceShip.getActualRect(), paint);
    canvas.drawBitmap(spaceShip.getBitmap(), spaceShip.getX(), spaceShip.getY(), paint);

}
    public void gameOver(){
        System.out.print("Game Over");
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
            //  draw the defender bullets
            canvas.drawBitmap(bitmapback, 0,  0, paint);

            for (int i = 0; i < bulletList.size(); i++) {
                if (bulletList.get(i).getStatus())
                    canvas.drawBitmap(bulletList.get(i).getBitmapBullet(), bulletList.get(i).getRect().left, bulletList.get(i).getRect().top, paint);
            }

            for (int i = 0; i < enemyBullets.size(); i++) {
                if (enemyBullets.get(i).getStatus())
                    canvas.drawBitmap(enemyBullets.get(i).getBitmapBullet(), enemyBullets.get(i).getRect().left, enemyBullets.get(i).getRect().top, paint);
            }
            canvas.drawBitmap(spaceShip.getBitmap(), spaceShip.getX(), spaceShip.getY(), paint);

            // draw all enemies
            for (Enemy enemy : enemies) {
                if (enemy.isActive()) {
                    canvas.drawBitmap(enemy.getCurrentBitmap(), enemy.getX(), enemy.getY(), paint);
                }
            }
            for (int i = 0; i < bossBulletList.size(); i++) {
                if (bossBulletList.get(i).getStatus())
                    canvas.drawBitmap(bossBulletList.get(i).getBitmapBullet(), bossBulletList.get(i).getRect().left, bossBulletList.get(i).getRect().top, paint);
            }
            if (boss != null && boss.isActive()) {
                canvas.drawBitmap(boss.getCurrentBitmap(), boss.getX(), boss.getY(), paint);
                boss.drawHealthBar(canvas,paint);
            }

            canvas.drawBitmap(spaceShip.getBitmap(), spaceShip.getX(), spaceShip.getY(), paint);

            //drawdebug();
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
            bulletList.add(new Bullet(context, screenY, screenX,0,spaceShip.getX()+( spaceShip.getLength() /6)
                    , spaceShip.getY() + spaceShip.getHeight() / 2));
            bulletList.get(bulletList.size() - 1).shoot();

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

                spaceShip.setX((int)
                        (motionEvent.getX() - spaceShip.getLength() /2));
                spaceShip.setY((int)
                        (motionEvent.getY() - spaceShip.getHeight()/2));

                break;

            // Player has removed finger from screen
            case MotionEvent.ACTION_UP:

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
