package com.example.readerdemo.Reader.Creator;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.util.Pair;

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
import java.util.Date;
import java.util.List;

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
        updatePages();
    }


    /**
     * 根据一个章节数据全不转化页面数据并保存再缓存中
     * @param bookName
     * @param currentChapter
     *
     */
    private void createPages(String bookName, int currentChapter) {
        //开始索引
        long startIndex = 0;
        int startTop = 0;
        EN_CNBookBean.ChapterBean chapter = mEN_cnBookBean.getChapterBeans().get(currentChapter);
        int mVisibleHeight = Config.getPageSize().y;
        int mCurrentHeight = 0;

        PageData page = new PageData();
        //设置书名
        page.setBookName(bookName);
        //设置章节序号
        page.setChapterIndex(currentChapter);
        for (EN_CNBookBean.ChapterBean.ParagraphBean paragraphBean : chapter.getParagraphBean()) {
            //处理段落数据：存在多个句子
            //处理流程：不断的新建行加入到pageData的行集合中
            //还可继续添加字符的行
            PageLineData canAddWordLine = null;
            for (EN_CNBookBean.ChapterBean.ParagraphBean.SentenceBean
                    sentenceBean : paragraphBean.getSentenceBeans()) {
                //处理句子数据：将句子划分成多个行
                //处理结果：多个行数据，还剩最后一个行可能可以继续加下一句的句子
                Log.d(TAG, "createPages: " + sentenceBean.getEnglish());
                List<PageLineData> lines = sentenceToPageLineData(sentenceBean, startIndex, canAddWordLine, startTop);
                PageLineData line = lines.get(lines.size()-1);
                if (isCanAddSentence(line)) {
                    canAddWordLine = line;
                }
                page.setStartIndex(startIndex);
                for (PageLineData pageLineData : lines) {
                   int lineHeight = pageLineData.getLineRect().height();
                    if (mCurrentHeight + lineHeight > mVisibleHeight) {
                        page.setEndIndex(pageLineData.getEndIndex());
                        setPageCache(page);
                    }else{
                        page.getLines().add(pageLineData);
                        for (PageLineData.PageSentenceData sentence : pageLineData.getSentences()) {
                            page.getWords().addAll(sentence.getEnglishWords());
                        }
                        mCurrentHeight += lineHeight;
                    }
                }
                startIndex =page.getEndIndex() + 1;
            }
        }
        setPageCache(page);
    }

    private Boolean isCanAddSentence(PageLineData line) {
        if(line.getLineRect() == null){
            return false;
        }
        return line.getLineRect().width() < Config.getPageSize().x;
    }

    //处理句子数据：将句子划分成多个行
    //处理结果：多个行数据，还剩最后一个行可能可以继续加下一句的句子
    private List<PageLineData> sentenceToPageLineData(
            EN_CNBookBean.ChapterBean.ParagraphBean.SentenceBean sentenceBean,
            long startIndex, PageLineData canAddWordLine, int startTop ) {
        List<PageLineData> lines = new ArrayList<>();
        PageLineData operateLine = null;
        if(canAddWordLine == null){
            operateLine = new PageLineData(startIndex);
        }else{
            operateLine = canAddWordLine;
        }
        String englishSentence = sentenceBean.getEnglish();
        while(englishSentence != null && englishSentence.length()>0){
            Pair<String, String> englishLines = createEnglishLines(englishSentence,
                    Config.getContentEnglishPaint());
            if (isCanAddSentence(operateLine)) {
                operateLine.setLineRect(new Rect(Config.margin, startTop,
                        Config.margin + Config.getLineWidth(Config.ENG,englishLines.first) ,
                        startTop + Config.getLineHeight(Config.ENG)));
            }else{
                operateLine.setLineRect(new Rect(Config.margin, startTop,
                        Config.margin + Config.getPageSize().x ,
                        startTop + Config.getLineHeight(Config.ENG)));

            }
            //添加句子
            PageLineData.PageSentenceData sentenceData = new PageLineData.PageSentenceData(
                    sentenceBean.getSentenceId(),sentenceBean.getStarTime(),sentenceBean.getEndTime());
            sentenceData.setEnglishString(englishLines.first);
            sentenceData.updateEnglish(operateLine.getLineRect());
            operateLine.getSentences().add(sentenceData);
            int wordNum = 0;
            for (PageLineData.PageSentenceData sentence : operateLine.getSentences()) {
                wordNum += sentence.getEnglishWords().size();
            }
            operateLine.setEndIndex(startIndex + wordNum);
            lines.add(operateLine);
            operateLine = new PageLineData(operateLine.getEndIndex()+1);
            englishSentence = englishLines.second;
        }
        return lines;

    }
}
