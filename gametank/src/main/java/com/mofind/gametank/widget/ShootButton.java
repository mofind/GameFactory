package com.mofind.gametank.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.mofind.gametank.GameConfig;

/**
 * Created by mofind on 16/6/30.
 * <p/>
 * 射击按钮
 */
public class ShootButton {

    public int x, y, r;

    private Paint p = new Paint();

    private int bgColor = 0x30ffffff;

    public ShootButton() {
        r = 80;
        x = GameConfig.SCREEN_W - 100;
        y = GameConfig.SCREEN_H - 150;
    }

    public void draw(Canvas c) {
        c.save();
        p.setColor(bgColor);
        c.drawCircle(x, y, r, p);
        p.setColor(0xccffffff);
        p.setTextSize(60); //设置字体大小
        c.drawText("射击", x - 60, y + 20, p);
        c.restore();
    }

    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();

        // 监听按下事件
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (Math.sqrt(Math.pow(touchX - x, 2) + Math.pow(touchY - y, 2)) <= r) {
                bgColor = 0x30ff0000;
                if (onClickListener != null) {
                    onClickListener.onClick(this);
                    return true;
                }
            }
        }
        // 监听松手的事件
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            bgColor = 0x30ffffff;
        }

        return false;
    }

    private OnClickListener onClickListener;

    public OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener {
        void onClick(ShootButton button);
    }
}
