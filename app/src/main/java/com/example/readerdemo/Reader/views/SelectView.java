package com.example.readerdemo.Reader.views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 掩膜层：画出被选单词的位置
 * 需要单词的Rect
 */

import com.example.readerdemo.R;

import androidx.annotation.Nullable;

public class SelectView extends View {
    public static final String TAG = "MaskView";
    private Paint mPaint;
    private Rect mRect;
    private float mDownX;
    private float mDownY;

    public SelectView(Context context) {
        this(context, null);
    }

    public SelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackground(getContext().getResources().getDrawable(R.mipmap.reader_select_indicator));
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + "touched");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                float x = event.getX();
                float y = event.getY();
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
                layoutParams.leftMargin += x -mDownX;
                layoutParams.topMargin += y-mDownY;
                layoutParams.rightMargin += mDownX-x;
                layoutParams.bottomMargin += mDownY - y;
                setLayoutParams(layoutParams);
                break;
        }
        return true;
    }
}
