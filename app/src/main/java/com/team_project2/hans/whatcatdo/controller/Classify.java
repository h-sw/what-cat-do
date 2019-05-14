package com.team_project2.hans.whatcatdo.controller;

import android.util.Log;

import com.team_project2.hans.whatcatdo.tensorflow.Classifier;

import java.util.List;

class Classify {
    //happy=1, angry=2, sad=3, curious=4, ignore=5, scared=6, sleepy=7, sad=8
    int image[] = {1,1,3,2,3,3,3,2,1,4,7,5,2,3,1,4,5,2};
    int accuracy;
    int result[] = new int[8];

    List<Classifier.Recognition> results;
    public Classify(List<Classifier.Recognition> results) {
        this.results = results;

        for(Classifier.Recognition result : results){
            Log.d("SSS",Float.toString(result.getConfidence()));
        }
    }

    void classify(){
        for(int i=0;i<18;i++){
            result[image[i]]++;
        }

        for(int i=0; i<8; i++){
            for(int j=0; j<7; j++){
                if(result[j]>result[j+1]){
                    int temp;
                    temp=result[j+1];
                    result[j+1]=result[j];
                    result[j]=temp;
                }
            }
        }

        for(int i=0; i<8; i++){
                    int cnt=0;
                    if(accuracy>70){
                        Log.d("T",Integer.toString(image[i]));
                        cnt++;
                    }
                    if(cnt==3){
                        break;
            }
        }

    }


}
