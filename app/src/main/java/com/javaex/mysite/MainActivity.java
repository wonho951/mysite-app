package com.javaex.mysite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.javaex.vo.GuestbookVo;


public class MainActivity extends AppCompatActivity {

    //필드
    private Button btnWrite;
    private EditText edtName;
    private EditText edtPassword;
    private EditText edtContent;
    //생성자
    //메소드 g/s
    //일반 메소드

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //저장버튼  -> xml에 있는 텍스트를 자바객체로 만드는거.
        btnWrite = (Button)findViewById(R.id.btnWrite);
        //이름정보
        edtName = (EditText)findViewById(R.id.edtName);
        //비밀번호 정보
        edtPassword = (EditText)findViewById(R.id.edtPassword);
        //컨텐츠 정보
        edtContent = (EditText)findViewById(R.id.edtContent);

        //툴바
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("방명록쓰기"); //xml에서 속성을 찾아서 처리



        //저장버튼을 클릭할 때
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("javaStudy", "저장버튼 클릭");
                //1. 방명록 데이터를 vo로 만든다
                //1-1 값을 뽑아온다.
                String name = edtName.getText().toString();
                String password = edtPassword.getText().toString();
                String content = edtContent.getText().toString();

                //1-2 값을 넣는다.
                GuestbookVo guestbookVo = new GuestbookVo(name, password, content);
                Log.d("javaStudy", "vo = " + guestbookVo.toString());

                //2. 서버에 전송하고
                Log.d("javaStudy", "서버전송");

                //3. 리스트 엑티비티로 전환
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);


            }
        });





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