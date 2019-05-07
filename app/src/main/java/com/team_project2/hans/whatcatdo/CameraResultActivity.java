package com.team_project2.hans.whatcatdo;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Executor;

import static java.lang.System.currentTimeMillis;

public class CameraResultActivity extends AppCompatActivity {
    private static final String  TAG             = "CAMERA RESULT ACTIVITY";

    /*constant value*/
    private static final String  MODEL_PATH      = "google_inception_v3_cats_feel.tflite";
    private static final boolean QUANT           = true;
    private static final String  LABEL_PATH      = "labels.txt";
    private static final int     INPUT_SIZE      = 224;
    private static final String  IMAGE_PATH      = "/whatcatdo/";
    private static final int     IMAGE_QUALITY   = 100;

    /*layout component*/
    private VideoView videoView;


    /*텐서플로우 관련*/
    private TensorFlowImageClassifier classifier;

    /*동영상 프레임 단위로 자르기*/
    private File videoFile;
    private Uri videoFileUri;
    private MediaMetadataRetriever mediaMetadataRetriever;
    private ArrayList<Bitmap> bitmapArrayList;
    private MediaPlayer mediaPlayer;
    private Bitmap bitmap;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_result);
        getSupportActionBar().hide();

        videoView = findViewById(R.id.videoView);

        convertVideoToImage();
    }


    void convertVideoToImage(){
        if(!getIntent().getStringExtra("videoPath").isEmpty()){
            String path = getIntent().getStringExtra("videoPath");
            videoView.setVideoURI(Uri.parse(path));
            videoView.start();

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
        String folder = Environment.getExternalStorageDirectory().toString();
        Long id = System.currentTimeMillis();
        File saveFolder = new File(folder + IMAGE_PATH);
        if(!saveFolder.exists()){
            saveFolder.mkdirs();
        }
        int i = 1;
        for (Bitmap b : saveBitmap){
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, IMAGE_QUALITY, bytes);
            File file = new File(saveFolder,("wcd_image_"+id+"_"+i+".jpg"));

            file.createNewFile();
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(bytes.toByteArray());

            fo.flush();
            fo.close();
            i++;
        }
        thread.interrupt();
    }

}
