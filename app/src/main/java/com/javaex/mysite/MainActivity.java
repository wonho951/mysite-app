package com.javaex.mysite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.javaex.vo.GuestbookVo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


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

                //글쓰기로 출장가셈
                WriteAsyncTask writeAsyncTask = new WriteAsyncTask();
                //쓰레드 개념이기 때문에 메소드 직접 시키면 안되니까 익스뀨뜨 시킴
                writeAsyncTask.execute(guestbookVo);


                /*
                //2. 서버에 전송하고
                Log.d("javaStudy", "서버전송");

                //3. 리스트 엑티비티로 전환
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);*/


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


    //2021.08.20
    //이너클래스(글쓰기용)
    public class WriteAsyncTask extends AsyncTask<GuestbookVo, Integer, String>{
        //제일처음
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        //두번째(요청보내는거)
        @Override
        protected String doInBackground(GuestbookVo... guestbookVos) {
            Log.d("javaStudy", "doInBackground()");
            Log.d("javaStudy", "Vo = " + guestbookVos[0].toString());

            //vo-->json
            Gson gson = new Gson();
            String json = gson.toJson(guestbookVos[0]);
            Log.d("javaStudy", "json -->" + json);

            //데이터 전송(json --> body)
                //1. 접속정보 먼저 씀
                //2. outputStream으로 보냄(json --> body)
            try {
                //1. 접속정보 먼저 씀
                URL url = new URL("http://221.146.110.204:8088/mysite5/api/guestbook/write2");  //url 생성

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();  //url 연결
                conn.setConnectTimeout(10000); // 10초 동안 기다린 후 응답이 없으면 종료
                conn.setRequestMethod("POST"); // 요청방식 POST
                conn.setRequestProperty("Content-Type", "application/json"); //요청시 데이터 형식 json
                conn.setRequestProperty("Accept", "application/json"); //응답시 데이터 형식 json
                conn.setDoOutput(true); //OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
                conn.setDoInput(true); //InputStream으로 서버로 부터 응답을 받겠다는 옵션.

                //2. outputStream으로 보냄(json --> body)
                OutputStream os = conn.getOutputStream();
                //데이터가 101010이런식으로 오니까 번역기 설치
                OutputStreamWriter osw = new OutputStreamWriter(os);
                //이게 빨대였나(속도?)-> 일정량이 차야 감
                BufferedWriter bw = new BufferedWriter(osw);

                bw.write(json);

                //데이터가 다 안차도 보냄
                bw.flush();

                //데이터를 받기전이기 때문에 이걸로 요청하면 안됨
                int resCode = conn.getResponseCode(); // 응답코드 200이 정상
                Log.d("javaStudy", "resCode = " + resCode);


                if(resCode == HttpURLConnection.HTTP_OK){ //정상이면

                    //리스트 액티비티로 전환
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    startActivity(intent);
                    //데이타 형식은 json으로 한다.

                }
            } catch (IOException e) {
                e.printStackTrace();
            }



            return null;
        }

        //진행상태
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        //doInBackground의 최종값을 받기 위해 사용
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }








}