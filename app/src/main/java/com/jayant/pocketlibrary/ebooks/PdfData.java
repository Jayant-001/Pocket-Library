package com.jayant.pocketlibrary.ebooks;

public class PdfData {

    String title, desc, contributorName, sem, sub, lang, url, date, time;

    public PdfData() {
    }

    public PdfData(String title, String desc, String contributorName, String sem, String sub, String lang, String url, String date, String time) {
        this.title = title;
        this.desc = desc;
        this.contributorName = contributorName;
        this.sem = sem;
        this.sub = sub;
        this.lang = lang;
        this.url = url;
        this.date = date;
        this.time = time;
    }

    @Override
    public String toString() {
        return "PdfData{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", contributorName='" + contributorName + '\'' +
                ", sem='" + sem + '\'' +
                ", sub='" + sub + '\'' +
                ", lang='" + lang + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getContributorName() {
        return contributorName;
    }

    public void setContributorName(String contributorName) {
        this.contributorName = contributorName;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
