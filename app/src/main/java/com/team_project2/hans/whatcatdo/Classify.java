package com.team_project2.hans.whatcatdo;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Classify {
    //happy=1, angry=2, sad=3, curious=4, ignore=5, scared=6, sleepy=7, sad=8
    ArrayList<Integer> image = new ArrayList();//이미지의 감정을 저장해주는 리스트
    int accuracy ;//정확도
    ArrayList<Integer> result = new ArrayList();//결과 값을 저장해주는 리스트

    List<Classifier.Recognition> results;
    public Classify(List<Classifier.Recognition> results) {
        this.results = results;

        for(Classifier.Recognition result : results) {
            Log.d("SSS", Float.toString(result.getConfidence()));
        }
    }


    public void classify(){
        for(int i=0;i<18;i++){
            result.add(image.get(i),+1);
        }

        Collections.sort(result);//result값을 소트 해준다

        for(int i=0; i<8; i++){
                    int cnt=0;
                    if(accuracy>70){
                        //result값을 출력해준다
                        //Log.d("T",Integer.toString(result);
                        cnt++;
                    }
                    if(cnt==3){
                        break;
            }
        }

    }


}
