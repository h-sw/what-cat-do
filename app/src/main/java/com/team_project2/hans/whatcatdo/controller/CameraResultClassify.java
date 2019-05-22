package com.team_project2.hans.whatcatdo.controller;

import android.util.Log;
import java.util.ArrayList;

import com.team_project2.hans.whatcatdo.database.Emotion;
import com.team_project2.hans.whatcatdo.tensorflow.Classifier;

import java.util.Collections;
import java.util.List;

public class CameraResultClassify {
    static final int HAPPY    = 0;
    static final int ANGRY    = 1;
    static final int CURIOUS  = 2;
    static final int EXCITED  = 3;
    static final int IGNORE   = 4;
    static final int SLEEPY   = 5;
    static final int SCARED   = 6;

    ArrayList<List<Classifier.Recognition>> results;
    Emotion[] result = new Emotion[7];

    public CameraResultClassify(ArrayList<List<Classifier.Recognition>> results) {
        this.results = results;
    }

    public Emotion getPrimaryEmotion(){
        ArrayList<Emotion> emotions = getAllEmotions();
        result = new Emotion[7];
        for(Emotion e : result){
            e = null;
        }

        for(Emotion e : emotions){
            switch (e.getTitle()) {
                case "happy":
                    calc(e, HAPPY);
                    break;
                case "angry":
                    calc(e, ANGRY);
                    break;
                case "curious":
                    calc(e, CURIOUS);
                    break;
                case "excited":
                    calc(e, EXCITED);
                    break;
                case "ignore":
                    calc(e, IGNORE);
                    break;
                case "sleepy":
                    calc(e, SLEEPY);
                    break;
                case "scared":
                    calc(e, SCARED);
                    break;
            }
        }

        int primary = -1;
        for(int i = 0; i< result.length ; i++){
            if(result[i]!=null){
                if(primary == -1){
                    primary = i;
                }else{
                    if(result[primary].getPercent()<result[i].getPercent())
                        primary = i;
                }
            }
        }
        return result[primary];
    }

    void calc(Emotion e, int n){
        if(result[n]==null){
            result[n] = new Emotion(e.getTitle(),e.getPercent());
        }else{
            result[n].setPercent(e.getPercent());
        }
    }

    ArrayList<Emotion> getAllEmotions(){
        ArrayList<Emotion> emotions = new ArrayList<>();
        for(List<Classifier.Recognition> list : results){
            for(Classifier.Recognition recognition : list){
                emotions.add(new Emotion(recognition.getTitle(),recognition.getConfidence()));
                Log.d("SS",recognition.toString());
            }
        }
        return emotions;
    }

    public ArrayList<Emotion> getCertifiedEmotions(){
        ArrayList<Emotion> arrayList = new ArrayList<>();
        for(Emotion e : result){
            if(e!=null){
                arrayList.add(e);
            }
        }

        return arrayList;
    }
}
