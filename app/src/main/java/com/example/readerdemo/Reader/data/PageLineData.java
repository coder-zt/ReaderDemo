package com.example.readerdemo.Reader.data;

import android.graphics.Rect;

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
    Rect mLineRect;
    List<PageSentenceData> sentences;

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

    public class PageSentenceData{
        //句子的id
        int sentenceId;
        long starTime;
        long endTime;
        List<String> chineseWords;
        List<Rect> chineseRectList;
        List<String> englishWords;
        List<Rect> englishRectList;

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
    }
}
