package com.example.readerdemo.Reader;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.util.Pair;

import com.example.readerdemo.Reader.Creator.CN_ENGContentCreator;
import com.example.readerdemo.Reader.Creator.ContentCreator;
import com.example.readerdemo.Reader.data.PageData;
import com.example.readerdemo.Reader.views.PageView;

import static com.example.readerdemo.Reader.Config.CHAPTER_LINE;
import static com.example.readerdemo.Reader.Config.CONTENT_CHINESE_LINE;
import static com.example.readerdemo.Reader.Config.CONTENT_ENGLISH_LINE;
import static com.example.readerdemo.Reader.Config.TITLE_LINE;
import static com.example.readerdemo.Reader.Config.languageModes;

/**
 * 页面工厂
 *    控制页面的动画，控制数据的产生
 */
public class PageFactory {

    private final PageView mPageView;
    private final Activity mActivity;
    private final Config mConfig;
    private final ContentCreator mContentCreator;
    private int CurrentPage = -1;

    //文字的画笔
    private final static String TAG = "PageFactory";

    // 绘制内容的高
    private float mVisibleHeight;
    // 绘制内容的宽
    private float mVisibleWidth;
    // 边缘距离
    private int mMargin;
    //文字字体大小
    private float m_fontSize ;
    //背景图片
    private Bitmap m_book_bg = null;


    public PageFactory(PageView pageView, Activity activity){
        mPageView = pageView;
        mActivity = activity;
        mConfig = Config.getInstance();
        mContentCreator = CN_ENGContentCreator.getInstance(mActivity);
        getDrawSize();
        m_fontSize = UIHelper.dp2px(12);
        //获取数据
//        mContentCreator.getChapterData();
    }


    /**
     * 绘画页面
     * @param bitmap
     * @param data
     */
    private void onDraw(Bitmap bitmap, PageData data) {
        Canvas c = new Canvas(bitmap);
        c.drawBitmap(getBgBitmap(), 0, 0, null);
        if(data == null){
            //数据为空
            return;
        }
        if (data.getHeightLightWord() != null) {
            c.drawRect(data.getHeightLightWord(), Config.getTitlePaint());
        }
        int currentHeight = 0;
        for (Pair<Integer, String> integerStringPair : data.getPageLine()) {
            Paint paint = null;
            switch (integerStringPair.first){
                case CHAPTER_LINE:
                    paint = Config.getChapterPaint();
                    currentHeight += getLineHeight(paint) + Config.chapterLinePadding;
                    c.drawText(integerStringPair.second,mMargin,currentHeight,paint);
                    break;
                case TITLE_LINE:
                    paint = Config.getTitlePaint();
                    currentHeight += getLineHeight(paint) + Config.titleLinePadding;
                    c.drawText(integerStringPair.second,mMargin + Config.getPageSize().x/2,currentHeight,paint);
                    break;
                case CONTENT_ENGLISH_LINE:
                    paint = Config.getContentEnglishPaint();
                    currentHeight += getLineHeight(paint) + Config.contentEnglishLinePadding;
                    c.drawText(integerStringPair.second,mMargin,currentHeight,paint);
                    break;
                case CONTENT_CHINESE_LINE:
                    paint = Config.getContentChinesePaint();
                    currentHeight += getLineHeight(paint) + Config.contentChineseLinePadding;
                    c.drawText(integerStringPair.second,mMargin,currentHeight,paint);
                    break;
            }
        }

    }

    private int getLineHeight(Paint paint) {

        Paint.FontMetricsInt m  =  paint.getFontMetricsInt();
        int height = m.bottom - m.top;
        return height;
    }


    /**
     * 获取页面背景
     * @return
     */
    private Bitmap getBgBitmap() {
        return m_book_bg;
    }

    //设置页面背景
    public void setBgBitmap(Bitmap BG) {
        m_book_bg = BG;
    }


    /**
     * 获取绘画的范围
     *
     * @param
     */
    private void getDrawSize() {

        int mWidth = mPageView.getCurPage().getWidth();
        int mHeight = mPageView.getNextPage().getHeight();
        mMargin = mPageView.getMargin();
        mVisibleHeight = mHeight - 2 * mMargin;
        mVisibleWidth = mWidth - 2 * mMargin;
        //更新配置
        Config.setPageSize(new Point((int)mVisibleWidth, (int)mVisibleHeight));
        Config.margin = mMargin;
        Bitmap bitmap = Bitmap.createBitmap(mWidth,mHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.parseColor("#eeeeee"));
        setBgBitmap(bitmap);
    }



    public void getData() {
        //获取数据
        if (mContentCreator != null) {
            mContentCreator.getChapterData();
            onDraw(mPageView.getCurPage(), getPage(CurrentPage+ 1, true));
            onDraw(mPageView.getNextPage(), getPage(CurrentPage , true));
        }
    }

    public PageData getPage(int page, boolean isNext) {
        PageData pageData = mContentCreator.getPage(page);
        if (pageData.isNoPre) {
            Log.d(TAG, "getPage: " + "在第一页");
            return null;
        }
        if (pageData.isNoNext) {
            Log.d(TAG, "getPage: " + "在最后一页");
            return null;
        }
        return pageData;
    }

    /**
     * 获取下页数据
     */
    public void getNextPage() {
        onPageSwitch();
        CurrentPage++;
        if (mContentCreator != null) {
            onDraw(mPageView.getCurPage(), getPage( CurrentPage, true));
            onDraw(mPageView.getNextPage(), getPage(CurrentPage + 1 , true));
        }
    }

    public void getPrePage() {
        onPageSwitch();
        CurrentPage--;
        if (mContentCreator != null) {
            onDraw(mPageView.getCurPage(), getPage( CurrentPage, true));
            onDraw(mPageView.getNextPage(), getPage(CurrentPage + 1 , true));
        }
    }

    /**
     * 根据点击坐标查询单词
     *
     * @param x
     * @param y
     * @return
     */
    public String queryClickWord(int x, int y) {
        PageData pd =getPage(CurrentPage + 1, true);
        String word = pd.queryWord(x,y);
        onDraw(mPageView.getNextPage(),getPage(CurrentPage + 1, true));
        mPageView.invalidate();
        return  word;
    }

    /**
     * 翻页时初始一些数据
     */
    private void onPageSwitch(){
        queryClickWord(-1, -1);
    }

    /**
     * 当前模式：中文、英文、中英文
     *
     * @return
     */
    public int switchLanguage() {
        int i = 0;
        for (int languageMode : languageModes) {
            if(languageMode == Config.LanguageMode){
                PageData data = mContentCreator.getPage(CurrentPage);
                Config.LanguageMode = languageModes[(i+1)%languageModes.length];
                mContentCreator.update();
                onDraw(mPageView.getNextPage(),mContentCreator.getPage(CurrentPage));
                mPageView.invalidate();
                return i;
            }
            i++;
        }
        return -1;
    }
}
