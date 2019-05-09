package com.team_project2.hans.whatcatdo;

public class Emotion {
    private long timestamp;
    private String title;
    private float percent;

    public Emotion(long timestamp, String title, float percent){
        this.timestamp = timestamp;
        this.title = title;
        this.percent = percent;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(long percent) {
        this.percent = percent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
