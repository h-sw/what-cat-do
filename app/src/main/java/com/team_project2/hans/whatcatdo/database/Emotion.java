package com.team_project2.hans.whatcatdo.database;

import java.io.Serializable;

public class Emotion implements Serializable {
    private long timestamp;
    private String title;
    private float percent;
    private int count;

    public Emotion(long timestamp, String title, float percent){
        this.timestamp = timestamp;
        this.title = title;
        this.percent = percent;
    }

    public Emotion(String title, float percent){
        this.title = title;
        this.percent = percent;
        this.count = 1;
    }

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
