package com.example.projectapplication;

import static com.example.projectapplication.Stage.STAGE1;
import static com.example.projectapplication.Stage.STAGE2;
import static com.example.projectapplication.Stage.STAGE3;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import java.time.LocalTime;
import java.util.ArrayList;
enum Stage{
    STAGE1, STAGE2,STAGE3
}
public class Boss extends Invader {
    public float getMaxhp() {
        return maxhp;
    }

    private float maxhp;

    public void setMaxhp(float maxhp) {
        this.maxhp = maxhp;
    }

    public float getHp() {
        return hp;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public void setInvincible(boolean invincible) {
        isInvincible = invincible;
    }

    private boolean isInvincible = true;

    public Stage getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }

    private float hp;

    private Stage currentStage = STAGE1;

    public Boss(Context context, int screenX, int screenY) {
        currentBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bossspaceship);
        this.screenX = screenX;
        this.screenY = screenY;
        System.out.println(height + length);
        height = currentBitmap.getHeight();
        length = currentBitmap.getWidth();
        x = (screenX - currentBitmap.getWidth()) / 2;
        y = 0;
        rectF = new RectF(x, y, x + currentBitmap.getWidth(), y + currentBitmap.getHeight());
        hp = 750;
        maxhp = hp;
        speed = 10;
        setBulletfrequency(750);
    }

    @Override
    public void shoot(ArrayList<Bullet> bossBulletList, Context context, Spaceship spaceship) {
        if (isActive()) {
            if (LocalTime.now().toNanoOfDay() / 1000000 - getLastBulletTime() >= getBulletfrequency()) {
                if (currentStage == STAGE1) {
                    bossBulletList.add(new Bullet(context, screenY, screenX, MovingState.DOWN,
                            (float) (this.getX() + this.getLength() / 2.6),
                            this.getY() + this.getHeight() / 2));
                    bossBulletList.get(bossBulletList.size() - 1).shoot();

                }
                if (currentStage == STAGE2) {
                    bossBulletList.add(new Bullet(context, screenY, screenX, MovingState.DOWN,
                            (float) (this.getX() + this.getLength() / 10),
                            this.getY() + this.getHeight() / 2));
                    bossBulletList.get(bossBulletList.size() - 1).shoot();
                    bossBulletList.add(new Bullet(context, screenY, screenX, MovingState.DOWN,
                            (float) (this.getX() + this.getLength() / 1.5),
                            this.getY() + this.getHeight() / 2));
                    bossBulletList.get(bossBulletList.size() - 1).shoot();
                }
                if (currentStage == STAGE3) {
                    bossBulletList.add(new Bullet(context, screenY, screenX, MovingState.DOWN,
                            (float) (this.getX() + this.getLength() / 2.6),
                            this.getY() + this.getHeight() / 2));
                    bossBulletList.get(bossBulletList.size() - 1).shoot(spaceship);

                }
                setLastBulletTime(LocalTime.now().toNanoOfDay() / 1000000);
            }
        }
    }

    public RectF getActualRect() {
        int diffx = (int) (0.15 * getLength());
        int diffy = (int) (0.05 * getHeight());
        return new RectF(getX() + diffx / 2, getY() + diffy / 2,
                getX() + getLength() - diffx / 2, getY() + getHeight() - diffy / 2);
    }

    public void update() {
        if (getHp() < 0.7 * getMaxhp() && currentStage != STAGE3 && currentStage != STAGE2) {
            currentStage = Stage.STAGE2;
            speed += 5;
            setBulletfrequency(500);
        }
        if (getHp() < 0.4 * getMaxhp() && currentStage != STAGE3) {
            currentStage = STAGE3;
            speed += 5;
            //spawnEnemies();
        }
        rectF.top = y;
        rectF.left = x;
        rectF.right = x + currentBitmap.getWidth();
        rectF.bottom = y + currentBitmap.getHeight();
    }

    public void move(Spaceship spaceship) {
        //animation to enter screen
        if (getY() <= (screenY - currentBitmap.getHeight()) / 4) {

            setY(getY() + 5);
            movingState = movingState.DOWN;
        } else {
            setInvincible(false);
            float diff = this.getSpeed();
            if (spaceship.getX() + spaceship.getLength() / 2 > this.getX() + this.getLength() / 2) {
                movingState = movingState.RIGHT;
                if (spaceship.getX() + spaceship.getLength() / 2 - (this.getX() + this.getLength() / 2) <= this.getSpeed()) {
                    diff = spaceship.getX() + spaceship.getLength() / 2 - (this.getX() + this.getLength() / 2);
                }
                setX(getX() + diff);
            } else {
                movingState = movingState.LEFT;
                if (this.getX() + this.getLength() / 2 - (spaceship.getX() + spaceship.getLength() / 2) <= this.getSpeed()) {
                    diff = this.getX() + this.getLength() / 2 - (spaceship.getX() + spaceship.getLength() / 2);
                }
                setX(getX() - diff);

            }


        }
    }

    public void drawHealthBar(Canvas canvas, Paint paint) {
        paint.setColor(Color.GRAY);
        float diff = (getMaxhp() - getLength()) / 2;
        canvas.drawRect(getX() - diff, getY() - 50, getX() - diff + getMaxhp(), getY() - 20, paint);
        paint.setColor(Color.RED);
        canvas.drawRect(getX() - diff, getY() - 50, getX() - diff + getHp(), getY() - 20, paint);
    }

    public void setHp(float hp) {
        this.hp = hp;
    }



}
