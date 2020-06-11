package com.example.readerdemo.Reader.Creator;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.util.Pair;

import com.example.readerdemo.Reader.Config;
import com.example.readerdemo.Reader.data.EN_CNBookBean;
import com.example.readerdemo.Reader.data.PageData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.readerdemo.Reader.Config.CONTENT_CHINESE_LINE;
import static com.example.readerdemo.Reader.Config.CONTENT_ENGLISH_LINE;

public class CN_ENGContentCreator extends ContentCreator {
    private EN_CNBookBean mEN_cnBookBean = new EN_CNBookBean();
    private static CN_ENGContentCreator instance = null;
    private CN_ENGContentCreator(Context context) {
        super(context, Config.ENG_CN);
    }

    public synchronized static CN_ENGContentCreator  getInstance(Context context){
        if(instance == null){
            synchronized(CN_ENGContentCreator.class){
                if(instance == null){
                    instance = new CN_ENGContentCreator(context);
                }
            }
        }
        return instance;
    }
    /**
     * 章节结构：
     * 章：id
     *      段：id
     *              句：id\startTime\endTime\chinese\english
     * @param data
     */
    @Override
    protected void handleData(String bookName, String data, int chapterIndex) {
        int paragraphIndex = 0;
        int sentenceIndex = 0;
        //设置书名
        if (mEN_cnBookBean.getBookName() == null) {
            mEN_cnBookBean.setBookName(bookName);
        }
        //创建一章
        EN_CNBookBean.ChapterBean chapterBean = new EN_CNBookBean.ChapterBean();
        chapterBean.setChapterId(chapterIndex);//设置章节Id
        Document doc = Jsoup.parse(data);
        //遍历段
        Elements paragraphs = doc.getElementsByTag("p");
        for (Element paragraph : paragraphs) {
            Log.d(TAG, "handleData: " + paragraph.id());
            //创建一段
            EN_CNBookBean.ChapterBean.ParagraphBean paragraphBean =
                    new EN_CNBookBean.ChapterBean.ParagraphBean();
            paragraphBean.setParagraphId(paragraphIndex);//设置段落id
            paragraph.id();
            for (Element span : paragraph.getElementsByTag("span")) {
                //创建一句
                EN_CNBookBean.ChapterBean.ParagraphBean.SentenceBean sentenceBean =
                    new EN_CNBookBean.ChapterBean.ParagraphBean.SentenceBean();
                sentenceBean.setSentenceId(sentenceIndex);//设置句子Id
                sentenceBean.setEnglish(span.text());
                sentenceBean.setChinese(span.attributes().get("chinese"));
                sentenceBean.setStarTime(transToLongTime(span.attributes().get("start_time")));
                sentenceBean.setEndTime(transToLongTime(span.attributes().get("end_time")));
                //将句保存在段中
                paragraphBean.getSentenceBeans().add(sentenceBean);
                sentenceIndex++;
            }
            //段存入章中
            chapterBean.getParagraphBean().add(paragraphBean);
            paragraphIndex++;
        }
        //保存一章
        mEN_cnBookBean.getChapterBeans().add(chapterBean);
    }

    /**
     * 字符串时间转为long
     * @param timeStr
     * @return
     * @throws ParseException
     */
    private long transToLongTime(String timeStr){
        String[] timeStrArray = timeStr.split("\\.");
        if(timeStrArray.length != 2){
            return -1;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        long ms = Long.parseLong(timeStrArray[1]);
        try{
            Date data = sdf.parse(timeStrArray[0]);
           long hour = data.getHours();
           long min = data.getMinutes() + 60 * hour;
           long seconds = data.getSeconds() + 60 * min;
            return  seconds * 1000 + ms;
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void createChapterPages(String bookName, int currentChapter) {

        createPages(bookName, currentChapter);
    }


    /**
     * 根据一个章节数据全不转化页面数据并保存再缓存中
     * @param chapter
     */
    private void createPages(String bookName, int currentChapter) {
        EN_CNBookBean.ChapterBean chapter = mEN_cnBookBean.getChapterBeans().get(currentChapter);
        int mVisibleHeight = Config.getPageSize().y;
        int mCurrentHeight = 0;
        Paint englishPaint = Config.getContentEnglishPaint();
        Paint.FontMetricsInt fmEnglish  =  englishPaint.getFontMetricsInt();
        int englishHeight = fmEnglish.bottom - fmEnglish.top;
        Paint chinesePaint = Config.getContentEnglishPaint();
        Paint.FontMetricsInt fmChinese  =  chinesePaint.getFontMetricsInt();
        int chineseHeight = fmChinese.bottom - fmChinese.top;
        PageData page = new PageData();
        //设置书名
        page.setBookName(bookName);
        //设置章节序号
        page.setParagraphIndex(currentChapter);
        for (EN_CNBookBean.ChapterBean.ParagraphBean paragraphBean : chapter.getParagraphBean()) {
            StringBuilder sbEnglish = new StringBuilder();
            StringBuilder sbChinese = new StringBuilder();
            for (EN_CNBookBean.ChapterBean.ParagraphBean.SentenceBean
                    sentenceBean : paragraphBean.getSentenceBeans()) {
                sbEnglish.append(sentenceBean.getEnglish());
                sbChinese.append(sentenceBean.getChinese());
            }
            if(Config.LanguageMode != Config.CN){
                List<String> pageEnglishLines = splitLineData(sbEnglish.toString(), Config.getContentEnglishPaint(), Config.ENG);
                for (String pageLine : pageEnglishLines) {
                    if(mCurrentHeight + Config.chapterLinePadding + englishHeight > mVisibleHeight){
                        page = setPageCache(page);
                        mCurrentHeight = 0;
                    }
                    page.getPageLine().add(new Pair<>(CONTENT_ENGLISH_LINE,pageLine));
                    mCurrentHeight+= englishHeight +  Config.chapterLinePadding;
                }
            }
            if(Config.LanguageMode != Config.ENG){
                List<String> pageChineseLines = splitLineData(sbChinese.toString().trim(), Config.getContentChinesePaint(), Config.CN);
                    for (String pageLine : pageChineseLines) {
                        if(mCurrentHeight + Config.chapterLinePadding + chineseHeight > mVisibleHeight){
                            page = setPageCache(page);
                            mCurrentHeight = 0;
                        }
                        page.getPageLine().add(new Pair<>(CONTENT_CHINESE_LINE,pageLine));
                        mCurrentHeight+= chineseHeight +  Config.chapterLinePadding;
                }
            }
        }
        setPageCache(page);
    }
}
