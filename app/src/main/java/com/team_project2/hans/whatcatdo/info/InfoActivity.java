package com.team_project2.hans.whatcatdo.info;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.team_project2.hans.whatcatdo.R;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener{
    private Button[] button;
    private Fragment[] fragment;

    /*Fragment management*/
    private FragmentManager fm;
    private FragmentTransaction tran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().hide();

        button = new Button[4];
        fragment = new Fragment[3];

        for(int i = 0;i<button.length ; i++){
            int id = getResources().getIdentifier("bt"+(i+1),"id",getPackageName());
            button[i] = findViewById(id);
            button[i].setOnClickListener(this);
        }

        fragment[0] = new InfoImageFragment(); //프래그먼트 객채셍성
        fragment[1] = new InfoCameraFragment(); //프래그먼트 객채셍성
        fragment[2] = new InfoLogFragment(); //프래그먼트 객채셍성

        setFragment(fragment[0].getId()); //프래그먼트 교체
    }

    @Override
    public void onClick(View v){
        setFragment(v.getId());
    }

    public void setFragment(int id){ //프래그먼트를 교체하는 작업을 하는 메소드를 만들었습니다
        fm = getSupportFragmentManager();
        tran = fm.beginTransaction();

        switch (id){ //replace 의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
            case R.id.bt1:
                tran.replace(R.id.main_frame, fragment[0]);
                break;
            case R.id.bt2:
                tran.replace(R.id.main_frame, fragment[1]);
                break;
            case R.id.bt3:
                tran.replace(R.id.main_frame, fragment[2]);
                break;
            case R.id.bt4:
                finish();
                break;
            default :
                break;
        }
        tran.commit();
    }
}
