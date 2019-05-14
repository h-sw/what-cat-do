package com.team_project2.hans.whatcatdo.controller;

import android.util.Log;
import java.util.ArrayList;
import com.team_project2.hans.whatcatdo.tensorflow.Classifier;

import java.util.Collections;
import java.util.List;

class Classify {
    //happy=1, angry=2, sad=3, curious=4, ignore=5, scared=6, sleepy=7, sad=8
    ArrayList<Integer> image = new ArrayList<>();//이미지의 결과
    ArrayList<Integer> result = new ArrayList<>();//결과 리스트
    int accuracy;//정확도

    List<Classifier.Recognition> results;
    public Classify(List<Classifier.Recognition> results) {
        this.results = results;

        for(Classifier.Recognition result : results){
            Log.d("SSS",Float.toString(result.getConfidence()));
        }
    }

    void classify(){
        for(int i=0;i<18;i++){
            result.add(image.get(i),+1);//result리스트에 값을 저장해준다
        }

        Collections.sort(result);//result값을 소트해준다

        for(int i=0; i<8; i++){
                    int cnt=0;
                    if(accuracy>70){//정확도가 70이 넘어 갈때
                        //image의 값을 출력한다
                        //Log.d("T",Integer.toString(image[i]));
                        cnt++;
                    }
                    if(cnt==3){//cnt가 3이 되면 탈출
                        break;
            }
        }

    }


}
