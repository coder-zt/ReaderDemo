package com.example.readerdemo.Reader.data;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.util.Pair;

import com.example.readerdemo.Reader.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * 存储页面的信息
 * 书名
 * 章节
 * 总页数
 * 页数
 *
 * 行的集合
 *      ->开始索引
 *      ->结束索引
 *     ->该行边界
 *     ->句子的集合
 *          ->时间戳->高亮
 *          ->句子id->位置变化后确定其新的位置
 *          ->英字符合集
 *             ->字符边界  ->1.点击查询->返回字符索引
 *                         ->2.范围查询->返回前后字符索引
 *          ->中文字符合集
 *              ->字符边界  ->1.点击查询->返回字符索引
 *                         ->2.范围查询->返回前后字符索引
 * 开始字符索引
 * 结束字符索引
 *
 *
 * 标记字符集合
 *      书名 章节 开始索引 结束索引 样式 注解 ->利用数据库存储本地
 */
public class PageData {

    public static final String TAG = "PageData";

    //书名-
    String bookName;
    //章节序号-
    int mChapterIndex;
    //总页数-
    int totalPageNum;
    //页数-
    int currentPageNum;
    //开始字符索引-
    long startIndex;
    //结束字符索引-
    long endIndex;
    //行的集合-
    List<PageLineData> mLines = new ArrayList<>();
    //字符集
    List<String> mWords = new ArrayList<>();
    //标记字符集--
    List<SignWordsBean> mWordsBeans;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getChapterIndex() {
        return mChapterIndex;
    }

    public void setChapterIndex(int chapterIndex) {
        this.mChapterIndex = chapterIndex;
    }

    public int getCurrentPageNum() {
        return currentPageNum;
    }

    public void setCurrentPageNum(int currentPageNum) {
        this.currentPageNum = currentPageNum;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }

    public long getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(long endIndex) {
        this.endIndex = endIndex;
    }



    public List<PageLineData> getLines() {
        return mLines;
    }

    public void setLines(List<PageLineData> lines) {
        mLines = lines;
    }

    public List<String> getWords() {
        return mWords;
    }

    public void setWords(List<String> words) {
        mWords = words;
    }

    //===================================分割线=======================

    public boolean isNoPre;
    public boolean isNoNext;
    //
    //所有单词的四周范围
    private List<Rect> mWordCoordinate = new ArrayList<>();
    //单词的高亮的四周
    private List<Rect> heightLight = new ArrayList<>();
    private List<String> mHeightWords = new ArrayList<>();
    private Rect mHeightLightWord;

    public boolean isNoPre() {
        return isNoPre;
    }

    public void setNoPre(boolean noPre) {
        isNoPre = noPre;
    }

    public boolean isNoNext() {
        return isNoNext;
    }

    public void setNoNext(boolean noNext) {
        isNoNext = noNext;
    }

    private List<Pair<Integer, String>> pageLine = new ArrayList<>();

    public List<Pair<Integer, String>> getPageLine() {
        return pageLine;
    }

    /**
     * 更新页面数据
     */
    public void update(){
        calculateWordCoordinate();
    }
    //计算出每个单词的坐标
    private void  calculateWordCoordinate(){
        int top = Config.margin;
        int left = Config.margin;
        if (pageLine != null) {
            Point point = new Point(left, top);
            Paint paint = null;
            int padding = 0;
            for (Pair<Integer, String> line : pageLine) {

                switch (line.first){
                    case Config.CHAPTER_LINE:
                        paint = Config.getChapterPaint();
                        padding = Config.chapterLinePadding;
                        break;
                    case Config.CONTENT_ENGLISH_LINE:
                        paint = Config.getContentEnglishPaint();
                        padding = Config.contentEnglishLinePadding;
                        break;
                    case Config.CONTENT_CHINESE_LINE:
                        paint = Config.getContentChinesePaint();
                        padding = Config.contentChineseLinePadding;
                        break;
                    case Config.TITLE_LINE:
                        paint = Config.getTitlePaint();
                        padding = Config.titleLinePadding;
                        break;
                }
                if (mWordCoordinate.size()>0) {
                    Rect rect = mWordCoordinate.get(mWordCoordinate.size()-1);
                    point = new Point(Config.margin, rect.bottom);
                }
                if (paint != null) {
                    if (paint.getTextAlign() == Paint.Align.CENTER) {
                        float titleWidth = paint.measureText(line.second);
                        int titleLeft = (int) ((Config.getPageSize().x -titleWidth)/2 + Config.margin);
                        point.x = titleLeft;
                    }
                    getWordsCoordinate(line, point, paint, padding);
                    paint = null;
                    padding = 0;
                }
            }
        }
    }


    private void getWordsCoordinate(Pair<Integer, String> line, Point point, Paint paint, int padding) {
        StringBuilder word = new StringBuilder();
        int spaceNum = 0;
        for (int i = 0; i < line.second.length(); i++) {
            char letter = line.second.charAt(i);
            if (letter == ' ') {
                if (word != null && word.length() > 0) {
                    String wordStr = word.toString();
                    Log.d(TAG, "getWordsCoordinate: " + wordStr);
                    point = getWordCoordinate(wordStr,point, paint, spaceNum, padding);
                    spaceNum = 0;
                    word.setLength(0);
                }
                spaceNum++;
            }else{
                word.append(letter);
            }
        }
        if(word.length() > 0){
            String wordStr = word.toString();
            Log.d(TAG, "getWordsCoordinate: " + wordStr);
            getWordCoordinate(wordStr,point, paint, spaceNum, padding);
            word.setLength(0);
        }
    }

    private Point getWordCoordinate(String wordStr, Point point, Paint paint, int spaceNum, int padding) {
        Log.d(TAG, "getWordCoordinate: " + wordStr);
        Log.d(TAG, "getWordCoordinate: " + point);
        Log.d(TAG, "getWordCoordinate: " + spaceNum);
        Log.d(TAG, "getWordCoordinate: ====================================");
        mWords.add(wordStr);
        float width = paint.measureText(wordStr);
        float spaceWidth = paint.measureText(" ") * spaceNum;
        int left = (int) (point.x + spaceWidth);
        int right = (int) (left+ width);
        int top = point.y;
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int height = (int) (fontMetrics.bottom - fontMetrics.top);
        int bottom = point.y + height + padding;
        mWordCoordinate.add(new Rect(left, top, right, bottom));
        return new Point(right, top);
    }

    public Rect getHeightLightWord() {
        return mHeightLightWord;
    }

    public String queryWord(int x, int y) {
        int index = -1;
        for (Rect originRect : mWordCoordinate) {
            index++;
            if(x > originRect.left && x < originRect.right && y > originRect.top && y < originRect.bottom){
                mHeightLightWord = new Rect(originRect.left, originRect.top, originRect.right, originRect.bottom);
                mHeightLightWord.top = mHeightLightWord.top + Config.contentEnglishLinePadding;
                return mWords.get(index);
            }
        }
        mHeightLightWord = null;
        return null;
    }

    public List<Rect> getWordCoordinate() {
        return mWordCoordinate;
    }
}
