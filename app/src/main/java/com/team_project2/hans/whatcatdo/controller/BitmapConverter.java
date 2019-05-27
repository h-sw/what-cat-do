package com.team_project2.hans.whatcatdo.controller;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

public class BitmapConverter {

    /**
     * ImageView에 저장된 이미지를 Bitmap으로 반환합니다.
     * */
    public static Bitmap ImageViewToBitmap(ImageView imageView, int INPUT_SIZE){
        BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();
        return Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
    }

    /**
     * 비트맵을 Inception V3에서 사용하는
     * 299 * 299 크기의 비트맵으로 변환하여 반환합니다.
     * */
    public static Bitmap ConvertBitmap(Bitmap bitmap, int INPUT_SIZE){
        return Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);
    }
}
