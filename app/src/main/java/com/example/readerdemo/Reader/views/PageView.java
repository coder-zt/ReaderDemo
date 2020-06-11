package com.example.readerdemo.Reader.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import androidx.annotation.Nullable;

import com.example.readerdemo.Reader.Config;
import com.example.readerdemo.Reader.UIHelper;
import com.example.readerdemo.Reader.animation.AnimationProvider;
import com.example.readerdemo.Reader.animation.CoverAnimation;
import com.example.readerdemo.Reader.animation.NoneAnimation;
import com.example.readerdemo.Reader.animation.SimulationAnimation;
import com.example.readerdemo.Reader.animation.SlideAnimation;

/**
 * 阅读器的页面视图
 * 功能：展示页面和监听事件
 * 展示数据：很多行数据
 *
 */

public class PageView extends View{
    public static final String TAG = "PageView";

    private int mScreenWidth = 0; // 屏幕宽
    private int mScreenHeight = 0; // 屏幕高
    private Context mContext;
    //切换前后页的图片
    Bitmap mCurPageBitmap = null;
    Bitmap mNextPageBitmap = null;
    //换页动画
    private AnimationProvider mAnimationProvider;
    //背景颜色
    private int mBgColor = 0xFFCEC29C;
    //翻页动画是否在执行
    private Boolean mIsRunning =false;
    //点击按下的坐标
    private int downX = 0;
    private int downY = 0;
    //移动的坐标
    private int moveX = 0;
    private int moveY = 0;
    //是否移动了
    private Boolean mIsMove = false;
    //是否没下一页或者上一页
    private Boolean mNoNext = false;
    //是否翻到下一页
    private Boolean mIsNext = false;
    //滑动效果器
    private Scroller mScroller;
    //是否取消翻页
    private boolean mCancelPage;
    //触摸监听器
    private TouchListener mTouchListener;
    private long mDownTime;


    public PageView(Context context) {
        this(context, null);
    }

