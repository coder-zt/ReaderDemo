package com.example.readerdemo.Reader;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;

import com.example.readerdemo.App;

public class UIHelper {
    /**
     * 将dp值转换为px值
     */
    public static int dp2px( float dpValue) {
        final float scale = App.getsContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


}
