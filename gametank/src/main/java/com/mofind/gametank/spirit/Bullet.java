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
    }
}
