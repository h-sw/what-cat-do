package com.team_project2.hans.whatcatdo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ImageSelectActivity extends AppCompatActivity {
    /*layout component*/
    private ImageView img_select;
    private Button btn_select;
    private Button btn_analyze_picture;
    private TextView text_score;


    /*tensorflow*/
    private static final String MODEL_PATH = "inceptionv3_slim_2016.tflite";
    private static final String LABEL_PATH = "mlabels.txt";
    private static final int INPUT_SIZE = 299;
    private static final boolean QUANT = false;
    private Classifier classifier;


    /*image buffer*/
    private boolean isSelect = false;
    private Bitmap img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_select);

        img_select = findViewById(R.id.img_select);
        btn_select = findViewById(R.id.btn_select);
        btn_analyze_picture = findViewById(R.id.btn_analyze_picture);
        text_score = findViewById(R.id.text_score);

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });

        btn_analyze_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isSelect && img!=null){
                   BitmapDrawable bitmapDrawable = (BitmapDrawable)img_select.getDrawable();
                   Bitmap bitmap = bitmapDrawable.getBitmap();
                   bitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

                   try {
                       classifier = new TensorFlowImageClassifier(getAssets(), MODEL_PATH, LABEL_PATH, INPUT_SIZE, QUANT);
                   } catch (IOException e) {
                       e.printStackTrace();
                   }

                   final List<Classifier.Recognition> results = classifier.recognizeImage(bitmap);
                   text_score.setText(results.toString());

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
}
