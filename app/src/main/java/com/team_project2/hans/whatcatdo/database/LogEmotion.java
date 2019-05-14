package com.team_project2.hans.whatcatdo.database;

import com.team_project2.hans.whatcatdo.database.Emotion;

import java.util.ArrayList;

public class LogEmotion {

    public ArrayList<Emotion> getEmotions() {
        return emotions;
    }

    private ArrayList<Emotion> emotions;
    private long timestamp;
    private String path;
    private String comment;

    public String getComment() {
        return comment;
    }

    public LogEmotion(long timestamp, String path, String comment){
        this.emotions = new ArrayList<>();
        this.timestamp =  timestamp;
        this.path = path;
        this.comment = comment;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void addEmotion(Emotion e){
        this.emotions.add(e);
    }

    public String getPrimaryEmotion(){
        if(emotions.isEmpty()){
            return "no ";
        }
        Emotion max = emotions.get(0);
        for(Emotion e : emotions){
            if(e.getPercent()>max.getPercent()){
                max = e;
            }
        }
        return max.getTitle();
    }

}
