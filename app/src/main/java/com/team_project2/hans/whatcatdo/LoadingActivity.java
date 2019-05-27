package com.team_project2.hans.whatcatdo;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.team_project2.hans.whatcatdo.common.Common;
import com.team_project2.hans.whatcatdo.tensorflow.TensorFlowImageClassifier;

import java.io.IOException;

public class LoadingActivity extends AppCompatActivity {
    private static final String TAG = "LOADING ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        getSupportActionBar().hide();
        if(TensorFlowImageClassifier.getTensorFlowClassifier()==null)
            startLoading();
        else
            finish();
    }

    private void startLoading(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    TensorFlowImageClassifier.createCatFinder(getAssets(),
                                                        Common.INCEPTION_MODEL_PATH,
                                                        Common.INCEPTION_LABEL_PATH,
                                                        Common.INPUT_SIZE);
                    TensorFlowImageClassifier.createEmotionClassifier(getAssets(),
                                                        Common.EMOTION_MODEL_PATH,
                                                        Common.EMOTION_LABEL_PATH,
                                                        Common.INPUT_SIZE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
            }
        }, 2000);
    }
}
