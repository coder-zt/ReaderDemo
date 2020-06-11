package com.example.readerdemo.Reader.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 掩膜层：画出被选单词的位置
 * 需要单词的Rect
 */
import com.example.readerdemo.R;
import com.example.readerdemo.Reader.UIHelper;

import androidx.annotation.Nullable;

public class RegionSelectView extends ViewGroup {
    public static final String TAG = "MaskView";
    private Paint mPaint;
    private Rect mRect;

    public RegionSelectView(Context context) {
        this(context, null);
    }

    public RegionSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RegionSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        ImageView startImageView = new ImageView(getContext());
        startImageView.setImageDrawable(getContext().getResources()
                .getDrawable(R.mipmap.reader_select_indicator));
        startImageView.setTag(1);
        addView(startImageView);
        ImageView endImageView = new ImageView(getContext());
        endImageView.setImageDrawable(getContext().getResources()
                .getDrawable(R.mipmap.ic_launcher));
        endImageView.setTag(2);
        addView(endImageView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int childWidth = MeasureSpec.makeMeasureSpec(UIHelper.dp2px(25),
                MeasureSpec.EXACTLY);
        int childHeight = MeasureSpec.makeMeasureSpec(UIHelper.dp2px(50),
                MeasureSpec.EXACTLY);
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(0);
            child.measure(childWidth,childHeight);
        }
        setMeasuredDimension(widthMeasureSpec,widthMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(0);
            int tag = (int)child.getTag();
            if(tag == 1){
                int left = 120;
                int top = 120;
                child.layout(left,top,left + child.getMeasuredWidth(),top +  child.getMeasuredHeight());
            }else if(tag == 2){
                int left = 500;
                int top = 500;
                child.layout(left,top,left + child.getMeasuredWidth(),top +  child.getMeasuredHeight());
            }
        }
    }
}
