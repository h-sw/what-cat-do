package com.team_project2.hans.whatcatdo.tensorflow;

import java.util.ArrayList;

public class ClassifyResult {
    private String title;
    private ArrayList<Float> confidence;

    public ClassifyResult(String title) {
        this.title = title;
        this.confidence = new ArrayList<>();
    }


}
