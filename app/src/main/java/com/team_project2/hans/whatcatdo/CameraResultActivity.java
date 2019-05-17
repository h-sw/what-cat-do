package com.team_project2.hans.whatcatdo;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;
import com.team_project2.hans.whatcatdo.common.Common;
import com.team_project2.hans.whatcatdo.controller.BitmapConverter;
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
    private TextView text_result_camera;
    private SliderView sliderView;

    /*&#xd150;&#xc11c;&#xd50c;&#xb85c;&#xc6b0; &#xad00;&#xb828;*/
    private TensorFlowImageClassifier classifier;

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

    private int selectImage;

    private Long timestamp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result);
        getSupportActionBar().hide();

        text_result_camera = findViewById(R.id.text_result_camera);
        sliderLayout = findViewById(R.id.imageSlider);

        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL);
        sliderLayout.setScrollTimeInSec(1); //set scroll delay in seconds

        new TaskClassifier().execute();
    }

    private class TaskClassifier extends AsyncTask<Void, Void, Void> {

        ProgressDialog asyncDialog = new ProgressDialog(
                CameraResultActivity.this);

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
                convertVideoToImage();
                classifyResults = classifyImages(bitmapArrayList);
                setSliderViews();

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
            //saveFrames();
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
        String folder = Environment.getExternalStorageDirectory().toString();
        bitmapPath = new ArrayList<>();
        timestamp = System.currentTimeMillis();
        File saveFolder = new File(folder + Common.IMAGE_PATH);
        if(!saveFolder.exists()){
            saveFolder.mkdirs();
        }
        int i = 0;
        for (Bitmap b : saveBitmap){
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, Common.IMAGE_QUALITY, bytes);
            File file = new File(saveFolder,("wcd_image_"+ timestamp +"_"+i+".jpg"));
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
        TensorFlowImageClassifier tensorFlowImageClassifier = TensorFlowImageClassifier.getTensorFlowClassifier();
        for(Bitmap bitmap : bitmaps){
            bitmap = BitmapConverter.ConvertBitmap(bitmap, Common.INPUT_SIZE);
            List<Classifier.Recognition> results = tensorFlowImageClassifier.recognizeImage(bitmap);
            recognitions.add(results);
        }
        return recognitions;
    }

    private void setSliderViews() {

        for (int i = 0; i < bitmapArrayList.size(); i++) {
            sliderView = new SliderView(this);

            Bitmap bitmap = bitmapArrayList.get(i);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            byte[] byteArray = stream.toByteArray();

            Log.d(TAG,byteArray.toString());

            sliderView.setImageByte(byteArray);

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            sliderView.setDescription("setDescription " + i);
            final int finalI = i;
            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {
                    selectImage = finalI;

                    Toast.makeText(CameraResultActivity.this, "This is slider " + (finalI), Toast.LENGTH_SHORT).show();
                }
            });
            sliderLayout.addSliderView(sliderView);
        }
    }

    public void evaluateClassifiedResult(ArrayList<List<Classifier.Recognition>> classifyResults){
        //ArrayList<>


    }



}
