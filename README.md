# 游戏编程中的那些事————SurfaceView的使用
<font color="#aaa">前言：在我还未学习编程之前，曾觉得电脑游戏非常的神奇，后来做了一名移动应用开发者，依然觉得这项技术很是神秘，直到我有幸接触了一些优秀的游戏开发者后，才一一揭开这些神秘面纱，今天就带着大家分享一下游戏编程中的那些事。</font>
<br><br>
<strong>SurfaceView的使用</strong>
<br>
<br>
<b>Q : 什么是SurfaceView?</b>
<br>
A : 在Android系统中，有一种特殊的视图，称为SurfaceView，它拥有独立的绘图表面，即它不与其宿主窗口共享同一个绘图表面。由于拥有独立的绘图表面，因此SurfaceView的UI就可以在一个独立的线程中进行绘制。又由于不会占用主线程资源，SurfaceView一方面可以实现复杂而高效的UI，另一方面又不会导致用户输入得不到及时响应。
<br>
<br>
<b>Q : SurfaceView与View有什么区别和联系?</b>
<br>
A : 普通的Android控件，例如TextView、Button和CheckBox等，它们都是将自己的UI绘制在宿主窗口的绘图表面之上，这意味着它们的UI是在应用程序的主线程中进行绘制的。由于应用程序的主线程除了要绘制UI之外，还需要及时地响应用户输入，否则的话，系统就会认为应用程序没有响应了，因此就会弹出一个ANR对话框出来。对于一些游戏画面，或者摄像头预览、视频播放来说，它们的UI都比较复杂，而且要求能够进行高效的绘制，因此，它们的UI就不适合在应用程序的主线程中进行绘制。这时候就必须要给那些需要复杂而高效UI的视图生成一个独立的绘图表面，以及使用一个独立的线程来绘制这些视图的UI。
<br>
<img src="http://img.my.csdn.net/uploads/201303/11/1363016714_1787.jpg" />
<br>
Activity窗口的顶层视图DecorView及其两个TextView控件的UI都是绘制在SurfaceFlinger服务中的同一个Layer上面的，而SurfaceView的UI是绘制在SurfaceFlinger服务中的另外一个Layer或者LayerBuffer上的。
<br>
注意，用来描述SurfaceView的Layer或者LayerBuffer的Z轴位置是小于用来其宿主Activity窗口的Layer的Z轴位置的，但是前者会在后者的上面挖一个“洞”出来，以便它的UI可以对用户可见。实际上，SurfaceView在其宿主Activity窗口上所挖的“洞”只不过是在其宿主Activity窗口上设置了一块透明区域。
<br>
<br>
<b>SurfaceView的双缓冲机制</b>
<br>
SurfaceView采用一种称为“双缓冲”的技术。双缓冲意味着要使用两个缓冲区，其中一个称为Front Buffer，另外一个称为Back Buffer。UI总是先在Back Buffer中绘制，然后再和Front Buffer交换，渲染到显示设备中。这样就不会阻塞主线程了，所以它更适合于游戏的开发。
<br>
-------------------------------------------------------------------------------------
<br>
<b>SurfaceView绘图的几个重要方法</b>
<br>
首先继承SurfaceView，并实现SurfaceHolder.Callback接口
<br>
实现它的三个方法：surfaceCreated，surfaceChanged，surfaceDestroyed。
<br>
surfaceCreated(SurfaceHolder holder)：surface创建的时候调用，一般在该方法中启动绘图的线程。
<br>
surfaceChanged(SurfaceHolder holder, int format, int width,int height)：surface尺寸发生改变的时候调用，如横竖屏切换。
<br>
surfaceDestroyed(SurfaceHolder holder) ：surface被销毁的时候调用，如退出游戏画面，一般在该方法中停止绘图线程。
<br>
<br>
<b>canvas.save()与canvas.restore()</b>
<br>
这里canvas.save();和canvas.restore();是两个相互匹配出现的，作用是用来保存画布的状态和取出保存的状态的。这里稍微解释一下，
当我们对画布进行旋转，缩放，平移等操作的时候其实我们是想对特定的元素进行操作，比如图片，一个矩形等，但是当你用canvas的方法来进行这些操作的时候，其实是对整个画布进行了操作，那么之后在画布上的元素都会受到影响，所以我们在操作之前调用canvas.save()来保存画布当前的状态，当操作之后取出之前保存过的状态，这样就不会对其他的元素进行影响
<br>
-------------------------------------------------------------------------------------
<br>
一、canvas.translate() － 画布的平移：
``` java
canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);  
```
<br>
此时整个画布的左上角出现了一个红色的矩形（为了更清楚，蓝色打个底）该矩形大小为400 X 400 ，效果如下：
<br>
<img src="http://img.blog.csdn.net/20150506124913650?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdGlhbmppYW40NTky/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center" />
<br>
接下来我们canvas.translate( )玩玩
<br>
``` java
canvas.drawColor(Color.BLUE);  
canvas.translate(100, 100);  
canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);  
```
<br>
<img src="http://img.blog.csdn.net/20150506125056610?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdGlhbmppYW40NTky/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center" />
<br>
-------------------------------------------------------------------------------------
<br>
二、canvas.scale( ) － 画布的缩放：
<br>
``` java
canvas.scale(0.5f, 0.5f); 
mPaint.setColor(Color.YELLOW);  
canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);  
```
<br>
<img src="http://img.blog.csdn.net/20150506204938070?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdGlhbmppYW40NTky/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center" />
<br>
-------------------------------------------------------------------------------------
<br>
三、canvas.rotate( ) － 画布的旋转：
<br>
``` java
mPaint.setColor(Color.YELLOW);  
canvas.rotate(45);  
canvas.drawRect(new Rect(0, 0, 400, 400), mPaint); 
```
<br>
<img src="http://img.blog.csdn.net/20150507125942943?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdGlhbmppYW40NTky/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center" />
``` java
canvas.rotate(45,200,200);  
```
<img src="http://img.blog.csdn.net/20150507130036966?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdGlhbmppYW40NTky/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center" />
<br>
-------------------------------------------------------------------------------------
<br>
四、canvas.skew( ) － 画布的错切： 
<br>
``` java
// x 方向上倾斜45 度  
canvas.skew(1, 0);  
mPaint.setColor(0x8800ff00);  
canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);  
```
<br>
<img src="http://img.blog.csdn.net/20150507132403246?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdGlhbmppYW40NTky/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center" />
<br>
<br>
关于Canvas(画布)的translate(平移)、scale(缩放) 、rotate(旋转) 、skew(错切)就说这么多，这些方法都不复杂，而灵活的使用往往能解决绘制中很多看似复杂的问题，所以重在理解，并在看到与之相关的效果时能够及时恰当的进行关联。
<br>
当然对Canvas的操作往往使用Matrix也能达到同样的效果
<br>
<br>
<b>游戏框架的实现</b>
<br>
<br>
``` java

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
```
<br>
绘制游戏按钮
<br>
``` java
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

```
<br>
游戏摇杆类
<br>
``` java
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
```
<br>
坦克实体类
<br>
``` java
/**
 * Created by mofind on 16/6/29.
 * <p/>
 * 坦克类
 */
public class Tank {

    public static final int DIR_LEFT = 0;
    public static final int DIR_UP = 1;
    public static final int DIR_RIGHT = 2;
    public static final int DIR_DOWN = 3;

    // 坦克的位置坐标
    public float x, y;

    // 坦克的大小
    public int width = 50, height = 50;

    // 坦克行走速度，单位(像素/帧)
    public int speed = 0;

    // 坦克行走状态
    public int status = DIR_UP;

    // 是否是我军坦克
    public boolean isSelf = false;

    public Tank() {
        // 坦克宽高
        width = GameConfig.mHeroTankRes[0].getWidth();
        height = GameConfig.mHeroTankRes[0].getHeight();
    }

    public void draw(Canvas c) {
        c.save();
        c.drawBitmap(isSelf ? GameConfig.mHeroTankRes[status] : GameConfig.mEnemyTankRes[status], x, y, null);
        c.restore();
    }

    /**
     * 坦克移动
     */
    public void move() {
        switch (status) {
            case DIR_LEFT:
                x -= speed;
                break;
            case DIR_RIGHT:
                x += speed;
                break;
            case DIR_DOWN:
                y += speed;
                break;
            case DIR_UP:
                y -= speed;
                break;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();

        return true;
    }
}

```
-------------------------------------------------------------------------------------
<br>
<br>
<br>
参考资料：
<br>
<a>Android视图SurfaceView的实现原理分析 http://blog.csdn.net/luoshengyang/article/details/8661317/</a>
<br>
<a>碰撞检测算法 http://blog.csdn.net/shineflowers/article/details/41084329</a>
<br>
<a>Canvas之translate、scale、rotate、skew方法讲解！ http://blog.csdn.net/tianjian4592/article/details/45234419</a>
<br>
<br>
特别鸣谢：王志翔、王珂两位游戏开发大神，膜拜！！！
<br>
