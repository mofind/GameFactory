package com.mofind.gametank.spirit;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.mofind.gametank.GameConfig;

/**
 * Created by mofind on 16/6/29.
 * <p/>
 * 坦克类
 */
public class Tank {

    public static final int DIR_LEFT = 0;
    public static final int DIR_UP = 1;
    public static final int DIR_RIGHT = 2;
    public static final int DIR_DOWN = 3;

    // 坦克的位置坐标
    public float x, y;

    // 坦克的大小
    public int width = 50, height = 50;

    // 坦克行走速度，单位(像素/帧)
    public int speed = 0;

    // 坦克行走状态
    public int status = DIR_UP;

    // 是否是我军坦克
    public boolean isSelf = false;

    public Tank() {
        // 坦克宽高
        width = GameConfig.mHeroTankRes[0].getWidth();
        height = GameConfig.mHeroTankRes[0].getHeight();
    }

    public void draw(Canvas c) {
        c.save();
        c.drawBitmap(isSelf ? GameConfig.mHeroTankRes[status] : GameConfig.mEnemyTankRes[status], x, y, null);
        c.restore();
    }

    /**
     * 坦克移动
     */
    public void move() {
        switch (status) {
            case DIR_LEFT:
                x -= speed;
                break;
            case DIR_RIGHT:
                x += speed;
                break;
            case DIR_DOWN:
                y += speed;
                break;
            case DIR_UP:
                y -= speed;
                break;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();

        return true;
    }
}
