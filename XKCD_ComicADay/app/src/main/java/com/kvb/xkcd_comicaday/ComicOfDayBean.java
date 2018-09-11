
package com.kvb.xkcd_comicaday;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ComicOfDayBean {

    @SerializedName("alt")
    private String mAlt;
    @SerializedName("day")
    private String mDay;
    @SerializedName("img")
    private String mImg;
    @SerializedName("link")
    private String mLink;
    @SerializedName("month")
    private String mMonth;
    @SerializedName("news")
    private String mNews;
    @SerializedName("num")
    private Long mNum;
    @SerializedName("safe_title")
    private String mSafeTitle;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("transcript")
    private String mTranscript;
    @SerializedName("year")
    private String mYear;

    public String getAlt() {
        return mAlt;
    }

    public void setAlt(String alt) {
        mAlt = alt;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public String getImg() {
        return mImg;
    }

    public void setImg(String img) {
        mImg = img;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getMonth() {
        return mMonth;
    }

    public void setMonth(String month) {
        mMonth = month;
    }

    public String getNews() {
        return mNews;
    }

    public void setNews(String news) {
        mNews = news;
    }

    public Long getNum() {
        return mNum;
    }

    public void setNum(Long num) {
        mNum = num;
    }

    public String getSafeTitle() {
        return mSafeTitle;
    }

    public void setSafeTitle(String safeTitle) {
        mSafeTitle = safeTitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTranscript() {
        return mTranscript;
    }

    public void setTranscript(String transcript) {
        mTranscript = transcript;
    }

    public String getYear() {
        return mYear;
    }

    public void setYear(String year) {
        mYear = year;
    }

}
