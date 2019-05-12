package com.team_project2.hans.whatcatdo;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener{
    Button bt1,bt2,bt3,bt4;
    FragmentManager fm;
    FragmentTransaction tran;
    Fragment frag1;
    Fragment frag2;
    Fragment frag3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main); // 메인 엑티비티가 아니라 인포액피비티 자나요!!!!!!!!!!!!!!!!
        setContentView(R.layout.activity_info);
        getSupportActionBar().hide();
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        bt4 = findViewById(R.id.bt4);
        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        frag1 = new InfoFragment1(); //프래그먼트 객채셍성
        frag2 = new InfoFragment2(); //프래그먼트 객채셍성
        frag3 = new InfoFragment3(); //프래그먼트 객채셍성
        setFrag(0); //프래그먼트 교체
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt1:
                setFrag(0);
                break;
            case R.id.bt2:
                setFrag(1);
                break;
            case R.id.bt3:
                setFrag(2);
                break;
            case R.id.bt4:
                setFrag(3);
        }
    }
    public void setFrag(int n){    //프래그먼트를 교체하는 작업을 하는 메소드를 만들었습니다
        fm = getSupportFragmentManager();
        tran = fm.beginTransaction();
        switch (n){
            case 0:
                tran.replace(R.id.main_frame, frag1);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                break;
            case 1:
                tran.replace(R.id.main_frame, frag2);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                break;
            case 2:
                tran.replace(R.id.main_frame, frag3);  //replace의 매개변수는 (프래그먼트를 담을 영역 id, 프래그먼트 객체) 입니다.
                break;
            case 3:
                finish();
                break;
        }
        tran.commit();
    }
}
