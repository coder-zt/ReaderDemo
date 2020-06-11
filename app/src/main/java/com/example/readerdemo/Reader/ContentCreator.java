package com.example.readerdemo.Reader;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.util.Pair;

import com.example.readerdemo.SourceStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingDeque;

import static com.example.readerdemo.Reader.Config.CHAPTER_LINE;
import static com.example.readerdemo.Reader.Config.CONTENT_LINE;
import static com.example.readerdemo.Reader.Config.TITLE_LINE;

/**
 * 内容创建器，获取数据形成缓存
 */
public class ContentCreator implements SourceStream.onDataCallback {

    public static final String TAG = "ContentCreator";
    private static Context mContext;


    private static ContentCreator mContentCreator = null;
    private SourceStream mSourceStream;
    //该书的缓存
    private List<BookBean> mBookCache = new ArrayList<>();
    //当前章节
    private int CurrentChapter = 0;
    //当前显示的第一行
    private int CurrentStartLine = 0;
    //当前显示的最后一行
    private int CurrentEndLine = 0;
    //未显示完全的行
    private String mRemainLine = "";
    private BookBean mBookBean;
    //配置管理器
    private Config mConfig;
    //一个章节缓存的页面
    private List<PageData> mPages = new ArrayList<>();;

    private ContentCreator(){
        mConfig = Config.getInstance();
        mSourceStream = new SourceStream();
        mSourceStream.setOnDataCallback(this);
    }

    public static ContentCreator getInstance(Context context) {
        if (mContentCreator == null) {
            synchronized (Config.class) {
                if (mContentCreator == null) {
                    mContentCreator = new ContentCreator();
                }
            }
        }
        mContext = context;
        return mContentCreator;
    }
    //获取数据
    public void getChapterData(){
        if (mSourceStream != null) {
            mSourceStream.requestData();
        }
    }

    /**
     * 数据产生的回调
     *
     * @param data
     */
    @Override
    public void dataCallback(String data) {

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
            createChapterPages(0);

    }

    /**
     * 将章节数据转化为pagedata数据
     */
    public void createChapterPages(int currentChapter){
        BookBean.ChapterBean chapterBean = mBookBean.chapterList.get(currentChapter);
        createPage(chapterBean);
    }

