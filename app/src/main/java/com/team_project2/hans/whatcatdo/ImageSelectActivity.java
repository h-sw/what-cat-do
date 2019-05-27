package com.team_project2.hans.whatcatdo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.team_project2.hans.whatcatdo.common.Common;
import com.team_project2.hans.whatcatdo.controller.BitmapConverter;
import com.team_project2.hans.whatcatdo.controller.RealPathManager;
import com.team_project2.hans.whatcatdo.tensorflow.Classifier;
import com.team_project2.hans.whatcatdo.tensorflow.TensorFlowImageClassifier;

import java.io.InputStream;
import java.util.List;

import static com.team_project2.hans.whatcatdo.controller.BitmapConverter.ImageViewToBitmap;

public class ImageSelectActivity extends AppCompatActivity {
    private static final String TAG = "IMAGE SELECT ACTIVITY";

    private static final int REQUEST_CODE = 1;

    /*layout component*/
    private ImageView img_select;
    private Button btn_select;
    private Button btn_analyze_picture;
    private TextView text_score;



    /*image buffer*/
    private boolean isSelect = false;
    private Bitmap img;
    private String path="";

    private String kind;


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
                openGallary();
            }
        });

        btn_analyze_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSelect){
                    if(isCat()){
                        startActivity(IntentBitmap(img_select));
                        finish();
                    }else{
                        Toast.makeText(ImageSelectActivity.this, "고양이가 맞는지 확인해주세요!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    /**
     * 고양이 인지 아닌지 판별하는 메소드 입니다.
     * 우선 Tensorflow로 CatFinder 모델을 로딩해 분석을 진행하고
     * 결과값으로 isCat(result)을 리턴합니다.
     * */
    boolean isCat(){
        TensorFlowImageClassifier classifier = TensorFlowImageClassifier.getCatFinder();
        Bitmap bitmap = BitmapConverter.ImageViewToBitmap(img_select,Common.INPUT_SIZE);
        List<Classifier.Recognition> result = classifier.recognizeImage(bitmap);
        Log.d(TAG,result.toString());

        return isCat(result);
    }

    boolean isCat(List<Classifier.Recognition> list){
        for(Classifier.Recognition r : list){
            String s = r.getTitle();
            if(s.contains("cat")||s.contains("tabby")||s.contains("kitten")||s.contains("angora")){
                kind = s;
                return true;
            }
        }
        return false;
    }

    /**
     * 비트맵을 인자로 가지는 Intent를 생성하는 메소드.
     * */
    Intent IntentBitmap(ImageView imageView){
        Intent intent = new Intent(ImageSelectActivity.this,ImageResultActivity.class);
        Bitmap bitmap = ImageViewToBitmap(imageView, Common.INPUT_SIZE);
        intent.putExtra("bitmap",bitmap);
        intent.putExtra("path",path);
        intent.putExtra("kind",kind);
        return intent;
    }

    /**
     *갤러리를 여는 메소드 입니다
     * */
    void openGallary(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,REQUEST_CODE);
    }

    /**
     * 갤러리에서 불러온 이미지를 screen에 저장, 출력하는 메소드.
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                try{
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());

                    path = RealPathManager.getRealPathFromURI_API19(this,data.getData());
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
