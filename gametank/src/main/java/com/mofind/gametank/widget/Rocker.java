package com.mofind.gametank.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.mofind.gametank.GameConfig;

/**
 * Created by mofind on 16/6/29.
 * <p>
 * 摇杆类
 */
public class Rocker {

    // 画笔
    private Paint paint;

    // 大圆与小圆的中心点坐标
    private float mBigCircleX, mBigCircleY;
    private float mSmallCircleX, mSmallCircleY;

    // 大圆与小圆的半径
    private final int mBigCircleR = 100;
    private final float mSmallCircleR = 40;

    // 默认位置
    private final int mDefaultPositionX = 150;
    private final int mDefaultPositionY = GameConfig.SCREEN_H - 150;

    // 摇杆状态
    public static final int DIR_NO = -1;
    public static final int DIR_LEFT = 0;
    public static final int DIR_UP = 1;
    public static final int DIR_RIGHT = 2;
    public static final int DIR_DOWN = 3;

    public Rocker() {
        paint = new Paint();
        paint.setAntiAlias(true);
        reset();
    }

    public void draw(Canvas canvas) {
        canvas.save();
        // 画摇杆大圆区域
        paint.setColor(0x30ffffff);
        canvas.drawCircle(mBigCircleX, mBigCircleY, mBigCircleR, paint);
        // 画摇杆小圆区域
        paint.setColor(0xccffffff);
        canvas.drawCircle(mSmallCircleX, mSmallCircleY, mSmallCircleR, paint);
        canvas.restore();
    }

    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();

        // 监听按下与移动事件
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
            // 手指触摸区域不能超过屏幕的左半部分
            if(touchX > GameConfig.SCREEN_W / 2) {
                reset();
                return true;
            }
            // 如果小圆的中心点移出大圆的范围
            if (Math.sqrt(Math.pow((mBigCircleX - touchX), 2) + Math.pow((mBigCircleY - touchY), 2)) >= mBigCircleR) {
                double tempRad = getRad(mBigCircleX, mBigCircleY, event.getX(), event.getY());
                getXY(mBigCircleX, mBigCircleY, mBigCircleR, tempRad);
            } else {
                mSmallCircleX = (int) event.getX();
                mSmallCircleY = (int) event.getY();
            }

            // 判断方向
            double angle = getAngle(mSmallCircleX, mSmallCircleY, mBigCircleX, mBigCircleY);
            setDirection(angle);
        }
        // 监听松手的事件
        else if (event.getAction() == MotionEvent.ACTION_UP) {
            reset();
        }

        return true;
    }

    /**
     * 角度计算
     */
    public double getAngle(float px1, float py1, float px2, float py2) {
        //两点的x、y值
        double x = px2 - px1;
        double y = py2 - py1;
        double hypotenuse = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        //斜边长度
        double cos = x / hypotenuse;
        double radian = Math.acos(cos);
        //求出弧度
        double angle = 180 / (Math.PI / radian);
        //用弧度算出角度
        if (y < 0) {
            angle = -angle;
        } else if ((y == 0) && (x < 0)) {
            angle = 180;
        }
//        Log.d("angle", "" + angle);
        return angle;
    }

    /**
     * 判断方向
     *
     * @param angle
     */
    public void setDirection(double angle) {
        if (onRockerStatusListener == null)
            return;

        if (angle > 45 && angle <= 135) // 方向为上
            onRockerStatusListener.onDirection(DIR_UP);

        if (angle < -45 && angle >= -135) // 方向为下
            onRockerStatusListener.onDirection(DIR_DOWN);

        if ((angle > 0 && angle <= 45) || (angle < 0 && angle >= -45)) // 方向为左
            onRockerStatusListener.onDirection(DIR_LEFT);

        if ((angle > 135 && angle <= 180) || (angle < -135 && angle >= -180)) // 方向为右
            onRockerStatusListener.onDirection(DIR_RIGHT);
    }

    public double getRad(float px1, float py1, float px2, float py2) {
        float x = px2 - px1;
        float y = py1 - py2;
        float xie = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        float cosAngle = x / xie;
        float rad = (float) Math.acos(cosAngle);
        if (py2 < py1) {
            rad = -rad;
        }
        return rad;
    }

    public void getXY(float centerX, float centerY, float R, double rad) {
        mSmallCircleX = (float) (R * Math.cos(rad)) + centerX;
        mSmallCircleY = (float) (R * Math.sin(rad)) + centerY;
    }

    public void reset() {
        mBigCircleX = mDefaultPositionX;
        mBigCircleY = mDefaultPositionY;
        mSmallCircleX = mDefaultPositionX;
        mSmallCircleY = mDefaultPositionY;

        if (onRockerStatusListener != null)
            onRockerStatusListener.onDirection(DIR_NO);
    }

    private OnRockerStatusListener onRockerStatusListener;

    public OnRockerStatusListener getOnRockerStatusListener() {
        return onRockerStatusListener;
    }

    public void setOnRockerStatusListener(OnRockerStatusListener onRockerStatusListener) {
        this.onRockerStatusListener = onRockerStatusListener;
    }

    public interface OnRockerStatusListener {
        void onDirection(int direction);
    }

}
