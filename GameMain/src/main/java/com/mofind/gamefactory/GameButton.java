package com.mofind.gamefactory;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by mofind on 16/6/27.
 */
public class GameButton {

    private int width, height, x, y;
    private Bitmap bg, bg2;
    private Paint paint = new Paint();

    private Canvas mCanvas;

    public boolean mIsPressed = false;

    public GameButton(int x, int y, Bitmap bg, Bitmap bg2) {
        this.width = bg.getWidth();
        this.height = bg.getHeight();
        this.x = x;
        this.y = y;
        this.bg = bg;
        this.bg2 = bg2;
    }

    public void draw(Canvas canvas) {
        mCanvas = canvas;
        canvas.save();
        if (mIsPressed) canvas.drawBitmap(bg2, x, y, paint);
        else canvas.drawBitmap(bg, x, y, paint);
        canvas.restore();
    }

    public void drawBg2(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(bg2, x, y, paint);
        canvas.restore();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Bitmap getBg() {
        return bg;
    }

    public void setBg(Bitmap bg) {
        this.bg = bg;
    }


    private boolean isPressed = false;

    private OnClickListener onClickListener;

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setButtonStatus(boolean status) {
        if (status) {
            drawBg2(mCanvas);
        } else {
            draw(mCanvas);
        }
    }

    public interface OnClickListener {
        void onClick();
    }
}
