package com.mofind.gametank;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by mofind on 16/6/29.
 */
public class GameConfig extends Application {

    // 屏幕宽高
    public static int SCREEN_W, SCREEN_H;

    public static Bitmap[] mHeroTankRes = null;
    public static Bitmap[] mEnemyTankRes = null;
    public static Bitmap mWallRes = null;

    public static GameConfig app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        // 获取屏幕宽高
        SCREEN_W = getScreenWidth();
        SCREEN_H = getScreenHeight();

        // 获取资源
        mHeroTankRes = getHeroTankRes(getResources());
        mEnemyTankRes = getEnemyTankRes(getResources());
        mWallRes = BitmapFactory.decodeResource(getResources(), R.drawable.wall);
    }

    public static final Bitmap[] getHeroTankRes(Resources res) {
        final Bitmap[] datas = new Bitmap[4];
        datas[0] = BitmapFactory.decodeResource(res, R.drawable.hero_tank_left);
        datas[1] = BitmapFactory.decodeResource(res, R.drawable.hero_tank_up);
        datas[2] = BitmapFactory.decodeResource(res, R.drawable.hero_tank_right);
        datas[3] = BitmapFactory.decodeResource(res, R.drawable.hero_tank_down);
        return datas;
    }

    public static final Bitmap[] getEnemyTankRes(Resources res) {
        final Bitmap[] datas = new Bitmap[4];
        datas[0] = BitmapFactory.decodeResource(res, R.drawable.enemy_tank_left);
        datas[1] = BitmapFactory.decodeResource(res, R.drawable.enemy_tank_up);
        datas[2] = BitmapFactory.decodeResource(res, R.drawable.enemy_tank_right);
        datas[3] = BitmapFactory.decodeResource(res, R.drawable.enemy_tank_down);
        return datas;
    }

    public int getScreenWidth() {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        return width;
    }

    public int getScreenHeight() {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        int height = metric.heightPixels;
        return height;
    }
}
