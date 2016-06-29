package com.mofind.gamefactory.main;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.SurfaceView;

import com.mofind.gamefactory.GameViewBg1;
import com.mofind.gamefactory.R;

/**
 * Created by mofind on 16/6/19.
 */
public class GameConfig extends Application {

    public static GameConfig app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static String[] getOptions() {
        return GameConfig.app.getResources().getStringArray(R.array.app_option);
    }

    /**
     * 根据索引判断加载GameView
     *
     * @param c
     * @param w
     * @param h
     * @param index
     * @return
     */
    public static <T extends SurfaceView> T buildGameView(Context c, int w, int h, int index) {
        T gameView = null;
        switch (index) {
            case 0:
                gameView = (T) new GameViewBg1(c, w, h);
                break;
            default:
                gameView = (T) new GameViewBg1(c, w, h);
                break;
        }
        return gameView;
    }

    /**
     * Activity 跳转
     *
     * @param c
     * @param target
     * @param extra
     */
    public static void launcher(Context c, Class target, Bundle extra) {
        Intent intent = new Intent(c, target);
        if (extra != null)
            intent.putExtra("extra", extra);
        c.startActivity(intent);
    }

    public static void launcher(Context c, Class target) {
        launcher(c, target, null);
    }
}
