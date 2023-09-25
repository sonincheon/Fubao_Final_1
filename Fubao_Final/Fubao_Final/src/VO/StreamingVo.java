package VO;

import java.sql.Date;

public class StreamingVo {
    private int sNum;
    private String genre;
    private String title;
    private Date date;
    private String cast;
    private String producer;
    private String runningTime;
    private int views;

    public StreamingVo(int sNum, String genre, String title, Date date, String cast, String producer, String runningTime, int views) {
        this.sNum = sNum;
        this.genre = genre;
        this.title = title;
        this.date = date;
        this.cast = cast;
        this.producer = producer;
        this.runningTime = runningTime;
        this.views = views;
    }

    public int getsNum() {
        return sNum;
    }

    public void setsNum(int sNum) {
        this.sNum = sNum;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(String runningTime) {
        this.runningTime = runningTime;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }
}
