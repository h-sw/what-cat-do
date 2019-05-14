package com.team_project2.hans.whatcatdo.database;

public class Log {
    private long timestamp;
    private String path;
    private String comment;

    public Log(long timestamp, String path){
        this.timestamp = timestamp;
        this.path = path;
        this.comment = null;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getTimestamp() {
        return timestamp;
    }


    public String getComment() {
        return comment;
    }
}
