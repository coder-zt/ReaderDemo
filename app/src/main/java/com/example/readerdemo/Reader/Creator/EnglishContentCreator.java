package com.example.readerdemo.Reader.Creator;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.util.Pair;

import com.example.readerdemo.Reader.data.BookBean;
import com.example.readerdemo.Reader.Config;
import com.example.readerdemo.Reader.data.PageData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.example.readerdemo.Reader.Config.CHAPTER_LINE;
import static com.example.readerdemo.Reader.Config.CONTENT_ENGLISH_LINE;
import static com.example.readerdemo.Reader.Config.TITLE_LINE;

public class EnglishContentCreator extends ContentCreator {

    public EnglishContentCreator(Context context){
        super(context, Config.ENG);
    }

    @Override
    protected void handleData(String bookName, String data, int chapter) {
        BookBean.ChapterBean chapterBean = new BookBean.ChapterBean();
        mBookBean = new BookBean();
        Document doc = Jsoup.parse(data);
        Elements elements = doc.select("h2");
        chapterBean.setChapter(elements.text());
        Elements elementB = doc.select("p").select("b");
        chapterBean.setTitle(elementB.text());
        Elements elementP = doc.select("p");
        List<String> strList = new ArrayList<>();
        String[] split = elementP.html().split("\n");
        for (String s : split) {
            if (s.contains("<b>")) {
                continue;
            }
            strList.add(s);
        }
        chapterBean.setpList(strList);
        mBookBean.chapterList.add(chapterBean);
    }

    @Override
    public void createChapterPages(String bookName, int currentChapter) {
        BookBean.ChapterBean chapter = mBookBean.chapterList.get(currentChapter);
        createPage(chapter);
    }


    public void createPage(BookBean.ChapterBean chapterBean){
        int mVisibleHeight = Config.getPageSize().y;
        int mCurrentHeight = 0;
        PageData pageData = new PageData();
        Pair<Integer, List<String>> chapterList = createChapter(chapterBean.getChapter());
        for (String s : chapterList.second) {
            if(mCurrentHeight + Config.chapterLinePadding + chapterList.first > mVisibleHeight){
                pageData = super.setPageCache(pageData);
                mCurrentHeight = 0;
            }
            pageData.getPageLine().add(new Pair<Integer, String>(CHAPTER_LINE,s));
            mCurrentHeight+=chapterList.first +  Config.chapterLinePadding;
        }


        Pair<Integer, List<String>> titleList = createTitle(chapterBean.getTitle());
        for (String s : titleList.second) {
            if(mCurrentHeight + titleList.first + Config.titleLinePadding > mVisibleHeight){
                pageData = setPageCache(pageData);
                mCurrentHeight = 0;
            }
            pageData.getPageLine().add(new Pair<Integer, String>(TITLE_LINE,s));
            mCurrentHeight+=titleList.first + Config.titleLinePadding;
        }

        //该章节的段落
        for (String pString : chapterBean.getpList()) {
            Pair<Integer, List<String>> pList = createContent(pString);
            //放入到pagedata的pageline中
            for (String s : pList.second) {
                if(mCurrentHeight + pList.first+ Config.contentEnglishLinePadding > mVisibleHeight){
                    pageData = setPageCache(pageData);
                    mCurrentHeight = 0;
                }
                pageData.getPageLine().add(new Pair<Integer, String>(CONTENT_ENGLISH_LINE,s));
                mCurrentHeight += pList.first + Config.contentEnglishLinePadding;
            }
        }
        if (pageData != null) {
            setPageCache(pageData);
        }
    }



    private Pair<Integer, List<String>> createContent(String pString) {
        if (pString == null) {
            return null;
        }
        Paint paint = Config.getContentEnglishPaint();
        Paint.FontMetricsInt m  =  paint.getFontMetricsInt();
        int height = m.bottom - m.top;
        Log.d(TAG, "createContent: content height is --->" + height);
        return new Pair<>(height,splitLineData(pString, paint, Config.ENG));
    }

    /**
     * 规范标题文字
     * @param title
     * @return
     */
    private Pair<Integer, List<String>> createTitle(String title) {
        if (title == null) {
            return null;
        }
        Paint paint = Config.getTitlePaint();
        Paint.FontMetricsInt m  =  paint.getFontMetricsInt();
        int height = m.bottom - m.top;
        return new Pair<>(height,splitLineData(title, paint, Config.ENG));
    }

    /**
     * 规范章节文字
     * @param chapter
     * @return
     */
    private Pair<Integer, List<String>> createChapter(String chapter) {
        if (chapter == null) {
            return null;
        }
        Paint paint = Config.getChapterPaint();
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        Paint.FontMetrics m  =  paint.getFontMetrics();
        int height = (int) (m.bottom - m.top);
        return new Pair<>(height,splitLineData(chapter, paint, Config.ENG));
    }

}
