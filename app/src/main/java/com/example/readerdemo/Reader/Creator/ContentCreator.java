package com.example.readerdemo.Reader.Creator;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.util.Pair;

import com.example.readerdemo.Reader.data.BookBean;
import com.example.readerdemo.Reader.Config;
import com.example.readerdemo.Reader.data.EN_CNBookBean;
import com.example.readerdemo.Reader.data.PageData;
import com.example.readerdemo.Reader.data.PageLineData;
import com.example.readerdemo.SourceStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


/**
 * 内容创建器，获取数据形成缓存
 */
public abstract class ContentCreator implements SourceStream.onDataCallback {

    protected static final String TAG = "ContentCreator";
    private static Context mContext;
    EN_CNBookBean mEN_cnBookBean = new EN_CNBookBean();


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
    public void dataCallback(String bookName, String data, int chapterIndex) {
        handleData(bookName, data, chapterIndex);
        createChapterPages(bookName, chapterIndex);
    }

    /**
     * 将网页数据初步转为章节数据
     * @param sourceData 源数据
     * @param chapterIndex 章的序号
     */
    protected abstract void handleData(String bookName, String sourceData, int chapterIndex);

    /**
     * 将章节数据转化为pageData数据
     */
    public void createChapterPages(String bookName, int currentChapter) {
        createPages(bookName, currentChapter);
        updatePages();
    }

    PageData setPageCache(PageData pageData) {
        //设置页数
        pageData.setCurrentPageNum(mPages.size() + 1);
        mPages.add(pageData);
//        Log.d(TAG, "setPageCache: createPages ---> page data is --->" + pageData.getCurrentPageNum() + "content is -->"+ pageData.getLines().get(0).getSentences().get(0).getEnglishString());
        pageData.update();
        return new PageData();
    }

