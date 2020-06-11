package com.example.readerdemo.Reader.Creator;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.util.Pair;

import com.example.readerdemo.Reader.BookBean;
import com.example.readerdemo.Reader.Config;
import com.example.readerdemo.Reader.PageData;
import com.example.readerdemo.SourceStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * 内容创建器，获取数据形成缓存
 */
public abstract class ContentCreator implements SourceStream.onDataCallback {

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
    protected BookBean mBookBean;
    //一个章节缓存的页面
    protected List<PageData> mPages = new ArrayList<>();;
    private final int mLanguageType;

    public ContentCreator(Context context, int type){
        mSourceStream = new SourceStream();
        mSourceStream.setOnDataCallback(this);
        mContext = context;
        mLanguageType = type;
    }

    //获取数据
    public void getChapterData(){
        if (mSourceStream != null) {
            mSourceStream.requestData(mLanguageType);
        }
    }

    /**
     * 数据产生的回调
     *
     * @param data
     */
    @Override
    public void dataCallback(String data) {
        handleData(data);
        createChapterPages(0);
    }

    /**
     * 将网页数据初步转为章节数据
     * @param data
     */
    protected abstract void handleData(String data);

    /**
     * 将章节数据转化为pagedata数据
     */
    public abstract void createChapterPages(int currentChapter);

    PageData setPageCache(PageData pageData) {
        mPages.add(pageData);
        Log.d(TAG, "setPageCache: " + mPages.size());
        pageData.update();
        return new PageData();
    }

    public PageData getPage(int page) {
        if(page<0){
            PageData data = new PageData();
            return data;
        }else if(page >= mPages.size()){
            PageData data = new PageData();
            return data;
        }
        Log.d(TAG, "getPage: " + mPages.size());
        return mPages.get(page);
    }

    /**
     *  将一段数据转为几行数据（与页面同宽）
     *
     * @param paragraph
     * @param paint
     * @return
     */
    List<String> splitLineData(String paragraph, Paint paint, int type) {
        List<String> linesList = new ArrayList<>();
        Pair<String, String> resultPair = null;
        do{
            switch (type) {
                case Config.ENG:
                    resultPair = createEnglishLines(paragraph, paint);
                    break;
                case Config.CN:
                    resultPair = createChineseLines(paragraph,paint);
                    break;
            }
            if (resultPair != null) {
                linesList.add(resultPair.first);
                paragraph = resultPair.second;
            }
        }while(resultPair != null &&  resultPair.second != null &&resultPair.second.length() > 0);
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
    protected Pair<String, String> createEnglishLines(String originString, Paint paint) {
        int mVisibleWidth = Config.getPageSize().x;
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
        String newLineString = wordListToString(newLineWords, true, addSpaceNumber, false);
        if (newLineWords.size() < wordList.size()){
            List<String> wordsList = new ArrayList<>();
            wordList = wordList.subList(newLineWords.size(), wordList.size());
            if (mSbDelChars != null && mSbDelChars.length()>0) {
                newLineString += "-";
                wordsList.add(mSbDelChars.toString());
            }
            wordsList.addAll(wordList);
            String remainString = wordListToString(wordsList,false, 0,false);
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
    protected String wordListToString(List<String> ListStr, boolean isView ,int spaceNumber, boolean isChinese){
        int spaceNum = ListStr.size()-1;
        StringBuilder sbListString = new StringBuilder();
        Iterator<String> iterator = ListStr.iterator();
        int spaceIndex = 0;
        while (iterator.hasNext()) {
            sbListString.append(iterator.next());
            if(iterator.hasNext()  && !isChinese){
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

    /**
     * 创建中文的一行
     *
     * @param originString
     * @param paint
     * @return
     */
    protected Pair<String, String> createChineseLines(String originString, Paint paint) {
        int mVisibleWidth = Config.getPageSize().x;
        List<String> newLineWords = new ArrayList<>();
        List<String> wordList = new ArrayList<>();
        for (int i = 0; i < originString.length(); i++) {
            wordList.add(String.valueOf(originString.charAt(i)));
        }
        float wordsWidth = 0.0f;
        float totalWordWidth = 0.0f;
        //遍历单词
        Iterator<String> iterator = wordList.iterator();
        while (iterator.hasNext()){
            String word = iterator.next();
            float wordSpaceWidth = paint.measureText(word);
            totalWordWidth += wordSpaceWidth;
            float wordWidth = paint.measureText(word);
            wordsWidth += wordWidth;
            if(totalWordWidth < mVisibleWidth){
                newLineWords.add(word);
            }else{
                break;
            }
        }
        if(wordList.size()>0){
            return new Pair<>(wordListToString(newLineWords,false, 0,true),
                    wordListToString(wordList.subList(newLineWords.size(),
                            wordList.size()),false, 0,true));
        }else{
            return new Pair<>(wordListToString(newLineWords,false, 0,true),
                    null);
        }
    }

    /**
     * 更新页面数据
     */
    public void update(){
        if (mSourceStream != null) {
            mPages.clear();
            mSourceStream.requestData(mLanguageType);
        }
    }

}
