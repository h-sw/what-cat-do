package com.team_project2.hans.whatcatdo;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.team_project2.hans.whatcatdo.common.Common;
import com.team_project2.hans.whatcatdo.controller.BitmapConverter;
import com.team_project2.hans.whatcatdo.controller.ResultManager;
import com.team_project2.hans.whatcatdo.database.LogDBManager;
import com.team_project2.hans.whatcatdo.database.Emotion;
import com.team_project2.hans.whatcatdo.database.Log;
import com.team_project2.hans.whatcatdo.tensorflow.Classifier;
import com.team_project2.hans.whatcatdo.tensorflow.TensorFlowImageClassifier;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CameraResultActivity extends AppCompatActivity {
    private static final String  TAG = "CAMERA RESULT ACTIVITY";

    /*layout component*/
    private SliderLayout sliderLayout;
    private TextView text_camera_result;
    private EditText edit_camera_comment;
    private Button btn_camera_save;
    private Button btn_camera_main;
    private SliderView sliderView;

    /*동영상 프레임 단위로 자르기*/
    private File videoFile;
    private Uri videoFileUri;
    private MediaMetadataRetriever mediaMetadataRetriever;
    private ArrayList<Bitmap> bitmapArrayList;
    private ArrayList<String> bitmapPath;
    private ArrayList<List<Classifier.Recognition>> classifyResults;
    private MediaPlayer mediaPlayer;
    private Bitmap bitmap;
    private Thread thread;

    /*database*/
    private String selectImage;
    private Long timestamp;
    private String comment;

    /*tensorflow classifier after work*/
    private ResultManager classify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result);
        getSupportActionBar().hide();

        text_camera_result = findViewById(R.id.text_camera_result);
        edit_camera_comment = findViewById(R.id.edit_camera_comment);
        btn_camera_save = findViewById(R.id.btn_camera_save);
        btn_camera_main = findViewById(R.id.btn_camera_main);
        sliderLayout = findViewById(R.id.imageSlider);

        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL);
        sliderLayout.setScrollTimeInSec(10); //set scroll delay in seconds

        btn_camera_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectImage==null){
                    Toast.makeText(CameraResultActivity.this, "저장할 대표 이미지를 지정 해 주세요!", Toast.LENGTH_SHORT).show();
                    return ;
                }
                comment = edit_camera_comment.getText().toString();
                saveOnDB();
                Toast.makeText(CameraResultActivity.this, "저장에 성공하였습니다!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        btn_camera_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new TaskClassifier().execute();
    }

    private class TaskClassifier extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(CameraResultActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("감정분석 중이에요~");
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                convertVideoToImage();
                classifyResults = classifyImages(bitmapArrayList);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setSliderViews();
                        classify = new ResultManager(classifyResults);
                        Emotion e = classify.getPrimaryEmotion();
                        text_camera_result.setText(e.getTitle());
                    }
                });
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

    boolean isCat(List<Classifier.Recognition> list){
        for(Classifier.Recognition r : list){
            String s = r.getTitle();
            if(s.contains("cat")||s.contains("tabby")||s.contains("kitten")||s.contains("angora")){
                return true;
            }
        }
        return false;
    }

    void convertVideoToImage(){
        if(!getIntent().getStringExtra("videoPath").isEmpty()){
            String path = getIntent().getStringExtra("videoPath");

            videoFile = new File(path);
            videoFileUri = Uri.parse(videoFile.toString());
            mediaMetadataRetriever = new MediaMetadataRetriever();
            bitmapArrayList = new ArrayList<>();
            mediaMetadataRetriever.setDataSource(videoFile.toString());
            mediaPlayer = MediaPlayer.create(getBaseContext(),videoFileUri);

            for(int i=0;i<mediaPlayer.getDuration(); i += 500){
                bitmap = mediaMetadataRetriever.getFrameAtTime(i*1000,MediaMetadataRetriever.OPTION_CLOSEST);
                bitmapArrayList.add(bitmap);
            }
            mediaMetadataRetriever.release();
            saveFrames();
        }
    }

    public void saveFrames(){
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    saveFrames(bitmapArrayList);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void saveFrames(ArrayList<Bitmap> saveBitmap) throws IOException{
        bitmapPath = new ArrayList<>();
        timestamp = System.currentTimeMillis();
        File saveFolder = new File(Common.IMAGE_PATH);
        if(!saveFolder.exists()){
            saveFolder.mkdirs();
        }
        int i = 0;
        for (Bitmap b : saveBitmap){
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, Common.IMAGE_QUALITY, bytes);
            File file = new File(saveFolder,("image_"+ timestamp +"_"+i+".jpg"));
            bitmapPath.add(file.getAbsolutePath());
            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());

            fo.flush();
            fo.close();
            i++;
        }
        thread.interrupt();
    }

    public ArrayList<List<Classifier.Recognition>> classifyImages(ArrayList<Bitmap> bitmaps){
        ArrayList<List<Classifier.Recognition>> recognitions = new ArrayList<>();
        TensorFlowImageClassifier classifier = TensorFlowImageClassifier.getTensorFlowClassifier();
        TensorFlowImageClassifier catFinder = TensorFlowImageClassifier.getCatFinder();
        boolean isCat = false;
        for(Bitmap bitmap : bitmaps){
            bitmap = BitmapConverter.ConvertBitmap(bitmap, Common.INPUT_SIZE);
            List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
            List<Classifier.Recognition> cats = catFinder.recognizeImage(bitmap);
            if(isCat(cats)) {
                isCat = true;
            }
            recognitions.add(results);
        }

        if(!isCat){
            Snackbar.make(findViewById(R.id.foo),"고양이가 맞나요?",Snackbar.LENGTH_INDEFINITE)
                    .setAction("yes", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    })
                    .setAction("no", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    })
                    .show();
        }

        return recognitions;
    }

    private void setSliderViews() {
        for(String s : bitmapPath){
            sliderView = new SliderView(this);
            sliderView.setImageUrl(s);
            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription(s);
            final String select = s;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    selectImage = select;
                    Toast.makeText(CameraResultActivity.this, "이 이미지가 선택되었습니다!.", Toast.LENGTH_SHORT).show();
                }
            });
            sliderLayout.addSliderView(sliderView);
        }
    }

    private void saveOnDB(){
        LogDBManager db = new LogDBManager(this);
        Log log = new Log(timestamp,selectImage);
        log.setComment(comment);
        ArrayList<Emotion> emotions = classify.getArrayEmotions();
        for(Emotion e : emotions){
            e.setTimeStamp(timestamp);
        }
        db.addLog(log,classify.getArrayEmotions());
    }
}
