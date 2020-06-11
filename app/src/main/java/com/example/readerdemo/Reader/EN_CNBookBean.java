package com.example.readerdemo.Reader;


import java.util.ArrayList;
import java.util.List;

/**
 * 章节结构：
 * 章：
 *      段：
 *              句：start\end\chinese\english
 */

/**
 * 双语书
 */
public class EN_CNBookBean {

    List<ChapterBean> mChapterBeans = new ArrayList<>();

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
        List<ParagraphBean> mParagraphBean = new ArrayList<>();
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
            private  String id;

            List<SentenceBean> mSentenceBeans = new ArrayList<>();
            public List<SentenceBean> getSentenceBeans() {
                return mSentenceBeans;
            }

            public void setSentenceBeans(List<SentenceBean> sentenceBeans) {
                mSentenceBeans = sentenceBeans;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            /**
             * 句
             */
            public static class SentenceBean{

                long starTime;
                long endTime;
                String chinese;
                String english;

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
