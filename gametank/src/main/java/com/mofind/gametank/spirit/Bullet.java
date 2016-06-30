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

    public float x, y, r;
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
        x = tank.x;
        y = tank.y;
        r = 10;

        mPaint.setColor(Color.WHITE);
    }

    public void draw(Canvas c) {
        c.save();
        c.drawCircle(x, y, r, mPaint);
        c.restore();
    }

    public void shoot() {
        y -= speed;
        if (y < -50) {
            isDead = true;
        }
    }
}
