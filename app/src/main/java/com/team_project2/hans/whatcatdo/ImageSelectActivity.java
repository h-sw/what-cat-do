package com.team_project2.hans.whatcatdo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImageSelectActivity extends AppCompatActivity {
    private ImageView img_select;
    private Button btn_select;
    private Button btn_analyze_picture;

    private static final String MODEL_PATH = "inceptionv3_slim_2016.tflite";
    private static final boolean QUANT = false;
    private static final String LABEL_PATH = "mlabels.txt";
    private static final int INPUT_SIZE = 299;

    private boolean isSelect = false;

    private Bitmap img;
    private Classifier classifier;
    private Executor executor = Executors.newSingleThreadExecutor();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);

        img_select = findViewById(R.id.img_select);
        btn_select = findViewById(R.id.btn_select);
        btn_analyze_picture = findViewById(R.id.btn_analyze_picture);



        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });
        //initTensorFlowAndLoadModel();

        btn_analyze_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isSelect && img!=null){
                   BitmapDrawable bitmapDrawable = (BitmapDrawable)img_select.getDrawable();
                   Bitmap bitmap = bitmapDrawable.getBitmap();
                   bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

                   try {
                       classifier = new TensorFlowImageClassifier(
                               getAssets(),
                               MODEL_PATH,
                               LABEL_PATH,
                               INPUT_SIZE,
                               QUANT);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

                   Log.d("ddd",classifier.toString());
                   final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
                   Toast.makeText(ImageSelectActivity.this, results.toString(), Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(ImageSelectActivity.this, "이미지를 먼저 선택해 주세요!", Toast.LENGTH_SHORT).show();
               }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                try{
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    img = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    img_select.setImageBitmap(img);
                    isSelect = true;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void initTensorFlowAndLoadModel(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    classifier = new TensorFlowImageClassifier(
                            getAssets(),
                            MODEL_PATH,
                            LABEL_PATH,
                            INPUT_SIZE,
                            QUANT);
                }catch (final Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

}
