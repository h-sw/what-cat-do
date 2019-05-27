package com.team_project2.hans.whatcatdo.tensorflow;

import android.annotation.SuppressLint;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;

import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 *
 * tflite파일을 읽고, 이미지를 추론하는 클래스 입니다.
 */

public class TensorFlowImageClassifier implements Classifier {
    private static final String TAG                = "TENSORFLOW IMAGE CLASSIFIER";

    private static final int    MAX_RESULTS        = 3;      // Max cont of result
    private static final int    BATCH_SIZE         = 1;      // batch size : Google Inception V3 is 1
    private static final int    PIXEL_SIZE         = 3;      // pixel size : Google Inception V3 is 3
    private static final float  THRESHOLD          = 0.1f;   //
    private static final int    NumBytesPerChannel = 4;      // a 32bit float value requires 4 bytes

    /*tensorflow*/
    private Interpreter interpreter;
    private int inputSize;
    private List<String> labelList;

    private static TensorFlowImageClassifier tensorFlowImageClassifier = null;
    private static TensorFlowImageClassifier tensorFlowCatFinder = null;

    private TensorFlowImageClassifier(AssetManager assetManager,
                                     String modelPath,
                                     String labelPath,
                                     int inputSize) throws IOException {
        this.interpreter = new Interpreter(this.loadModelFile(assetManager, modelPath), new Interpreter.Options());
        this.labelList = this.loadLabelList(assetManager, labelPath);
        this.inputSize = inputSize;
    }

    public static void createCatFinder(AssetManager assetManager,
                                String modelPath,
                                String labelPath,
                                int inputSize)throws IOException{

        tensorFlowCatFinder = create(assetManager,modelPath,labelPath,inputSize);
    }

    public static void createEmotionClassifier(AssetManager assetManager,
                                                              String modelPath,
                                                              String labelPath,
                                                              int inputSize)throws IOException{
        tensorFlowImageClassifier = create(assetManager,modelPath,labelPath,inputSize);
    }


    public static TensorFlowImageClassifier create(AssetManager assetManager,
                             String modelPath,
                             String labelPath,
                             int inputSize) throws IOException {
        return new TensorFlowImageClassifier(assetManager,modelPath,labelPath,inputSize);
    }

    public static TensorFlowImageClassifier getTensorFlowClassifier(){
        return tensorFlowImageClassifier;
    }

    public static TensorFlowImageClassifier getCatFinder(){
        return tensorFlowCatFinder;
    }

    @Override
    public List<Recognition> recognizeImage(Bitmap bitmap) {
        ByteBuffer byteBuffer = convertBitmapToByteBuffer(bitmap);
        float[][] result = new float[1][labelList.size()];
        interpreter.run(byteBuffer, result);

        return getSortedResult(result);
    }

    @Override
    public void close() {
        interpreter.close();
        interpreter = null;
    }

    private MappedByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private List<String> loadLabelList(AssetManager assetManager, String labelPath) throws IOException {
        List<String> labelList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(labelPath)));
        String line;
        while ((line = reader.readLine()) != null) {
            labelList.add(line);
        }
        reader.close();
        return labelList;
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(BATCH_SIZE * inputSize * inputSize * PIXEL_SIZE * NumBytesPerChannel);
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[inputSize * inputSize];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int pixel = 0;
        for (int i = 0; i < inputSize; ++i) {
            for (int j = 0; j < inputSize; ++j) {
                final int val = intValues[pixel++];
                byteBuffer.putFloat( (((val >> 16) & 0xFF) - 128) / 128.0f);
                byteBuffer.putFloat( (((val >> 8) & 0xFF) - 128) / 128.0f);
                byteBuffer.putFloat( ((val & 0xFF)-128)/128.0f);
            }
        }
        return byteBuffer;
    }

    @SuppressLint("DefaultLocale")
    private List<Recognition> getSortedResult(float[][] labelProbArray) {

        PriorityQueue<Recognition> pq =
                new PriorityQueue<>(
                        MAX_RESULTS,
                        new Comparator<Recognition>() {
                            @Override
                            public int compare(Recognition lhs, Recognition rhs) {
                                return Float.compare(rhs.getConfidence(), lhs.getConfidence());
                            }
                        });
        for (int i = 0; i < labelList.size(); ++i) {
            float confidence = labelProbArray[0][i];
            if (confidence > THRESHOLD) {
                pq.add(new Recognition("" + i, labelList.size() > i ? labelList.get(i) : "unknown", confidence));
            }
        }

        final ArrayList<Recognition> recognitions = new ArrayList<>();
        int recognitionsSize = Math.min(pq.size(), MAX_RESULTS);
        for (int i = 0; i < recognitionsSize; ++i) {
            recognitions.add(pq.poll());
        }
        return recognitions;
    }
}
