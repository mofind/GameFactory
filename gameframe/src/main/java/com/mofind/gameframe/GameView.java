package com.mofind.gameframe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

/**
 * Created by mofind on 16/6/28.
 */
public class GameView extends SurfaceView implements Callback, Runnable {

    private SurfaceHolder mHolder;
    private Paint mPaint;
    private Canvas mCanvas;
    // 游戏主线程
    private Thread mThread;
    private boolean isRunning;

    // 屏幕宽高
    private int SCREEN_W, SCREEN_H;
    private int mTouchX, mTouchY;

    public GameView(Context context) {
        super(context);
        //实例holder
        mHolder = getHolder();
        //为SurfaceView添加状态监听
        mHolder.addCallback(this);
        //设置画笔颜色为白色
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
    }

    /**
     * 当SurfaceView被创建时调用
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        SCREEN_W = this.getWidth();
        SCREEN_H = this.getHeight();
        mTouchX = SCREEN_W / 2;
        mTouchY = SCREEN_H / 2;
        isRunning = true;
        /*
         *  我们知道SurfaceView是View的子类。
         *  所以，SurfaceView也有View类的触摸屏监听、按键监听等等父类方法。
         *  但是，在SurfaceView中，onDraw方法不再执行，而是通过LockCanvas() 来实现获取Canvas。
         *  所以，我们在这里要自己写一个绘图方法doDraw()
         */
        doDraw();
    }

    /**
     * 当SurfaceView被修改时调用
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    /**
     * 当SurfaceView销毁时调用
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    /**
     * 自定义绘图函数
     */
    public void doDraw() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                mCanvas.drawRGB(0, 0, 0);
                mCanvas.drawCircle(mTouchX, mTouchY, 100, mPaint);
            }
        } catch (Exception e) {
            /*
            * 这里需要注意，在canvas绘图过程中，很有肯能出现各种各样奇怪的问题。
            * 所以，在这里使用try catch进行异常捕获。并且在finally语句块中把
            * canvas解锁。保证下次绘图的正常进行。
            */
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchX = (int) event.getX();
        mTouchY = (int) event.getY();
        doDraw();
        return true;
    }

    /**
     * 游戏逻辑
     */
    public void logic() {
        //TODO 游戏逻辑
    }

    @Override
    public void run() {
        while (isRunning) {
            synchronized (mHolder) {
                long startTime = System.currentTimeMillis();
                doDraw();
                logic();
                long endTime = System.currentTimeMillis();
                /*
                * 1000ms /60 = 16.67ms 这里，我们采用15，使帧率限制在最大66.7帧
                * 如果担心发热、耗电问题，同样可以使用稍大一些的值。经测试80基本为最大值。
                */
                if (endTime - startTime < 15) {
                    try {
                        Thread.sleep(15 - (endTime - startTime));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
