package com.team_project2.hans.whatcatdo.database;

import java.io.Serializable;

public class Emotion implements Serializable {
    private long timestamp;
    private String title;
    private float percent;
    private int count;

    //timestamp가 있는 Emotion 객체 생성
    public Emotion(long timestamp, String title, float percent){
        this.timestamp = timestamp;
        this.title = title;
        this.percent = percent;
    }
    //timestamp가 없는 Emotion 객체 생성
    public Emotion(String title, float percent){
        this.title = title;
        this.percent = percent;
        this.count = 1;
    }

    //각 변수들의 set,get함수
    public void setPercent(float percent) {
        this.percent = (this.percent*(this.count) + percent) / (++count);
    }

    public float getPercent() {
        return percent;
    }

    public String getTitle() {
        return title;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimeStamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
