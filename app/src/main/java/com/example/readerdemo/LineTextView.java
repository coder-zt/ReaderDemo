package com.example.readerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.Nullable;

import com.example.readerdemo.Reader.UIHelper;

/**
 *
 *  页面的组成元素：行
 *  行属性：本页第几行、行高（Y轴坐标）、文本、单词间隔线的X轴坐标，中英文类型，事件轴
 *
 */
public class LineTextView extends View {
    public static final String TAG = "LineTextView";
    //属性
    //行数
    private int mLineNum;
    //行高
    private int mLineHeight;
    //行宽
    private int mLineWidth;
    //最大行宽
    private int mLineMaxWidth;
    //文字大小
    private int mTextSize = 12;
    //需要添加空格数
    private float mAddSpaceNumber;
    private List<String> mWordList = new ArrayList<>();
    private List<Integer> mXDividingList = new ArrayList<>();
    //标记该行语言类型
    public final static int ENGLISH = 1;
    public final static int CHINESE = 2;
    private int mLanguageType = ENGLISH;
    //时间轴
    private long[] mTimeController = new long[2];
    //选中单词颜色
    private int mTargetWordColor = Color.BLUE;
    //选中部分单词的颜色
    private int mTargetSegmentColor = Color.RED;
    //画笔
    //单词的画笔
    private Paint mWordPaint;
    private Context mContext;

    public LineTextView(Context context) {
        this(context, null);
    }

    public LineTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasureSpecTemp = MeasureSpec.makeMeasureSpec(mLineWidth, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpecTemp, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mWordPaint.setLetterSpacing(mWordMargin/mTextSize);
//        }
        Log.d(TAG, "onDraw: " + wordListToString(mWordList, true));
        Paint.FontMetrics fontMetrics = mWordPaint.getFontMetrics();

        canvas.drawText(wordListToString(mWordList, true),0, mLineHeight-fontMetrics.descent,mWordPaint );
    }

    /**
     * 设置文字大小
     * @param dp 单位dp
     */
    public void setTextSize(int dp){
        mTextSize = UIHelper.dp2px(dp);
        mWordPaint.setTextSize(mTextSize);
        mWordPaint.setAntiAlias(true);
    }

    /**
     * 设置最大行宽
     * @param width
     */
    public void setLineMaxWidth(int width){
        mLineMaxWidth = width;
    }

    /**
     * 设置字间距
     * @param dp
     */
    public void setAddSpaceNumber(int dp){
        mAddSpaceNumber = UIHelper.dp2px(dp);
    }

    /**
     * 初始化属性
     */
    public void init(){
        mWordPaint = new Paint();
        mWordPaint.setTextSize(mTextSize);
    }

    /**
     * 功能：给一段文本创建一行文本，并返回多余的字符串
     * @param originString
     * @return
     */
    public String createLine(String originString, int type){
        //设置语言类型
        mLanguageType = type;
        //行高
        Paint.FontMetrics fontMetrics = mWordPaint.getFontMetrics();
        mLineHeight = (int) (fontMetrics.bottom - fontMetrics.top) * 2;
        if(type == CHINESE){
            return createChineseLines(originString);
        }else if(type == ENGLISH){
            return createEnglishLines(originString);
        }
        return null;
    }


    /**
     * 创建中文的一行
     *
     * @param originString
     * @return
     */
    private String createChineseLines(String originString) {
        List<String> wordList = new ArrayList<>();
        float totalWordWidth = 0.0f;
        int count = 0;
        for (int i = 0; i < originString.length(); i++) {
            float wordWidth = mWordPaint.measureText(String.valueOf(originString.charAt(i)));

            mLineWidth += wordWidth;
            totalWordWidth += wordWidth;
            if(mLineWidth < mLineMaxWidth){
                mWordList.add(String.valueOf(originString.charAt(i)));
                count++;
            }else{
                mLineWidth = mLineMaxWidth;
                //重新计算字间距
                float remainWidth = mLineMaxWidth - totalWordWidth;
                mAddSpaceNumber = (int) (remainWidth/mWordList.size() - 1);
                break;
            }
        }
        return originString.substring(count, originString.length());
    }

    /**
     * 创建英语的一行
     *
     * @param originString
     * @return
     */
    private String createEnglishLines(String originString) {
        String[] words = originString.split(" ");
        List<String> wordList = Arrays.asList(words);
        Iterator<String> iterator = wordList.iterator();
        float spaceWidth = mWordPaint.measureText(" ");
        float totalWordWidth = 0.0f;
        int count = 0;
        while (iterator.hasNext()){
            String word = iterator.next();
            float wordWidth = mWordPaint.measureText(word + " ");
            mLineWidth += wordWidth;
            totalWordWidth += wordWidth;
            if(mLineWidth < mLineMaxWidth){
                mWordList.add(word);
                count++;
            }else{
                totalWordWidth = totalWordWidth - wordWidth;
                mLineWidth = mLineMaxWidth;
                //重新计算字间距
                float remainWidth =  mLineMaxWidth - totalWordWidth;
                mAddSpaceNumber = (int) (remainWidth/spaceWidth);
                Log.d(TAG, "createEnglishLines: add space number is =---->" + mAddSpaceNumber);
                break;
            }
        }
        if (wordList.size()>count){
            Log.d(TAG, "createLine: " + count);
            return wordListToString(wordList.subList(count,wordList.size()), false);
        }else{
            return null;
        }
    }

    /**
     * 将字符列表转为字符串
     *
     * @param ListStr
     * @return
     */
    public String wordListToString(List<String> ListStr, boolean isView){
        int spaceNum = ListStr.size()-1;
        StringBuilder sbListString = new StringBuilder();
        Iterator<String> iterator = ListStr.iterator();
        int spaceIndex = 0;
        while (iterator.hasNext()) {
            sbListString.append(iterator.next());
            if(iterator.hasNext() && mLanguageType == ENGLISH){
                sbListString.append(" ");
                if(isView && mLineMaxWidth == mLineWidth){
                    for( int i=0; i<(int)(mAddSpaceNumber/spaceNum);i++ ){
                        sbListString.append(" ");
                    }
                    if(spaceIndex < mAddSpaceNumber % spaceNum){
                        sbListString.append(" ");
                    }
                }
            }
            spaceIndex++;
        }
        return sbListString.toString();
    }

    /**
     * 行高
     * @return
     */
    public int getLineHeight() {
        return mLineHeight;
    }
    /**
     * 行宽
     */
    public int getLineWidth(){
        return mLineWidth;
    }
}
