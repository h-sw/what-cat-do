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

    //enum을 활용해보자 상혁아!
    enum Emotion
    {
        HAPPY, ANGRY, SAD, CURIOUS, IGNORE, SCARED, SLEEPY
    }

    ArrayList<List<Classifier.Recognition>> results;
    public Classify(ArrayList<List<Classifier.Recognition>> results) {
        this.results = results;

        //사진 한장의 감정들 : List<Classifier.Recognition>
        //사진 여러장의 감정들 : List<Classifier.Recognition>가 여러개니까 저걸 ArrayList로 둘러 쌈

        for(Classifier.Recognition result : results){
            Log.d("SSS",Float.toString(result.getConfidence()));
        }
    }

    //일단 모든 감정들을 다 불러와서 평균을 구해서 정리하자

    //정리한 후에 정확도를 따지면 될 것같은데 (빈도수?는 굳이 신경 안써도 될듯?? 이건 내 생각임)

    //알고리즘을 정리하자면 일단 전체 평균을 내서 배열에 저장하는 함수 하나.

    //저장된 배열에서 정확도 순서로 정렬하는 알고리즘 하나

    void classify(){//이 함수가 정확도 순서대로 정렬할 것 같은데 리턴값을 세개짜리 Emotion( database 패키지에 있는 java 파일 참고) 배열로 보내면 되지 않을까?
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
