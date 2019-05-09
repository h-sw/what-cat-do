package com.team_project2.hans.whatcatdo;

public class Log {
    private long timestamp;
    private String path;

    public Log(long timestamp, String path){
        this.timestamp = timestamp;
        this.path = path;
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
}
