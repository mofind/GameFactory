package com.mofind.gamefactory;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by mofind on 16/6/30.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private Canvas mCanvas = null;
    private Paint mPaint = new Paint();

    private SurfaceHolder mHolder;

    private Thread mThread;
    private boolean isRunning = false;

    public GameView(Context context) {
        super(context);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mPaint.setTextSize(50);
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
            if (mCanvas != null) {
                mCanvas.drawColor(Color.BLUE);

                mCanvas.save();
                mPaint.setColor(Color.YELLOW);
                mCanvas.skew(2, 2);
                mCanvas.drawCircle(100, 100, 100, mPaint);
                mCanvas.restore();

                mCanvas.save();
                mPaint.setColor(Color.RED);
                mCanvas.translate(100, 0);
                mCanvas.rotate(45, 100, 100);
                mCanvas.scale(2, 2);
                mCanvas.drawRect(new Rect(200, 0, 400, 200), mPaint);
                mCanvas.restore();

            }
        } catch (Exception e) {
            //TODO
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }
}
