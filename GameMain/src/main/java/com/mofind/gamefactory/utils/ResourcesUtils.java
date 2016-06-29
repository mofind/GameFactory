package com.mofind.gamefactory.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.io.InputStream;

public class ResourcesUtils {

    private long mLastPlayTime = 0;
    private int mPlayID = 0;
    private int mFrameCount = 0;
    private Bitmap[] mframeBitmap = null;
    private boolean mIsLoop = false;
    private boolean mIsend = false;
    private static final int ANIM_TIME = 100;

    public ResourcesUtils(Context context, int[] frameBitmapID, boolean isloop) {
        mFrameCount = frameBitmapID.length;
        mframeBitmap = new Bitmap[mFrameCount];
        for (int i = 0; i < mFrameCount; i++) {
            mframeBitmap[i] = ReadBitMap(context, frameBitmapID[i]);
        }
        mIsLoop = isloop;
    }

    public ResourcesUtils(Context context, Bitmap[] frameBitmap, boolean isloop) {
        mFrameCount = frameBitmap.length;
        mframeBitmap = frameBitmap;
        mIsLoop = isloop;
    }

    public void DrawFrame(Canvas canvas, Paint paint, int x, int y, int index) {
        canvas.drawBitmap(mframeBitmap[index], x, y, paint);
    }

    public void DrawAnimation(Canvas Canvas, Paint paint, int x, int y) {
        if (!mIsend) {
            Canvas.drawBitmap(mframeBitmap[mPlayID], x, y, paint);
            long time = System.currentTimeMillis();
            if (time - mLastPlayTime > ANIM_TIME) {
                mPlayID++;
                mLastPlayTime = time;
                if (mPlayID >= mFrameCount) {
                    mIsend = true;
                    if (mIsLoop) {
                        mIsend = false;
                        mPlayID = 0;
                    }
                }
            }
        }
    }

    public Bitmap ReadBitMap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;

        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }
}
