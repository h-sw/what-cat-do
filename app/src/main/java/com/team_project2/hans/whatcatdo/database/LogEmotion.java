package com.team_project2.hans.whatcatdo.database;

import java.io.Serializable;
import java.util.ArrayList;

public class LogEmotion implements Serializable {

    public ArrayList<Emotion> getEmotions() {
        return emotions;
    }

    private ArrayList<Emotion> emotions;//감정들을 담은 list
    private long timestamp;
    private String path;
    private String comment;

    public String getComment() {
        return comment;
    }

    //logEmotion 생성자
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
            return "no data";
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
