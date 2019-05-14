package com.team_project2.hans.whatcatdo.database;

public class Log {
    private long timestamp;
    private String path;
    private String comment;

    public Log(long timestamp, String path, String comment){
        this.timestamp = timestamp;
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

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getComment() {
        return comment;
    }
}
