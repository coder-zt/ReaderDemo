package com.example.readerdemo.Reader.data;

/**
 * 标记字符集
 *      书名 章节 开始索引 结束索引 样式 注解 ->利用数据库存储本地
 */
public class SignWordsBean {
    String bookName;
    int paragraphIndex;
    long startIndex;
    long endIndex;
    int styleId;
    String signNote;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getParagraphIndex() {
        return paragraphIndex;
    }

    public void setParagraphIndex(int paragraphIndex) {
        this.paragraphIndex = paragraphIndex;
    }

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

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public String getSignNote() {
        return signNote;
    }

    public void setSignNote(String signNote) {
        this.signNote = signNote;
    }
}