    public void createPage(BookBean.ChapterBean chapterBean){
        int mVisibleHeight = mConfig.getPageSize().y;
        int mCurrentHeight = 0;
        PageData pageData = new PageData();
        Pair<Integer, List<String>> chapterList = createChapter(chapterBean.getChapter());
        for (String s : chapterList.second) {
            if(mCurrentHeight + Config.chapterLinePadding + chapterList.first > mVisibleHeight){
                pageData = setPageCache(pageData);
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
        Log.d(TAG, "createPage: paragraph size is----> " + chapterBean.getpList().size());
        for (String pString : chapterBean.getpList()) {
            Pair<Integer, List<String>> pList = createContent(pString);
            Log.d(TAG, "createPage: pList size is--->" + pList.second.size() );
            //放入到pagedata的pageline中
            for (String s : pList.second) {
                if(mCurrentHeight + pList.first+ Config.contentLinePadding > mVisibleHeight){
                    pageData = setPageCache(pageData);
                    mCurrentHeight = 0;
                }
                pageData.getPageLine().add(new Pair<Integer, String>(CONTENT_LINE,s));
                Log.d(TAG, "createPage: add line is ---> " + s);
                mCurrentHeight += pList.first + Config.contentLinePadding;
            }
        }

    }

    private PageData setPageCache(PageData pageData) {
        mPages.add(pageData);
        pageData.update();
        return new PageData();
    }

    private Pair<Integer, List<String>> createContent(String pString) {
        if (pString == null) {
            return null;
        }
        Paint paint = Config.getContentPaint();
        Paint.FontMetricsInt m  =  paint.getFontMetricsInt();
        int height = m.bottom - m.top;
        Log.d(TAG, "createContent: content height is --->" + height);
        return new Pair<>(height,splitLineData(pString, paint));
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
        return new Pair<>(height,splitLineData(title, paint));
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
        splitLineData(chapter, paint);
        return new Pair<>(height,splitLineData(chapter, paint));
    }

    private List<String> splitLineData(String paragraph, Paint paint) {
        List<String> linesList = new ArrayList<>();
        Pair<String, String> resultPair = null;
        do{
            resultPair = createEnglishLines(paragraph, paint);
            if (resultPair != null) {
                linesList.add(resultPair.first);
                paragraph = resultPair.second;
            }
        }while(resultPair != null && resultPair.second != null);
        Log.d(TAG, "splitLineData: " + linesList.size());
        return linesList;
    }

    /**
     * 创建英语的一行
     *
     * @param originString
     * @param paint
     * @return
     */
    private Pair<String, String> createEnglishLines(String originString, Paint paint) {
        int mVisibleWidth = mConfig.getPageSize().x;
        int addSpaceNumber = 0;
        List<String> newLineWords = new ArrayList<>();
        float wordsWidth = 0.0f;
        float totalWordWidth = 0.0f;
        float spaceWidth = paint.measureText(" ");
        String[] words = originString.split(" ");
        List<String> wordList = Arrays.asList(words);
        StringBuilder mSbDelChars = null;
        //特长词组
        mSbDelChars = new StringBuilder();
        //遍历单词
        Iterator<String> iterator = wordList.iterator();
        while (iterator.hasNext()){
            String word = iterator.next();
            float wordSpaceWidth = paint.measureText(word + " ");

            if(wordSpaceWidth > mVisibleWidth){
                while(wordSpaceWidth + totalWordWidth > mVisibleWidth){
                    // TODO: 2020/6/10 单词过长
                    word = word.substring(0,word.length()-1);
                    wordSpaceWidth = paint.measureText(word + "-");
                    String delStr = word.substring(word.length()-1);
                    mSbDelChars.insert(0, delStr);
                }
            }
            totalWordWidth += wordSpaceWidth;
            float wordWidth = paint.measureText(word);
            wordsWidth += wordWidth;
            if(totalWordWidth < mVisibleWidth){
                newLineWords.add(word);
            }else{
                Log.d(TAG, "createEnglishLines: total width is ---> " + (totalWordWidth-wordSpaceWidth));
                wordsWidth = wordsWidth - wordWidth;
                //重新计算字间距
                float remainWidth =  mVisibleWidth - wordsWidth;
                addSpaceNumber = (int) (remainWidth/spaceWidth);
                break;
            }
        }
        String newLineString = wordListToString(newLineWords, true, addSpaceNumber);
        if (newLineWords.size() < wordList.size()){
            List<String> wordsList = new ArrayList<>();
            wordList = wordList.subList(newLineWords.size(), wordList.size());
            if (mSbDelChars != null && mSbDelChars.length()>0) {
                newLineString += "-";
                wordsList.add(mSbDelChars.toString());
            }
            wordsList.addAll(wordList);
            String remainString = wordListToString(wordsList,false, 0);
            return new Pair<>(newLineString, remainString);
        }else{
            return new Pair<>(newLineString, null);
        }
    }

    /**
     * 将字符列表转为字符串
     *
     * @param ListStr
     * @return
     */
    public String wordListToString(List<String> ListStr, boolean isView, int spaceNumber){
        int spaceNum = ListStr.size()-1;
        StringBuilder sbListString = new StringBuilder();
        Iterator<String> iterator = ListStr.iterator();
        int spaceIndex = 0;
        while (iterator.hasNext()) {
            sbListString.append(iterator.next());
            if(iterator.hasNext()){
                sbListString.append(" ");
                if(isView){
                    for( int i=1; i< (spaceNumber/spaceNum);i++ ){
                        sbListString.append(" ");
                    }
                    if(spaceIndex < spaceNumber % spaceNum){
                        sbListString.append(" ");
                    }
                }
            }
            spaceIndex++;
        }
        return sbListString.toString();
    }

    public PageData getPage(int page) {
        if(page<0){
            PageData data = new PageData();
            data.isNoPre = true;
            return data;
        }else if(page >= mPages.size()){
            PageData data = new PageData();
            data.isNoNext = true;
            return data;
        }
        Log.d(TAG, "getPage: " + mPages.size());
        return mPages.get(page);
    }
}
