package com.mofind.gametank.spirit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by mofind on 16/6/30.
 * <p/>
 * 子弹类
 */
public class Bullet {

    public float mBulletX, mBulletY, mBulletR;
    public int speed = 20;
    private Paint mPaint = new Paint();

    private int dir;// 当前Boss子弹方向

    public static final int DIR_LEFT = 0;
    public static final int DIR_UP = 1;
    public static final int DIR_RIGHT = 2;
    public static final int DIR_DOWN = 3;

    // 子弹是否超屏， 优化处理
    public boolean isDead;

    public Bullet(HeroTank tank) {
        mBulletX = tank.x + tank.width / 2;
        mBulletY = tank.y;
        mBulletR = 10;

        mPaint.setColor(Color.WHITE);
    }

    public void draw(Canvas c) {
        c.save();
        c.drawCircle(mBulletX, mBulletY, mBulletR, mPaint);
        c.restore();

        shoot();
    }

    public void shoot() {
        mBulletY -= speed;
        if (mBulletY < -50) {
            isDead = true;
        }
    }
}
