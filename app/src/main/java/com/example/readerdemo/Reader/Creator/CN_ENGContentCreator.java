package com.example.readerdemo.Reader.Creator;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.LongDef;

import com.example.readerdemo.Reader.Config;
import com.example.readerdemo.Reader.data.EN_CNBookBean;
import com.example.readerdemo.Reader.data.PageData;
import com.example.readerdemo.Reader.data.PageLineData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CN_ENGContentCreator extends ContentCreator {
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

}
