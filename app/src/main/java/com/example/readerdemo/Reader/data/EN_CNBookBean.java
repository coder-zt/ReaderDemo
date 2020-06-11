package com.example.readerdemo.Reader.data;


import java.util.ArrayList;
import java.util.List;

/**
 * 书的数据结构
 *  书本结构：
 *  书：书名、章的集合
 *      章：id\段的集合
 *          段：id\句子的集合
 *              句：id\startTime\endTime\chinese\english
 */

/**
 * 双语书
 */
public class EN_CNBookBean {
    //书名
    String bookName;

    //一本书中章的集合
    List<ChapterBean> mChapterBeans = new ArrayList<>();

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public List<ChapterBean> getChapterBeans() {
        return mChapterBeans;
    }

    public void setChapterBeans(List<ChapterBean> chapterBeans) {
        mChapterBeans = chapterBeans;
    }

    /**
     * 章
     */
    public static class ChapterBean{
        //一章中的段的集合
        List<ParagraphBean> mParagraphBean = new ArrayList<>();
        //章的id
        int mChapterId;

        public int getChapterId() {
            return mChapterId;
        }

        public void setChapterId(int chapterId) {
            mChapterId = chapterId;
        }

        public List<ParagraphBean> getParagraphBean() {
            return mParagraphBean;
        }

        public void setSentenceBeans(List<ParagraphBean> sentenceBeans) {
            mParagraphBean = sentenceBeans;
        }

        /**
         * 段
         */
        public static class ParagraphBean{
            //段的id
            private int paragraphId;
            //一段中句子的集合
            List<SentenceBean> mSentenceBeans = new ArrayList<>();

            public List<SentenceBean> getSentenceBeans() {
                return mSentenceBeans;
            }

            public void setSentenceBeans(List<SentenceBean> sentenceBeans) {
                mSentenceBeans = sentenceBeans;
            }

            public int getParagraphId() {
                return paragraphId;
            }

            public void setParagraphId(int paragraphId) {
                this.paragraphId = paragraphId;
            }

            /**
             * 句子
             */
            public static class SentenceBean{
                //句子的id
                int sentenceId;
                long starTime;
                long endTime;
                String chinese;
                String english;

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

                public String getChinese() {
                    return chinese;
                }

                public void setChinese(String chinese) {
                    this.chinese = chinese;
                }

                public String getEnglish() {
                    return english;
                }

                public void setEnglish(String english) {
                    this.english = english;
                }
            }
        }

    }
}
