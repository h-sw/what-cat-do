package com.team_project2.hans.whatcatdo;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class Classify {
    //happy=1, angry=2, sad=3, curious=4, ignore=5, scared=6, sleepy=7, sad=8
    ArrayList image = new ArrayList();//이미지의 감정을 저장해주는 리스트
    int accuracy ;//정확도 값
    ArrayList result = new ArrayList();//결과 값을 저장해주는 리스트

    List<Classifier.Recognition> results;
    public Classify(List<Classifier.Recognition> results) {
        this.results = results;

        for(Classifier.Recognition result : results){
            Log.d("SSS",Float.toString(result.getConfidence()));
        }
    }


    void classify(){
        for(int i=0;i<18;i++){
            result.add(image.get(i));
        }

        Collections.sort(result);//result값을 소트 해준다

        for(int i=0; i<8; i++){
                    int cnt=0;
                    if(accuracy>70){
                        Log.d("T",Integer.toString(result);
                        cnt++;
                    }
                    if(cnt==3){
                        break;
            }
        }

    }


}
