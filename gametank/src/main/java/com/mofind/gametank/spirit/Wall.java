package com.mofind.gametank.spirit;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.mofind.gametank.GameConfig;

/**
 * Created by mofind on 16/6/29.
 */
public class Wall {

    public int x, y, w, h;
    private Bitmap b;

    public Wall(Bitmap b) {
        this.b = b;
        w = b.getWidth();
        h = b.getHeight();
        x = (GameConfig.SCREEN_W - w) / 2;
        y = GameConfig.SCREEN_H - h;
    }

    public void draw(Canvas c) {
        c.save();
        c.drawBitmap(b, x, y, null);
        c.restore();
    }

}
