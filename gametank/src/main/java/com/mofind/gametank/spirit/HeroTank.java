package com.mofind.gametank.spirit;

import android.util.Log;

import com.mofind.gametank.GameConfig;

/**
 * Created by mofind on 16/6/29.
 * <p/>
 * 玩家坦克类
 */
public class HeroTank extends Tank {

    public Bullet mBullet;

    public HeroTank() {
        super();
        this.isSelf = true;
        mBullet = new Bullet(this);
        reset();
    }

    public void reset() {
        x = 400;
        y = GameConfig.SCREEN_H - 150;
        // 坦克方向
        status = DIR_UP;
    }

    @Override
    public void move() {
        super.move();
        isCheckBorder();
    }

    // 碰撞测试 -- 边缘检测
    public boolean isCheckBorder() {
        if (x <= 0) {
            x = 0;
            Log.e("check", "左边界");
            return true;
        }

        if (x + width >= GameConfig.SCREEN_W) {
            x = GameConfig.SCREEN_W - width;
            Log.e("check", "右边界");
            return true;
        }

        if (y <= 0) {
            y = 0;
            Log.e("check", "上边界");
            return true;
        }

        if (y + height >= GameConfig.SCREEN_H) {
            y = GameConfig.SCREEN_H - height;
            Log.e("check", "下边界");
            return true;
        }

//        Log.i("check", "x= " + x + " y= " + y);

        return false;
    }

    // 碰撞测试 -- 障碍物
    public boolean isCheckWall(Wall wall) {
        if (this.x >= wall.x && this.x >= wall.x + wall.w) {
            return false;
        } else if (this.x <= wall.x && this.x + this.width <= wall.x) {
            return false;
        } else if (this.y >= wall.y && this.y >= wall.y + wall.h) {
            return false;
        } else if (this.y <= wall.y && this.y + this.height <= wall.y) {
            return false;
        }
        return true;
    }

    // 碰撞测试 -- 敌方坦克
    public boolean isCheckEnemy(Tank enemy) {
        if (this.x >= enemy.x && this.x >= enemy.x + enemy.width) {
            return false;
        } else if (this.x <= enemy.x && this.x + this.width <= enemy.x) {
            return false;
        } else if (this.y >= enemy.y && this.y >= enemy.y + enemy.height) {
            return false;
        } else if (this.y <= enemy.y && this.y + this.height <= enemy.y) {
            return false;
        }
        return true;
    }

}
