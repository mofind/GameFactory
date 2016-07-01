package com.mofind.gametank;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.mofind.gametank.spirit.Bullet;
import com.mofind.gametank.spirit.EnemyTank;
import com.mofind.gametank.spirit.HeroTank;
import com.mofind.gametank.spirit.Tank;
import com.mofind.gametank.spirit.Wall;
import com.mofind.gametank.widget.Rocker;
import com.mofind.gametank.widget.ShootButton;

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

    // 触摸点坐标
    private int mTouchX, mTouchY;

    // 精灵类与控件
    private HeroTank mHeroTank;
    private EnemyTank[] mEnemyTanks;
    private Bullet mBullet;
    private Wall mWall;
    private Rocker mRocker;
    private ShootButton mBtn;

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
        startGame();
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
        stopGame();
    }

    public void startGame() {
        isRunning = true;
        mThread = new Thread(this);
        mThread.start();

        // 绘制障碍物
        mWall = new Wall(GameConfig.mWallRes);

        // 英雄坦克
        mHeroTank = new HeroTank();

        // 敌方坦克
        mEnemyTanks = new EnemyTank[10];
        boolean flag = false;
        for (int i = 0; i < mEnemyTanks.length; i++) {
            if (flag) {
                mEnemyTanks[i] = new EnemyTank(0, i * 100, 1 + (int) (Math.random() * 19), Tank.DIR_LEFT);
            } else {
                mEnemyTanks[i] = new EnemyTank(0, i * 100, 1 + (int) (Math.random() * 19), Tank.DIR_RIGHT);
            }
            flag = !flag;
        }

        // 实例射击按钮
        mBtn = new ShootButton();
        mBtn.setOnClickListener(new ShootButton.OnClickListener() {
            @Override
            public void onClick(ShootButton button) {
                mBullet = new Bullet(mHeroTank);
            }
        });

        // 实例摇杆
        mRocker = new Rocker();
        // 摇杆监听
        mRocker.setOnRockerStatusListener(new Rocker.OnRockerStatusListener() {
            @Override
            public void onDirection(int direction) {
                // 方向
                if (direction == Rocker.DIR_NO) {
                    mHeroTank.speed = 0;
                } else {
                    mHeroTank.speed = 10;
                    mHeroTank.status = direction;
                }
            }
        });
    }

    public void stopGame() {
        isRunning = false;
    }

    /**
     * 自定义绘图函数
     */
    public void doDraw() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                // 绘制背景
                mCanvas.drawRGB(0, 0, 0);
                // 绘制摇杆
                mRocker.draw(mCanvas);
                // 绘制射击按钮
                mBtn.draw(mCanvas);
                // 绘制障碍物
                mWall.draw(mCanvas);
                // 绘制英雄坦克
                mHeroTank.draw(mCanvas);
                // 绘制敌军坦克
                for(EnemyTank tank : mEnemyTanks) {
                    tank.draw(mCanvas);
                }
                // 绘制子弹
                mBullet.draw(mCanvas);
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

        if (mBtn != null) {
            if (mBtn.onTouchEvent(event)) {
                return true;
            }
        }

        if (mRocker != null)
            mRocker.onTouchEvent(event);

        return true;
    }

    /**
     * 游戏逻辑
     */
    public void logic() {
        //TODO 游戏逻辑
        // 移动坦克
        mHeroTank.move();
        for(EnemyTank tank : mEnemyTanks) {
            tank.move();
        }

        // 碰撞测试 -- 敌军坦克
        if (mHeroTank.isCheckEnemy(mEnemyTanks)) {
            Log.e("check", "碰到敌方坦克，你挂了~");
            mHeroTank.reset();
        }

        // 碰撞测试 -- 障碍物
        if (mHeroTank.isCheckWall(mWall)) {
            Log.e("check", "亲，这是墙，别撞~");

            if (mHeroTank.x + mHeroTank.width >= mWall.x) {
                mHeroTank.x = mWall.x - mHeroTank.width;
            }
        }

        // 子弹超出屏幕回收
        if (mBullet != null && mBullet.isDead) {
            mBullet = null;
        }
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
