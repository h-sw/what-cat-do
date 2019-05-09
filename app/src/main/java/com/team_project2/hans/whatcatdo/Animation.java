import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.sampleandroidinfo.R;

public class ImageAnimScaleActivity extends Activity {

    ImageView mImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_anim_scale);

        mImgView = (ImageView) findViewById(R.id.imgTranslate);
        final Animation animTransRight = AnimationUtils
                .loadAnimation(this, R.anim.anim_scale);
        final Animation animTransLeft = AnimationUtils.loadAnimation(
                this, R.anim.anim_scale_alpha);
        final Animation animTransAlpha = AnimationUtils
                .loadAnimation(this, R.anim.anim_scale_point);
        final Animation animTransTwits = AnimationUtils
                .loadAnimation(this, R.anim.anim_scale_rotate);

        Button btnRight = (Button) findViewById(R.id.btn_right);
        Button btnLeft = (Button) findViewById(R.id.btn_left);
        Button btnAlpha = (Button) findViewById(R.id.btn_alpha);
        Button btnCycle = (Button) findViewById(R.id.btn_twit);

        btnRight.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mImgView.startAnimation(animTransRight);
            }
        });

        btnLeft.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mImgView.startAnimation(animTransLeft);
            }
        });

        btnAlpha.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mImgView.startAnimation(animTransAlpha);
            }
        });

        btnCycle.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                mImgView.startAnimation(animTransTwits);
            }
        });
    }
}

