package com.team_project2.hans.whatcatdo.database;

public class Log {
    private long timestamp;
    private String path;
    private String comment;

    //Log 생성자
    public Log(long timestamp, String path){
        this.timestamp = timestamp;
        this.path = path;
        this.comment = null;
    }

    //timestamp, path, comment 각각 변수들의 set과 get 함수들을 만들어준다

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTimestamp() {
        return timestamp;
    }

    //timestamp의 set 함수가 없어서 한번 만들봤어요!! 아니면 죄송해여!!!ㅎ
    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
