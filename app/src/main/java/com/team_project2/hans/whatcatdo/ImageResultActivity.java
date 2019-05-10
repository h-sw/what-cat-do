package com.team_project2.hans.whatcatdo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageResultActivity extends AppCompatActivity {


    private Classifier classifier;

    private Bitmap bitmap;

    private ImageView img_result;
    private TextView text_result;
    private Button btn_main;
    private EditText edit_comment;

    private DBLogHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_result);
        db = new DBLogHelper(this);
        getSupportActionBar().hide();

        img_result = findViewById(R.id.img_result);
        text_result = findViewById(R.id.text_result);
        btn_main = findViewById(R.id.btn_main);
        edit_comment = findViewById(R.id.edit_comment);

        bitmap = getIntent().getParcelableExtra("bitmap");
        img_result.setImageBitmap(bitmap);

        ClassifyImage(bitmap);


        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageResultActivity.this,MainActivity.class));
            }
        });

        db.getLogEmotion();
    }

    /**
     * bitmap이미지를 인자로 받아 결과를 추론하는 클래스 입니다.
     * 이 메소드는 추론된 결과를 반환하지 않고 바로 screen에 값을 수정하도록 합니다.
     *
     * */
    public void ClassifyImage(Bitmap bitmap){
        try {
            classifier = new TensorFlowImageClassifier(getAssets(), Common.MODEL_PATH, Common.LABEL_PATH, Common.INPUT_SIZE);
            final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
            long timestamp = System.currentTimeMillis();
            setScreen(results);
            Log log = new Log(timestamp, getIntent().getStringExtra("path"), edit_comment.getText().toString());
            ArrayList<Emotion> emotions = new ArrayList<>();

            for(Classifier.Recognition r : results){
                Emotion emotion = new Emotion(timestamp, r.getTitle(), r.getConfidence());
                emotions.add(emotion);
            }
            db.addLog(log,emotions);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 이 메소드는 bitmap이미지를 인자로 받아 추론결과를 List형태로 반환합니다.
     * */
    public List<Classifier.Recognition> getClassifiedResult(Bitmap bitmap){
        try {
            classifier = new TensorFlowImageClassifier(getAssets(), Common.MODEL_PATH, Common.LABEL_PATH, Common.INPUT_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classifier.recognizeImage(bitmap);
    }

    /**
     * 추론된 결과를 화면어 어떻게 넣을지 정하는 메소드 입니다.
     * */
    public void setScreen(List<Classifier.Recognition> list){
        text_result.setText(list.get(0).getTitle());
    }
}
