package com.example.readerdemo.Reader;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

/**
 * 阅读器的相关配置属性
 */
public class Config {

    //语言类型
    public final static int ENG = 1;
    public final static int CN = 2;
    public final static int ENG_CN = 0;
    public final static int[] languageModes = new int[]{ENG_CN, ENG, CN};
    //换页动画的类型
    public final static int PAGE_MODE_SIMULATION = 0;
    public final static int PAGE_MODE_COVER = 1;
    public final static int PAGE_MODE_SLIDE = 2;
    public final static int PAGE_MODE_NONE = 3;

    //每段文字的类型
    public final static int CHAPTER_LINE = 0;
    public final static int TITLE_LINE = 1;
    public final static int CONTENT_ENGLISH_LINE = 2;
    public final static int CONTENT_CHINESE_LINE = 3;
    //页边距
    public static int margin;
    private static Point mPageSize;

    public static Paint getChapterPaint() {
        return chapterPaint;
    }

    public static void setChapterPaint(Paint chapterPaint) {
        Config.chapterPaint = chapterPaint;
    }

    public static Paint getTitlePaint() {
        return titlePaint;
    }

    public static void setTitlePaint(Paint titlePaint) {
        Config.titlePaint = titlePaint;
    }

    public static Paint getContentEnglishPaint() {
        return contentEnglishPaint;
    }

    public static void setContentEnglishPaint(Paint contentPaint) {
        Config.contentEnglishPaint = contentPaint;
    }
    public static Paint getContentChinesePaint() {
        return contentChinesePaint;
    }

    public static void setContentChinesePaint(Paint contentPaint) {
        Config.contentChinesePaint = contentPaint;
    }

    //章节、标题、内容的样式
    //画笔
    private static Paint chapterPaint;
    private static Paint titlePaint;
    private static Paint contentEnglishPaint;
    private static Paint contentChinesePaint;
    //文字大小
    private static int chapterTextSize = 120;
    private static int titleTextSize = 80;
    private static int contentEnglishTextSize = 50;
    private static int contentChineseTextSize = 50;
    //文字颜色
    private static int chapterColor = Color.BLUE;
    private static int titleColor = Color.GRAY;
    private static int contentEnglishColor = Color.BLUE;
    private static int contentChineseColor = Color.BLACK;
    //文字行间距
    public static int chapterLinePadding = UIHelper.dp2px(20);
    public static int titleLinePadding = UIHelper.dp2px(20);
    public static int contentEnglishLinePadding = UIHelper.dp2px(20);
    public static int contentChineseLinePadding = UIHelper.dp2px(20);

    private static Config mConfig = null;
    private Config(){
        //初始化样式
        initPaint();
    }


    public static Config getInstance() {
        if (mConfig == null) {
            synchronized (Config.class) {
                if (mConfig == null) {
                    mConfig = new Config();
                }
            }
        }
        return mConfig;
    }

    private static void initPaint() {
        //章节文字样式
        chapterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 画笔
        chapterPaint.setTextAlign(Paint.Align.LEFT);// 左对齐
        chapterPaint.setTextSize(chapterTextSize);// 字体大小
        chapterPaint.setColor(chapterColor);// 字体颜色
        chapterPaint.setSubpixelText(true);// 设置该项为true，将有助于文本在LCD屏幕上的显示效果
        //标题文章样式
        titlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 画笔
        titlePaint.setTextAlign(Paint.Align.CENTER);// 左对齐
        titlePaint.setTextSize(titleTextSize);// 字体大小
        titlePaint.setColor(titleColor);// 字体颜色
        titlePaint.setSubpixelText(true);// 设置该项为true，将有助于文本在LCD屏幕上的显示效果
        //内容文章样式
        contentEnglishPaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 画笔
        contentEnglishPaint.setTextAlign(Paint.Align.LEFT);// 左对齐
        contentEnglishPaint.setTextSize(contentEnglishTextSize);// 字体大小
        contentEnglishPaint.setColor(contentEnglishColor);// 字体颜色
        contentEnglishPaint.setSubpixelText(true);// 设置该项为true，将有助于文本在LCD屏幕上的显示效果
        //内容文章样式
        contentChinesePaint = new Paint(Paint.ANTI_ALIAS_FLAG);// 画笔
        contentChinesePaint.setTextAlign(Paint.Align.LEFT);// 左对齐
        contentChinesePaint.setTextSize(contentChineseTextSize);// 字体大小
        contentChinesePaint.setColor(contentChineseColor);// 字体颜色
        contentChinesePaint.setSubpixelText(true);// 设置该项为true，将有助于文本在LCD屏幕上的显示效果
    }

    public static Point getPageSize() {
        return mPageSize;
    }

    public static void setPageSize(Point size) {
         mPageSize = size;
    }




    public static int LanguageMode = ENG;


    private Activity mActivity;

    /**
     * 计算行高
     * @param type
     * @return
     */
    public static int getLineHeight(int type) {
        Paint paint = null;
        int padding = 0;
        switch (type) {
            case CN:
                paint = getContentChinesePaint();
                padding = contentChineseLinePadding;
                break;
            case ENG:
                paint = getContentEnglishPaint();
                padding = contentEnglishLinePadding;
                break;
           //标题//章节
        }
        if(paint != null){
            Paint.FontMetricsInt fmChinese  =  paint.getFontMetricsInt();
            return fmChinese.bottom - fmChinese.top + padding;
        }else{
            return 0;
        }
    }

    public static int getLineWidth(int type, String str) {
        Paint paint = null;
        int padding = 0;
        switch (type) {
            case CN:
                paint = getContentChinesePaint();
                padding = contentChineseLinePadding;
                break;
            case ENG:
                paint = getContentEnglishPaint();
                padding = contentEnglishLinePadding;
                break;
            //标题//章节
        }
        if(paint != null){

            return (int) paint.measureText(str);
        }else{
            return 0;
        }
    }


    public void setCurrentLanguage(int language) {
        LanguageMode = language;
    }



    public void setActivity(Activity activity) {
        mActivity = activity;
    }
}
