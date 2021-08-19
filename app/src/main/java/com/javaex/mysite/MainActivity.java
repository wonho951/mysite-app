package com.javaex.mysite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    //필드
    //생성자
    //메소드 g/s
    //일반 메소드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //툴바
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("방명록쓰기");
    }

    //item은 클릭한애를 알려줌
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//뒤로가기 버튼 활성화

        Log.d("javaStudy", "home버튼 클릭");
        Log.d("javaStudy", "item.getItemId()-->"+item.getItemId());
        Log.d("javaStudy", "android.R.id.home-->"+android.R.id.home);

        //아이디를 알려줌
        //홈버튼이 눌리면 종료해라
        switch(item.getItemId()){
            case android.R.id.home: //R.id.home아니라 안드로이드 붙여야함
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }
}