    public PageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPage();
        //设置默认的翻页动画
        mAnimationProvider = new CoverAnimation(mCurPageBitmap,mNextPageBitmap,mScreenWidth,mScreenHeight);
        //初始化Scroller
        mScroller = new Scroller(getContext(),new LinearInterpolator());
    }


    /**
     * 初始化页面的大小和初始化前后页的Bitmap
     */
    private void initPage(){
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metric);
        mScreenWidth = metric.widthPixels;
        mScreenHeight = metric.heightPixels;
        mCurPageBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.RGB_565);      //android:LargeHeap=true  use in  manifest application
        mNextPageBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.RGB_565);
    }

    /**
     * 设置翻页模式
     * @param pageMode
     */
    public void setPageMode(int pageMode){
        switch (pageMode){
            case Config.PAGE_MODE_SIMULATION:
                mAnimationProvider = new SimulationAnimation(mCurPageBitmap,mNextPageBitmap,mScreenWidth,mScreenHeight);
                break;
            case Config.PAGE_MODE_COVER:
                mAnimationProvider = new CoverAnimation(mCurPageBitmap,mNextPageBitmap,mScreenWidth,mScreenHeight);
                break;
            case Config.PAGE_MODE_SLIDE:
                mAnimationProvider = new SlideAnimation(mCurPageBitmap,mNextPageBitmap,mScreenWidth,mScreenHeight);
                break;
            case Config.PAGE_MODE_NONE:
                mAnimationProvider = new NoneAnimation(mCurPageBitmap,mNextPageBitmap,mScreenWidth,mScreenHeight);
                break;
        }
    }

    /**
     * 获取bitmap绘制页面
     * @return
     */
    public Bitmap getCurPage(){
        return mCurPageBitmap;
    }

    public Bitmap getNextPage(){
        return mNextPageBitmap;
    }

    /**
     * 设置背景图片
     * @param color
     */
    public void setBgColor(int color){
        mBgColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawColor(0xFFAAAAAA);
        canvas.drawColor(mBgColor);
        if (mIsRunning) {
            mAnimationProvider.drawMove(canvas);
        } else {
            mAnimationProvider.drawStatic(canvas);
        }
    }

    /**
     * View的事件处理
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int x = (int)event.getX();
        int y = (int)event.getY();

        mAnimationProvider.setTouchPoint(x,y);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: " + "down");
                //记录点击时间
                mDownTime = System.currentTimeMillis();
                downX = (int) event.getX();
                downY = (int) event.getY();
                moveX = 0;
                moveY = 0;
                mIsMove = false;
//            cancelPage = false;
                mNoNext = false;
                mIsNext = false;
                mIsRunning = false;
                mAnimationProvider.setStartPoint(downX,downY);
                abortAnimation();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "onTouchEvent: " + "move");
                //判断是否是长按
                if(isLongClick()){
                    if (mTouchListener != null) {
                        mTouchListener.longClick(x,y);
                    }
                    return true;
                }
                final int slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                //判断是否移动了
                if (!mIsMove) {
                    mIsMove = Math.abs(downX - x) > slop || Math.abs(downY - y) > slop;
                }

                if (mIsMove){
                    if (moveX == 0 && moveY ==0) {
                        if (changPage(x)) return true;
                    }else{
                        //判断是否取消翻页
                        checkIsCancelPage(x);
                    }
                    moveX = x;
                    moveY = y;
                    mIsRunning = true;
                    this.postInvalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "onTouchEvent: " + "up");
                //判断是否是长按
                if(isLongClick()){
                    if (mTouchListener != null) {
                        mTouchListener.longClick(x,y);
                    }
                    return true;
                }
                //判断是否是点击事件
                if(isClickEvent(x, y)){
                    mTouchListener.click(x, y);
                    return true;
                }
                if (switchPage(x, y )) {
                    return true;
                }

                if (mCancelPage && mTouchListener != null){
                    mTouchListener.cancel();
                }
                if (!mNoNext) {
                    mIsRunning = true;
                    mAnimationProvider.startAnimation(mScroller);
                    this.postInvalidate();
                }
                break;
        }
        return true;
    }

    /**
     * 判断是否是长按事件
     * @return
     */
    private boolean isLongClick() {
        long currentTime = System.currentTimeMillis();
        if(currentTime - mDownTime > 800){
            return true;
        }
        return false;
    }

    /**
     * 判断事件是否为点击
     *
     * @param upX
     * @param upY
     * @return
     */
    private boolean isClickEvent(int upX, int upY) {
        if(upX == downX && upY == downY){
            return true;
        }
        return false;
    }

    /**
     * 根据点击位置判断向前后翻页
     *
     * @param x
     * @return
     */
    private boolean switchPage(int x, int y) {
        if (!mIsMove){
            mCancelPage = false;
            //是否点击了中间
            if (downX > mScreenWidth / 5 && downX < mScreenWidth * 4 / 5 &&
                    downY > mScreenHeight / 3 && downY < mScreenHeight * 2 / 3){
                if (mTouchListener != null){
                    mTouchListener.longClick(x,y);
                }
                return true;
            }else if (x < mScreenWidth / 2){
                mIsNext = false;
            }else{
                mIsNext = true;
            }

            if (mIsNext) {
                Boolean isNext = mTouchListener.nextPage();
                mAnimationProvider.setDirection(AnimationProvider.Direction.next);
                if (!isNext) {
                    return true;
                }
            } else {
                Boolean isPre = mTouchListener.prePage();
                mAnimationProvider.setDirection(AnimationProvider.Direction.pre);
                if (!isPre) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否取消翻页动作
     * @param x
     */
    private void checkIsCancelPage(int x) {
        if (mIsNext) {
            if (x - moveX > 0) {
                mCancelPage = true;
                mAnimationProvider.setCancel(true);
            } else {
                mCancelPage = false;
                mAnimationProvider.setCancel(false);
            }
        } else {
            if (x - moveX < 0) {
                mAnimationProvider.setCancel(true);
                mCancelPage = true;
            } else {
                mAnimationProvider.setCancel(false);
                mCancelPage = false;
            }
        }
    }

    /**
     * 切换页面
     *
     * @param x
     * @return
     */
    private boolean changPage(int x) {
        //判断翻得是上一页还是下一页
        //(x - downX) < 0 左滑->下一页
        //(x - downX) > 0 右滑->上一页
        mIsNext = (x - downX) < 0;
        mCancelPage = false;
        if (mIsNext) {//切换至下一页
            Boolean isNext = mTouchListener.nextPage();
            mAnimationProvider.setDirection(AnimationProvider.Direction.next);
            if (!isNext) {
                mNoNext = true;
                return true;
            }
        } else {//切换至上一页
            Boolean isPre = mTouchListener.prePage();
            mAnimationProvider.setDirection(AnimationProvider.Direction.pre);
            if (!isPre) {
                mNoNext = true;
                return true;
            }
        }
        return false;
    }


    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            float x = mScroller.getCurrX();
            float y = mScroller.getCurrY();
            mAnimationProvider.setTouchPoint(x,y);
            if (mScroller.getFinalX() == x && mScroller.getFinalY() == y){
                mIsRunning = false;
            }
            postInvalidate();
        }
        super.computeScroll();
    }


    /**
     * 中止动画
     */
    public void abortAnimation() {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
            mAnimationProvider.setTouchPoint(mScroller.getFinalX(),mScroller.getFinalY());
            postInvalidate();
        }
    }


    public boolean isRunning(){
        return mIsRunning;
    }

    /**
     * 设置触摸监听器
     * @param mTouchListener
     */
    public void setTouchListener(TouchListener mTouchListener){
        this.mTouchListener = mTouchListener;
    }

    public int getMargin() {
        return UIHelper.dp2px(10);
    }

    public interface TouchListener{
        //长按点击事件
        void longClick(int x, int y);
        Boolean prePage();
        Boolean nextPage();
        void cancel();
        //点击事件
        void click(int upX, int upY);
    }

}
