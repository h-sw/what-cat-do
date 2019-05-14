package com.team_project2.hans.whatcatdo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.team_project2.hans.whatcatdo.database.DBLogHelper;
import com.team_project2.hans.whatcatdo.database.Emotion;
import com.team_project2.hans.whatcatdo.database.Log;
import com.team_project2.hans.whatcatdo.tensorflow.Classifier;
import com.team_project2.hans.whatcatdo.tensorflow.TensorFlowImageClassifier;

import java.util.ArrayList;
import java.util.List;

public class ImageResultActivity extends AppCompatActivity {


    private Classifier classifier;

    private Bitmap bitmap;

    private ImageView img_result;
    private TextView text_result;
    private Button btn_main;
    private EditText edit_comment;
    private Button btn_save;

    private DBLogHelper db;

    private Log log;
    private ArrayList<Emotion> emotions;


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
        btn_save = findViewById(R.id.btn_save);

        bitmap = getIntent().getParcelableExtra("bitmap");
        img_result.setImageBitmap(bitmap);


        CheckTypesTask task = new CheckTypesTask();
        task.execute();

        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ImageResultActivity.this,MainActivity.class));
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.addLog(log,emotions);
                Toast.makeText(ImageResultActivity.this, "저장에 성공하였습니다!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        db.getLogEmotion();
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(
                ImageResultActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("감정분석 중이에요~");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {

                classifyImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }


    public void classifyImage(){
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    classifyImage(bitmap);
                }
            });
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    /**
     * bitmap이미지를 인자로 받아 결과를 추론하는 클래스 입니다.
     * 이 메소드는 추론된 결과를 반환하지 않고 바로 screen에 값을 수정하도록 합니다.
     *
     * */
    public void classifyImage(Bitmap bitmap){
        classifier = TensorFlowImageClassifier.getTensorFlowClassifier();
        final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
        long timestamp = System.currentTimeMillis();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setScreen(results);
            }
        });


        log = new Log(timestamp, getIntent().getStringExtra("path"), edit_comment.getText().toString());
        emotions = new ArrayList<>();

        for(Classifier.Recognition r : results){
            Emotion emotion = new Emotion(timestamp, r.getTitle(), r.getConfidence());
            emotions.add(emotion);
        }
    }

    /**
     * 이 메소드는 bitmap이미지를 인자로 받아 추론결과를 List형태로 반환합니다.
     * */
    public List<Classifier.Recognition> getClassifiedResult(Bitmap bitmap){
        classifier = TensorFlowImageClassifier.getTensorFlowClassifier();

        return classifier.recognizeImage(bitmap);
    }

    /**
     * 추론된 결과를 화면어 어떻게 넣을지 정하는 메소드 입니다.
     * */
    public void setScreen(List<Classifier.Recognition> list){
        text_result.setText(list.get(0).getTitle());
    }
}
