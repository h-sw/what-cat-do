package com.team_project2.hans.whatcatdo;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

public class BitmapConverter {

    static Bitmap ImageViewToBitmap(ImageView imageView, int INPUT_SIZE){
        BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        return Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
    }

    static Bitmap ConvertBitmap(Bitmap bitmap,int INPUT_SIZE){
        return Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
    }
}
