package com.team_project2.hans.whatcatdo.controller;

import java.util.ArrayList;

import com.team_project2.hans.whatcatdo.database.Emotion;
import com.team_project2.hans.whatcatdo.tensorflow.Classifier;

import java.util.List;

public class CameraResultClassify {//감정들을 int형태로 변환
    static final int HAPPY    = 0;
    static final int ANGRY    = 1;
    static final int CURIOUS  = 2;
    static final int EXCITED  = 3;
    static final int IGNORE   = 4;
    static final int SLEEPY   = 5;
    static final int SCARED   = 6;

    ArrayList<List<Classifier.Recognition>> src;
    Emotion[] result;

    public CameraResultClassify(ArrayList<List<Classifier.Recognition>> results) {
        this.src = results;
        init();
    }

    /**
     * 모든 사진들의 북석 결과를 Emotion Class로 생성한 뒤,
     * 하나의 ArrayList로 저장합니다.
     * */
    private ArrayList<Emotion> getAllEmotions(){
        ArrayList<Emotion> emotions = new ArrayList<>();
        for(List<Classifier.Recognition> list : src){
            for(Classifier.Recognition recognition : list){
                emotions.add(new Emotion(recognition.getTitle(),recognition.getConfidence()));
            }
        }
        return emotions;
    }

    /**
     * result 배열에 감정 수 만큼 Emotion 객체를 만들어 저장한 뒤,
     * 각 감정의 평균을 구해 저장합니다.
     * */
    public void init(){
        ArrayList<Emotion> emotions = getAllEmotions();
        this.result = new Emotion[7];

        for(Emotion e : emotions){
            switch (e.getTitle()) {
                case "happy":   group(e, HAPPY);   break;
                case "angry":   group(e, ANGRY);   break;
                case "curious": group(e, CURIOUS); break;
                case "excited": group(e, EXCITED); break;
                case "ignore":  group(e, IGNORE);  break;
                case "sleepy":  group(e, SLEEPY);  break;
                case "scared":  group(e, SCARED);  break;
            }
        }
    }


    /**
     * 최고의 정학도를 가진 감정을 반환합니다.
     * */
    public Emotion getPrimaryEmotion(){
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
        if(primary == -1)
            return null;
        return result[primary];
    }

    /**
     * result 배열이 비었으면 emotion 객체를 할당
     * result 배열이 존재하면 평균치를 저장
     * */
    void group(Emotion e, int n){
        if(result[n]==null)
            result[n] = new Emotion(e.getTitle(),e.getPercent());
        else
            result[n].setPercent(e.getPercent());
    }


    /**
     * result 배열을 ArrayList로 변환합니다.
     * */
    public ArrayList<Emotion> getArrayEmotions(){
        ArrayList<Emotion> arrayList = new ArrayList<>();
        for(Emotion e : result){
            if(e!=null){
                arrayList.add(e);
            }
        }
        return arrayList;
    }
}
