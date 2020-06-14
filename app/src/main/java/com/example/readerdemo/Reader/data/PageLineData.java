package com.example.readerdemo.Reader.data;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.example.readerdemo.Reader.Config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
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
 **/
public class PageLineData {
    long startIndex;
    long endIndex;
    String lineWords;
    Rect mLineRect;
    List<PageSentenceData> sentences = new ArrayList<>();

    public PageLineData(long startIndex){
        this.startIndex = startIndex;
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

    public Rect getLineRect() {
        return mLineRect;
    }

    public void setLineRect(Rect lineRect) {
        mLineRect = lineRect;
    }

    public List<PageSentenceData> getSentences() {
        return sentences;
    }

    public void setSentences(List<PageSentenceData> sentences) {
        this.sentences = sentences;
    }

    public String getLineWords() {
        return lineWords;
    }

    public void setLineWords(String lineWords) {
        this.lineWords = lineWords;
    }



    public static class PageSentenceData{
        //句子的id
        int sentenceId;
        long starTime;
        long endTime;
        String mEnglishString;
        List<String> chineseWords = new ArrayList<>();
        List<Rect> chineseRectList = new ArrayList<>();
        List<String> englishWords = new ArrayList<>();
        List<Rect> englishRectList = new ArrayList<>();

        public PageSentenceData(int id, long start, long end){
            sentenceId = id;
            starTime = start;
            endTime = end;
        }

        public String getEnglishString() {
            return mEnglishString;
        }

        public void setEnglishString(String englishString) {
            mEnglishString = englishString;
        }

        public int getSentenceId() {
            return sentenceId;
        }

        public void setSentenceId(int sentenceId) {
            this.sentenceId = sentenceId;
        }

        public long getStarTime() {
            return starTime;
        }

        public void setStarTime(long starTime) {
            this.starTime = starTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public List<String> getChineseWords() {
            return chineseWords;
        }

        public void setChineseWords(List<String> chineseWords) {
            this.chineseWords = chineseWords;
        }

        public List<Rect> getChineseRectList() {
            return chineseRectList;
        }

        public void setChineseRectList(List<Rect> chineseRectList) {
            this.chineseRectList = chineseRectList;
        }

        public List<String> getEnglishWords() {
            return englishWords;
        }

        public void setEnglishWords(List<String> englishWords) {
            this.englishWords = englishWords;
        }

        public List<Rect> getEnglishRectList() {
            return englishRectList;
        }

        public void setEnglishRectList(List<Rect> englishRectList) {
            this.englishRectList = englishRectList;
        }


        public Rect updateEnglish(Rect lineRect) {
            Point point = new Point(lineRect.left, lineRect.top);
            Paint paint = Config.getContentEnglishPaint();//需要按情况调整
            StringBuilder word = new StringBuilder();
            int spaceNum = 0;
            if(mEnglishString.length()>0){
                spaceNum = 0;
                for (int i = 0; i < mEnglishString.length(); i++) {
                    char letter = mEnglishString.charAt(i);
                    if (letter == ' ') {
                        if (word != null && word.length() > 0) {
                            String wordStr = word.toString();
                            point = getWordCoordinate(wordStr,point, paint, spaceNum);
                            spaceNum = 0;
                            word.setLength(0);
                        }
                        spaceNum++;
                    }else{
                        word.append(letter);
                    }
                }
            }
            if(word.length()>0){
                String wordStr = word.toString();
                point = getWordCoordinate(wordStr,point, paint, spaceNum);
            }
            return new Rect(point.x, point.y,0, 0);
        }

        private Point getWordCoordinate(String wordStr, Point point, Paint paint, int spaceNum) {
            int left = point.x + Config.getLineWidth(Config.ENG, " ") * spaceNum;
            int right = left + Config.getLineWidth(Config.ENG, wordStr);
            int top = point.y;
            int bottom = point.y + Config.getLineHeight(Config.ENG);
            englishWords.add(wordStr);
            englishRectList.add(new Rect(left, top, right, bottom));
            return new Point(right, top);
        }
    }
}