    /**
     * 更新本章总页数
     */
    void updatePages(){
        for (PageData page : mPages) {
            Log.d(TAG, "updatePages: 第" + page.getCurrentPageNum() + "页");
            Log.d(TAG, page.getLines().get(0).getSentences().get(0).getEnglishString());
            page.setTotalPageNum(mPages.size());
        }
        Log.d(TAG, "updatePages: ");
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
                    resultPair = createEnglishLines(null ,paragraph, paint);
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
     * @param currentLine
     * @param originString
     * @param paint
     * @return
     */
    protected Pair<String, String> createEnglishLines(String currentLine, String originString, Paint paint) {
        int mVisibleWidth = Config.getPageSize().x - Config.margin * 2;
        int addSpaceNumber = 0;
        int mWordNum = 0;
        List<String> newLineWords = new ArrayList<>();
        if (currentLine != null) {
            String[] currentWords = currentLine.split(" ");
            mWordNum = currentWords.length;
            newLineWords.addAll(Arrays.asList(currentWords));
        }
        float wordsWidth = currentLine==null?0:paint.measureText(currentLine);
        float totalWordWidth = currentLine==null?0:paint.measureText(currentLine + " ");
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
                wordsWidth = wordsWidth - wordWidth;
                if (totalWordWidth-wordSpaceWidth == 829.0) {

                    Log.d(TAG, "createEnglishLines: total width is ---> " + (totalWordWidth-wordSpaceWidth));
                }
                //重新计算字间距
                float remainWidth =  mVisibleWidth - wordsWidth;
                addSpaceNumber = (int) (remainWidth/spaceWidth);
                break;
            }
        }
        String newLineString = wordListToString(newLineWords, true, addSpaceNumber, false);
        if (newLineWords.size() - mWordNum  < wordList.size()){
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

    /**
     * 根据一个章节数据全部转化页面数据并保存再缓存中
     * @param bookName
     * @param currentChapter
     *
     */
    void createPages(String bookName, int currentChapter) {
        //开始索引
        long startIndex = 0;
        int startTop = 0;
        EN_CNBookBean.ChapterBean chapter = mEN_cnBookBean.getChapterBeans().get(currentChapter);
        int mVisibleHeight = Config.getPageSize().y;
        int mCurrentHeight = 0;

        PageData page = new PageData();
        for (EN_CNBookBean.ChapterBean.ParagraphBean paragraphBean : chapter.getParagraphBean()) {
            //设置书名
            page.setBookName(bookName);
            //设置章节序号
            page.setChapterIndex(currentChapter);
            //处理段落数据：存在多个句子
            //处理流程：不断的新建行加入到pageData的行集合中
            //还可继续添加字符的行
            PageLineData canAddWordLine = null;
            Log.d(TAG, "createPages: new paragraph so create new line");
            int sentenceBeanIndex = 0;
            for (EN_CNBookBean.ChapterBean.ParagraphBean.SentenceBean
                    sentenceBean : paragraphBean.getSentenceBeans()) {
                //处理句子数据：将句子划分成多个行
                //处理结果：多个行数据，还剩最后一个行可能可以继续加下一句的句子
                sentenceBeanIndex++;
                List<PageLineData> lines = sentenceToPageLineData(sentenceBean, startIndex, canAddWordLine);
                PageLineData line = lines.remove(lines.size()-1);
                if (isCanAddSentence(line) &&
                        sentenceBeanIndex != paragraphBean.getSentenceBeans().size()) {
                    canAddWordLine = line;
                }else{
                    lines.add(line);
                }
                page.setStartIndex(startIndex);
                for (PageLineData pageLineData : lines) {
                    int lineHeight = Config.getLineHeight(Config.ENG);
                    if (mCurrentHeight + lineHeight > mVisibleHeight) {
                        page.setEndIndex(pageLineData.getEndIndex());
                        page = setPageCache(page);
                        mCurrentHeight = 0;
                    }else{
                        if (isCanAddSentence(pageLineData)) {
                            pageLineData.setLineRect(new Rect(Config.margin, mCurrentHeight,
                                    Config.margin + Config.getLineWidth(Config.ENG,pageLineData.getLineWords()) ,
                                    mCurrentHeight + Config.getLineHeight(Config.ENG)));
                        }else{
                            pageLineData.setLineRect(new Rect(Config.margin, mCurrentHeight,
                                    Config.margin + Config.getPageSize().x ,
                                    mCurrentHeight + Config.getLineHeight(Config.ENG)));
                        }
                        //根据行的rect更新一行中句子的rect
                        Rect preRect = null;
                        for (PageLineData.PageSentenceData sentence : pageLineData.getSentences()) {
                            if(preRect == null){
                                preRect = sentence.updateEnglish(pageLineData.getLineRect());
                            }else{
                                preRect = sentence.updateEnglish(preRect);
                            }
                        }
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
        if (page.getBookName() != null) {
            setPageCache(page);
        }
    }

    private Boolean isCanAddSentence(PageLineData line) {
        if(line.getLineWords() == null){
            return false;
        }
        return Config.getLineWidth(Config.ENG, line.getLineWords() ) < Config.getPageSize().x;
    }

    /**
     *     //处理句子数据：将句子划分成多个行
     *     //处理结果：多个行数据
     *
     * @param sentenceBean
     * @param startIndex
     * @param canAddWordLine
     * @return
     */
    private List<PageLineData> sentenceToPageLineData(
            EN_CNBookBean.ChapterBean.ParagraphBean.SentenceBean sentenceBean,
            long startIndex, PageLineData canAddWordLine) {
        List<PageLineData> lines = new ArrayList<>();
        PageLineData operateLine = null;
        String preSentence = null;
        if(canAddWordLine == null){
            operateLine = new PageLineData(startIndex);
        }else{

            operateLine = canAddWordLine;
            Log.d(TAG, "sentenceToPageLineData: " + "在旧的一行中添加。。。");


            StringBuilder sbString = new StringBuilder();
            if (operateLine.getSentences() != null) {
                for (PageLineData.PageSentenceData sentence : operateLine.getSentences()) {
                    sbString.append(sentence.getEnglishString());
                }
                preSentence = sbString.toString();
            }
        }
        String englishSentence = sentenceBean.getEnglish();
        while(englishSentence != null && englishSentence.length()>0){
            Pair<String, String> englishLines = createEnglishLines(preSentence, englishSentence,
                    Config.getContentEnglishPaint());

            //添加句子
            PageLineData.PageSentenceData sentenceData = new PageLineData.PageSentenceData(
                    sentenceBean.getSentenceId(),sentenceBean.getStarTime(),sentenceBean.getEndTime());
            String english = englishLines.first;
            //当前面已有句子插入这行但未满，又插入新的一句，单词间的间隔发生变化
            if (preSentence != null) {
                english = changeAllSentences(operateLine, englishLines.first);
                Log.d(TAG, "sentenceToPageLineData: + " + english);
            }
            sentenceData.setEnglishString(english);
            operateLine.getSentences().add(sentenceData);
            int wordNum = 0;
            StringBuilder sbLineString = new StringBuilder();
            for (PageLineData.PageSentenceData sentence : operateLine.getSentences()) {
                wordNum += sentence.getEnglishWords().size();
                sbLineString.append(sentence.getEnglishString());
            }
            operateLine.setLineWords(sbLineString.toString());
            operateLine.setEndIndex(startIndex + wordNum);
            lines.add(operateLine);
            operateLine = new PageLineData(operateLine.getEndIndex()+1);
            englishSentence = englishLines.second;
            preSentence = null;
        }
        return lines;

    }

    /**
     *
     * @param operateLine
     * @param newLineWord
     */
    private String changeAllSentences(PageLineData operateLine, String newLineWord) {
        List<String> sentenceWords;
        String lineSplitWords;
        for (PageLineData.PageSentenceData sentence : operateLine.getSentences()) {
            sentenceWords = Arrays.asList(sentence.getEnglishString().split(" "));
            String finallyWord = sentenceWords.get(sentenceWords.size()-1);
            lineSplitWords = newLineWord.split(finallyWord)[0] + finallyWord;
            sentence.setEnglishString(lineSplitWords);
            newLineWord = newLineWord.replace(lineSplitWords, "");
        }
        return newLineWord;
    }

}
