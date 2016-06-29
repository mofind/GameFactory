package com.mofind.gametank.spirit;

import com.mofind.gametank.GameConfig;

/**
 * Created by mofind on 16/6/29.
 * <p/>
 * 敌方坦克类
 */
public class EnemyTank extends Tank {

    public EnemyTank(float x, float y, int speed, int status) {
        super();
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.status = status;
        this.isSelf = false;
    }

    @Override
    public void move() {
        super.move();
        movePath();
    }

    // 碰撞测试 -- 绘制移动路径
    public boolean movePath() {

        // 左边界
        if (x < 0) {
            status = DIR_RIGHT;
            return true;
        }

        // 右边界
        if (x > GameConfig.SCREEN_W - width) {
            status = DIR_LEFT;
            return true;
        }

//        // 上边界
//        if (y < 0) {
//            status = (x <= 0 ? DIR_RIGHT : DIR_LEFT);
//            return true;
//        }
//
//        // 下边界
//        if (y > GameConfig.SCREEN_H - height) {
//            status = DIR_UP;
//            return true;
//        }
//
//        Log.i("check", "x= " + x + " y= " + y + " h= " + (GameConfig.SCREEN_H - height));

        return false;
    }
}
