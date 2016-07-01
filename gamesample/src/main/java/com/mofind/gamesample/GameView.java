package com.mofind.gamesample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * Created by mofind on 16/6/30.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    SurfaceHolder mHolder;
    Canvas mCanvas;
    Paint mPaint = new Paint();

    Thread mThread;
    boolean isRunning = false;

    public GameView(Context context) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        isRunning = true;
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isRunning = false;
    }

    @Override
    public void run() {
        while (isRunning) {
            doDraw();
        }
    }

    private void doDraw() {

        try {
            mCanvas = mHolder.lockCanvas();
            mPaint.setColor(Color.RED);
            mCanvas.drawCircle(100, 100, 50, mPaint);

            mCanvas.save();
            mPaint.setColor(Color.YELLOW);
            mCanvas.drawRect(200, 200, 400, 400, mPaint);
            mCanvas.restore();

        } catch (Exception e) {

        } finally {
            mHolder.unlockCanvasAndPost(mCanvas);
        }

    }
}
