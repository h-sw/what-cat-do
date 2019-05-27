package com.team_project2.hans.whatcatdo.controller;

import android.os.Environment;

import com.team_project2.hans.whatcatdo.database.LogEmotion;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class DataManager {
    File dataDir;
    File rootDir;
    public DataManager(String path){

        dataDir = new File(path);
        rootDir = new File(Environment.getDataDirectory().getAbsolutePath());
    }

    private String getFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public String getFreeSpace(){
        return getFileSize(rootDir.getFreeSpace());
    }

    public String getDirSize(){
        File[] list = dataDir.listFiles();
        long dirSize = 0;
        for(File file : list){
            if(file.isFile())
                dirSize += file.length();
        }
        return getFileSize(dirSize);
    }

    public String getFileCount(){
        int count = 0;
        for(File file : dataDir.listFiles()){
            if(file.isFile()){
                count++;
            }
        }
        return count+"장";
    }

    public boolean isNull(){

        if(dataDir.listFiles()==null)
            return true;
        else
            return false;
    }

    public boolean deleteCache(ArrayList<LogEmotion>logs){
        File[] list = dataDir.listFiles();

        for(File file : list) {
            boolean isLog = false;
            for (LogEmotion emotion : logs) {
                if (file.getAbsolutePath().equals(emotion.getPath()))
                    isLog = true;
            }
            if (!isLog)
                file.delete();
        }
        return true;
    }


}
