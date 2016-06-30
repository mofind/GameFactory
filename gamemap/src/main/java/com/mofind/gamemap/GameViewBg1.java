package com.mofind.gamemap;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.InputStream;

/**
 * Created by mofind on 16/6/19.
 */
public class GameViewBg1 extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public final static int ANIM_DOWN = 0;
    public final static int ANIM_LEFT = 1;
    public final static int ANIM_RIGHT = 2;
    public final static int ANIM_UP = 3;
    public final static int ANIM_COUNT = 4;

    ResourcesUtils mHeroAnim[] = new ResourcesUtils[ANIM_COUNT];

    Paint mPaint = null;

    private boolean mAllkeyDown = false;
    private boolean mIskeyDown = false;
    private boolean mIskeyLeft = false;
    private boolean mIskeyRight = false;
    private boolean mIskeyUp = false;

    int mAnimationState = 0;

    public final static int TILE_WIDTH = 32;
    public final static int TILE_HEIGHT = 32;

    public final static int BUFFER_WIDTH_COUNT = 10;
    public final static int BUFFER_HEIGHT_COUNT = 15;

    public final static int SCENCE_WIDTH = 480;
    public final static int SCENCE_HEIGHT = 800;

    public final static int SCENCE_OFFSET = 3;
    public final static int SCENCE_OFFSET_WIDTH = 100;

    public final static int TILE_WIDTH_COUNT = 15;
    public final static int TILE_HEIGHT_COUNT = 25;

    public final static int TILE_NULL = 0;

    public int[][] mMapView = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

    public int[][] mMapAcotor = {
            {5, 6, 7, 0, 3, 4, 3, 4, 3, 4, 0, 0, 2, 2, 0},
            {13, 14, 15, 0, 11, 12, 11, 12, 11, 12, 0, 0, 10, 10, 0},
            {21, 22, 23, 0, 19, 20, 19, 20, 19, 20, 0, 0, 18, 18, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2},
            {10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10},
            {18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18},
            {8, 8, 8, 8, 9, 0, 0, 0, 0, 0, 9, 8, 8, 8, 8},
            {8, 8, 8, 8, 17, 0, 0, 0, 0, 0, 17, 8, 8, 8, 8},
            {3, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 5, 6, 7},
            {11, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 13, 14, 15},
            {19, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 18, 21, 22, 23},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {8, 8, 8, 8, 9, 0, 0, 0, 0, 0, 9, 8, 8, 8, 8},
            {8, 8, 8, 8, 17, 0, 0, 0, 0, 0, 17, 8, 8, 8, 8},
            {5, 6, 7, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 4},
            {13, 14, 15, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 11, 12},
            {21, 22, 23, 18, 0, 0, 0, 0, 0, 0, 0, 0, 0, 19, 20},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};

    public int[][] mCollision = {
            {-1, -1, -1, 0, -1, -1, -1, -1, -1, -1, 0, 0, -1, -1, 0},
            {-1, -1, -1, 0, -1, -1, -1, -1, -1, -1, 0, 0, -1, -1, 0},
            {-1, -1, -1, 0, -1, -1, -1, -1, -1, -1, 0, 0, -1, -1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1},
            {-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1},
            {-1, -1, -1, -1, -1, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1},
            {-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1},
            {-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1},
            {-1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {-1, -1, -1, -1, -1, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, -1, 0, 0, 0, 0, 0, -1, -1, -1, -1, -1},
            {-1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1},
            {-1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1},
            {-1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}};


    Bitmap mBitmap = null;

    Resources mResources = null;

    int mWidthTileCount = 0;
    int mHeightTileCount = 0;

    int mBitMapWidth = 0;
    int mBitMapHeight = 0;

    int mHeroPosX = 0;
    int mHeroPosY = 0;

    int mBackHeroPosX = 0;
    int mBackHeroPosY = 0;

    int mBackHeroScreenX = 0;
    int mBackHeroScreenY = 0;

    int mHeroImageX = 0;
    int mHeroImageY = 0;

    int mHeroScreenX = 0;
    int mHeroScreenY = 0;

    int mHeroIndexX = 0;
    int mHeroIndexY = 0;

    int mScreenWidth = 0;
    int mScreenHeight = 0;

    int mBufferIndexX = 0;
    int mBufferIndexY = 0;

    int mMapPosX = 0;
    int mMapPosY = 0;

    public final static int OFF_HERO_X = 16;
    public final static int OFF_HERO_Y = 35;

    public final static int HERO_STEP = 4;

    private boolean isAcotrCollision = false;
    private boolean isBorderCollision = false;
    private Thread mThread = null;
    private boolean mIsRunning = false;

    private SurfaceHolder mSurfaceHolder = null;
    private Canvas mCanvas = null;

    public GameViewBg1(Context context, int screenWidth, int screenHeight) {
        super(context);
        mPaint = new Paint();
        mScreenWidth = screenWidth;
        mScreenHeight = screenHeight;
        initAnimation(context);
        initMap(context);
        initHero();

        /** SurfaceHolder **/
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
    }

    private void initHero() {
        mHeroImageX = 100;
        mHeroImageY = 100;
        mHeroPosX = mHeroImageX + OFF_HERO_X;
        mHeroPosY = mHeroImageY + OFF_HERO_Y;
        mHeroIndexX = mHeroPosX / TILE_WIDTH;
        mHeroIndexY = mHeroPosY / TILE_HEIGHT;
        mHeroScreenX = mHeroPosX;
        mHeroScreenY = mHeroPosY;
    }

    private void initMap(Context context) {
        mBitmap = ReadBitMap(context, R.drawable.map);
        mBitMapWidth = mBitmap.getWidth();
        mBitMapHeight = mBitmap.getHeight();
        mWidthTileCount = mBitMapWidth / TILE_WIDTH;
        mHeightTileCount = mBitMapHeight / TILE_HEIGHT;
    }

    private void initAnimation(Context context) {
        mHeroAnim[ANIM_DOWN] = new ResourcesUtils(context, new int[]{
                R.drawable.hero_down_a, R.drawable.hero_down_b,
                R.drawable.hero_down_c, R.drawable.hero_down_d}, true);
        mHeroAnim[ANIM_LEFT] = new ResourcesUtils(context, new int[]{
                R.drawable.hero_left_a, R.drawable.hero_left_b,
                R.drawable.hero_left_c, R.drawable.hero_left_d}, true);
        mHeroAnim[ANIM_RIGHT] = new ResourcesUtils(context, new int[]{
                R.drawable.hero_right_a, R.drawable.hero_right_b,
                R.drawable.hero_right_c, R.drawable.hero_right_d}, true);
        mHeroAnim[ANIM_UP] = new ResourcesUtils(context, new int[]{
                R.drawable.hero_up_a, R.drawable.hero_up_b,
                R.drawable.hero_up_c, R.drawable.hero_up_d}, true);
    }

    protected void Draw() {
        DrawMap(mCanvas, mPaint, mBitmap);
        RenderAnimation(mCanvas);
        UpdateHero();
        if (isBorderCollision) {
            DrawCollision(mCanvas, "str1");
        }

        if (isAcotrCollision) {
            DrawCollision(mCanvas, "str2");
        }
    }

    private void DrawCollision(Canvas canvas, String str) {
        drawRimString(canvas, str, Color.WHITE, mScreenWidth >> 1,
                mScreenHeight >> 1);
    }

    private void UpdateHero() {
        if (mAllkeyDown) {
            if (mIskeyDown) {
                mAnimationState = ANIM_DOWN;
                mHeroPosY += HERO_STEP;

                if (mHeroScreenY >= 320) {
                    if (mHeroIndexY >= 10 && mHeroIndexY <= 20) {
                        mMapPosY -= HERO_STEP;
                    } else {
                        mHeroScreenY += HERO_STEP;
                    }
                } else {
                    mHeroScreenY += HERO_STEP;
                }
            } else if (mIskeyLeft) {
                mAnimationState = ANIM_LEFT;
                mHeroPosX -= HERO_STEP;
                if (mHeroScreenX <= 96) {
                    if (mHeroIndexX >= 3 && mHeroIndexX <= 7) {
                        mMapPosX += HERO_STEP;
                    } else {
                        mHeroScreenX -= HERO_STEP;
                    }
                } else {
                    mHeroScreenX -= HERO_STEP;
                }
            } else if (mIskeyRight) {
                mAnimationState = ANIM_RIGHT;
                mHeroPosX += HERO_STEP;
                if (mHeroScreenX >= 192) {
                    if (mHeroIndexX >= 6 && mHeroIndexX <= 10) {
                        mMapPosX -= HERO_STEP;
                    } else {
                        mHeroScreenX += HERO_STEP;
                    }
                } else {
                    mHeroScreenX += HERO_STEP;
                }
            } else if (mIskeyUp) {
                mAnimationState = ANIM_UP;
                mHeroPosY -= HERO_STEP;

                if (mHeroScreenY <= 160) {
                    if (mHeroIndexY >= 5 && mHeroIndexY <= 15) {
                        mMapPosY += HERO_STEP;
                    } else {
                        mHeroScreenY -= HERO_STEP;
                    }
                } else {
                    mHeroScreenY -= HERO_STEP;
                }
            }
            mHeroIndexX = mHeroPosX / TILE_WIDTH;
            mHeroIndexY = mHeroPosY / TILE_HEIGHT;


            isBorderCollision = false;
            if (mHeroPosX <= 0) {
                mHeroPosX = 0;
                mHeroScreenX = 0;
                isBorderCollision = true;
            } else if (mHeroPosX >= TILE_WIDTH_COUNT * TILE_WIDTH) {
                mHeroPosX = TILE_WIDTH_COUNT * TILE_WIDTH;
                mHeroScreenX = mScreenWidth;
                isBorderCollision = true;
            }
            if (mHeroPosY <= 0) {
                mHeroPosY = 0;
                mHeroScreenY = 0;
                isBorderCollision = true;
            } else if (mHeroPosY >= TILE_HEIGHT_COUNT * TILE_HEIGHT) {
                mHeroPosY = TILE_HEIGHT_COUNT * TILE_HEIGHT;

                mHeroScreenY = mScreenHeight;

                isBorderCollision = true;
            }

            if (mMapPosX >= 0) {
                mMapPosX = 0;
            } else if (mMapPosX <= -(480 - 320)) {
                mMapPosX = -(480 - 320);
            }
            if (mMapPosY >= 0) {
                mMapPosY = 0;
            } else if (mMapPosY <= -(800 - 480)) {
                mMapPosY = -(800 - 480);
            }

            /** Խ���� **/
            int width = mCollision[0].length - 1;
            int height = mCollision.length - 1;

            if (mHeroIndexX <= 0) {
                mHeroIndexX = 0;
            } else if (mHeroIndexX >= width) {
                mHeroIndexX = width;
            }
            if (mHeroIndexY <= 0) {
                mHeroIndexY = 0;
            } else if (mHeroIndexY >= height) {
                mHeroIndexY = height;
            }

            //��ײ���
            if (mCollision[mHeroIndexY][mHeroIndexX] == -1) {
                mHeroPosX = mBackHeroPosX;
                mHeroPosY = mBackHeroPosY;
                mHeroScreenY = mBackHeroScreenY;
                mHeroScreenX = mBackHeroScreenX;
                isAcotrCollision = true;
            } else {
                mBackHeroPosX = mHeroPosX;
                mBackHeroPosY = mHeroPosY;
                mBackHeroScreenX = mHeroScreenX;
                mBackHeroScreenY = mHeroScreenY;
                isAcotrCollision = false;
            }

            Log.v("hero", "mHeroIndexX " + mHeroIndexX);
            Log.v("hero", "mHeroIndexY " + mHeroIndexY);

            mHeroImageX = mHeroScreenX - OFF_HERO_X;
            mHeroImageY = mHeroScreenY - OFF_HERO_Y;
        }
    }

    private void RenderAnimation(Canvas canvas) {
        if (mAllkeyDown) {
            mHeroAnim[mAnimationState].DrawAnimation(canvas, mPaint, mHeroImageX, mHeroImageY);
        } else {
            mHeroAnim[mAnimationState].DrawFrame(canvas, mPaint, mHeroImageX, mHeroImageY, 0);
        }
    }

    public void setKeyState(int keyCode, boolean state) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_DOWN:
                mIskeyDown = state;
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                mIskeyUp = state;
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                mIskeyLeft = state;
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                mIskeyRight = state;
                break;
        }
        mAllkeyDown = state;
    }

    private void DrawMap(Canvas canvas, Paint paint, Bitmap bitmap) {
        int i, j;
        for (i = 0; i < TILE_HEIGHT_COUNT; i++) {
            for (j = 0; j < TILE_WIDTH_COUNT; j++) {
                int ViewID = mMapView[i][j];
                int ActorID = mMapAcotor[i][j];
                if (ViewID > TILE_NULL) {
                    DrawMapTile(ViewID, canvas, paint, bitmap, mMapPosX
                            + (j * TILE_WIDTH), mMapPosY
                            + (i * TILE_HEIGHT));
                }
                if (ActorID > TILE_NULL) {
                    DrawMapTile(ActorID, canvas, paint, bitmap, mMapPosX
                            + (j * TILE_WIDTH), mMapPosY
                            + (i * TILE_HEIGHT));
                }
            }
        }
    }

    private void DrawMapTile(int id, Canvas canvas, Paint paint,
                             Bitmap bitmap, int x, int y) {
        id--;
        int count = id / mWidthTileCount;
        int bitmapX = (id - (count * mWidthTileCount)) * TILE_WIDTH;
        int bitmapY = count * TILE_HEIGHT;
        DrawClipImage(canvas, paint, bitmap, x, y, bitmapX, bitmapY,
                TILE_WIDTH, TILE_HEIGHT);
    }

    /**
     * @param canvas
     * @param paint
     * @param bitmap
     * @param x
     * @param y
     * @param src_x
     * @param src_y
     */
    private void DrawClipImage(Canvas canvas, Paint paint, Bitmap bitmap,
                               int x, int y, int src_x, int src_y, int src_xp, int src_yp) {
        canvas.save();
        canvas.clipRect(x, y, x + src_xp, y + src_yp);
        canvas.drawBitmap(bitmap, x - src_x, y - src_y, paint);
        canvas.restore();
    }

    /**
     * @param bitmap
     * @param x
     * @param y
     * @param w
     * @param h
     * @return
     */
    public Bitmap BitmapClipBitmap(Bitmap bitmap, int x, int y, int w, int h) {
        return Bitmap.createBitmap(bitmap, x, y, w, h);
    }

    /**
     * @param context
     * @param resId
     * @return
     */
    public Bitmap ReadBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // ��ȡ��ԴͼƬ
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * @param canvas
     * @param str
     * @param color
     * @param x
     * @param y
     */
    public final void drawRimString(Canvas canvas, String str, int color,
                                    int x, int y) {
        int backColor = mPaint.getColor();
        mPaint.setColor(~color);
        canvas.drawText(str, x + 1, y, mPaint);
        canvas.drawText(str, x, y + 1, mPaint);
        canvas.drawText(str, x - 1, y, mPaint);
        canvas.drawText(str, x, y - 1, mPaint);
        mPaint.setColor(color);
        canvas.drawText(str, x, y, mPaint);
        mPaint.setColor(backColor);
    }

    @Override
    public void run() {
        while (mIsRunning) {
            synchronized (mSurfaceHolder) {
                mCanvas = mSurfaceHolder.lockCanvas();
                Draw();
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        mIsRunning = true;
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        mIsRunning = false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        setKeyState(keyCode, true);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        setKeyState(keyCode, false);
        return super.onKeyUp(keyCode, event);
    }
}
